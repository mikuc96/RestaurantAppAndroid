package com.example.waiter;


import android.support.v7.widget.RecyclerView;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.waiter.OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener;
import com.example.waiter.dummy.OrderContent.SingleOrder;

import java.util.List;

public class MyOrdersInPreparingRecyclerViewAdapter
        extends RecyclerView.Adapter<MyOrdersInPreparingRecyclerViewAdapter.ViewHolderInProcessing> {

    private final List<SingleOrder> mOrderListInPreparing;
    private final OnFragmentOfProcessingOrdersInteractionListener mListener;


    public MyOrdersInPreparingRecyclerViewAdapter(List<SingleOrder> items, OnFragmentOfProcessingOrdersInteractionListener listener) {
        mOrderListInPreparing = items;
        mListener = listener;
    }

    @Override
    public ViewHolderInProcessing onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order_in_processing, parent, false);
        return new ViewHolderInProcessing(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderInProcessing holder, int position) {
        holder.mItem = mOrderListInPreparing.get(position);
        holder.mMealNameView.setText(String.valueOf(mOrderListInPreparing.get(position).meal_name));
        holder.mTimerView.setText(String.valueOf(mOrderListInPreparing.get(position).timer));
        holder.mTableIdView.setText(String.valueOf(mOrderListInPreparing.get(position).table_id));

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
        holder.mView.setBackgroundColor(R.color.colorAccent);
    }


    @Override
    public int getItemCount() {
        return mOrderListInPreparing.size();
    }

    public class ViewHolderInProcessing extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMealNameView;
        public final TextView mTimerView;
        public final TextView mTableIdView;
        public SingleOrder mItem;
        RelativeLayout rl;

        public ViewHolderInProcessing(View view) {
            super(view);
            mView = view;
            mMealNameView = (TextView) view.findViewById(R.id.meal_name);
            mTimerView = (TextView) view.findViewById(R.id.timer);
            mTableIdView = (TextView) view.findViewById(R.id.table_id);
            view.setBackgroundColor(R.color.colorPrimary);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimerView.getText() + "'";
        }
    }
}
