package com.example.konnect.landing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.example.konnect.login_signup.LoginActivity;
import com.example.konnect.login_signup.RegisterActivity;

public class Start_Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPageAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnRegister;
    private Button loginBtn;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_start);

        viewPager =  findViewById(R.id.view_pager);
        dotsLayout =  findViewById(R.id.layoutDots);
        btnRegister =  findViewById(R.id.btn_register);
        loginBtn = findViewById(R.id.loginBtn);

        // layouts of all welcome sliders
        // last layout is dummy for handling exit on slide
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPageAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPageAdapter);
        // viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        /*btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });*/

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
    }

    private void startLoginActivity() {
        prefManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startRegisterActivity() {
        prefManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    private void launchHomeScreen() {
        prefManager.setIsFirstTimeLaunch(false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.setMargins(5,0,0,0);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setWidth(10);
            dots[i].setHeight(5);
            dots[i].setLayoutParams(textLayoutParams);
            dots[i].setBackground(getResources().getDrawable(R.drawable.landingpage_dotunselected));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }

        // Hiding the las dot, as the last layout is dummy for handling exit on landing page
        dots[layouts.length-1].setVisibility(View.GONE);

        if (dots.length > 0) {
            dots[currentPage].setWidth(70);
            dots[currentPage].setBackground(getResources().getDrawable(R.drawable.landingpage_dot_selected));
        }
    }

    private int getItem(int i) { return viewPager.getCurrentItem() + 1; }

    /*//  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
//                btnNext.setText(getString(R.string.start));
                btnSkip.setText("IR A MIS ASISTENCIAS");
                launchHomeScreen();
//                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
//                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };*/

    // Making notifications bar transparent
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    // View pager adapter
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {}

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container,false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}