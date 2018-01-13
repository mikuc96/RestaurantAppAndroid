package com.example.kitchen;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kitchen.MealsProcessingFragment.OnListFragmentInteractionListener;
import com.example.kitchen.dummy.OrderContent;
import com.example.kitchen.dummy.OrderContent.SingleOrder;

import java.text.MessageFormat;
import java.util.List;


public class MealsProcessingRecyclerViewAdapter extends RecyclerView.Adapter<MealsProcessingRecyclerViewAdapter.OrderViewHolder> {

    private final List<SingleOrder> mOrderList;
    private final OnListFragmentInteractionListener mListener;
    private final int TIME_UNIT = 300;

    public MealsProcessingRecyclerViewAdapter(List<SingleOrder> items, OnListFragmentInteractionListener listener) {
        mOrderList = items;
        mListener = listener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meals_proceesing_dashboard, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        holder.mItem = mOrderList.get(position);
        holder.mMealName.setText(mOrderList.get(position).meal_name);
        holder.mTimer.setText(formatTime(mOrderList.get(position).timer));
//        if holder.mItem.is_prepared
        // color.set()
        holder.mBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                removeFromList(newPosition);
                //send to waiter
            }
        });

        holder.less_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                updateTimer(newPosition, -TIME_UNIT);
            }
        });


        holder.more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                updateTimer(newPosition, TIME_UNIT);
            }
        });

        holder.start_preparing_chbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                SingleOrder singleOrd = getListElement(newPosition);
                singleOrd.is_prepared = Boolean.FALSE;
                //timer start()
                notifyItemChanged(newPosition);
            }
        });

        holder.finish_preparing_chbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                SingleOrder singleOrd = getListElement(newPosition);
                singleOrd.is_prepared = Boolean.TRUE;
                //timer stop()
                notifyItemChanged(newPosition);
                //notify waiter
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

    private void removeFromList(Integer position){
        mOrderList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mOrderList.size());
        Log.d("asaasasssssssssssss", String.valueOf(mOrderList.size()));
    }

    private SingleOrder getListElement(Integer listPosition){
        return OrderContent.processingOrderList.get(listPosition);
    }

    private void updateTimer(Integer listPosition, Integer val){
        SingleOrder singleOrd = getListElement(listPosition);
        singleOrd.timer += val;
        notifyItemChanged(listPosition);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public final View mBackground;
        public final TextView mMealName;
        public final TextView mTimer;
        public SingleOrder mItem;
        public Button more_btn;
        public Button less_btn;
        public Button reject_btn;
        public CheckBox start_preparing_chbox;
        public CheckBox finish_preparing_chbox;

        public OrderViewHolder(View view) {
            super(view);
            mBackground = view;
            mMealName = (TextView) view.findViewById(R.id.meal_name);
            mTimer = (TextView) view.findViewById(R.id.timer);
            reject_btn = (Button) view.findViewById(R.id.reject_btn);
            less_btn = (Button) view.findViewById(R.id.less_btn);
            more_btn = (Button) view.findViewById(R.id.more_btn);
            start_preparing_chbox = (CheckBox) view.findViewById(R.id.start_preparing);
            finish_preparing_chbox = (CheckBox) view.findViewById(R.id.finish_preparing);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimer.getText() + "'";
        }
    }
}
