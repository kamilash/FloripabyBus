package com.example.root.floripabybus.view;

import android.app.Activity;
import android.app.ListActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.root.floripabybus.R;
import com.example.root.floripabybus.model.Route;
import com.example.root.floripabybus.presenter.MainPresenter;

import java.util.List;


public class MainActivity extends ListActivity implements IMainView {

    private final static String EXTRA_MESSAGE = "com.example.root.floripabybus.MESSAGE";
    private final static String MAPS_MESSAGE = "com.example.root.floripabybus.MAPS_MESSAGE";
    private final static String EMPTY_RESPONSE = "No results found!";
    private final static int PICK_STREET_REQUEST = 1;

    private ListView listview;
    private ProgressBar spinner;
    private Button buttonSearch;
    private EditText mEdit;
    private Button buttonMap;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        hideProgress();

        presenter = new MainPresenter(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchStreetOnRoutes(mEdit.getText().toString());
            }
        });

        buttonMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivityForResult(intent, PICK_STREET_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            String mapRoad = (String) data.getSerializableExtra(MainActivity.MAPS_MESSAGE);
            mEdit.setText(mapRoad);
            searchStreetOnRoutes(mapRoad);
        }
    }

    @Override
    public void setRouteList(final List<Route> routes) {
        hideProgress();
        if(routes.isEmpty()) {
            showEmptyResponseMessage();
        }
        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(MainActivity.this,
                android.R.layout.simple_list_item_1, routes);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_MESSAGE, routes.get(position));
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        listview = (ListView) findViewById(android.R.id.list);
        buttonSearch = (Button) findViewById(R.id.button);
        buttonMap = (Button) findViewById(R.id.buttonMap);
        mEdit   = (EditText)findViewById(R.id.inputSearch);
    }

    private void searchStreetOnRoutes(String street) {
        presenter.loadRoutes(street);
        showProgress();
        hideKeyboard(this);
    }

    private void showProgress() {
        spinner.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
    }

    private void hideProgress(){
        spinner.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showEmptyResponseMessage() {
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(getApplicationContext(), EMPTY_RESPONSE, duration).show();
    }
}
