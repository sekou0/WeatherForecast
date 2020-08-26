package com.sesksoftware.demos.weatherforecast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sesksoftware.demos.weatherforecast.model.Forecast;
import com.sesksoftware.demos.weatherforecast.model.ForecastResponse;
import com.sesksoftware.demos.weatherforecast.model.MainStats;
import com.sesksoftware.demos.weatherforecast.model.Weather;

public class MockData {


    public static MainStats getMockMainStats(final String hum, final String min, final String max) {
        final MainStats mainStats = new MainStats();
        mainStats.setHumidity(hum);
        mainStats.setMinTemp(min);
        mainStats.setMaxTemp(max);

        return mainStats;
    }

    public static Weather[] getMockWeathers(final String main) {

        final Weather weather = new Weather();
        weather.setId(1);
        weather.setDescription("description");
        weather.setMain(main);
        return new Weather[] {weather};
    }

    public static Forecast getMockForecast(
            final Date date,
            final String main,
            final String hum, final String min, final String max) {

        final Forecast forecast = new Forecast();
        forecast.setDate(date);
        forecast.setMain(getMockMainStats(hum, min, max));
        forecast.setWeather(getMockWeathers(main));

        return forecast;
    }


    public static List<Forecast> getMockForecasts() {
        final List<Forecast> forecasts = new ArrayList<>();


        final Calendar today = Calendar.getInstance();
        today.set(2020, Calendar.MARCH, 10, 12, 0);

        forecasts.add(getMockForecast(today.getTime(), "Cloudy", "45", "15.24", "23.23"));

        today.set(2020, Calendar.MARCH, 10, 15, 0);

        forecasts.add(getMockForecast(today.getTime(), "Cloudy", "33", "12.34", "21.22"));

        today.set(2020, Calendar.MARCH, 10, 18, 0);

        forecasts.add(getMockForecast(today.getTime(), "Cloudy", "33", "12.34", "18.45"));

        today.set(2020, Calendar.MARCH, 11, 12, 0);

        forecasts.add(getMockForecast(today.getTime(), "Sunny", "22", "18.34", "27.45"));

        today.set(2020, Calendar.MARCH, 12, 12, 0);

        forecasts.add(getMockForecast(today.getTime(), "Sunny", "33", "19", "29.22"));

        today.set(2020, Calendar.MARCH, 13, 12, 0);

        forecasts.add(getMockForecast(today.getTime(), "Rainy", "60", "18", "26"));


        today.set(2020, Calendar.MARCH, 13, 15, 0);

        forecasts.add(getMockForecast(today.getTime(), "Rainy", "53", "17", "25"));

        today.set(2020, Calendar.MARCH, 14, 12, 0);

        forecasts.add(getMockForecast(today.getTime(), "Cloudy", "35", "17", "24"));

        today.set(2020, Calendar.MARCH, 15, 12, 0);

        forecasts.add(getMockForecast(today.getTime(), "Sunny", "34", "23", "30"));
        return forecasts;
    }

    public static ForecastResponse getMockForecastResponse() {
        final ForecastResponse response = new ForecastResponse();

        response.setCode("code");
        response.setCount("4");
        response.setMessage("message");
        response.setForecasts(getMockForecasts());

        return response;
    }
}
