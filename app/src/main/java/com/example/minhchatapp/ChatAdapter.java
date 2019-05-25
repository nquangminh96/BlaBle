package com.example.minhchatapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Chats> data;

    public ChatAdapter(Context context, ArrayList<Chats> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SharedPreferences pre=context.getSharedPreferences("my_data", Context.MODE_PRIVATE);
        String currentUser = pre.getString("username" , "");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(currentUser.equals(data.get(position).getSender()))convertView = inflater.inflate(R.layout.item_chat_right , null);
        else convertView = inflater.inflate(R.layout.item_chat_left , null);
        TextView message = convertView.findViewById(R.id.itemchat);
        message.setText(data.get(position).getMessage());

        return convertView;

    }
}
