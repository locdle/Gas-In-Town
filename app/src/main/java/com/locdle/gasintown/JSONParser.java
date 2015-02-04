package com.locdle.gasintown;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loc on 2/3/15.
 */
public class JSONParser {
    /**
     * Parse the json array which download from the web
     * @param jsonObject
     * @return the list of highways which contain the latlng
     */
    public List<Station> parse(JSONObject jsonObject){
        List<Station> stationList =  new ArrayList<>();
        try{
            JSONArray stationsJSONArray = jsonObject.getJSONArray("stations");
            for (int i = 0; i <stationsJSONArray.length() ; i++) {
                //create json object for each index of json array
                JSONObject stationJSONObject = stationsJSONArray.getJSONObject(i);
                
                //get string reg_price and conver it into double
                String regularPrice = stationJSONObject.getString("reg_price");
                
                //get string id and convert it into int
                int id = Integer.valueOf(stationJSONObject.getString("id"));
                
                //get latlng for each station and create Latlng obj
                double lat = Double.valueOf(stationJSONObject.getString("lat"));
                double lng = Double.valueOf(stationJSONObject.getString("lng"));
                LatLng latLng = new LatLng(lat, lng);
                
                //get the station name from the json object
                String station = stationJSONObject.getString("station");
                
                //create a station obj from the json object
                //then add to the list of station
                stationList.add(new Station(regularPrice, id, latLng, station));
                                       
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stationList;
    }
}