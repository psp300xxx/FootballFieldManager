package com.example.footballfieldmanager.fragments;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.fragments.BookableTimeFragment.OnListFragmentInteractionListener;
import com.example.footballfieldmanager.fragments.dummy.DummyContent.DummyItem;
import com.example.footballfieldmanager.model.BookableTime;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBookableTimeRecyclerViewAdapter extends RecyclerView.Adapter<MyBookableTimeRecyclerViewAdapter.ViewHolder> {

    private final List<BookableTime> mValues;
    private final List<Boolean> mChecked;
    private final OnListFragmentInteractionListener mListener;

    public MyBookableTimeRecyclerViewAdapter(List<BookableTime> items, OnListFragmentInteractionListener listener, @Nullable List<Boolean> isChecked) {
        mValues = items;
        mChecked = isChecked;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bookabletime, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(""+mValues.get(position).getHour());
        if(mChecked!=null){
            holder.mSwitch.setChecked( mChecked.get(position) );
        }
        else{
            holder.mSwitch.setChecked(false);
        }
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
        public final TextView mContentView;
        public Switch mSwitch;
        public BookableTime mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mSwitch =(Switch)view.findViewById(R.id.is_available);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
