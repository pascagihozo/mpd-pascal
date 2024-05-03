package com.example.gihozoweather.services;

public class MapData {
    private double latitude;
    private double longitude;
    private String minTemperature;
    private String pressure;
    private String humidity;

    public MapData( ) {

    }

    public MapData(double latitude, double longitude, String minTemperature, String pressure, String humidity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.minTemperature = minTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "MapData{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", minTemperature='" + minTemperature + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}

