package com.example.waiter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.waiter.OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener;
import com.example.waiter.OrderData.OrderContent;
import com.example.waiter.OrderData.OrderContent.SingleOrder;

import java.util.List;


public class RecyclerViewAdapterOrdersWaitingForAcception
        extends RecyclerView.Adapter<RecyclerViewAdapterOrdersWaitingForAcception.ViewHolderForWaitingForAcceptionOrders> {

    private final List<SingleOrder> mOrderListToAccept;
    private final OnListFragmentInteractionListener mListener;
    private final String START_MEAL_PREPARING = "START_PREPARING";
    private final String CANCEL_ORDER = "CANCEL";
    private final String ORDER_PROGRESS = "PROGRESS";

    public RecyclerViewAdapterOrdersWaitingForAcception(List<SingleOrder> items, OnListFragmentInteractionListener listener) {
        mOrderListToAccept = items;
        mListener = listener;
    }


    @Override
    public ViewHolderForWaitingForAcceptionOrders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orders_waiting_for_acception, parent, false);
        return new ViewHolderForWaitingForAcceptionOrders(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderForWaitingForAcceptionOrders holder, final int position) {
        holder.mItem = mOrderListToAccept.get(position);
        holder.mNameView.setText(String.valueOf(mOrderListToAccept.get(position).meal_name));
        holder.mTimerView.setText(String.valueOf(mOrderListToAccept.get(position).timer));
        Log.d("onBindViewHolder prep ", "waiting for");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                removeFromList(newPosition);
            }
        });

        holder.mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                SendAcceptedOrder(newPosition);
                OrderContent.moveElementToProcessingList(newPosition);
                removeFromList(newPosition);
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderListToAccept.size();
    }


    private void removeFromList(Integer position){
        mOrderListToAccept.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mOrderListToAccept.size());
    }


    private void SendAcceptedOrder(int listPosition)
    {
        KitchenSockets sc=new KitchenSockets();
        SingleOrder singleOrd = OrderContent.currentOrderList.get(listPosition);
        sc.sendToKitchen(singleOrd, START_MEAL_PREPARING);
    }


    public class ViewHolderForWaitingForAcceptionOrders extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public TextView mTimerView;
        public Button mAcceptBtn;
        public Button mRejectBtn;
        public SingleOrder mItem;

        public ViewHolderForWaitingForAcceptionOrders(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.meal_name);
            mTimerView = (TextView) view.findViewById(R.id.timer);
            mAcceptBtn = (Button) view.findViewById(R.id.accept_btn);
            mRejectBtn = (Button) view.findViewById(R.id.reject_btn);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTimerView.getText() + "'";
        }
    }
}






