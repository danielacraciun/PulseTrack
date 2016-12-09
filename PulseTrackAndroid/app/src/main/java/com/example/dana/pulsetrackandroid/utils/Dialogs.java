package com.example.dana.pulsetrackandroid.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;


public class Dialogs {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void optionDialog(String str, final Context source, Callable<Boolean> fct){
        AlertDialog.Builder builder = new AlertDialog.Builder(source);
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    dialog.dismiss();
                    try { fct.call(); } catch (Exception ignored) { }
                });

        builder.setNegativeButton(
                "No",
                (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void messageDialog(String str, final Context source) {
        AlertDialog.Builder builder = new AlertDialog.Builder(source);
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setNeutralButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
