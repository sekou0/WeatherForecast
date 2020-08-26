package com.sesksoftware.demos.weatherforecast;

import static com.sesksoftware.demos.weatherforecast.MockData.getMockForecastResponse;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.sesksoftware.demos.weatherforecast.model.ForecastResponse;
import com.sesksoftware.demos.weatherforecast.services.WeatherMapServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeatherForecastTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final PrintStream systemOut = System.out;
    private final PrintStream systemErr = System.err;

    private WeatherForecast weatherForecast;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        this.weatherForecast = new WeatherForecast(
                new WeatherMapServiceImpl("apiKey", restTemplate)
        );

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(systemOut);
        System.setErr(systemErr);
    }

    @Test
    public void getWeekForecast_andWriteResult() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "London";

        this.weatherForecast.getWeekForecast(city);

        assertThat(out.toString(), containsString("Weather for London"));
        assertThat(out.toString(), containsString("* Tuesday, Mar 10. Main: Cloudy"));
    }

    @Test
    public void getWeekForecastBadCity_andWriteError() {

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        final String city = "XXXX";

        this.weatherForecast.getWeekForecast(city);

        assertThat(out.toString(),
                containsString("Not Found - 404 getWeeklyForecast(XXXX) generated error: Not Found"));
    }

    @Test
    public void getDetailedForecast_andWriteResult() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "London";
        final String day = "Thursday";

        this.weatherForecast.getDetailedForecast(city, day);

        assertThat(out.toString(), containsString("Weather for London Thursday, Mar 12:"));
        assertThat(out.toString(), containsString("* Max temp: 29.22 Celsius degrees"));
    }

    @Test
    public void getDetailedBadCity_andWriteError() {

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        final String city = "XXXX";
        final String day = "Friday";

        this.weatherForecast.getDetailedForecast(city, day);

        assertThat(out.toString(),
                containsString("Not Found - 404 getWeeklyForecast(XXXX) generated error: Not Found"));
    }

    @Test
    public void getDetailedDayNotInNext5Days_andWriteError() {

        final ForecastResponse mockForecastResponse = getMockForecastResponse();

        assertNotNull(mockForecastResponse);
        assertThat(mockForecastResponse.getForecasts().size(), is(9));

        when(restTemplate.getForEntity(anyString(), eq(ForecastResponse.class)))
                .thenReturn(
                        new ResponseEntity<>(mockForecastResponse, HttpStatus.OK));

        final String city = "London";
        final String day = "Monday";

        this.weatherForecast.getDetailedForecast(city, day);

        assertThat(out.toString(),
                containsString(
                        "Not Found - 404 getDetailedForecast(London, Monday) generated error: Could not be found in the next 5 days"));
    }
}
