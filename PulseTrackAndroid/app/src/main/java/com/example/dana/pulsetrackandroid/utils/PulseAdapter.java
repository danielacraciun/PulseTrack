package com.example.dana.pulsetrackandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.dana.pulsetrackandroid.PulseLog;
import com.example.dana.pulsetrackandroid.R;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;


public class PulseAdapter extends RealmBaseAdapter<PulseLog> implements ListAdapter {

    private static class ViewHolder {
        TextView timestamp;
    }

    public PulseAdapter(Context context, OrderedRealmCollection<PulseLog> realmResults) {
        super(context, realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.timestamp = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PulseLog item = null;
        if (adapterData != null) {
            item = adapterData.get(position);
            viewHolder.timestamp.setText(item.toString());
        }

        return convertView;
    }
}