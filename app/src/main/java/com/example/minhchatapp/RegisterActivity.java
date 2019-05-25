package com.example.minhchatapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
    EditText ten, username, pass;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        ten = findViewById(R.id.edt_ten);
        username = findViewById(R.id.edt_username);
        pass = findViewById(R.id.edt_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               User user = new User(ten.getText().toString(), username.getText().toString(), pass.getText().toString(),"offline");
               myData.child("ListUsers").child(username.getText().toString()).setValue(user);
            }
        });
    }
}
