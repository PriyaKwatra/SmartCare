package com.example.acer.finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Intent i=getIntent();
        TextView textView =(TextView)findViewById(R.id.tip);
        if(i.getStringExtra("popout")!=null)
        {
            textView.setText(i.getStringExtra("popout"));
        }
        if(i.getStringExtra("Pending")!=null)
        textView.setText(i.getStringExtra("Pending"));

    }
}
