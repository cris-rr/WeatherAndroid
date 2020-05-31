package com.ruiz.weather;

public class GetWeatherAsync implements Runnable {
    private MainActivity activity;
    private String city;

    public GetWeatherAsync(MainActivity activity, String city) {
        this.activity = activity;
        this.city = city;
    }

    @Override
    public void run() {
        WeatherDataLoader loader = new WeatherDataLoader();
        // Getting the weather, after that, call the handle function in the mainActivity.

        final WeatherConditions conditions = loader.getWeatherAndPostResults(city);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Calling handle function to update the UI
                activity.handleWeatherResult(conditions);
            }
        });
    }
}
