package com.example.root.floripabybus.model;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class RoutesService {

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    public List<Route> getRoutesByStopName(String stopName) {
        String response = "";

        try {
            JSONObject parametersObject = new JSONObject();
            JSONObject stopNameObject = new JSONObject();

            if(!stopName.isEmpty()) {
                stopName = "%" + stopName + "%";
            }
            stopNameObject.put("stopName", stopName);
            parametersObject.put("params", stopNameObject);

            String requestBody = parametersObject.toString();

            URL url = new URL("https://api.appglu.com/v1/queries/findRoutesByStopName/run");
            response = getResponse(url, requestBody);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return responseParserRoutes(response);
    }

    public String[] getStopsByRouteId(Integer routeId) {
        String response = "";

        try {
            JSONObject parametersObject = new JSONObject();
            JSONObject routeIdObject = new JSONObject();
            routeIdObject.put("routeId", routeId);
            parametersObject.put("params", routeIdObject);

            String requestBody = parametersObject.toString();

            URL url = new URL("https://api.appglu.com/v1/queries/findStopsByRouteId/run");
            response = getResponse(url, requestBody);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return responseParserRoads(response);
    }

    public Timetable getTimetableByRouteId(Integer routeId) {
        String response = "";

        try {
            JSONObject parametersObject = new JSONObject();
            JSONObject routeIdObject = new JSONObject();
            routeIdObject.put("routeId", routeId);
            parametersObject.put("params", routeIdObject);

            String requestBody = parametersObject.toString();

            URL url = new URL("https://api.appglu.com/v1/queries/findDeparturesByRouteId/run");
            response = getResponse(url, requestBody);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return responseParserTimetable(response);
    }

    private static String getResponse(URL url, String requestBody) throws IOException {
        HttpsURLConnection urlConnection = null;
        urlConnection = (HttpsURLConnection) url.openConnection();
        String userCredentials = "WKD4N7YMA1uiM8V:DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68";
        String basicAuth = "Basic " + new String(Base64.encodeToString(userCredentials.getBytes("UTF-8"), Base64.NO_WRAP));
        urlConnection.setRequestProperty("Authorization", basicAuth);
        urlConnection.setRequestProperty("X-AppGlu-Environment", "staging");
        urlConnection.setRequestMethod("POST");
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON);

        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
        wr.write(requestBody);
        wr.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String response = in.readLine();
        System.out.println(response);
        Log.d("Main", "yaaayyy");

        assert urlConnection != null;
        urlConnection.disconnect();

        return response;
    }

    private static List<Route> responseParserRoutes(String response) {
        Route[] routeList = {};
        try {
            JSONArray routes = (new JSONObject(response)).getJSONArray("rows");
            System.out.println(routes);
            routeList = new Route[routes.length()];
            for (int i = 0; i < routes.length(); i++) {
                routeList[i] = new Route(routes.getJSONObject(i).getString("longName"),
                        routes.getJSONObject(i).getString("shortName"),
                        routes.getJSONObject(i).getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Arrays.asList(routeList);
    }

    private static Timetable responseParserTimetable(String response) {
        Timetable timetable = new Timetable();
        List<String> weekdays;
        List<String> saturday;
        List<String> sunday;

        try {
            JSONArray times = (new JSONObject(response)).getJSONArray("rows");
            weekdays = new ArrayList<String>();
            saturday = new ArrayList<String>();
            sunday = new ArrayList<String>();
            for (int i = 0; i < times.length(); i++) {
                if (Objects.equals(times.getJSONObject(i).getString("calendar"), "WEEKDAY")) {
                    weekdays.add(i, times.getJSONObject(i).getString("time"));
                   // times.remove(i);
                }
            }
            System.out.println(times);
            int y = 0;
            for (int i = 0; i < times.length(); i++) {
                if (Objects.equals(times.getJSONObject(i).getString("calendar"), "SATURDAY")) {
                    saturday.add(y, times.getJSONObject(i).getString("time"));
                   // times.remove(y);
                    y++;
                }
            }
            for (int i = 0; i < times.length(); i++) {
                int x = 0;
                if (Objects.equals(times.getJSONObject(i).getString("calendar"), "SUNDAY")) {
                    sunday.add(x, times.getJSONObject(i).getString("time"));
                  //  times.remove(x);
                    x++;
                }
            }

            timetable.setSaturday(saturday);
            timetable.setSunday(sunday);
            timetable.setWeekdays(weekdays);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timetable;
    }

    private static String[] responseParserRoads(String response) {
        String[] values = {};
        try {
            JSONArray roads = (new JSONObject(response)).getJSONArray("rows");
            System.out.println(roads);
            values = new String[roads.length()];
            for (int i = 0; i < roads.length(); i++) {
                values[i] = roads.getJSONObject(i).getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return values;
    }
}