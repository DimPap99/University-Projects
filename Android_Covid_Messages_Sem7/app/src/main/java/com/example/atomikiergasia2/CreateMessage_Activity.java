package com.example.atomikiergasia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class CreateMessage_Activity extends AppCompatActivity {
//initialise
    TextView codetxt;
    TextView msgtxt;
    SQLiteDatabase db;
    public int result_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init values
        setContentView(R.layout.activity_create_message_2);
        codetxt = findViewById(R.id.editTextTextPersonName9);
        msgtxt = findViewById(R.id.editTextTextPersonName10);
        db = openOrCreateDatabase(getResources().getString(R.string.db_name), Context.MODE_PRIVATE, null);
        result_code = 1000; //BACK RES CODE


    }



    public void create_message(View view){
        boolean success = false;
        if( checkValues() == true) {
            try{
                //get the values from the edittexts
            int code = Integer.parseInt(codetxt.getText().toString());
            String message = msgtxt.getText().toString();
            ContentValues insertValues = new ContentValues();//insert the values into the SQLITE db
            insertValues.put("code", code);
            insertValues.put("message", message);
            db.insert(getResources().getString(R.string.table_messages), null, insertValues);
            success = true; //succesful insertion
            }
            catch (NumberFormatException | SQLException e){ //catch sql exception in case the user gives a code that is already registered in the db
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                success = false;
                if( e instanceof SQLException ){
                    Toast.makeText(getApplicationContext(),"The message code you provided already exists!",Toast.LENGTH_LONG).show();
                }
            }

        }
        if(success == true){
            Toast.makeText(getApplicationContext(),"The message was created succesfully!",Toast.LENGTH_LONG).show();
            result_code = 1002; //success code
        }
    }

    public void back(View view){
        setResult(result_code);//if res code is 1000 when we go back we dont query for the updated data
        finish();
    }


    public boolean checkValues(){ //check that the edittexts are not empty
        if(codetxt.getText().toString().equals("") || msgtxt.getText().toString().equals("")){
            return false;
        }else return true;

    }
}