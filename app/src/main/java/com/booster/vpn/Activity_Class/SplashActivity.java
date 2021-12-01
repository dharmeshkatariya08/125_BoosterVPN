package com.booster.vpn.Activity_Class;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.booster.vpn.AdsClass.AppOpenManager;
import com.booster.vpn.AdsClass.DataLoaded;
import com.booster.vpn.AdsClass.MyAddManager;
import com.booster.vpn.AdsClass.SettingsClass;
import com.booster.vpn.R;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    ArrayList<String> apiList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (apiList.size() > 0) apiList.clear();

        AudienceNetworkAds.initialize(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        GetAdId();
    }

    public void HomeScreen() {
        if (SettingsClass.StartApp_App_Status == 1 && !SettingsClass.StartApp_App_ID.isEmpty()) {
            StartAppAd startAppAd = new StartAppAd(this);
            StartAppSDK.init(this, SettingsClass.StartApp_App_ID, false);
            StartAppAd.disableSplash();
            StartAppAd.disableAutoInterstitial();
        }
        if (SettingsClass.appOpenManager != null) {
            SettingsClass.appOpenManager = null;
        }
        SettingsClass.appOpenManager = new AppOpenManager(SettingsClass.myApplication);

        if (isConnected()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MyAddManager.getInstance(SplashActivity.this).showInterstitialAd(SplashActivity.this, SplashActivity.this, new MyAddManager.MyCallback() {
                        @Override
                        public void callbackCall(boolean isSuccess) {
                            Intent intent = new Intent(SplashActivity.this, Main_Activity.class);
                            intent.addFlags(65536);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }, 3000);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    private void GetAdId() {
        SettingsClass.Update_My_Data(SplashActivity.this, new DataLoaded() {
            @Override
            public void callbackCall(boolean isSuccess) {
                MyAddManager.getInstance(SplashActivity.this).loadintertialads(SplashActivity.this, true, false);
                HomeScreen();
            }
        });
    }

}
