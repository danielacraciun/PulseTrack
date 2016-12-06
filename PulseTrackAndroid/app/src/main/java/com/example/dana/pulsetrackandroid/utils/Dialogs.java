package com.example.dana.pulsetrackandroid.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;


public class Dialogs {
    public static void optionDialog(String str, final Intent intent, final Context source){
        AlertDialog.Builder builder = new AlertDialog.Builder(source);
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(intent != null) { source.startActivity(intent); }
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void messageDialog(String str, final Intent intent, final Context source) {
        AlertDialog.Builder builder = new AlertDialog.Builder(source);
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(intent != null) { source.startActivity(intent); }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
