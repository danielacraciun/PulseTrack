package com.example.dana.pulsetrackandroid;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dana.pulsetrackandroid.utils.PulseAdapter;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView) ListView myList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        Realm realm;
        try {
            realm = Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            Realm.deleteRealm(realmConfiguration);

            //Realm file has been deleted.
            realm = Realm.getInstance(realmConfiguration);
        }

        RealmResults<PulseLog> logs = realm.where(PulseLog.class).findAll();
        final PulseAdapter adapter = new PulseAdapter(this, logs);
        myList.setAdapter(adapter);

        // this listener automatically updates if there are changes
        RealmChangeListener changeListener = element -> adapter.notifyDataSetChanged();
        logs.addChangeListener(changeListener);

        myList.setOnItemClickListener((parent, view, position, id) ->
                detailLog(logs.get(position).getId()));
    }

    public void viewStats(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    public void addLog(View view) {
        Intent intent = new Intent(this, AddPulseLog.class);
        startActivity(intent);
    }

    public void detailLog(long id) {
        Intent intent = new Intent(this, PulseLogDetail.class);
        intent.putExtra("id", (int) id);
        startActivity(intent);
    }
}
