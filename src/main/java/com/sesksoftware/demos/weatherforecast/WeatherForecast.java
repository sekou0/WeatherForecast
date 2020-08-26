package com.sesksoftware.demos.weatherforecast;

import java.text.SimpleDateFormat;
import java.util.List;

import com.sesksoftware.demos.weatherforecast.model.Forecast;
import com.sesksoftware.demos.weatherforecast.services.WeatherMapService;
import com.sesksoftware.demos.weatherforecast.services.WeatherMapServiceImpl;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/***
 * WeatherForecast main program to get either a 5 day forecast or a specific day forecast
 */
public class WeatherForecast {

    private final WeatherMapService weatherMapService;

    public WeatherForecast(WeatherMapService weatherMapService) {
        this.weatherMapService = weatherMapService;
    }

    public static void main(String[] args) {

        if(args.length < 2) {
            System.err.println("Usage Error: {api_key} {city} {day}<optional>\n");
            System.exit(-1);
        }

        final RestTemplate restTemplate = new RestTemplate();
        final WeatherMapService weatherMapService = new WeatherMapServiceImpl(args[0], restTemplate);
        final WeatherForecast weatherForecast = new WeatherForecast(weatherMapService);

        if(args.length == 2) {
            weatherForecast.getWeekForecast(args[1]);
        } else {
            weatherForecast.getDetailedForecast(args[1], args[2]);
        }
    }

    /***
     * Gets the next 5 days forecast and prints to console the information
     * @param city - name of a city..  e,g London, "New York", Taipei
     */
    public void getWeekForecast(String city) {

        try {
            final List<Forecast> forecasts = weatherMapService.getWeeklyForecast(city);

            final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");

            System.out.println(String.format("Weather for %s\n", city));

            forecasts.forEach( forecast -> {

                final String formattedDate = dateFormat.format(forecast.getDate());

                final String printLine = String.format("* %s. Main: %s\n", formattedDate, forecast.getWeather()[0].getMain() );

                System.out.println(printLine);
            });
        } catch (HttpClientErrorException e) {
            System.out.println(String.format("%s - %s", e.getStatusCode().getReasonPhrase(), e.getMessage()));
        } catch (HttpServerErrorException e) {
            System.out.println(String.format("%s - %s", e.getStatusCode().getReasonPhrase(), e.getMessage()));
        }
    }

    /****
     * Prints the forecast for the given city on a specific day within the next 5 days
     * @param city - name of a city..  e,g London, "New York", Taipei
     * @param day - Day of the week, e.g Monday, Tues, Wednesday
     */
    public void getDetailedForecast(String city, String day) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");

        try {
            final Forecast forecast = weatherMapService.getDetailedForecast(city, day);

            System.out.println(String.format("Weather for %s %s:\n", city, dateFormat.format(forecast.getDate())));

            System.out.println(String.format("* Main: %s\n", forecast.getWeather()[0].getMain()));

            System.out.println(String.format("* Min temp: %s Celsius degrees\n", forecast.getMain().getMinTemp()));

            System.out.println(String.format("* Max temp: %s Celsius degrees\n", forecast.getMain().getMaxTemp()));

            System.out.println(String.format("* Humidity: %s\n", forecast.getMain().getHumidity()));
        } catch (HttpClientErrorException e) {
            System.out.println(String.format("%s - %s", e.getStatusCode().getReasonPhrase(), e.getMessage()));
        }catch (HttpServerErrorException e) {
            System.out.println(String.format("%s - %s", e.getStatusCode().getReasonPhrase(), e.getMessage()));
        }
    }
}
