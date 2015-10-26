package com.example.root.floripabybus.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.floripabybus.R;
import com.example.root.floripabybus.presenter.MapsPresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapClickListener, IMapsView {

    private final static String MAPS_MESSAGE = "com.example.root.floripabybus.MAPS_MESSAGE";
    private final static String HOW_TO = "Tap on the map to select street :-)";
    private final static double FLORIPA_LATITUDE = -27.5954;
    private final static double FLORIPA_LONGITUDE = -48.5480;

    private GoogleMap mMap;
    private Marker marker;

    private MapsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        presenter = new MapsPresenter(this);

        final Button buttonOk = (Button) findViewById(R.id.button);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMapMarker();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void showMapMarker() {
        if (marker != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(MAPS_MESSAGE, marker.getSnippet());
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(getApplicationContext(), HOW_TO, duration).show();
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(FLORIPA_LATITUDE, FLORIPA_LONGITUDE));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.setOnMapClickListener(MapsActivity.this);
    }

    @Override
    public void onMapClick(LatLng point) {
        System.out.println(point);
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(point).title("Street"));

        presenter.loadStreetName(String.valueOf(point.latitude), String.valueOf(point.longitude));
    }

    @Override
    public void setStreetName(String street) {
        marker.setSnippet(street);
        marker.showInfoWindow();
    }
}
