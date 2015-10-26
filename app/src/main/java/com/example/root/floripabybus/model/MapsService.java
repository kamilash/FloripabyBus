package com.example.root.floripabybus.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class MapsService {

    public String getStreetNameByPosition(String latitude, String longitude) {

        final String CONTENT_TYPE_LABEL = "Content-Type";
        final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
        final String URL_STRING = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

        String response;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(URL_STRING + latitude + "," + longitude);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            while ((response = in.readLine()) != null) {
                builder.append(response);
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(builder);
        Log.d("Main", "yaaayyy");

        assert urlConnection != null;
        urlConnection.disconnect();

        return responseParserStreet(builder.toString());

    }

    private String responseParserStreet(String response) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);
        String street = null;
        try {
            JSONArray results = jsonObject.getJSONArray("results");
            JSONObject r = results.getJSONObject(0);
            JSONArray addressComponentsArray = r.getJSONArray("address_components");
            for (int x = 0; x < addressComponentsArray.length(); x++) {
                JSONObject addressComponents = addressComponentsArray.getJSONObject(x);
                JSONArray typesArray = addressComponents.getJSONArray("types");
                String types = typesArray.getString(0);
                if (Objects.equals(types, "route")) {
                    street = addressComponents.getString("long_name");
                    System.out.println(street);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return street;
    }
}
