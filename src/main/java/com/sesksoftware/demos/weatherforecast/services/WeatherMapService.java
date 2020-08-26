package com.sesksoftware.demos.weatherforecast.services;

import java.util.List;

import com.sesksoftware.demos.weatherforecast.model.Forecast;

public interface WeatherMapService {
    List<Forecast> getWeeklyForecast(final String city);
    Forecast getDetailedForecast(final String city, final String day);
}
