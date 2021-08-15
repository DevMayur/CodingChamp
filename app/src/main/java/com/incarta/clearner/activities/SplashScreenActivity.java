package com.incarta.clearner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.incarta.clearner.R;
import com.ncorti.slidetoact.SlideToActView;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreenActivity extends AppCompatActivity {

    Button bt_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        printHashKey(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setContentView(R.layout.splash_activity);
        }else {
            setContentView(R.layout.splash_activity_old);
        }

        bt_start = findViewById(R.id.bt_start_splash);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SlideToActView slideView = findViewById(R.id.slide_view);
            slideView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                @Override
                public void onSlideComplete(@NotNull SlideToActView slideToActView) {
                    startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                    finish();
                }
            });
        }



    }

    public static String TAG = "facebook_hashes";

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }
}