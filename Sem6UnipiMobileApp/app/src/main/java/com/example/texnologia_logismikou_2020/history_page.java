package com.example.texnologia_logismikou_2020;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class history_page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

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
                    Intent intent = new Intent(history_page.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                if(expandableListTitle.get(groupPosition)=="ΚΕΝΤΡΙΚΗ"){
                    Intent intent = new Intent(history_page.this, MainActivity.class);
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
                        childPosition) == "Ιστορία"){
                    Intent intent = new Intent(history_page.this, history_page.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Στρατιγική Πανεπιστημίου"){
                    Intent intent = new Intent(history_page.this, Message_proedrou.class);
                    startActivity(intent);
                }else if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition) == "Πολιτική Ποιότητας"){
                    Intent intent = new Intent(history_page.this, tomeis_ereunas.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}