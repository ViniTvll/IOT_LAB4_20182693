package com.example.iot_lab4_20182693.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.iot_lab4_20182693.model.FutureWeather;
import com.example.iot_lab4_20182693.R;

import java.util.List;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.FutureViewHolder> {

    private final List<FutureWeather> futureWeatherList;

    public FutureAdapter(List<FutureWeather> futureWeatherList) {
        this.futureWeatherList = futureWeatherList;
    }

    @NonNull
    @Override
    public FutureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_future, parent, false);
        return new FutureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureViewHolder holder, int position) {
        FutureWeather weather = futureWeatherList.get(position);
        holder.tvTime.setText("Hora: " + weather.getTime());
        holder.tvTemperature.setText("Temperatura: " + weather.getTemperature());
        holder.tvCondition.setText("Condición: " + weather.getCondition());
    }

    @Override
    public int getItemCount() {
        return futureWeatherList.size();
    }

    // ViewHolder interno
    static class FutureViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvTemperature, tvCondition;

        FutureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvCondition = itemView.findViewById(R.id.tvCondition);
        }
    }
}
