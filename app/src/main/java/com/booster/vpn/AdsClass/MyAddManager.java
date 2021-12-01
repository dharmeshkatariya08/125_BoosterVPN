package com.booster.vpn.AdsClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;
import com.booster.vpn.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import java.util.ArrayList;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class MyAddManager {
    public static SharedPreferences mysharedpreferences;
    public static String ADNetwork_ADMOB = "Admob";
    public static String ADNetwork_FB = "FB_audiencenetwork";
    public static String ADNetwork_STARTAPP = "StartApp";
    public static String ADNetwork_ADManaer = "AdManager";
    public static String ADNetwork_MAX = "AppLovinMax";
    public static String ADNetwork_UNITY = "Unity";

    ArrayList<String> Interstitial_adNetworks;
    ArrayList<String> simpleBanner_adNetworks;
    ArrayList<String> native_adNetworks;
    ArrayList<String> Rewarded_adNetworks;


    public static Display display;
    public static int ad_dialog_time_in_second = 2;
    public static boolean isRewarded = false;
    static Context activity = null;
    static MyCallback myCallback;
    static String Priority_AdNetwork = SettingsClass.Priority_AdNetwork;
    private static MyAddManager mInstance;
    public Dialog dialog;
    public StartAppAd startAppAd;

    boolean isLoading_Interstitial = false;
    int DisplayPixel = 0;
    Banner startAppBanner;
    boolean isLoading;
    String FB_RewardedAd_ID = SettingsClass.Fb_Rewarded;

    String TAG = "";

    //ADMob
    public com.google.android.gms.ads.interstitial.InterstitialAd admob_interstitial;
    public RewardedAd admobrewardedAd;
    String ADMob_InterStitial_ID = SettingsClass.AdMob_InterstitialID;
    String ADManager_InterStitial_ID = SettingsClass.AdMob_InterstitialID;


    //FB
    public InterstitialAd fbInterstitialAd;
    String FB_InterStitial_ID = SettingsClass.FB_InterstitialID;


    //region Native Ad Setup
    String ADMob_RewardedAd_ID = SettingsClass.AdMob_Rewarded;
    private RewardedVideoAd fbRewardedVideoAd;
    private MaxRewardedAd rewardedAd;

    //MAX
    private MaxInterstitialAd maxInterstitialAd;
    String MAX_InterStitial_ID = SettingsClass.AdMob_InterstitialID;
    private MaxAdView maxNativeADView;
    private MaxRewardedAd maxRewardedAd;

    //Unity Ads
    private Boolean testMode = false;
    private String UNITY_InterStitial_ID;


    public interface MyCallback {
        void callbackCall(boolean isSuccess);
    }

    public MyAddManager(Context context) {
        activity = context;
        mysharedpreferences = context.getSharedPreferences(context.getPackageName(), 0);
    }

    public static MyAddManager getInstance(Context context) {
        activity = context;

        if (!SettingsClass.isInitialized) {
            SettingsClass.Update_My_Data(context, new DataLoaded() {
                @Override
                public void callbackCall(boolean isSuccess) {
                    AudienceNetworkAds.initialize(context);
                    MobileAds.initialize(context, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                        }
                    });

                    AppLovinSdk.getInstance(context).setMediationProvider("max");
                    AppLovinSdk.initializeSdk(context, new AppLovinSdk.SdkInitializationListener() {
                        @Override
                        public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                            // AppLovin SDK is initialized, start loading ads
                        }
                    });

                    if (mInstance == null) {
                        mInstance = new MyAddManager(context);
                    }
                }
            });

        } else {
            if (mInstance == null) {
                mInstance = new MyAddManager(context);
            }
        }
        return mInstance;
    }

    public static boolean hasActiveInternetConnection(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //region Interstitial Ad Setup
    //********************** >> Region Interstitial Banner << **********************

    private void Show_next_Interstitial(String adNetwork, Activity activity2) {
        Interstitial_adNetworks.add(adNetwork);
        if (!Interstitial_adNetworks.contains(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 1) {
            loadAdmobInterstitial(activity2, ADMob_InterStitial_ID, ADNetwork_ADMOB);
        } else if (!Interstitial_adNetworks.contains(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 1) {
            loadAdmobInterstitial(activity2, ADManager_InterStitial_ID, ADNetwork_ADManaer);
        } else if (!Interstitial_adNetworks.contains(ADNetwork_MAX) && SettingsClass.MAX_AD_Status == 1) {
            loadMAX_Interstitial(activity2, SettingsClass.StartApp_App_ID, ADNetwork_MAX);
        } else if (!Interstitial_adNetworks.contains(ADNetwork_FB) && SettingsClass.FB_AD_Status == 1) {
            loadFacebookInterstitial(activity2, FB_InterStitial_ID, ADNetwork_FB);
        } else if (!Interstitial_adNetworks.contains(ADNetwork_UNITY) && SettingsClass.UNITY_AD_Status == 1) {
            loadUnityInterstitialAd(activity2, SettingsClass.UNITY_GameID, ADNetwork_UNITY);
        } else if (!Interstitial_adNetworks.contains(ADNetwork_STARTAPP) && SettingsClass.StartApp_App_Status == 1) {
            loadStartAppInterstitial(activity2, SettingsClass.StartApp_App_ID, ADNetwork_STARTAPP);
        }
    }

//    public void showInterstitialAd(Context context, final Activity activity, final MyAddManager.MyCallback myCallback2) {
//        try {
//            if ((admob_interstitial != null) || (fbInterstitialAd != null && fbInterstitialAd.isAdLoaded()) || isLoading_Interstitial) {
//                this.dialog = new Dialog(context);
//                View inflate = LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, (ViewGroup) null);
//                this.dialog.setContentView(inflate);
//                this.dialog.setCancelable(false);
//                this.dialog.getWindow().setLayout(-1, -2);
//                this.dialog.show();
//                final CircularProgressIndicator circularProgressIndicator = (CircularProgressIndicator) inflate.findViewById(R.id.circular_progress);
//                circularProgressIndicator.setProgress(0.0d, 100.0d);
//                new CountDownTimer((long) (ad_dialog_time_in_second * 1000), 100) {
//                    public void onTick(long j) {
//                        circularProgressIndicator.setProgress((double) ((j / 10) / ((long) MyAddManager.ad_dialog_time_in_second)), 100.0d);
//                    }
//
//                    public void onFinish() {
//                        ADManager.this.dialog.dismiss();
//                        Display_Interstitial(activity, myCallback2);
//                    }
//                }.start();
//            } else {
//                Display_Interstitial(activity, myCallback2);
//            }
//        } catch (Exception e) {
//            if (ADManager.this.dialog != null && ADManager.this.dialog.isShowing())
//                ADManager.this.dialog.dismiss();
//
//            Display_Interstitial(activity, myCallback2);
//        }
//    }

    public void loadintertialads(Activity activity2, Boolean isSplash, boolean AdCount_Checker) {
//        SettingsClass.Update_My_Data(activity2, new DataLoaded() {
//            @Override
//            public void callbackCall(boolean isSuccess) {
        int adCall = SettingsClass.adshow_Calls;
        if (AdCount_Checker) {
            SettingsClass.adshow_Calls = SettingsClass.adshow_Calls < SettingsClass.Ad_Interval ? adCall + 1 : adCall - 1;
            if (!SettingsClass.Allowtype || SettingsClass.adshow_Calls != SettingsClass.Ad_Interval) {
                return;
            }
        }

        if (Interstitial_adNetworks != null) {
            Interstitial_adNetworks.clear();
        } else {
            Interstitial_adNetworks = new ArrayList<>();
        }

        if (isSplash) {
            FB_InterStitial_ID = SettingsClass.FB_InterstitialID2;
            ADMob_InterStitial_ID = SettingsClass.AdMob_InterstitialID2;
            ADManager_InterStitial_ID = SettingsClass.ADManager_InterstitialID2;
            UNITY_InterStitial_ID = SettingsClass.UNITY_InterstitialID2;
            MAX_InterStitial_ID = SettingsClass.MAX_InterstitialID2;

        } else {
            FB_InterStitial_ID = SettingsClass.FB_InterstitialID;
            ADMob_InterStitial_ID = SettingsClass.AdMob_InterstitialID;
            ADManager_InterStitial_ID = SettingsClass.ADManager_InterstitialID;
            UNITY_InterStitial_ID = SettingsClass.UNITY_InterstitialID;
            MAX_InterStitial_ID = SettingsClass.MAX_InterstitialID;
        }

        switch (Priority_AdNetwork) {
            case "Admob":
                loadAdmobInterstitial(activity2, ADMob_InterStitial_ID, ADNetwork_ADMOB);
                break;
            case "AdManager":
                loadAdmobInterstitial(activity2, ADManager_InterStitial_ID, ADNetwork_ADManaer);
                break;
            case "FB_audiencenetwork":
                loadFacebookInterstitial(activity2, FB_InterStitial_ID, ADNetwork_FB);
                break;
            case "StartApp":
                loadStartAppInterstitial(activity2, SettingsClass.StartApp_App_ID, ADNetwork_STARTAPP);
                break;
            case "AppLovinMax":
                loadMAX_Interstitial(activity2, MAX_InterStitial_ID, ADNetwork_MAX);
                break;
            case "Unity":
                loadUnityInterstitialAd(activity2, SettingsClass.UNITY_GameID, ADNetwork_UNITY);
                break;
        }
//            }
//        });
    }

    private void loadAdmobInterstitial(final Activity activity2, String str, String adNetwork) {
        try {
            if (adNetwork.equals(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 0) {
                Show_next_Interstitial(adNetwork, activity2);
                return;
            } else if (adNetwork.equals(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 0) {
                Show_next_Interstitial(adNetwork, activity2);
                return;
            }
            if (!str.isEmpty()) {
                AdRequest adRequest = new AdRequest.Builder().build();
                isLoading_Interstitial = true;

                com.google.android.gms.ads.interstitial.InterstitialAd.load(activity2, str, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                        admob_interstitial = interstitialAd;
                        isLoading_Interstitial = false;

                        if (admob_interstitial != null) {
                            admob_interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when fullscreen content is dismissed.
                                    Log.d("TAG", "The ad was dismissed.");
                                    MyAddManager.this.interstitialCallBack(true);
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    admob_interstitial = null;
                                    Log.d("TAG", "The ad was shown.");
                                }
                            });
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        isLoading_Interstitial = false;

                        admob_interstitial = null;
                        Show_next_Interstitial(adNetwork, activity2);
                    }

                });
            } else {
                Show_next_Interstitial(adNetwork, activity2);
            }
        } catch (Exception e) {
            Show_next_Interstitial(adNetwork, activity2);
        }
    }

    private void loadFacebookInterstitial(final Activity activity2, String str, String adNetwork) {
        try {
            if (SettingsClass.FB_AD_Status == 1 && !str.isEmpty()) {
                AdRequest adRequest = new AdRequest.Builder().build();
                isLoading_Interstitial = true;
                fbInterstitialAd = new InterstitialAd(activity2, str);
                InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        Log.e(TAG, "Interstitial ad displayed.");
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                        Log.e(TAG, "Interstitial ad dismissed.");
                        MyAddManager.this.interstitialCallBack(true);
                        admob_interstitial = null;

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());

                        isLoading_Interstitial = false;

                        admob_interstitial = null;
                        Show_next_Interstitial(adNetwork, activity2);
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                        // Show the ad
//                    fbInterstitialAd.show();
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
                fbInterstitialAd.loadAd(
                        fbInterstitialAd.buildLoadAdConfig()
                                .withAdListener(interstitialAdListener)
                                .build());
            } else {
                Show_next_Interstitial(adNetwork, activity2);
            }
        } catch (Exception e) {
            Show_next_Interstitial(adNetwork, activity2);
        }

    }

    private void loadMAX_Interstitial(final Activity activity2, String str, String adNetwork) {
        try {
            if (SettingsClass.MAX_AD_Status == 1 && !str.isEmpty()) {

                if (maxInterstitialAd != null) {
                    maxInterstitialAd.destroy();
                    maxInterstitialAd = null;
                }
                isLoading_Interstitial = true;

                maxInterstitialAd = new MaxInterstitialAd(MAX_InterStitial_ID, activity2);
                maxInterstitialAd.loadAd();
                maxInterstitialAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        Toast.makeText(activity2, "MAX Loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        MyAddManager.this.interstitialCallBack(true);
                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        isLoading_Interstitial = false;
                        Toast.makeText(activity2, "Faild to MAX Load", Toast.LENGTH_SHORT).show();

                        maxInterstitialAd = null;
                        Show_next_Interstitial(adNetwork, activity2);
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                        Toast.makeText(activity2, "Faild to MAX Display", Toast.LENGTH_SHORT).show();
                        maxInterstitialAd = null;
                        MyAddManager.this.interstitialCallBack(true);
                    }
                });
            } else {
                Show_next_Interstitial(adNetwork, activity2);
            }
        } catch (Exception e) {
            Show_next_Interstitial(adNetwork, activity2);
        }
    }

    private void loadUnityInterstitialAd(final Activity activity2, String str, String adNetwork) {
        try {
            if (SettingsClass.UNITY_AD_Status == 1 && !str.isEmpty()) {
//                final UnityAdsListener myAdsListener = new UnityAdsListener ();
                // Add the listener to the SDK:
//                UnityAds.addListener(myAdsListener);
                // Initialize the SDK:
                isLoading_Interstitial = true;

                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {

                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        MyAddManager.this.interstitialCallBack(true);
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
                        isLoading_Interstitial = false;

                        Show_next_Interstitial(adNetwork, activity2);
                    }
                });
                UnityAds.initialize(activity2, str, testMode);
            }
        } catch (Exception e) {
            Show_next_Interstitial(adNetwork, activity2);
        }
    }


    private void loadStartAppInterstitial(final Activity activity2, String str, String adNetwork) {
        try {
            if (SettingsClass.StartApp_App_Status == 1) {
                isLoading_Interstitial = true;

                startAppAd = new StartAppAd(activity2);
                startAppAd.loadAd(new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                        isLoading_Interstitial = false;
                        Show_next_Interstitial(adNetwork, activity2);
                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                        isLoading_Interstitial = false;
                        Show_next_Interstitial(adNetwork, activity2);
                    }
                });
            }
        } catch (Exception e) {
            Show_next_Interstitial(adNetwork, activity2);
        }

    }

    public void showInterstitialAd(Context context, final Activity activity, final MyCallback myCallback2) {
        try {
            if ((admob_interstitial != null) || (fbInterstitialAd != null && fbInterstitialAd.isAdLoaded()) || isLoading_Interstitial) {
                this.dialog = new Dialog(context);
                View inflate = LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, (ViewGroup) null);
                this.dialog.setContentView(inflate);
                this.dialog.setCancelable(false);
                this.dialog.getWindow().setLayout(-1, -2);
                this.dialog.show();
                final CircularProgressIndicator circularProgressIndicator = (CircularProgressIndicator) inflate.findViewById(R.id.circular_progress);
                circularProgressIndicator.setProgress(0.0d, 100.0d);
                new CountDownTimer((long) (ad_dialog_time_in_second * 1000), 100) {
                    public void onTick(long j) {
                        circularProgressIndicator.setProgress((double) ((j / 10) / ((long) MyAddManager.ad_dialog_time_in_second)), 100.0d);
                    }

                    public void onFinish() {
                        MyAddManager.this.dialog.dismiss();
                        Display_Interstitial(activity, myCallback2);
                    }
                }.start();
            } else {
                Display_Interstitial(activity, myCallback2);
            }
        } catch (Exception e) {
            if (MyAddManager.this.dialog != null && MyAddManager.this.dialog.isShowing())
                MyAddManager.this.dialog.dismiss();

            Display_Interstitial(activity, myCallback2);
        }
    }

    private void Display_Interstitial(Activity activity, MyCallback myCallback2) {
        myCallback = myCallback2;
        if (admob_interstitial != null) {
            SettingsClass.adshow_Calls = 0;
            admob_interstitial.show(activity);
        } else if (fbInterstitialAd != null && fbInterstitialAd.isAdLoaded()) {
            SettingsClass.adshow_Calls = 0;
            fbInterstitialAd.show();
        } else if (maxInterstitialAd != null && maxInterstitialAd.isReady()) {
            SettingsClass.adshow_Calls = 0;
            maxInterstitialAd.showAd();
        } else if (UnityAds.isReady(SettingsClass.UNITY_InterstitialID)) {
            UnityAds.show(activity, SettingsClass.UNITY_InterstitialID);
        } else {
            MyAddManager.this.interstitialCallBack(false);

            if (startAppAd != null) {
                SettingsClass.adshow_Calls = 0;
                startAppAd.showAd();
            }
        }
        isLoading_Interstitial = false;
    }

    public void interstitialCallBack(boolean isSuccess) {
        MyCallback myCallback2 = myCallback;
        if (myCallback2 != null) {
            myCallback2.callbackCall(isSuccess);
            myCallback = null;
        }
    }

    //endregion

    //region Native Ad Setup
    //********************** >> Region Native Banner << **********************

    private void Show_next_native(String adNetwork, ViewGroup viewGroup) {
        native_adNetworks.add(adNetwork);
        if (!native_adNetworks.contains(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 1) {
            showAdmobNative(viewGroup, ADNetwork_ADMOB, SettingsClass.AdMob_NativeID);
        } else if (!native_adNetworks.contains(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 1) {
            showAdmobNative(viewGroup, ADNetwork_ADManaer, SettingsClass.ADManager_NativeID);
        } else if (!native_adNetworks.contains(ADNetwork_MAX) && SettingsClass.MAX_AD_Status == 1) {
            showMAXNative_Banner(viewGroup, ADNetwork_MAX);
        } else if (!native_adNetworks.contains(ADNetwork_FB) && SettingsClass.FB_AD_Status == 1) {
            showFacebookNative(viewGroup, ADNetwork_FB);
        } else if (!native_adNetworks.contains(ADNetwork_STARTAPP) && SettingsClass.StartApp_App_Status == 1) {
            ShowStartAppNative(viewGroup, ADNetwork_STARTAPP);
        } else {
            viewGroup.removeAllViews();
            viewGroup.setVisibility(8);
        }
    }

    public void show_NATIVE(ViewGroup viewGroup, int _displayPixel) {
        this.DisplayPixel = _displayPixel;
        if (native_adNetworks == null) {
            native_adNetworks = new ArrayList<>();
        } else native_adNetworks.clear();
        showNative(viewGroup);
    }

    private void showNative(ViewGroup viewGroup) {
        if (Priority_AdNetwork.isEmpty()) return;

        switch (Priority_AdNetwork) {
            case "Admob":
                showAdmobNative(viewGroup, ADNetwork_ADMOB, SettingsClass.AdMob_NativeID);
                break;
            case "AdManager":
                showAdmobNative(viewGroup, ADNetwork_ADManaer, SettingsClass.ADManager_NativeID);
                break;
            case "FB_audiencenetwork":
                showFacebookNative(viewGroup, ADNetwork_FB);
                break;
            case "StartApp":
                ShowStartAppNative(viewGroup, ADNetwork_STARTAPP);
                break;
            case "AppLovinMax":
                showMAXNative_Banner(viewGroup, ADNetwork_MAX);
                break;
        }
    }

    private void showMAXNative_Banner(final ViewGroup viewGroup, String adNetwork) {
        try {
            if (!SettingsClass.MAX_NativeID.isEmpty() && SettingsClass.FB_AD_Status == 1) {
                maxNativeADView = new MaxAdView(SettingsClass.MAX_NativeID, MaxAdFormat.MREC, (Activity) activity);
                maxNativeADView.setListener(new MaxAdViewAdListener() {
                    @Override
                    public void onAdExpanded(MaxAd ad) {

                    }

                    @Override
                    public void onAdCollapsed(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        viewGroup.removeAllViews();
                        Show_next_native(adNetwork, viewGroup);
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                });

                int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
                int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);

                maxNativeADView.setLayoutParams(new FrameLayout.LayoutParams(widthPx, heightPx));

                // Set background or background color for MRECs to be fully functional
                maxNativeADView.setBackgroundColor(activity.getResources().getColor(R.color.background_color));

                viewGroup.removeAllViews();
                viewGroup.addView(maxNativeADView);

                // Load the ad
                maxNativeADView.loadAd();
            } else {
                Show_next_native(adNetwork, viewGroup);
            }
        } catch (Exception e) {
            Show_next_native(adNetwork, viewGroup);
        }
    }


    private void showAdmobNative(final ViewGroup viewGroup, final String adNetwork, String adUnity_ID) {
        try {

            if (adNetwork.equals(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 0) {
                if (adUnity_ID.isEmpty()) {
                    Show_next_native(adNetwork, viewGroup);
                    return;
                }
            } else if (adNetwork.equals(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 0) {
                if (adUnity_ID.isEmpty()) {
                    Show_next_native(adNetwork, viewGroup);
                    return;
                }
            }

            AdLoader.Builder builder = new AdLoader.Builder(activity, adUnity_ID);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                    new Inflate_ADS(MyAddManager.activity).inflate_NATIV_ADMOB(nativeAd, viewGroup);
//                    if (DisplayPixel <= 1280) {
//                        new Inflate_ADS(MyAddManager.activity).inflate_NATIV_ADMOB(nativeAd, viewGroup);
//                    } else {
//                        new Inflate_ADS(MyAddManager.activity).inflate_NATIV_ADMOB1(nativeAd, viewGroup);
//                    }
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Show_next_native(adNetwork, viewGroup);
                }

            }).build().loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            Show_next_native(adNetwork, viewGroup);
        }
    }

    private void showFacebookNative(final ViewGroup viewGroup, final String adNetwork) {
        try {
            if (SettingsClass.FB_NativeID.isEmpty() || SettingsClass.FB_AD_Status == 0) {
                Show_next_native(adNetwork, viewGroup);
                return;
            }
            final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, SettingsClass.FB_NativeID);
            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
                public void onAdClicked(Ad ad) {
                }

                public void onLoggingImpression(Ad ad) {
                }

                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Show_next_native(adNetwork, viewGroup);
                }

                public void onAdLoaded(Ad ad) {
                    if (nativeAd != null && nativeAd == ad) {
                        new Inflate_ADS(MyAddManager.activity).inflate_NATIV_FB(nativeAd, viewGroup);
                    }
                }
            }).build());
        } catch (Exception e) {
            Show_next_native(adNetwork, viewGroup);
        }
    }

    private void ShowStartAppNative(final ViewGroup viewGroup, final String adNetwork) {
        try {
            if (SettingsClass.StartApp_App_Status == 1) {
                viewGroup.removeAllViews();
                StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
                startAppNativeAd.loadAd(new NativeAdPreferences(), new AdEventListener() {
                    @Override
                    public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                        View inflate = LayoutInflater.from(activity).inflate(R.layout.startapp_native_ad, (ViewGroup) null);
                        viewGroup.addView(inflate);
                    }

                    @Override
                    public void onFailedToReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                        Show_next_native(adNetwork, viewGroup);
                    }
                });
            } else {
                Show_next_native(adNetwork, viewGroup);
            }
        } catch (Exception e) {
            Show_next_native(adNetwork, viewGroup);
        }
    }


    //endregion


    //region Small Ad Setup
    //********************** >> Region Small Banner << **********************

    public void show_BANNER(ViewGroup viewGroup, Context context, Display _display) {
        display = _display;
        if (hasActiveInternetConnection(activity) && SettingsClass.Allowtype) {
            if (simpleBanner_adNetworks == null) {
                simpleBanner_adNetworks = new ArrayList<>();
            } else simpleBanner_adNetworks.clear();
            showBanner(viewGroup, context);

        }
    }

    private void showBanner(ViewGroup viewGroup, Context context) {

        if (Priority_AdNetwork.isEmpty()) return;

        switch (Priority_AdNetwork) {
            case "Admob":
                showAdmobBanner(viewGroup, ADNetwork_ADMOB, context, SettingsClass.AdMob_BannerID, SettingsClass.AdMob_NativeID);
                break;
            case "AdManager":
                showAdmobBanner(viewGroup, ADNetwork_ADManaer, context, SettingsClass.ADManager_BannerID, SettingsClass.ADManager_NativeID);
                break;
            case "FB_audiencenetwork":
                showFacebookBanner(viewGroup, ADNetwork_FB, context);
                break;
            case "StartApp":
                showStartAppBanner(viewGroup, ADNetwork_STARTAPP, context);
                break;
            case "AppLovinMax":
                showMAXBanner(viewGroup, ADNetwork_MAX, context);
                break;
        }
    }

    private void Show_next_banner(ViewGroup viewGroup, String adNetwork, Context context) {
        simpleBanner_adNetworks.add(adNetwork);
        if (!simpleBanner_adNetworks.contains(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 1) {
            showAdmobBanner(viewGroup, ADNetwork_ADMOB, context, SettingsClass.AdMob_BannerID, SettingsClass.AdMob_NativeID);
        } else if (!simpleBanner_adNetworks.contains(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 1) {
            showAdmobBanner(viewGroup, ADNetwork_ADManaer, context, SettingsClass.ADManager_BannerID, SettingsClass.ADManager_NativeID);
        } else if (!simpleBanner_adNetworks.contains(ADNetwork_MAX) && SettingsClass.MAX_AD_Status == 1) {
            showMAXBanner(viewGroup, ADNetwork_MAX, context);
        } else if (!simpleBanner_adNetworks.contains(ADNetwork_FB) && SettingsClass.FB_AD_Status == 1) {
            showFacebookBanner(viewGroup, ADNetwork_FB, context);
        } else if (!simpleBanner_adNetworks.contains(ADNetwork_STARTAPP) && SettingsClass.StartApp_App_Status == 1) {
            showStartAppBanner(viewGroup, ADNetwork_STARTAPP, context);
        } else {
            viewGroup.removeAllViews();
            viewGroup.setVisibility(8);
        }
    }

    private void showAdmobBanner(final ViewGroup viewGroup, final String adNetwork, final Context context, String adUnit_IDbanner, String adUnit_IDnative) {
        try {
            if (adNetwork.equals(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 0) {
                Show_next_banner(viewGroup, adNetwork, context);
                return;
            } else if (adNetwork.equals(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 0) {
                Show_next_banner(viewGroup, adNetwork, context);
                return;
            }


            if (!SettingsClass.StartApp.contains("StopNative")) {
                AdLoader adLoader = new AdLoader.Builder(context, adUnit_IDnative)
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                new Inflate_ADS(MyAddManager.activity).inflate_Small_NATIV_ADMOB(nativeAd, viewGroup);
                            }
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {
                                // Handle the failure by logging, altering the UI, and so on.
                                viewGroup.removeAllViews();
                                final com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(activity);
                                AdRequest adRequest =
                                        new AdRequest.Builder()
                                                .build();
                                AdSize adSize = getAdSize();
                                adView.setAdSize(adSize);
                                adView.setAdUnitId(adUnit_IDbanner);
                                adView.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdLoaded() {
                                        viewGroup.removeAllViews();
                                        viewGroup.addView(adView);
                                    }

                                    @Override
                                    public void onAdFailedToLoad(LoadAdError adError) {
                                        // Code to be executed when an ad request fails.
                                        viewGroup.removeAllViews();
                                        Show_next_banner(viewGroup, adNetwork, context);
                                    }
                                });
                                adView.loadAd(adRequest);
                            }
                        }).build();
                adLoader.loadAd(new AdRequest.Builder().build());
            } else {
                final com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(activity);
                AdRequest adRequest =
                        new AdRequest.Builder()
                                .build();
                AdSize adSize = getAdSize();
                adView.setAdSize(adSize);
                adView.setAdUnitId(adUnit_IDbanner);
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Code to be executed when an ad request fails.
                        viewGroup.removeAllViews();
                        Show_next_banner(viewGroup, adNetwork, context);
                    }
                });
                adView.loadAd(adRequest);
            }


        } catch (Exception e) {
            MyAddManager.this.Show_next_banner(viewGroup, adNetwork, context);
        }
    }

    private void showFacebookBanner(final ViewGroup viewGroup, final String adNetwork, final Context context) {
        try {
            if (SettingsClass.FB_BannerID.isEmpty() || SettingsClass.FB_AD_Status == 0) {
                Show_next_banner(viewGroup, adNetwork, context);
                return;
            }

            NativeBannerAd nativeBannerAd = new NativeBannerAd(context, SettingsClass.Fb_Native_Banner);
            nativeBannerAd.loadAd((NativeAdBase.NativeLoadAdConfig) nativeBannerAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    Log.e("TAG FB Banner", "Native banner show");

                    new Inflate_ADS(MyAddManager.activity).inflate_FBSmallNativeAd(nativeBannerAd, viewGroup);
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Log.e("TAG FB Banner", adError.getErrorMessage());

                    com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, SettingsClass.FB_BannerID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                    adView.loadAd(adView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
                        public void onAdClicked(Ad ad) {
                        }

                        public void onLoggingImpression(Ad ad) {
                        }

                        public void onError(Ad ad, AdError adError) {
                            MyAddManager.this.Show_next_banner(viewGroup, adNetwork, context);
                        }

                        public void onAdLoaded(Ad ad) {
                            viewGroup.removeAllViews();
                            viewGroup.addView(adView);
                        }
                    }).build());
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            }));
        } catch (Exception e) {
            Show_next_banner(viewGroup, adNetwork, context);
        }
    }

    private AdSize getAdSize() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);

    }

    private void showStartAppBanner(final ViewGroup viewGroup, String adNetwork, Context context) {
        if (SettingsClass.StartApp_App_Status == 0) {
            Show_next_banner(viewGroup, adNetwork, context);
            return;
        }

        startAppBanner = new Banner(context);
        viewGroup.removeAllViews();
        viewGroup.addView(startAppBanner);

    }

    private void showMAXBanner(final ViewGroup viewGroup, String adNetwork, Context context) {
        try {
            if (SettingsClass.MAX_BannerID.isEmpty() || SettingsClass.MAX_AD_Status == 0) {
                Show_next_banner(viewGroup, adNetwork, context);
                return;
            }
            MaxAdView adView = new MaxAdView(SettingsClass.MAX_BannerID, (Activity) context);
//        adView.setListener( context );
            adView.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {

                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    Show_next_banner(viewGroup, adNetwork, context);
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    Show_next_banner(viewGroup, adNetwork, context);
                }
            });

            // Stretch to the width of the screen for banners to be fully functional
            int width = ViewGroup.LayoutParams.MATCH_PARENT;

            // Banner height on phones and tablets is 50 and 90, respectively
            int heightPx = context.getResources().getDimensionPixelSize(R.dimen.banner_height);

            adView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));

            // Set background or background color for banners to be fully functional
