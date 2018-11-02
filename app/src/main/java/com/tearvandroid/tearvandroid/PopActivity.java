package com.tearvandroid.tearvandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));
    }
}
