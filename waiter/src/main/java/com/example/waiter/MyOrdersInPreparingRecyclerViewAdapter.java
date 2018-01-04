package com.example.waiter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waiter.OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener;
import com.example.waiter.dummy.OrderContent.SingleOrder;

import java.util.List;

public class MyOrdersInPreparingRecyclerViewAdapter extends RecyclerView.Adapter<MyOrdersInPreparingRecyclerViewAdapter.ViewHolderInProcessing> {

    private final List<SingleOrder> mValues;
    private final OnFragmentOfProcessingOrdersInteractionListener mListener;


    public MyOrdersInPreparingRecyclerViewAdapter(List<SingleOrder> items, OnFragmentOfProcessingOrdersInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolderInProcessing onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orderswaitingforacception, parent, false);
        return new ViewHolderInProcessing(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderInProcessing holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).meal_name));
        holder.mContentView.setText(String.valueOf(mValues.get(position).timer));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentInteraction(holder.mItem);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolderInProcessing extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public SingleOrder mItem;

        public ViewHolderInProcessing(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.meal_name);
            mContentView = (TextView) view.findViewById(R.id.timer);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
