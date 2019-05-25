package com.example.minhchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListUsersAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> data;

    public ListUsersAdapter(Context context, ArrayList<User> data) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.itemlistusers , null);
        TextView txtname =  convertView.findViewById(R.id.name);
        CircleImageView statusonline = convertView.findViewById(R.id.online);
        CircleImageView statusoffline = convertView.findViewById(R.id.offline);
        if(data.get(position).getStatus()!=null &&data.get(position).getStatus().equals("offline")){
            statusoffline.setVisibility(View.VISIBLE);
            statusonline.setVisibility(View.GONE);
        }
        if(data.get(position).getStatus()!=null && data.get(position).getStatus().equals("online")){
            statusonline.setVisibility(View.VISIBLE);
            statusoffline.setVisibility(View.GONE);
        }
        txtname.setText(data.get(position).getTen());
        return convertView;
    }
}
