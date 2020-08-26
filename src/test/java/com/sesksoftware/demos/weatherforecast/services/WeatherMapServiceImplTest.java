package com.sesksoftware.demos.weatherforecast.services;

import static com.sesksoftware.demos.weatherforecast.MockData.getMockForecastResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.text.SimpleDateFormat;
import java.util.List;

import com.sesksoftware.demos.weatherforecast.model.Forecast;
import com.sesksoftware.demos.weatherforecast.model.ForecastResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class WeatherMapServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private WeatherMapService weatherMapService;

    @Before
    public void setUp() throws Exception {

        initMocks(this);
        this.weatherMapService = new WeatherMapServiceImpl("apiKey", this.restTemplate);
    }

    @Test
    public void getWeeklyForecast_willReturn200() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "london";

        final List<Forecast> results = this.weatherMapService.getWeeklyForecast(city);

        assertNotNull(results);
        assertThat(results.size(), is(6));
        assertThat(results.get(0).getMain().getMinTemp(), is("15.24"));
    }

    @Test(expected = HttpClientErrorException.class)
    public void getWeeklyForecastNullCity_willReturn40x() {
        this.weatherMapService.getWeeklyForecast(null);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getWeeklyForecastBadCity_willReturn40x() {

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        final String city = "XXXXXXX";
        this.weatherMapService.getWeeklyForecast(city);
    }

    @Test
    public void getDetailedForecast_willReturn200() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "London";
        final String day = "Friday";

        final Forecast result = this.weatherMapService.getDetailedForecast(city, day);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");

        assertNotNull(result);
        assertThat(dateFormat.format(result.getDate()), is("Friday"));

        assertThat(result.getMain().getMinTemp(), is("18"));
    }

    @Test(expected = HttpClientErrorException.class)
    public void getDetailedForecastNullCity_willReturn40x() {

        final String day = "Monday";
        this.weatherMapService.getDetailedForecast(null, day);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getDetailedForecastNullDay_willReturn40x() {

        final String city = "London";
        this.weatherMapService.getDetailedForecast(city, null);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getDetailedForecastBadCity_willReturn40x() {

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        final String city = "XXXXXXX";
        final String day = "Monday";
        this.weatherMapService.getDetailedForecast(city, day);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getDetailedForecastBadDays_willReturn40x() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "London";
        final String day = "FlutterDay";
        this.weatherMapService.getDetailedForecast(city, day);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getDetailedForecastMoreThan5Days_willReturn40x() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "London";
        final String day = "Monday";
        this.weatherMapService.getDetailedForecast(city, day);
    }
}
