package com.sesksoftware.demos.weatherforecast.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.sesksoftware.demos.weatherforecast.model.Forecast;
import com.sesksoftware.demos.weatherforecast.model.ForecastResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/*****
 * WeatherMapService implementation
 *
 * Methods to implement get5DayForecast and getDetailedForecast
 */
@Service
public class WeatherMapServiceImpl implements WeatherMapService {

    private final static String OPENWEATHER_URL =
            "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=Metric";

    private final RestTemplate restTemplate;

    private final String apiKey;

    public WeatherMapServiceImpl(final String apiKey, final RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    /****
     * getWeeklyForecast - Will get basic details for the next 5 days forecast including today.
     * @param city - name of the city of interest, e.g London, Taipei,
     * @return - a list of simple forecast data.
     */
    public List<Forecast> getWeeklyForecast(final String city) {

        if(city == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    String.format("getWeeklyForecast() generated error: City parameter cannot be null"));
        }

        final Calendar today = Calendar.getInstance();

        final String url = String.format(OPENWEATHER_URL, city, apiKey);

        ResponseEntity<ForecastResponse> response =
                restTemplate.getForEntity(url, ForecastResponse.class);

        if(response.getStatusCode().is2xxSuccessful()) {

            final Calendar forecastDate =
                    Calendar.getInstance(TimeZone.getTimeZone("European/London"));

            if(response.hasBody()) {
                final List<Forecast> forecasts = response.getBody().getForecasts();

                return
                        forecasts
                                .stream()
                                .filter(forecast -> {
                                            forecastDate.setTime(forecast.getDate());
                                            return isIncluded(today, forecastDate);
                                        }
                                ).collect(Collectors.toList());
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Response does not contain a body");
            }
        } else {
            if(response.getStatusCode().is4xxClientError()) {
                throw new HttpClientErrorException(response.getStatusCode(),
                        String.format("getWeeklyForecast(%s) generated error: %s",
                                city,
                                response.getStatusCode().getReasonPhrase()));
            }

            throw new HttpServerErrorException(response.getStatusCode(),
                    String.format("getWeeklyForecast(%s) generated error: %s",
                            city,
                            response.getStatusCode().getReasonPhrase()));
        }
    }

    /****
     * getDetailedForecast - will return a forecast for a specific day in the next 5 days
     * @param city - valid city name to search for
     * @param day - day to search within the next 5 days
     * @return - a Forecast for the specific day
     * @throws  HttpClientErrorException -  404 Not Found if the day is outside of the next 5 days, or if the name of the city is not found
     * @throws  HttpServerErrorException -  for api end point errors
     */
    @Override
    public Forecast getDetailedForecast(final String city, final String day) {

        if(city == null || day == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    String.format("getDetailedForecast(%s, %s) generated error: Parameters cannot be null",
                            city, day ));
        }

        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");

        final List<Forecast> forecasts = this.getWeeklyForecast(city);

        for (Forecast forecast : forecasts) {
            final String forecastDay = dateFormat.format(forecast.getDate()).toUpperCase();

            if(forecastDay.startsWith(day.toUpperCase())) {
                return forecast;
            }
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                String.format("getDetailedForecast(%s, %s) generated error: Could not be found in the next 5 days",
                        city, day ));
    }

    /***
     * calculates of the forecast should be returned from the list given.  Only 12pm forecasts or the last one for the current day
     * @param today - Calendar value for todays time
     * @param forecastDate - Calendar value of the forecast date
     * @return - true if the forecast date is today the current time is after 12pm and the current record is for 9pm otherwise if the current record has a 12pm timestamp
     */
    private boolean isIncluded(Calendar today, Calendar forecastDate) {

        if(forecastDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
            if(today.get(Calendar.HOUR_OF_DAY) > 12) {
                if(forecastDate.get(Calendar.HOUR_OF_DAY) == 21) {
                    return true;
                }
            } else {
                if(forecastDate.get(Calendar.HOUR_OF_DAY) == 12) {
                    return true;
                }
            }
        } else {
            if(forecastDate.get(Calendar.HOUR_OF_DAY) == 12) {
                return true;
            }
        }
        return false;
    }
}
