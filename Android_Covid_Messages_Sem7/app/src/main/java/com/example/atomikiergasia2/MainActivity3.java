package com.example.atomikiergasia2;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements LocationListener {
    //intiialize values
    SQLiteDatabase db;
    public List<String> messages;
    public List<String> codes;
    public ListView listView;
    TextView full_name_txt;
    Cursor cursor;
    LocationManager locationManager;
    public String email;
    public String password;
    public String full_name ;
    public String address ;
    public static int SUCCESFUL_EDIT = 999; //edit personal info result codes
    public static int EDIT_ACTIVITY = 998;
    public static int BACK = 1000;
    private static final int RECORDING_RESULT = 1100; //recording result code
    public static int CREATE_MESSAGE_ACTIVITY = 1001; //create message result codes
    public static int SUCCESFUL_MESSAGE_CREATION = 1002;
    public static int EDIT_MESSAGE_ACTIVITY = 1003;//edit message result codes
    public static int SUCCESFUL_MESSAGE_EDITING = 1004;
    public static String last_selected_code = ""; //used in the onclick listview event to grab the last selected code
    public static TTS tts;
    FirebaseDatabase database_fb;
    public static String user_uid;
    DatabaseReference myRef;
    public String last_sel_msg = "";//used in the onclick listview event to grab the last selected msg description
    public static double longtitude;
    public static double latitude;
    public static long timestamp;
    //buttons
    Button edit_msg;
    Button delete_msg;
    Button create_msg;
    Button send_msg;
    Button go_edit;
//a function that queries the db in order to assign to email/pass/... vars
public void setInfo(){
    cursor = db.rawQuery("SELECT * FROM "+getResources().getString(R.string.table_users)+"  WHERE  email='"+email+"'", null);

    if (cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
            email = String.valueOf(cursor.getString(0));
            password = String.valueOf(cursor.getString(1));
            full_name = String.valueOf(cursor.getString(2));
            address = String.valueOf(cursor.getString(3));
        }}

}
//populate the lsitview with codes and their descriptions
public void populate_listview() {
    codes = new ArrayList<String>();
    messages = new ArrayList<String>();
    //select everything
    cursor = db.rawQuery("SELECT * FROM Messages", null);
    if (cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
            messages.add(String.valueOf(cursor.getString(0)));
            codes.add(String.valueOf(cursor.getString(1)));

        }
        listView.setAdapter(new CovidMsgAdapter(getApplicationContext(), codes, messages));
    }
}

