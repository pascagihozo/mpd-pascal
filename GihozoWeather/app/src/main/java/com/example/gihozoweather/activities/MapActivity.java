//package com.example.gihozoweather.activities;
//
//import static com.example.gihozoweather.parsers.DataFetcher.fetchWeatherDataInBackground;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.gihozoweather.R;
//import com.example.gihozoweather.services.MapData;
//import com.example.gihozoweather.services.WeatherInfo;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private String[] urls = {
//            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286",
//            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241",
//            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154",
//            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743",
//            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581",
//            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579"
//    };
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        // Initialize the map fragment asynchronously
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_fragment);
//        mapFragment.getMapAsync(this);
//    }
//
////    @Override
////    public void onMapReady(@NonNull GoogleMap googleMap) {
////        mMap = googleMap;
////
////        // Add a marker in a specific location and move the camera
////        LatLng defaultLocation = new LatLng(0, 0); // Default location (e.g., center of the world)
////        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in Default Location"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
////    }
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Fetch weather data and add markers on map
//        List<MapData> mapDataList = fetchWeatherDataBackground(urls);
//        if (mapDataList != null) {
//            for (MapData mapData : mapDataList) {
//                LatLng location = new LatLng(mapData.getLatitude(), mapData.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(location).title("Click me!"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//            }
//        }
//
//        // Set up marker click listener
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(@NonNull Marker marker) {
//                // Retrieve the MapData object associated with the clicked marker
//                LatLng position = marker.getPosition();
//                MapData clickedMapData = getMapDataByLocation(mapDataList, position);
//
//                // Display details of the clicked MapData object
//                if (clickedMapData != null) {
//                    displayMapDataDetails(clickedMapData);
//                }
//
//                // Return true to consume the event
//                return true;
//            }
//        });
//    }
//
//    // Method to retrieve MapData object by location
//    private MapData getMapDataByLocation(List<MapData> mapDataList, LatLng location) {
//        for (MapData mapData : mapDataList) {
//            LatLng mapDataLocation = new LatLng(mapData.getLatitude(), mapData.getLongitude());
//            if (mapDataLocation.equals(location)) {
//                return mapData;
//            }
//        }
//        return null;
//    }
//
//    // Method to display details of the clicked MapData object
//    private void displayMapDataDetails(MapData mapData) {
//        String details = "Min Temperature: " + mapData.getMinTemperature() +
//                "\nPressure: " + mapData.getPressure() +
//                "\nHumidity: " + mapData.getHumidity();
//
//        Toast.makeText(this, details, Toast.LENGTH_LONG).show();
//    }
//
//
//
//
//    // This method fetches weather data from the URLs and returns list of MapData objects
//    private List<MapData> fetchWeatherDataBackground(String[] urls) {
//        List<MapData> mapDataList = new ArrayList<>();
//        for (String url : urls) {
//            // Fetch weather data and parse it
//            List<WeatherInfo> weatherInfoList = fetchWeatherDataInBackground(url);
//            if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
//                WeatherInfo firstWeatherInfo = weatherInfoList.get(0);
//                String []location = firstWeatherInfo.getLocation().split(" ");
//                double latitude = Double.parseDouble( location[0]);
//                double longitude = Double.parseDouble(location[1]);
//                String minTemperature =  firstWeatherInfo.getMinTemperature() ;
//               String pressure =  firstWeatherInfo.getPressure() ;
//                String humidity =  firstWeatherInfo.getHumidity() ;
//
//                // Create a MapData object and add it to the list
//                MapData mapData = new MapData( latitude , longitude ,
//                        minTemperature , pressure ,  humidity );
//                mapDataList.add(mapData);
//            }
//        }
//        return mapDataList;
//    }
//}


package com.example.gihozoweather.activities;

import static com.example.gihozoweather.parsers.DataFetcher.fetchWeatherDataInBackground;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import com.example.gihozoweather.R;
import com.example.gihozoweather.services.MapData;
import com.example.gihozoweather.services.WeatherInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] urls = {
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581",
            "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize the map fragment asynchronously
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Fetch weather data and add markers on map
        List<MapData> mapDataList = fetchWeatherDataBackground(urls);
        if (mapDataList!= null) {
            for (MapData mapData : mapDataList) {
                LatLng location = new LatLng(mapData.getLatitude(), mapData.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title("Click me"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            }
        }

        // Set up marker click listener
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                // Retrieve the MapData object associated with the clicked marker
                LatLng position = marker.getPosition();
                MapData clickedMapData = getMapDataByLocation(mapDataList, position);

                // Display details of the clicked MapData object
                if (clickedMapData!= null) {
                    displayMapDataDetails(clickedMapData);
                }

                // Return true to consume the event
                return true;
            }
        });
    }

    // Method to retrieve MapData object by location
    private MapData getMapDataByLocation(List<MapData> mapDataList, LatLng location) {
        for (MapData mapData : mapDataList) {
            LatLng mapDataLocation = new LatLng(mapData.getLatitude(), mapData.getLongitude());
            if (mapDataLocation.equals(location)) {
                return mapData;
            }
        }
        return null;
    }

    // Method to display details of the clicked MapData object
    private void displayMapDataDetails(MapData mapData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Weather Details");
        builder.setMessage("Min Temperature: " + mapData.getMinTemperature() +
                "\nPressure: " + mapData.getPressure() +
                "\nHumidity: " + mapData.getHumidity());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    // This method fetches weather data from the URLs and returns list of MapData objects
    private List<MapData> fetchWeatherDataBackground(String[] urls) {
        List<MapData> mapDataList = new ArrayList<>();
        for (String url : urls) {
            // Fetch weather data and parse it
            List<WeatherInfo> weatherInfoList = fetchWeatherDataInBackground(url);
            if (weatherInfoList!= null &&!weatherInfoList.isEmpty()) {
                WeatherInfo firstWeatherInfo = weatherInfoList.get(0);
                String[] location = firstWeatherInfo.getLocation().split(" ");
                if (location.length >= 2) { // Ensure there are at least two elements
                    double latitude = Double.parseDouble(location[0]);
                    double longitude = Double.parseDouble(location[1]); // Corrected to parse longitude
                    String minTemperature = firstWeatherInfo.getMinTemperature();
                    String pressure = firstWeatherInfo.getPressure();
                    String humidity = firstWeatherInfo.getHumidity();

                    // Create a MapData object and add it to the list
                    MapData mapData = new MapData(latitude, longitude, minTemperature, pressure, humidity);
                    mapDataList.add(mapData);
                }
            }
        }
        return mapDataList;
    }
}
