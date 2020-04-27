package com.kalu.covidmap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    public int[] list_img={
            R.drawable.prevent_wash,
            R.drawable.prevent_handshake,
            R.drawable.prevent_touchface,
            R.drawable.prevent_social


    };
    public  String[] list_title={
            "Never forget,Wash hands",
            "Avoid Handshaes",
            "Do not touch your face",
            "Social distancing"
    };


    public  String[] list_descri={
            "Any time you touch, there is apossibility of cathing the virus",
            "Shaking other peoples hands significantly increases yor chances of getting the virus",
            "When you touch your face the virus defintely enters your body",
            "Avoid interactions with people"
    };
    public int[] list_bgd={
           R.color.navajo_white,
            R.color.navajo_white,
            R.color.navajo_white,
            R.color.navajo_white
    };

    public SlideAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return list_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(ConstraintLayout)object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.preventin_slides,container,false);
        ConstraintLayout linearLayout=(ConstraintLayout) view.findViewById(R.id.con);
        ImageView imageView=(ImageView)view.findViewById(R.id.preventionimg);
        TextView txtDescri=(TextView)view.findViewById(R.id.preventiondescri);
        TextView txtTitle=(TextView)view.findViewById(R.id.preventiotitle);
        linearLayout.setBackgroundColor(list_bgd[position]);
        imageView.setImageResource(list_img[position]);
        txtDescri.setText(list_descri[position]);
        txtTitle.setText(list_title[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }


}
