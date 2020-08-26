package com.sesksoftware.demos.weatherforecast.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {

    @JsonProperty("cod")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("cnt")
    private String count;

    @JsonProperty("list")
    private List<Forecast> forecasts;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = count;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(final List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public String toString() {
        return "ForecastResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", count='" + count + '\'' +
                ", forecasts=" + forecasts +
                '}';
    }
}
