package com.example.acer.finder;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.string.ok;

/**
 * Created by acer on 27.08.2017.
 */

public class RecyclerActivity extends RecyclerView.Adapter<RecyclerActivity.ViewHolder>{

    ArrayList<Results> list1;
    Context c;
    RecyclerView rView;
    MapsActivity mapsActivity;
    public RecyclerActivity(ArrayList<Results> list1,Context c,RecyclerView rView ,MapsActivity mapsActivity)
    {
        this.list1=list1;
        this.c=c;
        this.rView=rView;
        this.mapsActivity=mapsActivity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(c);
       View v= inflater.inflate(R.layout.recyclerlayout,parent,false);
       v.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               OkHttpClient okHttpClient=new OkHttpClient();
               String id= list1.get(rView.getChildLayoutPosition(v)).getPlace_id();
               Request request=new Request.Builder().url("https://maps.googleapis.com/maps/api/place/details/json?placeid="+id+"&key=AIzaSyAw1OigZp0ibhshMjSsP1yScduuorpCl1U").build();
               okHttpClient.newCall(request).enqueue(new Callback() {
                   @Override
                   public void onFailure(Call call, IOException e) {

                   }

                   @Override
                   public void onResponse(Call call, Response response) throws IOException {


                       String s=response.body().string();
                       Log.e("details",s);
                       Gson gson=new Gson();
                       OuterDetail outerDetail=gson.fromJson(s,OuterDetail.class);


                      final String phoneno=outerDetail.getResult().getInternational_phone_number();
                       Log.e("phoneno",phoneno);

                      // mapsActivity.runOnUiThread(new Runnable() {
                          // @Override
                           //public void run() {


                             // AlertDialog builder=  new AlertDialog.Builder(c).setMessage(phoneno).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                //  @Override
                                 // public void onClick(DialogInterface dialog, int which) {

                                 // }
                            //  }).create();;




                             //  builder.show();
                          // }
                      // });

                   }
               });
           }
       });
        ViewHolder holder=new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Results results =list1.get(position);
        Log.e("tag",results.getName());
       holder.name.setText(results.getName()
       );




    }

    @Override
    public int getItemCount() {
        return list1.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
       TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);

        }
    }

}
