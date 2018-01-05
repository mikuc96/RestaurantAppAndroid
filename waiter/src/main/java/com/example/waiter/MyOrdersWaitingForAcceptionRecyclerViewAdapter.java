package com.example.waiter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.waiter.OrdersWaitingForAcceptionFragment.OnListFragmentInteractionListener;
import com.example.waiter.dummy.OrderContent;
import com.example.waiter.dummy.OrderContent.SingleOrder;

import java.util.List;
import java.util.Random;


public class MyOrdersWaitingForAcceptionRecyclerViewAdapter
        extends RecyclerView.Adapter<MyOrdersWaitingForAcceptionRecyclerViewAdapter.ViewHolderForWaitingForAcceptionOrders> {

    private final List<SingleOrder> mOrderListToAccept;
    private final OnListFragmentInteractionListener mListener;

    public MyOrdersWaitingForAcceptionRecyclerViewAdapter(List<SingleOrder> items, OnListFragmentInteractionListener listener) {
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
    public void onBindViewHolder(final ViewHolderForWaitingForAcceptionOrders holder, int position) {
        holder.mItem = mOrderListToAccept.get(position);
        holder.mNameView.setText(String.valueOf(mOrderListToAccept.get(position).meal_name));
        holder.mTimerView.setText(String.valueOf(mOrderListToAccept.get(position).timer));

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

        holder.mRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                Log.d("thien.van","on Click onBindViewHolder");
                mOrderListToAccept.remove(newPosition);
//                OrderContent.removeElementFromOrderList(); // widac  samo mOrderListToAccept.remove(newPosition) wystarcza
            }
        });

        holder.mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                OrderContent.moveElementToProcessingList(newPosition);
                Log.d("thien.van","on Click onBindViewHolder");
//                mOrderListToAccept.remove(newPosition);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderListToAccept.size();
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






