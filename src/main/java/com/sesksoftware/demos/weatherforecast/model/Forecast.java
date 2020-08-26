package com.sesksoftware.demos.weatherforecast.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    @JsonProperty("dt_txt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Weather[] weather;

    private MainStats main;

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(final Weather[] weather) {
        this.weather = weather;
    }

    public MainStats getMain() {
        return main;
    }

    public void setMain(final MainStats main) {
        this.main = main;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Forecast forecast = (Forecast) o;
        return Objects.equals(date, forecast.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date=" + date +
                ", weather=" + Arrays.toString(weather) +
                ", main=" + main +
                '}';
    }
}
