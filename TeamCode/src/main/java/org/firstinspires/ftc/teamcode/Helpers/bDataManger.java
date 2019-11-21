package org.firstinspires.ftc.teamcode.Helpers;

import org.firstinspires.ftc.robotcore.internal.system.PreferencesHelper;

import android.app.Application;
import android.content.SharedPreferences;


public class bDataManger {

    //We add an ID to all key names to avoid interfering with other parts of the code that use the PrefHelper
    private static final String id = "Very_Cool_Robot_code";
    protected PreferencesHelper preferencesHelper;

    public void Start() {
        preferencesHelper = new PreferencesHelper("bDataManger");
    }

    //Writes data via the sharedPrefs manager, the key is its name and value is its value!
    public void writeData(String key, double value) {
        preferencesHelper.writeFloatPrefIfDifferent(id + key, (float) value);
    }


    public double readData(String key, double defaultValue) {
        return (double) preferencesHelper.readFloat(id + key, (float) defaultValue);
    }
}
