package com.example.root.floripabybus.presenter;

import android.os.AsyncTask;

import com.example.root.floripabybus.model.MapsService;
import com.example.root.floripabybus.view.IMapsView;


public class MapsPresenter {

    private IMapsView mMapsView;

    public MapsPresenter(IMapsView View) {

        mMapsView = View;
    }
    public void loadStreetName (String latitude, String longitude) {
        new loadStreetNameByPosition().execute(latitude, longitude);
    }

    private class loadStreetNameByPosition extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... parameters) {
            MapsService service = new MapsService();
            return service.getStreetNameByPosition(parameters[0], parameters[1]);
        }

        @Override
        protected void onPostExecute(final String result) {

            mMapsView.setStreetName(result);
        }
    }
}
