package com.example.acer.finder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Handler;

import me.relex.circleindicator.CircleIndicator;

public class StartActivity extends AppCompatActivity {

        AddFloatingActionButton FindDoctor ;
    AddFloatingActionButton TrackActivity;
    AddFloatingActionButton  SocialTab;


    private static ViewPager mPager;
    ArrayList<String>list1;
    private static int currentPage = 0;
    private static final Integer[] SliderPics = {R.drawable.battary_low, R.drawable.charging};
    private ArrayList<Integer> Spics = new ArrayList<Integer>();
             FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        list1=new ArrayList<>();
        list1.add("Diets don't work, a change in lifestyle is necessary.");
        list1.add("Water is the driving force of nature as well as your body.");
        list1.add("Early to bed, early to rise make man healthy and wise.");
        list1.add("Do yoga, and you'll be more focussed within few minutes.");
        list1.add("Apples have antioxidant named flavonoids that reduces the risk of diabetes and asthma.");
        list1.add("Blackgrapes can help in balaning your blood pressure.");
        list1.add("Eggs not only are protein boosters but your mtabolism boosters too.");
        list1.add("Let peaches handle your immune.");
        list1.add("Almonds are brain friendly, and also helps in weight loss.");
        list1.add("Wanna lose weight,add cucumbers to your daily diet.");
        list1.add("Tomato a vegetable but originally a fruit,boosters of your vitamin C.");
        list1.add("Oatmeal, a new trend in daily routine.");
        list1.add("Yes, you can have few pieces of dark chocolate, they cause no harm.");
        list1.add("Less known green coffee, a perfect cup of awesomeness.");
        list1.add("No elevator to success, take stairs hence.");
        list1.add("Don't ignore Vitamin D deficiency,look how sun shines for you.");
        list1.add("Your perfect morning elixir,water,lemon and honey.");
        list1.add("Don't rely on supplements, search those nutrients in real today.");
        list1.add("Keep salt off your dining, and hence live longer.");
        list1.add("Ginger makes your gut happy.");
        Random randi = new Random();
        final int  ni = randi.nextInt(list1.size());

        FindDoctor = (AddFloatingActionButton)findViewById(R.id.ChooseButton);
        FindDoctor.setImageResource(R.drawable.ic_search_black_24dp);
        FindDoctor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF57C4E9")));
           frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
       TrackActivity = (AddFloatingActionButton)findViewById(R.id.ChooseButton1);
        TrackActivity.setImageResource(R.drawable.ic_directions_run_black_24dp);
        TrackActivity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF57C4E9")));

      SocialTab = (AddFloatingActionButton)findViewById(R.id.ChooseButton2);
        SocialTab.setImageResource(R.drawable.ic_people_black_24dp);
        SocialTab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF57C4E9")));
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu)findViewById(R.id.fab_menu);
        SocialTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StartActivity.this, NotificationActivity.class);
                i.putExtra("popout",list1.get(ni));
                startActivity(i);
            }
        });



        FindDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent mapLauncher = new Intent(StartActivity.this,MapsActivity.class);
                startActivity(mapLauncher);



            }
        });


        TrackActivity.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent trackLauncher = new Intent(StartActivity.this,MainActivity.class);
                startActivity(trackLauncher);
            }
        });

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {


            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);

                frameLayout.setOnTouchListener(new View.OnTouchListener(){


                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });


            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });



        Intent i=new Intent(getBaseContext(),TaskReceiver.class);

        i.putExtra("HEALTH",list1.get(ni));
        PendingIntent pi=PendingIntent.getBroadcast(getBaseContext(),1,i,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pi);
        FragmentManager fragmentManager = getSupportFragmentManager();
  frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameBox, new SliderFragment());
        fragmentTransaction.commit();


    }

}