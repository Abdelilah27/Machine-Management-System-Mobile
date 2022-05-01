package com.machine.management.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.machine.management.R;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class WelcomeActivity extends AppCompatActivity {

    BlurView blurView_welcome_fragment;
    CardView card_view_welcome_activity;
    Button button_welcome_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        card_view_welcome_activity = findViewById(R.id.card_view_welcome_activity);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) card_view_welcome_activity.getLayoutParams();
        layoutParams.setMargins(0, (int) (height / 3), 0, 0);
        card_view_welcome_activity.requestLayout();

        blurView_welcome_fragment = findViewById(R.id.blurView_welcome_fragment);
        BlurView();

        button_welcome_view = findViewById(R.id.button_welcome_view);

        button_welcome_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity currentActivity = (Activity) view.getContext();
                Intent i = new Intent(currentActivity, LoginActivity.class);
                currentActivity.startActivity(i);
            }
        });
    }


    private void BlurView() {
        float radius = 10;
        View decorView = WelcomeActivity.this.getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        blurView_welcome_fragment.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(getApplicationContext()))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true);


    }
}