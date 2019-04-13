package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.OrderBookingModel;
import com.swp.culturehomestay.models.ReservationModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemBooingView = inflater.inflate(R.layout.item_booking, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemBooingView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookingAdapter.ViewHolder viewHolder, int position) {
        ReservationModel order = mOrders.get(position);
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        TextView homestayName = viewHolder.homestayName;
        TextView totalPayment = viewHolder.totalPayment;
        TextView roomname = viewHolder.roomname;
        TextView dateCheckIn = viewHolder.dateCheckIn;
        TextView dateCheckOut = viewHolder.dateCheckOut;
        homestayName.setText(order.getHomestayId());
        totalPayment.setText(order.getPaymentWant().getCurrency());
        if(order.getNumRoom()!= null){
            roomname.setText(order.getNumRoom().toString());
        }else{
            roomname.setText("");
        }
        dateCheckIn.setText(sf.format(new Date(order.getDStart())));
        dateCheckOut.setText(sf.format(new Date(order.getDEnd())));

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView homestayName;
        private TextView totalPayment;
        private TextView roomname;
        private TextView dateCheckIn;
        private TextView dateCheckOut;
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            homestayName = (TextView) itemView.findViewById(R.id.homestayName);
            totalPayment = (TextView) itemView.findViewById(R.id.totalPayment);
            roomname = (TextView) itemView.findViewById(R.id.roomName);
            dateCheckIn = (TextView) itemView.findViewById(R.id.date_checkin);
            dateCheckOut = (TextView) itemView.findViewById(R.id.date_checkout);
        }
    }
    private List<ReservationModel> mOrders;

    // Pass in the contact array into the constructor
    public BookingAdapter(List<ReservationModel> orders) {
        mOrders= orders;
    }
}
