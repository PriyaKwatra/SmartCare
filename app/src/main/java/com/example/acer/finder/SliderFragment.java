package com.example.acer.finder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class SliderFragment extends Fragment {
   Context c;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.photo1,R.drawable.photo2,R.drawable.photo3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    public SliderFragment() {
        // Required empty public constructor
    }

    public static SliderFragment newInstance(Context c) {

        Bundle args = new Bundle();


        SliderFragment fragment = new SliderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);
             View v =      inflater.inflate(R.layout.fraglayout,container,false);
        mPager = (ViewPager)v.findViewById(R.id.pager);
        mPager.setAdapter(new PAdapter(this.getActivity(),XMENArray));
        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        return v;
    }



    }

    // TODO: Rename method, update argument and hook method into UI event


