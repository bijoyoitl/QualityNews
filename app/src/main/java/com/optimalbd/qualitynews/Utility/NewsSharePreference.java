package com.optimalbd.qualitynews.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ripon on 4/4/2017.
 */

public class NewsSharePreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    public NewsSharePreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("news", Context.MODE_PRIVATE);
    }

    public void saveTextSize(int size, int position, String title) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("size", size);
        editor.putInt("s_position", position);
        editor.putString("type", title);
        editor.apply();
        editor.commit();
    }

    public void saveListSelector(int position) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position", position);
        editor.apply();
        editor.commit();
    }

    public void saveTotalPage(int pages) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pages", pages);
        editor.apply();
        editor.commit();
    }

    public int getTotalPages() {
        return sharedPreferences.getInt("pages", 1);
    }

    public int getSelectorPosition() {
        return sharedPreferences.getInt("position", 0);
    }

    public int getTextSize() {
        return sharedPreferences.getInt("size", 0);
    }

    public int getPosition() {
        return sharedPreferences.getInt("s_position", 0);
    }

    public String getType() {
        return sharedPreferences.getString("type", "");
    }

}
