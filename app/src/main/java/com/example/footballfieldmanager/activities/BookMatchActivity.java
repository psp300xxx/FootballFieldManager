package com.example.footballfieldmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.R;


import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.rent.LDMRent;
import com.example.footballfieldmanager.controller.rent.RentDelegate;
import com.example.footballfieldmanager.controller.rent.RentIF;
import com.example.footballfieldmanager.controller.sport_center.LDMSportCenter;
import com.example.footballfieldmanager.controller.sport_center.SportCenterDelegate;
import com.example.footballfieldmanager.controller.sport_center.SportCenterIF;
import com.example.footballfieldmanager.controller.weather.OpenWeather;
import com.example.footballfieldmanager.controller.weather.WeatherDelegate;
import com.example.footballfieldmanager.controller.weather.WeatherIF;
import com.example.footballfieldmanager.fragments.BookableTimeFragment;
import com.example.footballfieldmanager.fragments.StringFragment;
import com.example.footballfieldmanager.fragments.WeatherFragment;
import com.example.footballfieldmanager.model.BookableTime;
import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.SportCenter;
import com.example.footballfieldmanager.model.WeatherData;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class BookMatchActivity extends AppCompatActivity implements BookableTimeFragment.OnListFragmentInteractionListener, StringFragment.OnListFragmentInteractionListener, SportCenterDelegate, RentDelegate, CalendarView.OnDateChangeListener, WeatherFragment.OnFragmentInteractionListener, WeatherDelegate {

    private SportCenter sportCenter =  null;
    private CalendarView calendarView = null;
    private BookableTimeFragment bookableTimeFragment = null;
    private StringFragment bookableFieldsFragment = null;
    private TextView selectedDateTextView = null;
    private List<FootballFieldRent> rents;
    private RentIF rentIF = new LDMRent();
    private SportCenterIF sportCenterIF = new LDMSportCenter();
    private Button bookFieldButton = null;
    private Date currentDate = null ;
    private int fieldIndex;
    private BookableTime selectedItem;
    private WeatherFragment weatherFragment;
    private WeatherIF weatherIF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_match);
        sportCenter = (SportCenter) getIntent().getExtras().getSerializable("center");
        calendarView = (CalendarView)findViewById(R.id.calendar_view);
        selectedDateTextView = (TextView) findViewById(R.id.day_booked);
        bookableTimeFragment = (BookableTimeFragment) getSupportFragmentManager().findFragmentById(R.id.bookable_center_fragment);
        bookableFieldsFragment = (StringFragment) getSupportFragmentManager().findFragmentById(R.id.select_field_fragment);
        bookFieldButton = (Button) findViewById(R.id.book_day_button);
        changeSecondPartVisibility(View.GONE);
        sportCenterIF.setDelegate(this);
        RequestQueue queue = RequestQueueSingleton.getInstance(this).getQueue();
        sportCenterIF.searchFieldsForCenter((int)sportCenter.getId(), queue);
        currentDate = new Date(calendarView.getDate());
        weatherFragment = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.weather_fragment);
        weatherFragment.getView().setVisibility(View.GONE);
        calendarView.setOnDateChangeListener(this);
        weatherIF = new OpenWeather();
        weatherIF.setDelegate(this);
        bookFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FootballFieldRent.Builder builder = FootballFieldRent.newBuilder();
                builder.setFieldId(fieldIndex).setId(new Random().nextInt());
                builder.setDate(currentDate).addUserId(SharedPrefencesManager.getInstance(BookMatchActivity.this).getString("user_id"));
                FootballFieldRent rent = builder.build();
                String token = SharedPrefencesManager.getInstance(BookMatchActivity.this).getString("token");
                RequestQueue queue = RequestQueueSingleton.getInstance(BookMatchActivity.this).getQueue();
                rentIF.bookField(rent, (int) sportCenter.getId(), token, queue);
            }
        });
        weatherIF.searchWeatherData(sportCenter.getLatitude(), sportCenter.getLongitude(), queue);
    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        currentDate = new Date(year-1900, month, dayOfMonth);
        if(rents==null){
            return;
        }
        updateViews(currentDate);
    }

    @Override
    public void onListFragmentInteraction(BookableTime item) {
        selectedItem = item;
        bookFieldButton.setVisibility(View.VISIBLE);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, selectedItem.getHour());
        currentDate =  new Date(calendar.getTime().getTime());
        String formatDate = "YYYY-MM-dd HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        String dateString = dateFormat.format(currentDate);
        selectedDateTextView.setText(dateString);
    }

    @Override
    public void fieldbookedCorrectly() {
        RequestQueue queue = RequestQueueSingleton.getInstance(this).getQueue();
        rentIF.searchFutureRents(fieldIndex, queue);
    }


    public void onListFragmentInteraction(Uri item){


    }


    @Override
    public void onListFragmentInteraction(String item){
        String beginString = item.substring(5);
        int fieldIndexInString = beginString.indexOf(" ");
        fieldIndex = Integer.parseInt(beginString.substring(0, fieldIndexInString));

        rentIF = new LDMRent();
        rentIF.setDelegate(this);
        RequestQueue queue = RequestQueueSingleton.getInstance(this).getQueue();
        rentIF.searchFutureRents(fieldIndex, queue);

    }

    @Override
    public void sportCenterDownloaded(List<SportCenter> centers) {

    }

    @Override
    public void sportCenterDownloadFailed(Exception exc) {
        Toast.makeText(this, getResources().getText(R.string.football_field_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void footballFieldDownloaded(List<FootballField> footballFields) {
        List<String> stringedFields = new ArrayList<>();
        for (FootballField field : footballFields){
            stringedFields.add(field.toString());
        }
        bookableFieldsFragment.setList(stringedFields, this);
    }

    private void changeSecondPartVisibility(int newVisibility){
//        bookFieldButton.setVisibility(newVisibility);
        selectedDateTextView.setVisibility(newVisibility);
        bookableTimeFragment.getView().setVisibility(newVisibility);
    }


    private void updateViews(Date date){
        BookableTime bookableTimeList [] = BookableTime.allTimes();
        List<Boolean> list = new ArrayList<>();
        List<Integer> busyTimes = new ArrayList<>();
        for ( FootballFieldRent i : rents ){
            if ( isSameDay(date, i.getDate()) ){
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(i.getDate());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                busyTimes.add(hour);
            }
        }
        for(int i =0 ; i<bookableTimeList.length ; i++){
            list.add( busyTimes.contains(bookableTimeList[i].getHour()) );
        }
        bookableTimeFragment.setTimes(Arrays.asList(bookableTimeList), list);
        changeSecondPartVisibility(View.VISIBLE);
    }

    private boolean isSameDay(Date a, Date b){
        Calendar calendarA = new GregorianCalendar();
        calendarA.setTime(a);
        int currentYear = calendarA.get(Calendar.YEAR);
        int currentMonth = calendarA.get(Calendar.MONTH);
        int currentDay = calendarA.get(Calendar.DAY_OF_MONTH);

        Calendar calendarB = new GregorianCalendar();
        calendarB.setTime(b);
        int otherYear = calendarB.get(Calendar.YEAR);
        int otherMonth = calendarB.get(Calendar.MONTH);
        int otherDay = calendarB.get(Calendar.DAY_OF_MONTH);

        return currentDay == otherDay && currentMonth == otherMonth && otherYear == currentYear;
    }

    @Override
    public void futureRentDownloaded(List<FootballFieldRent> list) {
        rents = list;
        updateViews(currentDate);
    }

    @Override
    public void downloadFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void weatherDataDownloaded(List<WeatherData> weatherDataList) {
        weatherFragment.setHeaderText(weatherDataList.get(0).getWeather());
        weatherFragment.setList(weatherDataList);
        weatherFragment.getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void operationFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
