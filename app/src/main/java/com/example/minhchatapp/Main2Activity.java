package com.example.minhchatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<User> datalistview = new ArrayList<>();
    ListUsersAdapter adapter;
    String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.list);
        final Intent intent = getIntent();
        currentUser = intent.getStringExtra("user");
        DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
        myData.child("ListUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datalistview.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (!user.getUsername().equals(currentUser))
                        datalistview.add(user);
                }
                adapter = new ListUsersAdapter(Main2Activity.this,  datalistview);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(Main2Activity.this, ChatActivity.class);
                intent1.putExtra("sender", currentUser);
                intent1.putExtra("receiver", datalistview.get(position).getUsername());
                startActivityForResult(intent1 , 11);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
        myData.child("ListUsers").child(currentUser).child("status").setValue("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
        myData.child("ListUsers").child(currentUser).child("status").setValue("offline");
    }

    @Override
    protected void onStop() {
        super.onStop();
        DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
        myData.child("ListUsers").child(currentUser).child("status").setValue("offline");
    }
}
