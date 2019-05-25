package com.example.minhchatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ImageButton btn_send;
    EditText edt_message;
    ListView listView;
    ArrayList<Chats> data = new ArrayList<>();
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btn_send = findViewById(R.id.send);
        edt_message = findViewById(R.id.message);
        listView = findViewById(R.id.list);
        listView.setDivider(null);
        Intent i = getIntent();
        final String me  = i.getStringExtra("sender");
        final String you = i.getStringExtra("receiver");
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edt_message.getText().toString();
                Chats chats = new Chats(me , you , message);
                DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
                myData.child("ListChats").push().setValue(chats);
                edt_message.setText("");
            }
        });
        DatabaseReference myData = FirebaseDatabase.getInstance().getReference();
        myData.child("ListChats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
              for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                  Chats chats = snapshot.getValue(Chats.class);
                  if((chats.getSender().equals(me) && chats.getReceiver().equals(you))
                          || chats.getSender().equals(you) && chats.getReceiver().equals(me)){
                      // add message vao data cua listview
                      data.add(chats);
                  }
              }
              chatAdapter = new ChatAdapter(ChatActivity.this , data);
              listView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
