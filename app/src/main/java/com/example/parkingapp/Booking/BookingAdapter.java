package com.example.parkingapp.Booking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.R;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder>{
    Context context;
    List<Booking> bookings;
    int hora=7;
    int position1;
    String[] horasOcupadas;

    private View.OnClickListener listener;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hora_tv.setText(bookings.get(position).getInitialTime());
        holder.estado_tv.setText(bookings.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hora_tv,estado_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hora_tv = itemView.findViewById(R.id.hora_tv);
            estado_tv = itemView.findViewById(R.id.status_tv);


        }


    }


}