//used to delete a listview item based on the last selected code
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void delete(View view){
        if(last_selected_code != ""){
            Toast.makeText(getApplicationContext(),last_selected_code,Toast.LENGTH_LONG).show();
            String table = "Messages";
            String whereClause = "code=?";
            String[] whereArgs = new String[] { last_selected_code };
            db.delete(table, whereClause, whereArgs);
            startActivity(getIntent());
        }else{
            //Voice message from the TTS if the deletion failed because an item was not selected
            speak("Select a code");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //initialize
        Bundle extras = getIntent().getExtras();
        //required values from mainactivity
        email = extras.getString("email");
        user_uid = extras.getString("user_id");
        go_edit = findViewById(R.id.button6);
        edit_msg = findViewById(R.id.button4);
        delete_msg = findViewById(R.id.button7);
        send_msg = findViewById(R.id.button5);
        create_msg = findViewById(R.id.button8);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        tts = new TTS(this);

        full_name_txt = findViewById(R.id.textView5);

        listView = findViewById(R.id.listview);
        database_fb = FirebaseDatabase.getInstance();
        myRef = database_fb.getReference("Users/" + user_uid);



            //open db
        db = openOrCreateDatabase(getResources().getString(R.string.db_name), Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Messages(code INTEGER PRIMARY KEY ,message TEXT)");
        setInfo();
        String temp_txt = full_name_txt.getText().toString();
        full_name_txt.setText(temp_txt +  " " + full_name);
        populate_listview();
        //set an onclick listener on the listview in order to get the code/msg description of a clicked item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                last_selected_code =((TextView) view.findViewById(R.id.code)).getText().toString();
                last_sel_msg = ((TextView) view.findViewById(R.id.message)).getText().toString();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if the user succesfully edited something
        if(resultCode == SUCCESFUL_EDIT && requestCode == EDIT_ACTIVITY){
            setInfo();
            //change the name in case it's been edited
            full_name_txt.setText("Welcome Back: " + full_name);}
        else if(resultCode == SUCCESFUL_MESSAGE_CREATION && requestCode == CREATE_MESSAGE_ACTIVITY){
            populate_listview();
            //change the name in case it's been edited
            }
        //succesfull message edit
        else if (resultCode == SUCCESFUL_MESSAGE_EDITING && requestCode == EDIT_MESSAGE_ACTIVITY){
            populate_listview();
            //TTS used for commands
        }else if (requestCode==RECORDING_RESULT && resultCode==RESULT_OK){
            //use the button name's as the words we can recognise
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches.contains("send selected message"))
                send_msg.performClick();
            if (matches.contains("edit"))
                edit_msg.performClick();
            if (matches.contains("delete"))
                delete_msg.performClick();
            if (matches.contains("create new message"))
                create_msg.performClick();
            if (matches.contains("edit my info"))
                go_edit.performClick();
        }


    }

    //Use the TTS to give commands with the users voice
    public void recognize(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something!");
        startActivityForResult(intent,RECORDING_RESULT);
    }
// go to edit activity
    public void go_edit(View view){
        Intent intent = new Intent(getApplicationContext(), EditUser_Activity.class);
        //get the required variables into the intent for the edit activity
        intent.putExtra("full_name", full_name);
        intent.putExtra("address", address);
        intent.putExtra("email", email);
        intent.putExtra("uid", user_uid);
        startActivityForResult(intent, EDIT_ACTIVITY);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speak(String sentence){
        tts.speak(sentence);
    }
    public void create_new_msg(View view){
        Intent intent = new Intent(getApplicationContext(), CreateMessage_Activity.class);
        startActivityForResult(intent, CREATE_MESSAGE_ACTIVITY);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //Edit a message if a listview item was selected
    public void edit_message(View view){
        if(!last_selected_code.equals("")){
        Intent intent = new Intent(getApplicationContext(), EditMessage_Activity.class);
        intent.putExtra("code", last_selected_code);
        intent.putExtra("description", last_sel_msg);
        startActivityForResult(intent, EDIT_MESSAGE_ACTIVITY);
        }
        else {speak("Select a code!");

    }
}

    //the method that the activate button uses to activate the location manager
    public void gps(View view) {
//check the permissions we have written on the manifest xml that have to do with the Location Manager (ACCESS_FINE_LOCATION, etc...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},234);
            return;
        }
        //activate the location manager. minTime,minDist = 0 to give updateds constantly
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.001,  this);}

    //standard location manager methods
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLocationChanged(Location location) {
        longtitude = location.getLongitude();
        latitude = location.getLatitude();

    }
    //standard location manager methods
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void sendMessage(View view){

        if(!last_selected_code.equals("")){
            //activate the location manager in order to get longtitude/latitude
               gps(view);
            //get immediately the timestamp of that action

            timestamp = System.currentTimeMillis()/1000;
            //get the phone number from strings.xml
            String message = last_selected_code.toString() + " "+full_name +" " +address;
//check permisions for sending a message
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},5434);
            }else {
                SmsManager manager = SmsManager.getDefault();
                //send our message to the number we specified
                manager.sendTextMessage(getResources().getString(R.string.call_number),null,message,null,null);
//if longtitude/latitude equal 0 then that means that we didnt get an update hence the value we should give to the firebase db is null
                //firebase doesnt accept null as a value so we will pass the string "null"
                if(longtitude == 0 || latitude == 0){
                    Location_Coordinates_str loc = new Location_Coordinates_str("null", "null", last_selected_code, timestamp);
                    //get the reference "Users/user_uid/Locations" of the firebase db ---> this will create the nodes up to Locations
                    // user_uid is the unique id we get when we authenticate for each user hence every user has only 1 personal node for his locations
                    DatabaseReference loc_dbref = database_fb.getReference("Users/" + user_uid+"/Locations/");
                    //get a unique id for every message we send so that we can store the location, code, timestamp in a unique node based on that id
                    String id = loc_dbref.push().getKey();
                    loc_dbref.child(id).setValue(loc);
                }else{
                    //the same applies heres
                    Location_Coordinates loc = new Location_Coordinates(longtitude, latitude, last_selected_code, timestamp);
                    DatabaseReference loc_dbref = database_fb.getReference("Users/" + user_uid+"/Locations/");
                    String id = loc_dbref.push().getKey();
                    loc_dbref.child(id).setValue(loc);}
                    Toast.makeText(this,"Message Sent!",Toast.LENGTH_LONG).show();
            }
            locationManager.removeUpdates(this); // stop the location  manager
        }else{
            speak("Select a code!");
        }



    }

//used to store the longtitude/lat/etc when we dont have null values
    class Location_Coordinates{
        public double longtitude;
        public double latitude;
        public String code;
        public double timestamp;


        public  Location_Coordinates(double lgt, double lat, String code, double timestamp){
            this.longtitude = lgt;
            this.latitude = lat;
            this.code = code;
            this.timestamp = timestamp;
        }
    }

//used to store the longtitude/lat/etc when we  have null values
    class Location_Coordinates_str{
        public String longtitude;
        public String latitude;
        public String code;
        public double timestamp;


        public  Location_Coordinates_str(String lgt, String lat, String code, double timestamp){
            this.longtitude = lgt;
            this.latitude = lat;
            this.code = code;
            this.timestamp = timestamp;
        }
    }

}