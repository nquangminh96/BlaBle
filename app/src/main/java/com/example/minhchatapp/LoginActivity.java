package com.example.minhchatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText edt_user, edt_pass;
    Button btn_login, btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_user = findViewById(R.id.username);
        edt_pass = findViewById(R.id.pass);
        btn_login = findViewById(R.id.login);
        btn_register = findViewById(R.id.register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivityForResult(intent , 11);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_user.getText().toString();
                String password = edt_pass.getText().toString();
                Login(user , password);
            }
        });
    }
    private void Login(final String username, final String pass){
        final DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
        myData.child("ListUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    Log.d("bug" , user.getUsername() + " " + user.getPassword());
                    assert user!=null;
                    if (user.getUsername().equals(username) && user.getPassword().equals(pass)){
                        Toast.makeText(LoginActivity.this, "Dang Nhap Thanh cong", Toast.LENGTH_SHORT).show();


                        SharedPreferences pre=getSharedPreferences("my_data", MODE_PRIVATE);
                        SharedPreferences.Editor edit=pre.edit();
                        edit.putString("username" , username);
                        edit.commit();







                        Intent intent = new Intent(LoginActivity.this , Main2Activity.class);
                        intent.putExtra("user" , username);
                        startActivityForResult(intent , 11);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
