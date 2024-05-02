package com.example.gihozoweather.parsers;

import com.example.gihozoweather.models.CurrentObservation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.StringReader;

public class CurrentObservationParser {

    public static CurrentObservation parseObservationXml(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(xmlData));
            parser.nextTag(); // Advance to the first tag

            return readObservation(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static CurrentObservation readObservation(XmlPullParser parser) throws XmlPullParserException, IOException {
        String location = null;
        String weatherCondition = null;
        double temperature = 0.0;
        double windSpeed = 0.0;
        int humidity = 0;
        double pressure = 0.0;

        parser.require(XmlPullParser.START_TAG, null, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                parser.require(XmlPullParser.START_TAG, null, "channel");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String tagName = parser.getName();
                    if (tagName.equals("location")) {
                        location = readTag(parser, "location");
                    } else if (tagName.equals("item")) {
                        parser.require(XmlPullParser.START_TAG, null, "item");
                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.getEventType() != XmlPullParser.START_TAG) {
                                continue;
                            }
                            String itemTagName = parser.getName();
                            if (itemTagName.equals("condition")) {
                                weatherCondition = readTag(parser, "condition");
                            } else if (itemTagName.equals("temp_c")) {
                                temperature = Double.parseDouble(readTag(parser, "temp_c"));
                            } else if (itemTagName.equals("wind_kph")) {
                                windSpeed = Double.parseDouble(readTag(parser, "wind_kph"));
                            } else if (itemTagName.equals("humidity")) {
                                humidity = Integer.parseInt(readTag(parser, "humidity"));
                            } else if (itemTagName.equals("pressure_mb")) {
                                pressure = Double.parseDouble(readTag(parser, "pressure_mb"));
                            } else {
                                skip(parser);
                            }
                        }
                    } else {
                        skip(parser);
                    }
                }
            } else {
                skip(parser);
            }
        }
        return new CurrentObservation(location, weatherCondition, temperature, windSpeed, humidity, pressure);
    }

    private static String readTag(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tagName);
        String tagValue = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tagName);
        return tagValue;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
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
}
