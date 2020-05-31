package com.ruiz.weather;

public class GetForecastAsync implements Runnable {
    private MainActivity activity;
    private String city;

    public GetForecastAsync(MainActivity activity, String city) {
        this.activity = activity;
        this.city = city;
    }

    @Override
    public void run() {
        WeatherDataLoader loader = new WeatherDataLoader();
        // Getting forecast, after that, call the handle function in the mainActivity.

        final WeatherForecast forecast = loader.getForecastAndPostResults(city);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Calling handle function to update the UI
                activity.handleForecastResult(forecast);
            }
        });

    }
}
