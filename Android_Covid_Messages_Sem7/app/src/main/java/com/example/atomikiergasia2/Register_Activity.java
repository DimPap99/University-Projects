package com.example.atomikiergasia2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    EditText fullname;
    EditText address;
    FirebaseUser currentUser;
    SQLiteDatabase db;
    FirebaseDatabase database_fb;
    DatabaseReference myRef;
    DatabaseReference userRef;


    //initialize db, firebase, refference ui components
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        email = findViewById(R.id.editTextTextPersonName3);
        password = findViewById(R.id.editTextTextPersonName4);
        fullname = findViewById(R.id.editTextTextPersonName5);
        address = findViewById(R.id.editTextTextPersonName6);
        mAuth = FirebaseAuth.getInstance();
        db = openOrCreateDatabase("UserDB",Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS User(email TEXT ,password TEXT,full_name TEXT, address TEXT )");

    }


//Register user with firebase auth the following code is from the docs
    public void register(View view){

        mAuth.createUserWithEmailAndPassword(
                email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if all the fields are filled then procceed to sign up the user and add his info in the sqlite db
                        if (task.isSuccessful() && check_fields()){
                            Toast.makeText(getApplicationContext(),"Signup success!",Toast.LENGTH_LONG).show();
                            insertUser(email.getText().toString(), password.getText().toString(), fullname.getText().toString(), address.getText().toString());
                            currentUser = mAuth.getCurrentUser();
                            setResult(1000);
                            database_fb = FirebaseDatabase.getInstance();

                            myRef = database_fb.getReference("Users/" + currentUser.getUid().toString());
                            myRef.setValue(new User(email.getText().toString(), fullname.getText().toString(), address.getText().toString()));
                            finish();

                        } else {

                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            if(check_fields() == false){
                                Toast.makeText(getApplicationContext(), "Fill all the fields!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
//Check for empty fields. Only fullname and address because email, pass is checked from firebase
    public boolean check_fields(){
        if(fullname.getText().toString().equals("") || address.getText().toString().equals("")){
            return false;
        }else return true;
    }
//Inserts the information of the user inside the User_Info table

    public void insertUser(String email, String password, String full_name, String address ){
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("full_name", full_name);
        values.put("address", address);
        db.insert("User",null, values);
    }


}