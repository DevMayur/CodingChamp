package com.incarta.clearner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.Task;
import com.incarta.clearner.R;
import com.incarta.clearner.fragments.ExamplesFragment;
import com.incarta.clearner.fragments.HomeFragment;

import static android.widget.Toast.LENGTH_LONG;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        loadBannerAd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setContent(HomeFragment.newInstance(null,null));
        AudienceNetworkAds.initialize(this);

        getRatings();

        BottomNavigationView navigationView = findViewById(R.id.main_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        setContent(HomeFragment.newInstance(null,null));
                        break;

                    case R.id.menu_examples:
                        setContent(ExamplesFragment.newInstance(null,null));
                        break;

                }
                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ffffff"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else {
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    public AdView adView;
    public void loadBannerAd () {
        adView = new AdView(this,getString(R.string.banner_home), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);

        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.d("facebook_audiance","Error : " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    ReviewManager manager;
    ReviewInfo reviewInfo;
    private void getRatings() {
        manager = ReviewManagerFactory.create(this);

        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
               reviewInfo = task.getResult();
            } else {
                // There was some problem, continue regardless of the result.
            }
        });



    }

    private void setContent(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    public void rateApplication() {
        if (reviewInfo != null) {
            Task<Void> flow = manager.launchReviewFlow(HomeActivity.this, reviewInfo);
            flow.addOnCompleteListener(task -> {

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                }
            });
        } else {

        }
    }
}