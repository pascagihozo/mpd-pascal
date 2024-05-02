package com.example.gihozoweather.models;

public class CurrentObservation {
    private String location;
    private String weatherCondition;
    private double temperature;
    private double windSpeed;
    private int humidity;
    private double pressure; // Added pressure field

    public CurrentObservation(String location, String weatherCondition, double temperature, double windSpeed, int humidity, double pressure) { // Added pressure to the constructor
        this.location = location;
        this.weatherCondition = weatherCondition;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() { // Added getter for pressure
        return pressure;
    }

    public void setPressure(double pressure) { // Added setter for pressure
        this.pressure = pressure;
    }
}