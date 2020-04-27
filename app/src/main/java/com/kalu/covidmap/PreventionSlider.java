package com.kalu.covidmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PreventionSlider extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter slideAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_prevention_slider);
        viewPager=findViewById(R.id.viewpager);
        slideAdapter=new SlideAdapter(this);
        viewPager.showContextMenu();
        viewPager.setAdapter(slideAdapter);
    }
}
