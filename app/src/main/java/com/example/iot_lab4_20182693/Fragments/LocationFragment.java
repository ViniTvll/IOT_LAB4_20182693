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

import com.android.volley.RequestQueue;
import com.example.iot_lab4_20182693.Adaptadores.LocationAdapter;
import com.example.iot_lab4_20182693.R;
import com.example.iot_lab4_20182693.model.Location;

import java.util.ArrayList;
import java.util.List;
import com.android.volley.Request;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationFragment extends Fragment {

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<Location> locationList;
    private EditText etLocation;
    private Button btnSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = view.findViewById(R.id.recyclerLocation);
        etLocation = view.findViewById(R.id.etLocation);
        btnSearch = view.findViewById(R.id.btnSearch);

        locationList = new ArrayList<>();
        adapter = new LocationAdapter(locationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String locationName = etLocation.getText().toString().trim();
            if (!locationName.isEmpty()) {
                fetchLocations(locationName);
                hideKeyboard();
            } else {
                Toast.makeText(getContext(), "Ingrese una ubicación", Toast.LENGTH_SHORT).show();
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

    private void fetchLocations(String locationName) {
        String url = "http://api.weatherapi.com/v1/search.json?key=ec24b1c6dd8a4d528c1205500250305&q=" + locationName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    locationList.clear();
                    parseLocations(response);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Resultados obtenidos", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(getContext(), "Error al obtener datos: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }

    private void parseLocations(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                String name = obj.optString("name", "Sin nombre");
                String region = obj.optString("region", "Sin región");
                String country = obj.optString("country", "Sin país");
                String id = obj.optString("id", "0");

                Location location = new Location(id, name + ", " + region + ", " + country);
                locationList.add(location);
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

