package com.example.acer.finder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Interpolator;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    ArrayList<InnerDetail> innerDetails = new ArrayList<>();
    private GoogleMap mMap;
    FloatingActionButton searchhos;
    ArrayList<Results> names;
    OkHttpClient okHttpClient;
    Location mlocation;
    LocationRequest mLocationRequest;
    double lati;
    double longi;
    GoogleApiClient mGoogleApiClient;
    MarkerOptions markerOptions;
    Marker marker = null;
    ArrayList<OuterDetail> outerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchhos = (FloatingActionButton) findViewById(R.id.searchhos);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).
                addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        markerOptions = new MarkerOptions();
        outerDetails = new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                okHttpClient = new OkHttpClient();


                Request request = new Request.Builder().url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latLng.latitude + "," + latLng.longitude + "&radius=1000&type=doctor&key=AIzaSyAw1OigZp0ibhshMjSsP1yScduuorpCl1U&sensor=true").build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String s = response.body().string();
                        Log.e("tag", s);
                        Gson gson = new Gson();
                        OuterClass outerClass = gson.fromJson(s, OuterClass.class);
                        names = outerClass.getResults();
                        Log.e("tagfgfg", names.size() + "");

                        MyAsync async = new MyAsync();
                        async.execute(names);


                    }
                });

            }
        });
        searchhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpClient = new OkHttpClient();


                Request request = new Request.Builder().url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lati + "," + longi + "&radius=1000&type=doctor&key=AIzaSyAw1OigZp0ibhshMjSsP1yScduuorpCl1U&sensor=true").build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String s = response.body().string();
                        Log.e("tag", s);
                        Gson gson = new Gson();
                        OuterClass outerClass = gson.fromJson(s, OuterClass.class);
                        names = outerClass.getResults();
                        Log.e("tagfgfg", names.size() + "");

                        MyAsync async = new MyAsync();
                        async.execute(names);


                    }
                });
            }

        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mlocation != null) {
            lati = mlocation.getLatitude();
            longi = mlocation.getLongitude();

        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(5000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {

            lati = location.getLatitude();
            longi = location.getLongitude();
            Log.e("tag", lati + " " + longi + "");
            LatLng latLng = new LatLng(lati, longi);

            markerOptions.position(latLng).title("current position");
            if (marker == null) {

            } else {
                marker.remove();
            }


            marker = mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();


        }
    }

    class MyAsync extends AsyncTask<ArrayList<Results>, Void, ArrayList<OuterDetail>> {
        @Override
        protected void onPostExecute(ArrayList<OuterDetail> aVoid) {
            super.onPostExecute(aVoid);


        }

        @Override
        protected ArrayList<OuterDetail> doInBackground(final ArrayList<Results>... params) {

            for (Results x : params[0]) {
                Log.e("OURID", x.getPlace_id());
                OkHttpClient okHttpClient = new OkHttpClient();
                String id = x.getPlace_id();
                Request request = new Request.Builder().url("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + id + "&key=AIzaSyAw1OigZp0ibhshMjSsP1yScduuorpCl1U").build();


                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override

                    public void onFailure(Call call, IOException e) {

                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {


                        String s = response.body().string();
                        Log.e("details", s);
                        Gson gson = new Gson();
                        OuterDetail outerDetail = gson.fromJson(s, OuterDetail.class);
                        Log.e("ADDRESS", outerDetail.getResult().getFormatted_address());

                        innerDetails.add(outerDetail.getResult());
                        if (innerDetails.size() == params[0].size()) {

                            Log.e("GURLEEN", "GURLEEN");
//                            Log.e("DDDD", outerDetails.get(4).getResult().getFormatted_address());
                            Intent i = new Intent(MapsActivity.this, HospitalListActivity.class);
                            i.putParcelableArrayListExtra("Hospitals", names);
                            i.putParcelableArrayListExtra("Detail", innerDetails);
                            startActivity(i);
                        }
                    }
                });
            }

            return outerDetails;
        }

    }

    class MyAsync2 extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }


}







