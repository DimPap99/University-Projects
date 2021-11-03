package com.example.atomikiergasia01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//implement OnMapReadyCallback to use the mapview
public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {
    //init
    public List<String> timestamps;
    public List<String> latitude;
    public List<String> longtitude;
    public List<String> speed;
    SQLiteDatabase db;
    public ListView listView;
    Button back;
    
    private MapView mapView;
    private GoogleMap gmap;
    private static boolean map_ready = false;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static int last_selected_timestamp= 0; // used for deleting a timestamp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.listview);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
//init the mapview
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        //open the DB
        db = openOrCreateDatabase("GeoDB", Context.MODE_PRIVATE, null);




        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filters_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected_value = spinner.getSelectedItem().toString();


                //Empty/ re-init the lists on item change to repopulate the list view
                timestamps = new ArrayList<String>();
                latitude = new ArrayList<String>();
                longtitude = new ArrayList<String>();
                speed = new ArrayList<String>();
                long current_day_timestamp = System.currentTimeMillis()/1000;
                Cursor cursor;
                int s = 0; //seconds

                if(selected_value.equals("Day")){
                    s = 60*60*24; // day seconds
                }else if(selected_value.equals("Week")){
                    s = 60*60*24 * 7; //week seconds
                }else if(selected_value.equals("Month")){
                    s = 60*60*24 * 30; //month seconds
                }
// choose rows based on the filter choice
                if(selected_value.equals("All")){
                    cursor = db.rawQuery("SELECT * FROM Location", null);
                }else{
                    cursor = db.rawQuery("SELECT * FROM Location  WHERE  " + String.valueOf(current_day_timestamp) + "-timestamp <=" + String.valueOf(s), null);

                }
//add elements in the list to populate the listview
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        timestamps.add(String.valueOf(cursor.getString(0))) ;
                        latitude.add(String.valueOf(cursor.getString(1)));
                        longtitude.add(String.valueOf(cursor.getString(2)));
                        speed.add(String.valueOf(cursor.getString(3)));}

                }

                listView.setAdapter(new SPListAdapter(getApplicationContext(), timestamps, latitude, longtitude, speed));
                //Set an event onclick on listview items --> On click show the row Lat/Longt on the mapview
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // selected item
                        String timestamp = ((TextView) view.findViewById(R.id.timestamp)).getText().toString();
                        last_selected_timestamp = Integer.parseInt(timestamp);
                        String lat = ((TextView) view.findViewById(R.id.latitude)).getText().toString();
                        String lgt = ((TextView) view.findViewById(R.id.longtitude)).getText().toString();
                        String speed = ((TextView) view.findViewById(R.id.speed)).getText().toString();
                        String toast_str = "Location for timestamp: " + timestamp + " and Speed(km/h): " + speed;
                        Toast toast = Toast.makeText(getApplicationContext(), toast_str, Toast.LENGTH_SHORT);
                        toast.show();
                        //if the map has loaded add marker based on the clicked listview row
                        if(map_ready == true){
                            LatLng pos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lgt));
                            gmap.addMarker(new MarkerOptions()
                                    .position(pos)
                                    .title("lat: " + lat + " lgt: " + lgt ));
                            gmap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        back = findViewById(R.id.button3); // back to MainActivity button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
//delete a row from the db based on the timestamp
public void delete(View view){
        if(last_selected_timestamp != 0){
            String table = "Location";
            String whereClause = "timestamp=?";
            String[] whereArgs = new String[] { String.valueOf(last_selected_timestamp) };
            db.delete(table, whereClause, whereArgs);

            startActivity(getIntent());
    }

}

//Standard methods for the mapview
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        map_ready = true;
    }
}