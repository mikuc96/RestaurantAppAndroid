package com.example.waiter;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waiter.OrdersInPreparingFragment.OnFragmentOfProcessingOrdersInteractionListener;
import com.example.waiter.OrderData.OrderContent.SingleOrder;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapterOrdersInPreparing
        extends RecyclerView.Adapter<RecyclerViewAdapterOrdersInPreparing.ViewHolderInProcessing> {

    private final List<SingleOrder> mOrderListInPreparing;
    private final OnFragmentOfProcessingOrdersInteractionListener mListener;
    private final int NOT_STARTED = Color.BLUE;
    private final int PREPARING_STATE = Color.YELLOW;
    private final int FINISHED_STATE = Color.GREEN;
    private final int REJECTED_STATE = Color.RED;

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
        holder.mTimerView.setText(formatTime(mOrderListInPreparing.get(position).timer));
        holder.mTableIdView.setText("Stolik: " + String.valueOf(mOrderListInPreparing.get(position).table_id));

        holder.mImageView.setColorFilter(NOT_STARTED, PorterDuff.Mode.SRC);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
                    mOrderListInPreparing.get(newPosition).is_just_served = true;
                    notifyItemChanged(newPosition);
                }
            }
        });
    }

    private String formatTime(Integer t){
        Integer hours = t / 3600;
        Integer minutes = (t % 3600) / 60;
        Integer seconds = t % 60;
        Integer[] args = {hours, minutes, seconds};
        MessageFormat fmt = new MessageFormat("{0}:{1}:{2}");
        return fmt.format(args);
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
