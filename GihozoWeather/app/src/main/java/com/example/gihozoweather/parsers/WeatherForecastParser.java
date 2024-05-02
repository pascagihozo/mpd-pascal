package com.example.gihozoweather.parsers;

import android.util.Xml;

import com.example.gihozoweather.models.CurrentObservation;
import com.example.gihozoweather.models.DayForecast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherForecastParser {

    private static final String NS = null;

    public static List<DayForecast> parseForecastXml(String xmlData) {
        List<DayForecast> forecasts = new ArrayList<>();

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xmlData));
            parser.nextTag();
            forecasts = readForecastFeed(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return forecasts;
    }

    private static List<DayForecast> readForecastFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<DayForecast> forecasts = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, NS, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                forecasts = readForecastChannel(parser);
            } else {
                skip(parser);
            }
        }

        return forecasts;
    }

    private static List<DayForecast> readForecastChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<DayForecast> forecasts = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, NS, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                forecasts.add(readForecastItem(parser));
            } else {
                skip(parser);
            }
        }

        return forecasts;
    }

    private static DayForecast readForecastItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        String day = null;
        String date = null;
        double minTemperature = 0.0;
        double maxTemperature = 0.0;
        String weatherCondition = null;

        parser.require(XmlPullParser.START_TAG, NS, "item");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                date = readTitle(parser);
                day = extractDayFromTitle(date);
            } else if (name.equals("description")) {
                String description = readDescription(parser);
                weatherCondition = extractWeatherConditionFromDescription(description);
                minTemperature = extractMinTemperatureFromDescription(description);
                maxTemperature = extractMaxTemperatureFromDescription(description);
            } else {
                skip(parser);
            }
        }

        return new DayForecast(day, date, minTemperature, maxTemperature, weatherCondition, "", 0.0, 0.0);
    }

    public static CurrentObservation parseCurrentObservationXml(String xmlData) throws XmlPullParserException, IOException {
        CurrentObservation observation = null;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xmlData));
            parser.nextTag();
            observation = readCurrentObservation(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return observation;
    }
    // Helper method to read the current observation from the XML
    private static CurrentObservation readCurrentObservation(XmlPullParser parser) throws XmlPullParserException, IOException {
        String location = null;
        String weatherCondition = null;
        double temperature = 0.0;
        double windSpeed = 0.0; // Assuming you need to parse this from XML
        int humidity = 0; // Assuming you need to parse this from XML
        double pressure = 0.0; // Assuming you need to parse this from XML

        parser.require(XmlPullParser.START_TAG, NS, "currentObservation");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("location")) {
                location = readText(parser);
            } else if (name.equals("temperature")) {
                temperature = Double.parseDouble(readText(parser));
            } else if (name.equals("weatherCondition")) {
                weatherCondition = readText(parser);
            } else if (name.equals("windSpeed")) { // Assuming you have a windSpeed element in your XML
                windSpeed = Double.parseDouble(readText(parser));
            } else if (name.equals("humidity")) { // Assuming you have a humidity element in your XML
                humidity = Integer.parseInt(readText(parser));
            } else if (name.equals("pressure")) { // Assuming you have a pressure element in your XML
                pressure = Double.parseDouble(readText(parser));
            } else {
                skip(parser);
            }
        }

        return new CurrentObservation(location, weatherCondition, temperature, windSpeed, humidity, pressure);
    }



    private static String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, NS, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, NS, "title");
        return title;
    }

    private static String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, NS, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, NS, "description");
        return description;
    }

    private static String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    // Helper methods to extract data from the title and description strings
    private static String extractDayFromTitle(String title) {
        // Implement logic to extract the day of the week from the title string
        // Example: "Wednesday, 21 June 2023"
        Pattern pattern = Pattern.compile("^(\\w+),");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String extractWeatherConditionFromDescription(String description) {
        // Implement logic to extract the weather condition from the description string
        // Example: "Sunny intervals and a gentle breeze. Highs of 22C inland, but cooler near the coast."
        Pattern pattern = Pattern.compile("^(\\w+\\s*\\w*)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static double extractMinTemperatureFromDescription(String description) {
        // Implement logic to extract the minimum temperature from the description string
        // Example: "Sunny intervals and a gentle breeze. Highs of 22C inland, but cooler near the coast."
        Pattern pattern = Pattern.compile("Lows of (-?\\d+)°C");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0;
    }

    private static double extractMaxTemperatureFromDescription(String description) {
        // Implement logic to extract the maximum temperature from the description string
        // Example: "Sunny intervals and a gentle breeze. Highs of 22C inland, but cooler near the coast."
        Pattern pattern = Pattern.compile("Highs of (-?\\d+)°C");
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0;
    }
}