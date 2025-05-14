package com.example.iot_lab4_20182693.model;

public class FutureWeather {
    private String time;
    private String temperature;
    private String condition;

    public FutureWeather(String time, String temperature, String condition) {
        this.time = time;
        this.temperature = temperature;
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }
}