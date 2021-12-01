package com.booster.vpn.AdsClass;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.anchorfree.partner.api.ClientInfo;
import com.anchorfree.sdk.HydraTransportConfig;
import com.anchorfree.sdk.NotificationConfig;
import com.anchorfree.sdk.TransportConfig;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.sdk.UnifiedSDKConfig;
import com.anchorfree.vpnsdk.callbacks.CompletableCallback;

import com.booster.vpn.R;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.northghost.caketube.OpenVpnTransportConfig;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static MyApplication instance;

    private static final String CHANNEL_ID = "vpn";
    private static Context context;
    UnifiedSDK unifiedSDK;

    public static Context getContext() {
        return context;
    }

    public MyApplication() {
        instance = this;
    }

    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        context = this;
        initHydraSdk();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

//        FacebookSdk.sdkInitialize(getApplicationContext());
        SettingsClass.appOpenManager = new AppOpenManager(this);
    }

    public void initHydraSdk() {
        createNotificationChannel();

        SharedPreferences prefs = getPrefs();
        ClientInfo clientInfo = ClientInfo.newBuilder()
                .baseUrl(prefs.getString("com.booster.vpn.STORED_HOST_KEY", "https://d2isj403unfbyl.cloudfront.net"))
                .carrierId(prefs.getString("com.booster.vpn.CARRIER_ID_KEY", "1_firevpn1"))
                .build();
        List<TransportConfig> transportConfigList = new ArrayList<>();
        transportConfigList.add(HydraTransportConfig.create());
        transportConfigList.add(OpenVpnTransportConfig.tcp());
        transportConfigList.add(OpenVpnTransportConfig.udp());
        UnifiedSDK.update(transportConfigList, CompletableCallback.EMPTY);
        UnifiedSDKConfig config = UnifiedSDKConfig.newBuilder().idfaEnabled(false).build();
        unifiedSDK = UnifiedSDK.getInstance(clientInfo, config);
        NotificationConfig notificationConfig = NotificationConfig.newBuilder()
                .title(getResources().getString(R.string.app_name))
                .channelId(CHANNEL_ID)
                .build();
        UnifiedSDK.update(notificationConfig);
        UnifiedSDK.setLoggingLevel(Log.VERBOSE);
    }

    public void setNewHostAndCarrier(String hostUrl, String carrierId) {
        SharedPreferences prefs = getPrefs();
        if (TextUtils.isEmpty(hostUrl)) {
            prefs.edit().remove("com.booster.vpn.STORED_HOST_KEY").apply();
        } else {
            prefs.edit().putString("com.booster.vpn.STORED_HOST_KEY", hostUrl).apply();
        }
        if (TextUtils.isEmpty(carrierId)) {
            prefs.edit().remove("com.booster.vpn.CARRIER_ID_KEY").apply();
        } else {
            prefs.edit().putString("com.booster.vpn.CARRIER_ID_KEY", carrierId).apply();
        }
        initHydraSdk();
    }

    public SharedPreferences getPrefs() {
        return getSharedPreferences("NORTHGHOST_SHAREDPREFS", Context.MODE_PRIVATE);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sample VPN";
            String description = "VPN notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}