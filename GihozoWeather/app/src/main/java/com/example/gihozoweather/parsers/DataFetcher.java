package com.example.gihozoweather.parsers;

import com.example.gihozoweather.services.WeatherInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataFetcher {

    public static List<WeatherInfo> fetchWeatherData(String urlString) {
        List<WeatherInfo> weatherInfoList = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            WeatherInfo weatherInfo = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName != null && tagName.equals("item")) {
                            weatherInfo = new WeatherInfo();
                        } else if (weatherInfo != null) {
                            if (tagName != null && tagName.equals("title")) {
                                String text = parser.nextText();
                                weatherInfo.setCondition(text.split(":")[1].split(",")[0]);
                                weatherInfo.setDate(text.split(":")[0]);
                            } else if (tagName != null && tagName.equals("link")) {
                                weatherInfo.setLocation(parser.nextText());
                            } else if (tagName != null && tagName.equals("description")) {
                                String description = parser.nextText();

                                String[] attributes = description.split(", ");
                                for (String attribute : attributes) {
                                    if (attribute.startsWith("Minimum Temperature:")) {
                                        weatherInfo.setMinTemperature(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Maximum Temperature:")) {
                                        weatherInfo.setMaxTemperature(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Wind Direction:")) {
                                        weatherInfo.setWindDirection(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Wind Speed:")) {
                                        weatherInfo.setWindSpeed(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Visibility:")) {
                                        weatherInfo.setVisibility(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Pressure:")) {
                                        weatherInfo.setPressure(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Humidity:")) {
                                        weatherInfo.setHumidity(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("UV Risk:")) {
                                        weatherInfo.setUvRisk(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Pollution:")) {
                                        weatherInfo.setPollution(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Sunrise:")) {
                                        weatherInfo.setSunriseTime(attribute.split(":")[1].trim());
                                    } else if (attribute.startsWith("Sunset:")) {
                                        weatherInfo.setSunsetTime(attribute.split(":")[1].trim());
                                    }
                                }
                            } else if (tagName != null && tagName.equals("georss:point")) {
                                weatherInfo.setLocation(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName != null && tagName.equals("item")) {
                            weatherInfoList.add(weatherInfo);
                            weatherInfo = null;
                        }
                        break;
                }
                eventType = parser.next();
            }

            inputStream.close();
            connection.disconnect();

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        return weatherInfoList;
    }

    public static void main(String[] args) {
        String urlString = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123";
        List<WeatherInfo> weatherInfoList = fetchWeatherData(urlString);

        for (WeatherInfo weatherInfo : weatherInfoList) {
            System.out.println(weatherInfo);
        }
    }

    public static List<WeatherInfo> fetchWeatherDataInBackground(String urlString) {
        // Create a fixed thread pool with a single thread
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Submit the task to fetch weather data asynchronously
        Future<List<WeatherInfo>> future = CompletableFuture.supplyAsync(() -> {
            return DataFetcher.fetchWeatherData(urlString);
        }, executor);

        // Shutdown the executor when done
        executor.shutdown();

        try {
            // Get the result of the asynchronous task
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
