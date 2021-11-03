package com.example.atomikiergasia2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CovidMsgAdapter extends BaseAdapter {
    public List<String> messages;
    public List<String> codes;


    public LayoutInflater layoutInflater;
    public Context context;
    //init use lists to populate the textviews of the messages.xml
    public CovidMsgAdapter(Context aContext, List<String> listData, List<String> listData2) {
        this.context = aContext;
        this.messages = listData;
        this.codes = listData2;


        layoutInflater = LayoutInflater.from(aContext);
    }
    //Standard Base Adapter methods
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.messages, null);
            // give to the holderview properties the references to the Textviews
            holder = new ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set the textviews based on the position of the item in the listview
        holder.message.setText( this.messages.get(position));
        holder.code.setText(this.codes.get(position));


        return convertView;
    }

    //Class that acts as a struct

    static class ViewHolder {
        TextView message;
        TextView code;
    }}
