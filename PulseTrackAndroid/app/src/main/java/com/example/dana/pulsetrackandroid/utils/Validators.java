package com.example.dana.pulsetrackandroid.utils;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import static com.example.dana.pulsetrackandroid.utils.Dialogs.messageDialog;

/**
 * Created by dana on 12/20/16.
 */

public class Validators {

    public static boolean validateUpdateInput(String pulse, String feeling, Context source) {
        if (StringUtils.isBlank(pulse) && StringUtils.isBlank(feeling)) {
            messageDialog("Can't proceed with empty fields", source);
            return false;
        } else if (StringUtils.isBlank(pulse)) {
            messageDialog("Can't proceed with empty pulse field", source);
            return false;
        } else if (StringUtils.isBlank(feeling)) {
            messageDialog("Can't proceed with empty feeling field", source);
            return false;
        } else if (Integer.valueOf(pulse) <= 0) {
            messageDialog("Pulse field value is not allowed", source);
            return false;
        }
        return true;
    }
}