//        adView.setBackgroundColor( R.color.background_color );

//        ViewGroup rootView = findViewById( android.R.id.content );
            viewGroup.addView(adView);

            // Load the ad
            adView.loadAd();
        } catch (Exception e) {
            Show_next_banner(viewGroup, adNetwork, context);
        }
    }

    //endregion

    //region Small Ad Setup
    //********************** >> Region Small Banner << **********************

    private void Load_next_RewarrdAd(String adNetwork, Activity activity2, Context context) {
        Rewarded_adNetworks.add(adNetwork);

        if (!Rewarded_adNetworks.contains(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 1) {
            loadAdmobRewardedAd(context, activity2, SettingsClass.AdMob_Rewarded, ADNetwork_ADMOB);
        } else if (!Rewarded_adNetworks.contains(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 1) {
            loadAdmobRewardedAd(context, activity2, SettingsClass.ADManager_Rewarded, ADNetwork_ADManaer);
        } else if (!Rewarded_adNetworks.contains(ADNetwork_MAX) && SettingsClass.MAX_AD_Status == 1) {
            loadMAXRewardedAd(context, activity2, SettingsClass.MAX_RewardedID, ADNetwork_MAX);
        } else if (!Rewarded_adNetworks.contains(ADNetwork_FB) && SettingsClass.FB_AD_Status == 1) {
            loadFBRewardedAd(context, activity2, SettingsClass.Fb_Rewarded, ADNetwork_FB);
        } else if (!Rewarded_adNetworks.contains(ADNetwork_UNITY) && SettingsClass.UNITY_AD_Status == 1) {
            loadUnityRewardedAD(context, activity2, SettingsClass.UNITY_GameID, ADNetwork_UNITY);
        } else {
            loadintertialads(activity2, false, false);
        }
    }

    public void loadRewardedAd(Context context, Activity activity2) {
        if (!SettingsClass.Allowtype) return;
        if (Rewarded_adNetworks != null) {
            Rewarded_adNetworks.clear();
        } else {
            Rewarded_adNetworks = new ArrayList<>();
        }

        switch (Priority_AdNetwork) {
            case "Admob":
                loadAdmobRewardedAd(context, activity2, SettingsClass.AdMob_Rewarded, ADNetwork_ADMOB);
                break;
            case "AdManager":
                loadAdmobRewardedAd(context, activity2, SettingsClass.ADManager_Rewarded, ADNetwork_ADManaer);
                break;
            case "FB_audiencenetwork":
                loadFBRewardedAd(context, activity2, SettingsClass.Fb_Rewarded, ADNetwork_FB);
                break;
            case "AppLovinMax":
                loadMAXRewardedAd(context, activity2, SettingsClass.MAX_RewardedID, ADNetwork_MAX);
                break;
            case "Unity":
                loadUnityRewardedAD(context, activity2, SettingsClass.UNITY_GameID, ADNetwork_UNITY);
                break;
        }
    }

    private void Display_RewardedAd(Context context, Activity activity, MyCallback myCallback2) {
        myCallback = myCallback2;
        if (admobrewardedAd != null) {
            showAdmobRewardedVideo(context, activity);
        } else if (fbRewardedVideoAd != null && fbRewardedVideoAd.isAdLoaded()) {
            fbRewardedVideoAd.show();
        } else if (maxRewardedAd != null && maxRewardedAd.isReady()) {
            maxRewardedAd.showAd();
        } else if (UnityAds.isReady(SettingsClass.UNITY_RewardedID)) {
            UnityAds.show(activity, SettingsClass.UNITY_RewardedID);
        } else {
            MyAddManager.this.interstitialCallBack(true);
        }
    }

    private void loadAdmobRewardedAd(final Context context, final Activity activity, String adUnitID, String adNetwork) {
        try {

            if (adNetwork.equals(ADNetwork_ADMOB) && SettingsClass.Admob_adstatus == 0) {
                Load_next_RewarrdAd(adNetwork, activity, context);
                return;
            } else if (adNetwork.equals(ADNetwork_ADManaer) && SettingsClass.ADManager_AD_Status == 0) {
                Load_next_RewarrdAd(adNetwork, activity, context);
                return;
            }

            if (!adUnitID.isEmpty()) {
                if (admobrewardedAd == null) {
                    isLoading = true;
                    AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();

                    RewardedAd.load(context, adUnitID, adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            admobrewardedAd = rewardedAd;
                            isLoading = false;
//                            Toast.makeText(MainActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d("TAG", loadAdError.getMessage());
                            admobrewardedAd = null;
                            isLoading = false;
                            Load_next_RewarrdAd(adNetwork, activity, context);
//                            Toast.makeText(MainActivity.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Load_next_RewarrdAd(adNetwork, activity, context);
            }
        } catch (Exception e) {
            Load_next_RewarrdAd(adNetwork, activity, context);
        }
    }

    private void loadFBRewardedAd(final Context context, final Activity activity, String adUnitID, String adNetwork) {
        try {
            if (SettingsClass.FB_AD_Status == 1 && !adUnitID.isEmpty()) {
                if (fbRewardedVideoAd == null) {
                    isLoading = true;
                    isRewarded = false;
                    fbRewardedVideoAd = new RewardedVideoAd(context, adUnitID);
                    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
                        @Override
                        public void onError(Ad ad, AdError error) {
                            // Rewarded video ad failed to load
                            Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
                            isLoading = false;
                            Load_next_RewarrdAd(adNetwork, activity, context);

                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            // Rewarded video ad is loaded and ready to be displayed
                            Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                            isLoading = false;
                        }

                        @Override
                        public void onAdClicked(Ad ad) {
                            // Rewarded video ad clicked
                            Log.d(TAG, "Rewarded video ad clicked!");
                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {
                            // Rewarded Video ad impression - the event will fire when the
                            // video starts playing
                            Log.d(TAG, "Rewarded video ad impression logged!");
                        }

                        @Override
                        public void onRewardedVideoCompleted() {
                            // Rewarded Video View Complete - the video has been played to the end.
                            // You can use this event to initialize your reward
                            Log.d(TAG, "Rewarded video completed!");
                            isRewarded = true;
                            // Call method to give reward
                            // giveReward();
                        }

                        @Override
                        public void onRewardedVideoClosed() {
                            if (isRewarded) MyAddManager.this.interstitialCallBack(true);
                            else MyAddManager.this.interstitialCallBack(false);
                            // The Rewarded Video ad was closed - this can occur during the video
                            // by closing the app, or closing the end card.
                            Log.d(TAG, "Rewarded video ad closed!");
                        }
                    };

                    fbRewardedVideoAd.loadAd(
                            fbRewardedVideoAd.buildLoadAdConfig()
                                    .withAdListener(rewardedVideoAdListener)
                                    .build());
                }
            }
        } catch (Exception e) {
            Load_next_RewarrdAd(adNetwork, activity, context);
        }
    }

    private void loadUnityRewardedAD(final Context context, final Activity activity, String adUnitID, String adNetwork) {
        try {
            if (SettingsClass.UNITY_AD_Status == 1 && !adUnitID.isEmpty()) {
                isLoading = true;

                UnityAds.addListener(new IUnityAdsListener() {
                    @Override
                    public void onUnityAdsReady(String placementId) {
                        isLoading = false;
                    }

                    @Override
                    public void onUnityAdsStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsFinish(String placementId, UnityAds.FinishState result) {
                        MyAddManager.this.interstitialCallBack(true);
                        isLoading = false;
                        // Implement conditional logic for each ad completion status:
                        if (result.equals(UnityAds.FinishState.COMPLETED)) {
                            // Reward the user for watching the ad to completion.
                            if (isRewarded) MyAddManager.this.interstitialCallBack(true);
                            else MyAddManager.this.interstitialCallBack(false);

                        } else if (result == result.SKIPPED) {
                            // Do not reward the user for skipping the ad.
                        } else if (result == result.ERROR) {
                            // Log an error.
                        }
                    }

                    @Override
                    public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
                        isLoading_Interstitial = false;

                        Load_next_RewarrdAd(adNetwork, activity, context);
                    }
                });
                UnityAds.initialize(activity, adUnitID, testMode);
            }
        } catch (Exception e) {
            Load_next_RewarrdAd(adNetwork, activity, context);
        }
    }

    private void loadMAXRewardedAd(final Context context, final Activity activity, String adUnitID, String adNetwork) {

        try {
            if (SettingsClass.MAX_AD_Status == 1 && !adUnitID.isEmpty()) {
                rewardedAd = MaxRewardedAd.getInstance(adUnitID, activity);
                rewardedAd.setListener(new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {
                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        isRewarded = true;
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {

                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        if (isRewarded) MyAddManager.this.interstitialCallBack(true);
                        else MyAddManager.this.interstitialCallBack(false);
                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        Load_next_RewarrdAd(adNetwork, activity, context);
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                });

            } else {
                Load_next_RewarrdAd(adNetwork, activity, context);
            }
        } catch (Exception e) {
            Load_next_RewarrdAd(adNetwork, activity, context);
        }


    }


    private void showAdmobRewardedVideo(final Context context, final Activity activity) {
        if (admobrewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;
        }

        admobrewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {

                        // Called when ad is shown.
                        Log.d("TAG", "onAdShowedFullScreenContent");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        admobrewardedAd = null;
                        if (isRewarded) interstitialCallBack(true);
                        else interstitialCallBack(false);
                    }
                });

        Activity activityContext = activity;
        admobrewardedAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        isRewarded = true;
                        Log.d("TAG", "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                    }
                });
    }
    //endregion
}
