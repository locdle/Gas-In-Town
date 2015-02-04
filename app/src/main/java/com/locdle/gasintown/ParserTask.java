package com.locdle.gasintown;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

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
            JSONObject jObject = new JSONObject(jsonData[0]);
            JSONParser parser = new JSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<Station> stationList) {
        for (int i = 0; i < stationList.size(); i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(stationList.get(i).getLatLng())
                    .title(stationList.get(i).getRegularPrice()));
        }

    }
}