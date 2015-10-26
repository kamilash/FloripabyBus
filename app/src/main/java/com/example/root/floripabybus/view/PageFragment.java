package com.example.root.floripabybus.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.root.floripabybus.R;

import java.util.ArrayList;


public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    String[] roads;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        ArrayAdapter<String> adapter = null;

        ArrayList<String> weekdays = (ArrayList<String>) ((DetailsActivity)getActivity()).getTimetable().getWeekdays();
        ArrayList<String> saturday = (ArrayList<String>) ((DetailsActivity)getActivity()).getTimetable().getSaturday();
        ArrayList<String> sunday = (ArrayList<String>) ((DetailsActivity)getActivity()).getTimetable().getSunday();

        if (mPage == 1) {
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, ((DetailsActivity) getActivity()).getRoute());
        } else if (mPage == 2) {
            if(weekdays != null) {
                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, (weekdays));
            }
        } else if (mPage == 3) {
            if(saturday != null) {
                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, (saturday));
            }
        } else {
            if(sunday != null) {
                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, (sunday));
            }
        }

        listView.setAdapter(adapter);

        return view;
    }
}