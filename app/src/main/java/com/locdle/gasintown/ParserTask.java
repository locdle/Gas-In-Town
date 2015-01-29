package com.locdle.gasintown;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by locle on 1/28/15.
 */
public class ParserTask extends AsyncTask<String, Integer, List<Station>> {

    protected GoogleMap mMap;

    public ParserTask(GoogleMap mMap) {
        this.mMap = mMap;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<Station> doInBackground(String... jsonData) {

        List<Station> routes =  new ArrayList<>();
        try {
            JSONArray jObject = new JSONArray(jsonData[0]);
//            JSONParser parser = new JSONParser();
//
//            // Starts parsing data
//            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<Station> result) {


    }
}