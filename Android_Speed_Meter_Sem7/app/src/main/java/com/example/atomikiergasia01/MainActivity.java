package com.example.atomikiergasia01;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    SharedPreferences preferences;
    EditText editText;
    SQLiteDatabase db;
    TextView textView;
    LocationManager locationManager;
    Button set_speed_limit;
    Button check_speed_violations;
    Button activate;
    Button deactivate;
    Button speed_limit_violations;
    public TextView warning;
    public TextView limit_txt;

    private static final int RECORDING_RESULT = 1000;

//static variables used to monitor states
    public static boolean created_button = false; //monitor whether we ve dynamically created a button to save a speed limit
    public static boolean detected_speed_violation = false; // boolean to distinct between states of speed violations ( No violation/ Violated a limit/ Over speed limit and detected_speed_violation
    // -> True then the speed limit is still getting violated
    public static String x = "0"; //save the lat when a violation occured
    public static String y =  "0"; //save the lgt when a violation occured
    public static String speed_v =  "0";  //save the top speed of a violation
    public static Long timestamp;
    public static TTS tts;


    public static final String SHARED_PREFS = "speed_limit";
    // Initialise components and event listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        limit_txt = findViewById(R.id.txt4);

        warning = findViewById(R.id.textView3);
        activate = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        check_speed_violations = findViewById(R.id.button4);
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.constraint_layout);
        ConstraintSet set = new ConstraintSet();
        db = openOrCreateDatabase("GeoDB", Context.MODE_PRIVATE,null);
        deactivate = findViewById(R.id.button6);
        tts = new TTS(this);
        db.execSQL("CREATE TABLE IF NOT EXISTS Location(timestamp INTEGER ,latitude TEXT,longtitude TEXT, speed TEXT )");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        //check if the key exists
        if(preferences.contains("limit")){
            limit_txt.setText("Limit: " + String.valueOf(preferences.getFloat("limit", 0)));
        }
        set_speed_limit = findViewById(R.id.button2);
        speed_limit_violations = findViewById(R.id.button4);
        check_speed_violations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity2.class);

                startActivity(intent);
            }
        });
//Set event listener to the set speed limit button in order to save  a speed limit
        set_speed_limit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                if(created_button == false) {
//Programmatically create components to save a speed limit in sharedpreferences

                    EditText editText = Create_Component.create_EditText(set_speed_limit.getWidth() + 20,set_speed_limit.getHeight() - 10,
                            R.color.black, getApplicationContext(), constraintLayout, set_speed_limit.getLeft(), set_speed_limit.getTop() + 200, set );

                    Button button = Create_Component.create_Button(set_speed_limit.getWidth() , set_speed_limit.getHeight(),
                            R.color.purple_other, getApplicationContext(), constraintLayout,set_speed_limit.getLeft() + 9, set_speed_limit.getTop() + 400, set );
                    //use the boolean created_button in order to create only 1 at a time
                    created_button = true;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String limit = editText.getText().toString();
                            try {
                                float limit_f = Float.parseFloat(limit);
                                save(limit_f);
                                created_button = false;
                                constraintLayout.removeView(button);
                                constraintLayout.removeView(editText);

                            }catch (Exception NumberFormatException){
                                editText.setText("Invalid Input!");
                            }
                        }
                    });

                }

            }
        });
    }
public void check_speed_violations(View view){
        Intent intent = new Intent(view.getContext(), MainActivity2.class);
        startActivity(intent);
}
//Based on the TTS results perform button actions
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RECORDING_RESULT && resultCode==RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches.contains("activate"))
                activate.performClick();
            if (matches.contains("deactivate"))
                deactivate.performClick();
            if (matches.contains("speed limit violations"))
                speed_limit_violations.performClick();
        }

    }
// onpause close the location manager updates
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void deactivate(View view){
        textView.setText("Inactive");
        locationManager.removeUpdates(this);
    }

//the method that the activate button uses to activate the location manager
    public void gps(View view) {
//check the permissions we ve written on the manifest xml that have to do with the Location Manager (ACCESS_FINE_LOCATION, etc...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},234);
            return;
        }
        //activate the location manager. minTime,minDist = 0 to give updateds constantly
        textView.setText("0.0");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);}

        //standard location manager methods
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLocationChanged(Location location) {
//get the speed
        double speed = location.getSpeed();
//check for a speed limit
        textView.setText(String.valueOf(speed));
        if(preferences.contains("limit")){
            float limit = preferences.getFloat("limit", 0);
            // save the timestamp from when the speed violation occured
            if(speed > limit ){
//initial point of speed violation
                if(!detected_speed_violation){
                    speak();
                    warning.setText("Danger!Slow down!");
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    timestamp = System.currentTimeMillis()/1000;
                    x = String.valueOf(location.getLatitude());
                    y = String.valueOf(location.getLongitude());
                    detected_speed_violation = true; }
            }
            // keep values while the speed limit is being violated
            if(speed > limit && detected_speed_violation){
                speed_v = String.valueOf(speed);

            //Once the speed has dropped below out limit but we previously detected a speed violation
            //save the last speed we recorded when the user was violating speed limits
            }else if(speed <= limit && detected_speed_violation){
                save(x, y, timestamp, speed_v);
                detected_speed_violation = false;
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                warning.setText("");
            }

        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
//save values to DB
    public void save(String x, String y, Long time, String speed){
        ContentValues values = new ContentValues();
        values.put("timestamp", time);
        values.put("latitude", String.valueOf(x));
        values.put("longtitude", String.valueOf(y));
        values.put("speed", String.valueOf(speed));
        db.insert("Location",null, values);
    }
//save values to SharedPreferences
    public void save(float limit){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("limit", limit );
        editor.apply();
        limit_txt.setText("Limit: " + String.valueOf(preferences.getFloat("limit", 0)));
    }
//use the tts to speak
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speak(){
        tts.speak("Danger! Slow down!");
    }
//Use TTS to perform Activation/Deactivation and check limit violations
    public void recognize(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something!");
        startActivityForResult(intent,RECORDING_RESULT);
    }
}
