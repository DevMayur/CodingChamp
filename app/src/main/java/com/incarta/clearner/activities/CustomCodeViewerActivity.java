package com.incarta.clearner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.incarta.clearner.R;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;

import static android.widget.Toast.LENGTH_LONG;

public class CustomCodeViewerActivity extends AppCompatActivity {

    TextView title;
    CodeView codeView;
    CodeView outputView;
    CodeView exaplainationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_code_viewer);

        title = findViewById(R.id.single_title_example);
        codeView = findViewById(R.id.codeView);
        outputView = findViewById(R.id.outputView);
        exaplainationView = findViewById(R.id.explainationView);

        loadBannerAd();
        loadInterstitialAd();


        title.setText(getIntent().getStringExtra("program_title"));

        codeView.setOptions(Options.Default.get(this)
                .withLanguage("c")
                .withCode(getIntent().getStringExtra("code"))
                .withTheme(ColorTheme.MONOKAI));

        outputView.setOptions(Options.Default.get(this)
                .withLanguage("c")
                .withCode(getIntent().getStringExtra("output"))
                .withTheme(ColorTheme.SOLARIZED_LIGHT));

        exaplainationView.setOptions(Options.Default.get(this)
                .withLanguage("c")
                .withCode(getIntent().getStringExtra("explanation"))
                .withTheme(ColorTheme.DEFAULT));


    }

    public AdView adView;
    public void loadBannerAd () {
        adView = new AdView(this,getString(R.string.banner_custom_code_viewer), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container2);
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



    public static String TAG = "facebook_audiance";
    private InterstitialAd interstitialAd;

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this, "2159335480866757_2159469847519987");

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    @Override
    public void onBackPressed() {
        if (adView != null) {
            adView.destroy();
        }
        interstitialAd.show();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}