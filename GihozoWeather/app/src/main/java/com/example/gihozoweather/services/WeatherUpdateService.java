package com.example.gihozoweather.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.example.gihozoweather.models.CurrentObservation;
import com.example.gihozoweather.models.DayForecast;
import com.example.gihozoweather.parsers.CurrentObservationParser;
import com.example.gihozoweather.parsers.WeatherForecastParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherUpdateService extends Service {

    private static final String TAG = "WeatherUpdateService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fetchAndUpdateWeatherData();
        return START_STICKY;
    }

    private void fetchAndUpdateWeatherData() {
        new FetchWeatherXmlTask().execute();
    }

    private class FetchWeatherXmlTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String xmlData = "";
            try {
                URL url = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                xmlData = stringBuilder.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error fetching weather XML", e);
            }
            return xmlData;
        }

        @Override
        protected void onPostExecute(String xmlData) {
            super.onPostExecute(xmlData);
            if (!xmlData.isEmpty()) {
                try {
                    CurrentObservation observation = CurrentObservationParser.parseObservationXml(xmlData);
                    List<DayForecast> forecasts = WeatherForecastParser.parseForecastXml(xmlData);
                    updateWeatherData(observation, forecasts);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing weather XML", e);
                }
            }
        }
    }

    private void updateWeatherData(CurrentObservation observation, List<DayForecast> forecasts) {
        // Implement logic to update the weather data using the parsed observation and forecasts
        // You can use a ContentProvider, database, or shared preferences to store the data
    }
}