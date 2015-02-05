package com.locdle.gasintown;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
//    private LatLng resultLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status != ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
        else{

           /* //Get my current location latlng
            LatLng myCurrentLocation = getCurrentLatLng();
            
            if(myCurrentLocation != null){
                myCurrentLocation = new LatLng(45.509534, -122.681081);
            }

            String downloadURLGeoLocationNearbyGasStations = urlGeoLocationNearbyGasStations(myCurrentLocation.latitude, myCurrentLocation.longitude);

            System.out.println(downloadURLGeoLocationNearbyGasStations);

            DownloadTask downloadTask = new DownloadTask(mMap);

            downloadTask.execute(downloadURLGeoLocationNearbyGasStations);*/

            // Getting reference to btn_find of the layout activity_main
            Button btn_find = (Button) findViewById(R.id.btn_find);

            // Defining button click event listener for the find button
            View.OnClickListener findClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Getting reference to EditText to get the user input location
                    EditText etLocation = (EditText) findViewById(R.id.et_location);

                    // Getting user input location
                    String location = etLocation.getText().toString();

                    if(location!=null && !location.equals("")){
                        new GeocoderTask().execute(location);
                    }
                }
            };

            // Setting button click event listener for the find button
            btn_find.setOnClickListener(findClickListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link # setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            //Enable toolbar so users can use google map navigation
            mMap.getUiSettings().setMapToolbarEnabled(true);

            //load default map onto Portland Oregon
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.509534, -122.681081), 10.0f));

        }
    }

    private String urlGeoLocationNearbyGasStations(double lat, double lng){
        String  url = "http://devapi.mygasfeed.com/";
        String requestUrl = "stations/radius/";
        String latitude = String.valueOf(lat) +"/";
        String longitude = String.valueOf(lng)+"/";
        String distance = "2/";
        String fuel = "reg/";
        String sortby = "price/";
        String apikey = "rfej9napna" + ".json";

        return url + requestUrl + latitude + longitude + distance + fuel + sortby + apikey;
    }

    private LatLng getCurrentLatLng(){
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);
        
        return new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
    }

    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mMap.clear();

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){

                Address address =  addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                LatLng resultLocation = new LatLng(address.getLatitude(), address.getLongitude());

                String downloadURLGeoLocationNearbyGasStations = urlGeoLocationNearbyGasStations(resultLocation.latitude, resultLocation.longitude);

                System.out.println(downloadURLGeoLocationNearbyGasStations);

                DownloadTask downloadTask = new DownloadTask(mMap);

                downloadTask.execute(downloadURLGeoLocationNearbyGasStations);

                // Locate the first location
                if(i==0)
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(resultLocation));
            }
        }
    }
}
