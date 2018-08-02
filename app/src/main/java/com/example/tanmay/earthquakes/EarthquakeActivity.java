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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            TextView offsetLocationView = convertView.findViewById(R.id.earthquake_location_offset);
            TextView primaryLocationView = convertView.findViewById(R.id.earthquake_location_primary);
            TextView dateView = convertView.findViewById(R.id.earthquake_date);
            TextView timeView = convertView.findViewById(R.id.earthquake_time);

            float magnitude_raw = currentEarthquake.getMagnitude();

            // Convert all magnitudes into decimal with one place
            DecimalFormat formatter = new DecimalFormat("0.0");
            String magnitude_formatted = formatter.format(magnitude_raw);

            magnitudeView.setText(magnitude_formatted);

            String location_raw = currentEarthquake.getLocation();

            int breakStringIndex = location_raw.indexOf(" of ") + 4;

            // Offset location not present
            if (breakStringIndex < 4) {
                offsetLocationView.setText(R.string.offset_default);
                primaryLocationView.setText(location_raw);
            } else {
                offsetLocationView.setText(location_raw.substring(0, breakStringIndex));
                primaryLocationView.setText(location_raw.substring(breakStringIndex));
            }

            long time_raw = currentEarthquake.getTimeInMilliseconds();

            Date dateObject = new Date(time_raw);

            String formattedDate = formatDate(dateObject);
            dateView.setText(formattedDate);

            String formattedTime = formatTime(dateObject);
            timeView.setText(formattedTime);


            return convertView;

        }

        private String formatDate(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yy");
            return dateFormat.format(date);
        }

        private String formatTime(Date date) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            return timeFormat.format(date);
        }

    }
}
