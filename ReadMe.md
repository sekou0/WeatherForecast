# Adaptavist coding test

## Weather Challenge App

### Java implementation of a gradle task


The user is meant to type the Gradle task followed by the name of a city.
After pressing Enter, the 5-day forecast is displayed including the current day. This summary shows the "main" data at
12:00 (or if it is for the current day, and the current hour is greater than 12:00, the last hour
contained in the response):

```
./gradlew weatherForecast -Pcity=Madrid
  Weather for Madrid
  * Monday, Jul 1. Main: Clear.
  * Tuesday, Jul 2. Main: Clouds.
  * Wednesday, Jul 3. Main: Clear.
  * Thursday, Jul 4. Main: Clear.
  * Friday, Jul 5. Main: Clouds.
```

```
./gradlew weatherForecast -Pcity=Madrid -Pday=Monday
Weather for Madrid Monday, Jul 1:
* Main: Clear.
* Min temp: 25 Celsius degrees
* Max temp: 35 Celsius degrees
* Humidity: 21
```

####Technologies
As my groovy experience is somewhat limited and somewhat old, I implemented the gradle task utilising Java

Java

Spring (for RestTemplate)

Junit

Mockito

Gradle

####How to build & run

````
./gradlew weatherForecast -Pcity={city name} -Pday={day of week}

e.g.

./gradlew weatherForecast -Pcity="New York"

./gradlew weatherForecast -Pcity=Taipei-Pday=Sunday

````

The Gradle Task takes 2 Parameters

1: -Pcity - This is a city name, this must exist in the OpenWeather API and will have a value such as London, or "New York"
2: -Pday - This is the optional day of the week - This day must be within the next five days, otherwise a not found error will be thrown

The java application implementation takes 3 command line parameters -

1: ApI Key - This has been placed in the gradle.bundle and can be replaced if ever expired
2: City - This is a city name, this must exist in the OpenWeather API and will have a value such as London, or "New York"
3: Day - This is the optional day of the week - This day must be within the next five days, otherwise a not found error will be thrown

