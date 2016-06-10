package com.mahtiyhti.areena;

import android.app.Activity;
import android.os.Bundle;

import android.view.WindowManager;




public class Taistelu extends Activity{

    TaisteluPaneeli taistelu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //turn off the title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);

        taistelu = new TaisteluPaneeli (this);

        //set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(taistelu);
    }
}
