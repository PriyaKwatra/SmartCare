package com.example.acer.finder;

import android.graphics.Interpolator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            okHttpClient=new OkHttpClient();
               double longt= latLng.longitude;
                double latt=latLng.latitude;

                Request request=new Request.Builder().url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latt+","+longt+"&radius=500&type=restaurant&key=AIzaSyAw1OigZp0ibhshMjSsP1yScduuorpCl1U&sensor=true").build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String s=response.body().string();
                        Log.e("tag",s);
                        Gson gson=new Gson();
                        OuterClass outerClass =gson.fromJson(s,OuterClass.class);
                       final ArrayList<Results> names=outerClass.getResults();
                        Log.e("tagfgfg",names.size()+"");

                     final   RecyclerView recyclerView =(RecyclerView)findViewById(R.id.recycler);
                        final RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getBaseContext());



                        MapsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(new RecyclerActivity(names,getBaseContext(),recyclerView,MapsActivity.this));
                            }
                        });
                    }
                });
            }
        });


    }
}
