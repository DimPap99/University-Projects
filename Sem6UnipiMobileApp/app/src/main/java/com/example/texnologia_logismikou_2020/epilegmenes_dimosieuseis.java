package com.example.texnologia_logismikou_2020;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class epilegmenes_dimosieuseis extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epilegmenes_dimosieuseis);

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
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                if(expandableListTitle.get(groupPosition)=="ΚΕΝΤΡΙΚΗ"){
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, MainActivity.class);
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
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, Message_proedrou.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Τομείς έρευνας") {
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, tomeis_ereunas.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Επιλεγμένες δημοσιεύσεις") {
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, epilegmenes_dimosieuseis.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Ερευνητικά έργα") {
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, ereunitika_erga.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Ερευνητικά εργαστήρια") {
                    Intent intent = new Intent(epilegmenes_dimosieuseis.this, ereunitika_ergastiria.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}