package com.example.root.floripabybus.view;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.root.floripabybus.R;
import com.example.root.floripabybus.model.Route;
import com.example.root.floripabybus.model.RoutesService;
import com.example.root.floripabybus.model.Timetable;


public class DetailsActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.root.floripabybus.MESSAGE";
    private Route route;
    String [] roads;
    Timetable timetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        route = (Route) getIntent().getSerializableExtra(DetailsActivity.EXTRA_MESSAGE);

        new getTimetableByRouteId().execute(route.getId());
        new getStopsByRouteId().execute(route.getId());

        TextView textView = (TextView) findViewById(R.id.routeName);
        textView.setText(route.getName());
    }

    private class getTimetableByRouteId extends AsyncTask<String, Void, Timetable> {

        protected Timetable doInBackground(String... parameters) {
            RoutesService service = new RoutesService();
            Integer routeId = Integer.parseInt(parameters[0]);
            return service.getTimetableByRouteId(routeId);
        }

        @Override
        protected void onPostExecute(Timetable result) {
            timetable = result;
        }
    }

    private class getStopsByRouteId extends AsyncTask<String, Void, String[]> {

        protected String[] doInBackground(String... parameters) {
            RoutesService service = new RoutesService();
            Integer routeId = Integer.parseInt(parameters[0]);
            return service.getStopsByRouteId(routeId);
        }

        @Override
        protected void onPostExecute(String[] result) {

            roads = result;
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(),
                    DetailsActivity.this));

            TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public String[] getRoute() {
        return roads;
    }

    public Timetable getTimetable(){
        return timetable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
