package com.example.tanmay.earthquakes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ListView earthquakeListView = findViewById(R.id.activity_earthquake_list_view);

        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = adapter.getItem(i);

                // Convert the String URL into a URI object
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);

            }
        });

    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        @Override
        protected List<Earthquake> doInBackground(String... strings) {

            if (strings.length < 1 || strings[0] == null) return null;

            return QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {

        }
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

            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

            int magnitudeColor = getMagnitudeColor(magnitude_raw);

            magnitudeCircle.setColor(magnitudeColor);

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

        private int getMagnitudeColor(float magnitude) {

            int magnitudeColorResourceId = 0;

            switch ((int) Math.floor(magnitude)) {

                case 1:
                    magnitudeColorResourceId = R.color.magnitude1;
                    break;
                case 2:
                    magnitudeColorResourceId = R.color.magnitude2;
                    break;
                case 3:
                    magnitudeColorResourceId = R.color.magnitude3;
                    break;
                case 4:
                    magnitudeColorResourceId = R.color.magnitude4;
                    break;
                case 5:
                    magnitudeColorResourceId = R.color.magnitude5;
                    break;
                case 6:
                    magnitudeColorResourceId = R.color.magnitude6;
                    break;
                case 7:
                    magnitudeColorResourceId = R.color.magnitude7;
                    break;
                case 8:
                    magnitudeColorResourceId = R.color.magnitude8;
                    break;
                case 9:
                    magnitudeColorResourceId = R.color.magnitude9;
                    break;
                case 10:
                    magnitudeColorResourceId = R.color.magnitude10plus;
                    break;
                default:
                    magnitudeColorResourceId = android.R.color.black;
                    break;
            }

            return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

        }

    }
}
