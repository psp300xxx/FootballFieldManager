package com.example.footballfieldmanager.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.fragments.FootballFieldRentFragment.OnListFragmentInteractionListener;
import com.example.footballfieldmanager.fragments.dummy.DummyContent.DummyItem;
import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.FootballFieldRent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFootballFieldRentRecyclerViewAdapter extends RecyclerView.Adapter<MyFootballFieldRentRecyclerViewAdapter.ViewHolder> {

    private final List<FootballFieldRent> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFootballFieldRentRecyclerViewAdapter(List<FootballFieldRent> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_footballfieldrent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId()+"");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Date date = mValues.get(position).getDate();
        holder.mDateView.setText(dateFormat.format(date));
        List<String> list = mValues.get(position).getUserIdList();
        ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<String>(holder.mView.getContext(), R.layout.user_list_layout, list);

        holder.mListView.setAdapter(newArrayAdapter);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mDateView;
        public final TextView mFieldId;
        public final ListView mListView;
        public FootballFieldRent mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id_rent);
            mDateView = (TextView) view.findViewById(R.id.date_rent);
            mFieldId =(TextView)view.findViewById(R.id.field_id_rent);
            mListView = (ListView)view.findViewById(R.id.users_list_view);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'";
        }
    }
}
