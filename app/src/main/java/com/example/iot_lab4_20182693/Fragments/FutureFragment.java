package com.example.iot_lab4_20182693.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iot_lab4_20182693.Adaptadores.FutureAdapter;
import com.example.iot_lab4_20182693.model.FutureWeather;
import com.example.iot_lab4_20182693.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class FutureFragment extends Fragment {

    private RecyclerView recyclerView;
    private FutureAdapter adapter;
    private List<FutureWeather> futureWeatherList;
    private EditText etFutureLocation, etFutureDate;
    private Button btnFutureSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future, container, false);

        recyclerView = view.findViewById(R.id.recyclerFuture);
        etFutureLocation = view.findViewById(R.id.etFutureLocation);
        etFutureDate = view.findViewById(R.id.etFutureDate);
        btnFutureSearch = view.findViewById(R.id.btnFutureSearch);

        futureWeatherList = new ArrayList<>();
        adapter = new FutureAdapter(futureWeatherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnFutureSearch.setOnClickListener(v -> {
            String locationId = etFutureLocation.getText().toString().trim();
            String futureDate = etFutureDate.getText().toString().trim();

            if (!locationId.isEmpty() && !futureDate.isEmpty()) {
                fetchFutureWeather(locationId, futureDate);
                hideKeyboard();
            } else {
                Toast.makeText(getContext(), "Complete ambos campos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getView() != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    private void fetchFutureWeather(String locationId, String date) {
        String url = "http://api.weatherapi.com/v1/future.json?key=ec24b1c6dd8a4d528c1205500250305&q=" + locationId + "&dt=" + date;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    futureWeatherList.clear();
                    parseFutureWeather(response);
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    private void parseFutureWeather(JSONObject response) {
        try {
            JSONObject forecast = response.getJSONObject("forecast");
            JSONArray hourArray = forecast.getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour");

            for (int i = 0; i < hourArray.length(); i++) {
                JSONObject hour = hourArray.getJSONObject(i);
                String time = hour.getString("time");
                String temp = hour.getString("temp_c") + " °C";
                String condition = hour.getJSONObject("condition").getString("text");

                futureWeatherList.add(new FutureWeather(time, temp, condition));
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
