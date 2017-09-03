package com.example.acer.finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class HospitalListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        Intent i = getIntent();
        ArrayList<Results> results = i.getParcelableArrayListExtra("Hospitals");

        ArrayList<InnerDetail> innerDetails = i.getParcelableArrayListExtra("Detail");

        Log.e("IPO", innerDetails.size() + "");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(results, innerDetails, getBaseContext(), recyclerView, HospitalListActivity.this));
    }
}
