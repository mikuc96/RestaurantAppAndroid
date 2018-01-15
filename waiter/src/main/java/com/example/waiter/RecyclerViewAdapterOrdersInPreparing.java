package com.example.waiter;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waiter.OrderData.OrderContent;
import com.example.waiter.OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener;
import com.example.waiter.OrderData.OrderContent.SingleOrder;

import java.util.List;

public class RecyclerViewAdapterOrdersInPreparing
        extends RecyclerView.Adapter<RecyclerViewAdapterOrdersInPreparing.ViewHolderInProcessing> {

    private final List<SingleOrder> mOrderListInPreparing;
    private final OnFragmentOfProcessingOrdersInteractionListener mListener;
    private final int NOT_STARTED = Color.GRAY;
    private final int PREPARING_STATE = Color.YELLOW;
    private final int FINISHED_STATE = Color.GREEN;
    private final int REJECTED_STATE = Color.RED;
    private final int PAYING = Color.YELLOW;
    private final String ORDER_PREPARED = "PREPARED";

    public RecyclerViewAdapterOrdersInPreparing(List<SingleOrder> items, OnFragmentOfProcessingOrdersInteractionListener listener) {
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
        OrderContent.updateTimer(position);
        holder.mTimerView.setText("Czas: " + OrderContent.formatTime(mOrderListInPreparing.get(position).timer));
        holder.mTableIdView.setText("Stolik: " + String.valueOf(mOrderListInPreparing.get(position).table_id));

        updateOrderStatusImg(holder.mImageView, mOrderListInPreparing.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    holder.mImageView.setColorFilter(REJECTED_STATE, PorterDuff.Mode.SRC);
                    mListener.onFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mGivenToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    int newPosition = holder.getAdapterPosition();
                    SendOrderFinished(newPosition);
                    mOrderListInPreparing.get(newPosition).is_just_served = true;
                    mOrderListInPreparing.get(newPosition).is_prepared = false;
                    notifyItemChanged(newPosition);
                }
            }
        });
    }

    private void updateOrderStatusImg(ImageView iv, SingleOrder order){
        if(order.is_preparing)
            iv.setColorFilter(PREPARING_STATE, PorterDuff.Mode.SRC);
        else if(order.is_prepared)
            iv.setColorFilter(FINISHED_STATE, PorterDuff.Mode.SRC);
        else if(order.is_just_served)
            iv.setColorFilter(NOT_STARTED, PorterDuff.Mode.SRC);
        else if(order.is_rejected_by_kitchen)
            iv.setColorFilter(REJECTED_STATE, PorterDuff.Mode.SRC);
        else if(order.is_paying)
            iv.setColorFilter(PAYING, PorterDuff.Mode.SRC);
        else{
            iv.setColorFilter(NOT_STARTED, PorterDuff.Mode.SRC);
        }
    }


    private void SendOrderFinished(int listPosition)
    {
        ClientSockets sc=new ClientSockets();
        if(OrderContent.currentOrderList.size() > 0) {
            SingleOrder singleOrd = OrderContent.currentOrderList.get(listPosition);

            int tabId = Integer.parseInt(singleOrd.table_id);
            if (tabId > 0 && tabId <= 6)
                sc.sendToClient(singleOrd, ORDER_PREPARED);
        }
        }

    @Override
    public int getItemCount() {
        return mOrderListInPreparing.size();
    }

    public class ViewHolderInProcessing extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMealNameView;
        public final TextView mTableIdView;
        public TextView mTimerView;
        public SingleOrder mItem;
        public ImageView mImageView;
        CheckBox mGivenToClient;

        public ViewHolderInProcessing(View view) {
            super(view);
            mView = view;
            mMealNameView = (TextView) view.findViewById(R.id.meal_name);
            mTimerView = (TextView) view.findViewById(R.id.timer);
            mTableIdView = (TextView) view.findViewById(R.id.table_id);
            mImageView = (ImageView) view.findViewById(R.id.status);
            mGivenToClient= (CheckBox) view.findViewById(R.id.given_to_client);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimerView.getText() + "'";
        }
    }
}
