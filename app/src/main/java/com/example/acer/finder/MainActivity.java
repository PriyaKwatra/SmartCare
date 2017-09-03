package com.example.acer.finder;

import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import android.os.Handler;

import java.util.logging.LogRecord;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity


implements OnDataPointListener,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    EditText t1;
    int missing;
    private static ViewPager mPager;
    FloatingActionButton send;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.battary_low,R.drawable.charging,R.drawable.startpic};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    TextView data;

    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;




    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
               data = (TextView)findViewById(R.id.count);
                 t1 = (EditText)findViewById(R.id.target);
            t1.setText("0");
            send = (FloatingActionButton)findViewById(R.id.messaged);
                     send.setOnClickListener(new View.OnClickListener(){
                         @Override
                         public void onClick(View v) {


                             Intent sendIntent = new Intent();
                             sendIntent.setAction(Intent.ACTION_SEND);
                             sendIntent.putExtra(Intent.EXTRA_TEXT, "Target set :" + t1.getText().toString() + "Steps taken " + data.getText().toString());
                             sendIntent.setType("text/plain");
                             startActivity(sendIntent);


                         }
                     });
            if (savedInstanceState != null) {
                authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
            }

            mApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Fitness.SENSORS_API)
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

        }

        @Override
        public void onConnected(Bundle bundle) {
            DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                    .setDataTypes( DataType.TYPE_STEP_COUNT_CUMULATIVE ,DataType.TYPE_CYCLING_PEDALING_CUMULATIVE,DataType.AGGREGATE_CALORIES_EXPENDED)

                    .setDataSourceTypes( DataSource.TYPE_RAW )
                    .build();

            ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
                @Override
                public void onResult(DataSourcesResult dataSourcesResult) {
                    for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                        if( DataType.TYPE_STEP_COUNT_CUMULATIVE.equals( dataSource.getDataType() ) ) {
                            registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                        }


                    }
                }
            };

            Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                    .setResultCallback(dataSourcesResultCallback);

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

    @Override
    protected void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove( mApiClient, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }



    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {

        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType( dataType )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add( mApiClient, request, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e( "GoogleFit", "SensorApi successfully added" );
                        }
                    }
                });
    }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {



            if( !authInProgress ) {
                try {
                    authInProgress = true;
                    connectionResult.startResolutionForResult( MainActivity.this, REQUEST_OAUTH );
                } catch(IntentSender.SendIntentException e ) {

                }
            } else {
                Log.e( "GoogleFit", "authInProgress" );
            }














        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                if( !mApiClient.isConnecting() && !mApiClient.isConnected() ) {
                    mApiClient.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }





            @Override
            public void onDataPoint(DataPoint dataPoint) {
                for( final Field field : dataPoint.getDataType().getFields() ) {
                    Log.e("TAG",dataPoint.getDataType().getFields().size() + "");
                    final Value value = dataPoint.getValue( field );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                              if(field.getName().equals("steps"))
                            {

                                data.setText(value + "");

                                missing = Integer.parseInt(t1.getText().toString())-(Integer.parseInt(data.getText().toString()));
                                if(missing<0)
                                {

                                }
                                else{
                                Toast.makeText(getApplicationContext(), "You're Missing your Target by " + missing + "steps",Toast.LENGTH_SHORT).show();}
                            }
                            else
                              {
                                  Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                              }
                        }
                    });
                }






        }




















    }
















