package com.locdle.gasintown;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by locle on 1/28/15.
 */
public class Station {
    protected String regularPrice;
    protected int id;
    protected LatLng latLng;
    protected String station;


    public Station(String regularPrice, int id, LatLng latLng, String station) {
        this.regularPrice = regularPrice;
        this.id = id;
        this.latLng = latLng;
        this.station = station;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public int getId() {
        return id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getStation() {
        return station;
    }
}
