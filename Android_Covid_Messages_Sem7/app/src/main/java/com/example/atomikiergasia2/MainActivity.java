package com.example.atomikiergasia2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
//initialize
    Button register_b;
    Button login_b;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;

    FirebaseUser currentUser;
    public static final String SHARED_PREFS = "db_info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_b = findViewById(R.id.button);
        register_b = findViewById(R.id.button3);
        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPersonName2);

        mAuth = FirebaseAuth.getInstance();
    }

//register --> go to register activity
    public void register(View view){
        Intent intent = new Intent(view.getContext(), Register_Activity.class);

        startActivity(intent);
    }
//login with the firebase auth. The following code comes from the docs
    public void login(View view){
        mAuth.signInWithEmailAndPassword(
                email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Login success!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity3.class);
                            //add email, user_id in order for them to be used in querying sqlite and firebasedb respectively
                            intent.putExtra("email",email.getText().toString());
                            intent.putExtra("user_id", currentUser.getUid().toString()); // ---> unique id for everyuser that will be used in Users FirebaseDB node
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }






}