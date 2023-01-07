package com.example.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    TextView latitude, longitude, city, temp1, temp2, temp3, temp4, desc1, desc2, desc3, desc4;
    EditText zipCode, countryCode;
    Button button;
    ImageView imageView;
    String zipcode, countrycode, lat, lon, t1, t2, t3, t4, tmp1, tmp2, tmp3, tmp4, d1, d2, d3, d4, tod1, tod2, tod3, tod4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitude = findViewById(R.id.textView2);
        longitude = findViewById(R.id.textView3);
        city = findViewById(R.id.textView4);
        temp1 = findViewById(R.id.textView5);
        temp2 = findViewById(R.id.textView6);
        temp3 = findViewById(R.id.textView8);
        temp4 = findViewById(R.id.textView9);
        desc1 = findViewById(R.id.textView10);
        desc2 = findViewById(R.id.textView11);
        desc3 = findViewById(R.id.textView12);
        desc4 = findViewById(R.id.textView13);
        zipCode = findViewById(R.id.editTextNumber);
        countryCode = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zipcode = String.valueOf(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        countryCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countrycode = String.valueOf(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zipcode.equals("") || countrycode.equals(""))
                    Toast.makeText(MainActivity.this, "The above fields cannot be empty!", Toast.LENGTH_LONG).show();
                else {
                    new DisplayWeather1().execute();
                }
            }
        });

    }

    public class DisplayWeather1 extends AsyncTask<Void, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void ... params) {
            try {
                URL url1 = new URL("http://api.openweathermap.org/geo/1.0/zip?zip=" + zipcode + "," + countrycode + "&appid=c1d6a1c00ef87e7bc9360e95cf041ee8");
                URLConnection uConnect1 = url1.openConnection();
                InputStream inputStream1 = uConnect1.getInputStream();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream1));

                String line1;
                StringBuilder sb1 = new StringBuilder();

                while ((line1 = br1.readLine()) != null) {
                    sb1.append(line1).append('\n');
                }
                br1.close();
                JSONObject weather1 = new JSONObject(sb1.toString());
                return weather1;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject weather1) {
            super.onPostExecute(weather1);
            try {
                lat = String.valueOf(weather1.get("lat"));
                latitude.setText("Latitude: " + lat);
                lon = String.valueOf(weather1.get("lon"));
                longitude.setText("Longitude: " + lon);
                city.setText("City: " + String.valueOf(weather1.get("name")));
                new DisplayWeather2().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public class DisplayWeather2 extends AsyncTask<Void, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void ... params) {
            try {
                URL url2 = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&units=imperial&exclude=current,minutely,daily,alerts&appid=c1d6a1c00ef87e7bc9360e95cf041ee8");
                URLConnection uConnect2 = url2.openConnection();
                InputStream inputStream2 = uConnect2.getInputStream();
                BufferedReader br2 = new BufferedReader(new InputStreamReader(inputStream2));

                String line2;
                StringBuilder sb2 = new StringBuilder();

                while ((line2 = br2.readLine()) != null) {
                    sb2.append(line2).append('\n');
                }
                br2.close();
                JSONObject weather2 = new JSONObject(sb2.toString());
                return weather2;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject weather2) {
            super.onPostExecute(weather2);
            try {

                JSONArray arr = weather2.getJSONArray("hourly");
                tmp1  = String.valueOf(arr.getJSONObject(0).getDouble("temp"));
                tmp2  = String.valueOf(arr.getJSONObject(1).getDouble("temp"));
                tmp3  = String.valueOf(arr.getJSONObject(2).getDouble("temp"));
                tmp4  = String.valueOf(arr.getJSONObject(3).getDouble("temp"));

                if (arr.getJSONObject(0).getDouble("temp") < 33)
                    d1 = "very cold; freezing temperature";
                else if (arr.getJSONObject(0).getDouble("temp") < 60)
                    d1 = "moderate temperature";
                else
                    d1 = "very hot";
                if (arr.getJSONObject(1).getDouble("temp") < 33)
                    d2 = "very cold; freezing temperature";
                else if (arr.getJSONObject(1).getDouble("temp") < 60)
                    d2 = "moderate temperature";
                else
                    d2 = "very hot";
                if (arr.getJSONObject(2).getDouble("temp") < 33)
                    d3 = "very cold; freezing temperature";
                else if (arr.getJSONObject(2).getDouble("temp") < 60)
                    d3 = "moderate temperature";
                else
                    d3 = "very hot";
                if (arr.getJSONObject(3).getDouble("temp") < 33)
                    d4 = "very cold; freezing temperature";
                else if (arr.getJSONObject(3).getDouble("temp") < 60)
                    d4 = "moderate temperature";
                else
                    d4 = "very hot";

                Date date = new Date();
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
                int hour = hour1;
                if (hour > 12) {
                    hour -= 12;
                    tod1 = "PM";
                    t1 = String.valueOf(hour);
                    if (hour + 1 > 12) {
                        hour -= 12;
                        tod2 = "PM";
                    }
                    else
                        tod2 = "AM";
                    t2 = String.valueOf(hour + 1);
                    if (hour + 2 > 12) {
                        hour -= 12;
                        tod3 = "AM";
                    }
                    else
                        tod3 = "PM";
                    t3 = String.valueOf(hour + 2);
                    if (hour + 3 > 12) {
                        hour -= 12;
                        tod4 = "PM";
                    }
                    else
                        tod4 = "AM";
                    t4 = String.valueOf(hour + 3);

                }
                else {
                    tod1 = "AM";
                    t1 = String.valueOf(hour);
                    if (hour + 1 > 12) {
                        hour -= 12;
                        tod2 = "PM";
                    }
                    tod2 = "AM";
                    t2 = String.valueOf(hour + 1);
                    if (hour + 2 > 12) {
                        hour -= 12;
                        tod3 = "PM";
                    }
                    else
                        tod3 = "AM";
                    t3 = String.valueOf(hour + 2);
                    if (hour + 3 > 12) {
                        hour -= 12;
                        tod4 = "PM";
                    }
                    else
                        tod4 = "AM";
                    t4 = String.valueOf(hour + 3);
                }

                if (hour1 < 7 || hour1 > 17)
                    imageView.setImageResource(R.drawable.moon);
                else
                    imageView.setImageResource(R.drawable.sun);

                temp1.setText("Temperature for " + t1 + tod1 + ": " + tmp1 + "F");
                temp2.setText("Temperature for " + t2 + tod2 + ": " + tmp2 + "F");
                temp3.setText("Temperature for " + t3 + tod3 + ":" + tmp3 + "F");
                temp4.setText("Temperature for " + t4 + tod4 + ": " + tmp4 + "F");
                desc1.setText("Description: " + d1);
                desc2.setText("Description: " + d2);
                desc3.setText("Description: " + d3);
                desc4.setText("Description: " + d4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
