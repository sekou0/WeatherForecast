package com.sesksoftware.demos.weatherforecast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainStats {

    @JsonProperty("temp_min")
    private String minTemp;

    @JsonProperty("temp_max")
    private String maxTemp;

    @JsonProperty("humidity")
    private String humidity;

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(final String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(final String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(final String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "MainStats{" +
                "minTemp='" + minTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
