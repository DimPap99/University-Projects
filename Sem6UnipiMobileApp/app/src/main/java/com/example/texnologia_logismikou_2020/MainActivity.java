package com.example.texnologia_logismikou_2020;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.navigationmenu);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if(expandableListTitle.get(groupPosition)=="ΚΕΝΤΡΙΚΗ"){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                if(expandableListTitle.get(groupPosition)=="ΚΕΝΤΡΙΚΗ"){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Μήνυμα Προέδρου"){
                    Intent intent = new Intent(MainActivity.this, Message_proedrou.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Τομείς έρευνας") {
                    Intent intent = new Intent(MainActivity.this, tomeis_ereunas.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Επιλεγμένες δημοσιεύσεις") {
                    Intent intent = new Intent(MainActivity.this, epilegmenes_dimosieuseis.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Ερευνητικά έργα") {
                    Intent intent = new Intent(MainActivity.this, ereunitika_erga.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Ερευνητικά εργαστήρια") {
                    Intent intent = new Intent(MainActivity.this, ereunitika_ergastiria.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.arxiki:
                Toast.makeText(MainActivity.this,"Panepistimio selected",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}