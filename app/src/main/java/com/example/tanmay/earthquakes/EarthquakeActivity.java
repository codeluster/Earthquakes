package com.example.tanmay.earthquakes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        // Create a list of earthquakes
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        ListView earthquakeListView = findViewById(R.id.activity_earthquake_list_view);

        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        earthquakeListView.setAdapter(adapter);

    }

    private class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


        public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
            }

            Earthquake currentEarthquake = getItem(position);

            TextView magnitudeView = convertView.findViewById(R.id.earthquake_magnitude);
            TextView locationView = convertView.findViewById(R.id.earthquake_location);
            TextView timeView = convertView.findViewById(R.id.earthquake_time);

            magnitudeView.setText(currentEarthquake.getMagnitude());
            locationView.setText(currentEarthquake.getLocation());
            timeView.setText(currentEarthquake.getTime());

            return convertView;

        }
    }
}
