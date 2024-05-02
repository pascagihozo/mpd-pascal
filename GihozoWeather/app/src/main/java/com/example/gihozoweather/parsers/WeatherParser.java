package com.example.gihozoweather.parsers;

import com.example.gihozoweather.services.WeatherData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class WeatherParser {

    public static void main(String[] args) {
        String xml = ""; // Replace with your XML string
        List<WeatherData> weatherDataList = parseWeatherData(xml);
        for (WeatherData data : weatherDataList) {
            System.out.println("Day: " + data.getDay() + ", Min Temp: " + data.getMinTemp() + ", Max Temp: " + data.getMaxTemp() + ", Condition: " + data.getCondition() + ", Pressure: " + data.getPressure() + ", Humidity: " + data.getHumidity() + ", Wind Speed: " + data.getWindSpeed());
        }
    }

    public static List<WeatherData> parseWeatherData(String xml) {
        List<WeatherData> weatherDataList = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType!= XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("item")) {
                    WeatherData weatherData = new WeatherData();
                    while (eventType!= XmlPullParser.END_TAG &&!parser.getName().equals("item")) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String tagName = parser.getName();
                            if (tagName.equals("title")) {
                                weatherData.setDay(parser.nextText());
                            } else if (tagName.equals("description")) {
                                String description = parser.nextText();
                                String[] parts = description.split(", ");
                                if (parts.length >= 6) {
                                    weatherData.setMinTemp(parts[0].substring(parts[0].indexOf(":") + 2));
                                    weatherData.setMaxTemp(parts.length > 1? parts[1].substring(parts[1].indexOf(":") + 2) : null);
                                    weatherData.setCondition(parts.length > 2? parts[2].substring(parts[2].indexOf(":") + 2) : null);
                                    weatherData.setPressure(parts.length > 3? parts[3].substring(parts[3].indexOf(":") + 2) : null);
                                    weatherData.setHumidity(parts.length > 4? parts[4].substring(parts[4].indexOf(":") + 2) : null);
                                    weatherData.setWindSpeed(parts.length > 5? parts[5].substring(parts[5].indexOf(":") + 2) : null);
                                }
                            }
                        }
                        eventType = parser.next();
                    }
                    weatherDataList.add(weatherData);
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherDataList;
    }

}