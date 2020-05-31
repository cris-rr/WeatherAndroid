package com.ruiz.weather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText cityText;
    ListView forecastListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityText = (EditText) findViewById(R.id.cityText);
        forecastListView = (ListView) findViewById(R.id.forecastListView);
    }

    public void getWeather(View view) {
        String city = cityText.getText().toString();
        Log.d("MainActivity", String.format("Getting weather for the city: %s", city));

        //Requesting async query to the API
        GetWeatherAsync getWeatherAsync =new GetWeatherAsync(this, city);
        Thread t = new Thread(getWeatherAsync);
        t.start();
    }

    public void getForecast(View view) {
        String city = cityText.getText().toString();
        Log.d("MainActivity", String.format("Getting forecast for the city: %s", city));

        //Requesting async query to the API
        GetForecastAsync getForecastAsync = new GetForecastAsync(this, city);
        Thread t = new Thread(getForecastAsync);
        t.start();
    }

    public void handleWeatherResult(WeatherConditions conditions) {
        Log.d("MainActivity", "Have response from weather API");

        if (conditions == null) {
            Log.d("MainActivity", "No valid results from weather API");
            Toast.makeText(this, "Error not valid data from weather API",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.d("MainActivity", "Valid data from weather API, conditions: " + conditions.getMeasurements().toString());

            //display the current temp
            Float currentTemp = conditions.getMeasurements().get("temp");
            Toast.makeText(this, String.format("Current temperature in %s : %s degrees",
                    cityText.getText().toString(), currentTemp), Toast.LENGTH_SHORT).show();
        }
    }

    public void handleForecastResult(WeatherForecast forecast) {
        Log.d("MainActivity", "Have response From forecast API");
        if (forecast == null) {
            Log.d("MainActivity", "No valid result from forecast API");
            Toast.makeText(this, "Error not valid data from forecast API",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.d("MainActivity", "Valid data form forecast API");

            // creating the list view of forecast descriptions for the city
            List<WeatherForecastItem> items = forecast.getForecastItems();
            List<String> descriptions = new ArrayList<>();
            for (WeatherForecastItem i : items) {
                if (i.getDescriptions().size()>0) {
                    descriptions.add(i.getDescriptions().get(0).getDescription());
                }
            }

            //display the forecast descriptions in the listview.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,descriptions);

            forecastListView.setAdapter(adapter);

        }
    }
}
