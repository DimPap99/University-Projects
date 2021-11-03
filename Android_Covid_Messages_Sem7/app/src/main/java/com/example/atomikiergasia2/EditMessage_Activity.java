package com.example.atomikiergasia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.atomikiergasia2.MainActivity.SHARED_PREFS;

public class EditMessage_Activity extends AppCompatActivity {

//initialize
    EditText code;
    EditText descr;
    public String edit_code;
    SQLiteDatabase db;
    public int result_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message_);
        //get required variables from previous intent
        Bundle extras = getIntent().getExtras();//get the required variables from the prev activity
        edit_code = extras.getString("code");
        code = findViewById(R.id.edit_code);
        descr = findViewById(R.id.descr);
        //set the edittexts with the values we acquired
        descr.setText(extras.getString("description"));
        code.setText(String.valueOf(edit_code));
        result_code = 1000;

    }


    public void edit(View view) {
        boolean success = false;

        db = openOrCreateDatabase(getResources().getString(R.string.db_name), Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("code", code.getText().toString()); //These Fields should be your String values of actual column names
        values.put("message", descr.getText().toString());
        try {
            //update the db based on the code THAT ACTS AS A PRIMARY KEY
            db.update(getResources().getString(R.string.table_messages), values, "code = ?", new String[]{String.valueOf(edit_code)});
            success = true;
        } catch (NumberFormatException | SQLException e) { //catch sql exception in case the user gives wrong input
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            success = false;
            if (e instanceof SQLException) {
                Toast.makeText(getApplicationContext(), "Invalid code format!", Toast.LENGTH_LONG).show();
            }
        }


        if (success == true) {
            Toast.makeText(getApplicationContext(), "The message was edited succesfully!", Toast.LENGTH_LONG).show();
            result_code = 1004;
        }

    }


    public void back(View view){
        setResult(result_code);
        finish();
    }

}