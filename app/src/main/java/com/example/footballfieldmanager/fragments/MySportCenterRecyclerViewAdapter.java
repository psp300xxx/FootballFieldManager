package com.example.footballfieldmanager.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.fragments.SportCenterFragment.OnListFragmentInteractionListener;
import com.example.footballfieldmanager.fragments.dummy.DummyContent.DummyItem;
import com.example.footballfieldmanager.model.SportCenter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySportCenterRecyclerViewAdapter extends RecyclerView.Adapter<MySportCenterRecyclerViewAdapter.ViewHolder> {

    private final List<SportCenter> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySportCenterRecyclerViewAdapter(List<SportCenter> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sportcenter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(""+mValues.get(position).getId());
        holder.mCenterNameView.setText(mValues.get(position).getName());
        holder.mCityView.setText(mValues.get(position).getLocation());

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
        public final TextView mCenterNameView;
        public final TextView mCityView;
        public SportCenter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mCenterNameView = (TextView) view.findViewById(R.id.center_name_text_view);
            mCityView = (TextView) view.findViewById(R.id.city_text_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCenterNameView.getText() + "'";
        }
    }
}
