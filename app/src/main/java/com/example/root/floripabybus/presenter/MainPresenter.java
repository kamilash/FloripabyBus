package com.example.root.floripabybus.presenter;

import android.os.AsyncTask;

import com.example.root.floripabybus.model.Route;
import com.example.root.floripabybus.model.RoutesService;
import com.example.root.floripabybus.view.IMainView;

import java.util.List;


public class MainPresenter {

    private IMainView mMainView;

    public MainPresenter(IMainView View) {

        mMainView = View;
    }

    public void loadRoutes (String streetName) {

        new loadRoutesByStreetName().execute(streetName);
    }

    private class loadRoutesByStreetName extends AsyncTask<String, Void, List<Route>> {
        @Override
        protected List<Route> doInBackground(String... parameters) {
            RoutesService service = new RoutesService();
            String stopName = parameters[0];
            return service.getRoutesByStopName(stopName);
        }

        @Override
        protected void onPostExecute(final List<Route> result) {

            mMainView.setRouteList(result);
        }
    }
}
