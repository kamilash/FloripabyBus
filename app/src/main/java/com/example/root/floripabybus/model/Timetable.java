package com.example.root.floripabybus.model;

import java.util.List;


public class Timetable {
    private List<String> weekdays = null;
    private List<String> saturday = null;
    private List<String> sunday = null;

    public List<String> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<String> weekdays) {

        this.weekdays = weekdays;
    }

    public List<String> getSaturday() {

        return saturday;
    }

    public void setSaturday(List<String> saturday) {

        this.saturday = saturday;
    }

    public List<String> getSunday() {

        return sunday;
    }

    public void setSunday(List<String> sunday) {

        this.sunday = sunday;
    }
}
