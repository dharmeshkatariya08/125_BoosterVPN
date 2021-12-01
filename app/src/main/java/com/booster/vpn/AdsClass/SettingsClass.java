package com.booster.vpn.AdsClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.booster.vpn.BuildConfig;
import com.booster.vpn.R;
import com.bumptech.glide.Glide;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.github.javiersantos.appupdater.AppUpdater;

import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SettingsClass {
    public static boolean isInitialized = false;

    public static Activity _recActivity = null;
    public static SharedPreferences sp;
    public static Editor ed;
    public static int adshow_Calls;
    public static String updateMessage = "Check out the latest version available to get the latest features";
    //AdMob
    public static int MAXRANDOM = 1;

    public static String updateTitle = "New Update Available";
    public static MyApplication myApplication;
    public static AppOpenManager appOpenManager;
    public static String info_type = "Characters";


    public static String SP_KEY_AllowType = "AllowType";
    public static String SP_KEY_CONTACTNO = "CONTACTNO";
    public static String SP_KEY_COUNTRY = "COUNTRY";
    public static String SP_KEY_NICKNAME = "NICKNAME";
    public static String SP_KEY_GIRLNUMBER = "GIRLNUMBER";
    public static String SP_KEY_LASTDATE = "LASTDATE";
    public static String SP_KEY_ACCOUNT_NAME = "Accountname";


    public static String SP_KEY_StartApp_App_ID = "StartApp_App_ID";
    public static String SP_KEY_StartApp_App_Status = "StartApp_App_Status";
    public static String SP_KEY_Ironsorsource = "Ironsorsource";
    public static String SP_KEY_StartApp = "StartApp";

    //fb
    public static String SP_KEY_FB_InterstitialID = "FB_InterstitialID";
    public static String SP_KEY_FB_NativeID = "FB_NativeID";
    public static String SP_KEY_FB_BannerID = "FB_BannerID";
    public static String SP_KEY_Fb_Rewarded = "Fb_Rewarded";
    public static String SP_KEY_Fb_Native_Banner = "Fb_Native_Banner";
    public static String SP_KEY_FB_AD_Status = "FB_AD_Status";

    //admob
    public static String SP_KEY_AdMob_AppID = "AdMob_AppID";
    public static String SP_KEY_AdMob_BannerID = "AdMob_BannerID";
    public static String SP_KEY_AdMob_InterstitialID = "AdMob_InterstitialID";
    public static String SP_KEY_AdMob_NativeID = "AdMob_NativeID";
    public static String SP_KEY_AdMob_Rewarded = "AdMob_Rewarded";
    public static String SP_KEY_AdMob_AppOpen_ID = "AdMob_AppOpen_ID";
    public static String SP_KEY_admob_stop = "admob_stop";

    //ADManager
    public static String SP_KEY_ADManager_AppID = "ADManager_AppID";
    public static String SP_KEY_ADManager_BannerID = "ADManager_BannerID";
    public static String SP_KEY_ADManager_InterstitialID = "ADManager_InterstitialID";
    public static String SP_KEY_ADManager_NativeID = "ADManager_NativeID";
    public static String SP_KEY_ADManager_Rewarded = "ADManager_Rewarded";
    public static String SP_KEY_ADManager_AppOpen_ID = "ADManager_AppOpen_ID";
    public static String SP_KEY_ADManager_AD_Status = "ADManager_AD_Status";


    //AppLovin_MAX
    public static String SP_KEY_MAX_BannerID = "MAX_BannerID";
    public static String SP_KEY_MAX_InterstitialID = "MAX_InterstitialID";
    public static String SP_KEY_MAX_NativeID = "MAX_NativeID";
    public static String SP_KEY_MAX_Rewarded = "MAX_Rewarded";
    public static String SP_KEY_MAX_AD_Status = "MAX_AD_Status";


    //Unity_Ads
    public static String SP_KEY_UNITY_InterstitialID = "UNITY_InterstitialID";
    public static String SP_KEY_UNITY_Rewarded = "UNITY_Rewarded";
    public static String SP_KEY_UNITY_GameID = "UNITY_GameID";
    public static String SP_KEY_UNITY_AD_Status = "UNITY_AD_Status";


    //other
    public static String SP_KEY_Allowtype = "Allowtype";
    public static String SP_KEY_App_Version = "App_Version";
    public static String SP_KEY_under_development = "under_development";

    public static String SP_KEY_Privecy_policy = "Privecy_policy";
    public static String SP_KEY_Priority_AdNetwork = "Priority_AdNetwork";
    public static String SP_KEY_Ad_Interval = "Ad_Interval";

    public static String Privecy_policy = "";
    public static String Account_Name = "";
    public static String Priority_AdNetwork = "";
    public static int Ad_Interval;

    public static String AdMob_AppID = "";
    public static String AdMob_BannerID = "";
    public static String AdMob_InterstitialID = "";
    public static String AdMob_InterstitialID2 = "";
    public static String AdMob_NativeID = "";
    public static String AdMob_Rewarded = "";
    public static String AdMob_AppOpen_ID = "aa";
    public static String AdMob_AppOpen_ID2 = "aa";

    public static String ADManager_BannerID = "";
    public static String ADManager_InterstitialID = "";
    public static String ADManager_InterstitialID2 = "";
    public static String ADManager_NativeID = "";
    public static String ADManager_Rewarded = "";

    public static String FB_InterstitialID = "";
    public static String FB_InterstitialID2 = "";
    public static String FB_NativeID = "";
    public static String FB_BannerID = "";
    public static String Fb_Rewarded = "";
    public static String Fb_Native_Banner = "";


    public static String MAX_InterstitialID = "";
    public static String MAX_InterstitialID2 = "";
    public static String MAX_NativeID = "";
    public static String MAX_BannerID = "";
    public static String MAX_RewardedID = "";

    public static String UNITY_InterstitialID = "";
    public static String UNITY_InterstitialID2 = "";
    public static String UNITY_RewardedID = "";
    public static String UNITY_GameID = "";


    public static int Admob_adstatus = 1;
    public static int FB_AD_Status = 1;
    public static int ADManager_AD_Status = 1;
    public static int MAX_AD_Status = 1;
    public static int UNITY_AD_Status = 1;
    public static int StartApp_App_Status = 1;

    public static String StartApp_App_ID = "";
    public static String Ironsorsource = "";
    public static String StartApp = "";

    public static boolean Allowtype = false;
    public static int App_Version;
    public static boolean under_development = true;
    public static String content1;
    public static ArrayList<Object> data;

    static DataLoaded isDataLoaded;

    public static void DataisLoaded(boolean isSuccess) {
        DataLoaded isDataLoaded2 = isDataLoaded;
        if (isDataLoaded2 != null) {
            isDataLoaded2.callbackCall(isSuccess);
            isDataLoaded = null;
        }
    }


    static ArrayList<String> apiList = new ArrayList<>();

    public static void Update_My_Data(Context context, DataLoaded isDataLoaded3) {
        isDataLoaded = isDataLoaded3;

        if (AdMob_InterstitialID.length() == 0 && Privecy_policy.length() == 0) {
            ReloadData(context);
        } else {
            UpdateAdData(context);
        }

    }

    static void UpdateAdData(Context context) {
        try {
            Allowtype = GetStringData(context, SP_KEY_Allowtype).contains("1");
            App_Version = Integer.parseInt(GetStringData(context, SP_KEY_App_Version));
            under_development = GetStringData(context, SP_KEY_under_development).contains("1");

            Privecy_policy = GetStringData(context, SP_KEY_Privecy_policy);
            Priority_AdNetwork = GetStringData(context, SP_KEY_Priority_AdNetwork);
            Ad_Interval = Integer.parseInt(GetStringData(context, SP_KEY_Ad_Interval));

            AdMob_AppID = GetStringData(context, SP_KEY_AdMob_AppID);
            AdMob_BannerID = GetStringData(context, SP_KEY_AdMob_BannerID);
            AdMob_InterstitialID = GetStringData(context, SP_KEY_AdMob_InterstitialID);
            if (AdMob_InterstitialID.contains("&&")) {
                String[] separated = AdMob_InterstitialID.split("&&");
                AdMob_InterstitialID = separated[0].trim();
                AdMob_InterstitialID2 = separated[1].trim();
            } else {
                AdMob_InterstitialID2 = AdMob_InterstitialID;
            }
            Account_Name = GetStringData(context, SP_KEY_ACCOUNT_NAME).trim().replace(" ", "+");

            AdMob_NativeID = GetStringData(context, SP_KEY_AdMob_NativeID);
            AdMob_Rewarded = GetStringData(context, SP_KEY_AdMob_Rewarded);
            AdMob_AppOpen_ID = GetStringData(context, SP_KEY_AdMob_AppOpen_ID);
            if (AdMob_AppOpen_ID.contains("&&")) {
                String[] separated = AdMob_AppOpen_ID.split("&&");
                AdMob_AppOpen_ID = separated[0].trim();
                AdMob_AppOpen_ID2 = separated[1].trim();
            } else {
                AdMob_AppOpen_ID2 = AdMob_AppOpen_ID;
            }
            Admob_adstatus = GetStringData(context, SP_KEY_admob_stop).contains("1") ? 1 : 0;

            ADManager_BannerID = GetStringData(context, SP_KEY_ADManager_BannerID);
            ADManager_InterstitialID = GetStringData(context, SP_KEY_ADManager_InterstitialID);
            if (ADManager_InterstitialID.contains("&&")) {
                String[] separated = ADManager_InterstitialID.split("&&");
                ADManager_InterstitialID = separated[0].trim();
                ADManager_InterstitialID2 = separated[1].trim();
            } else {
                ADManager_InterstitialID2 = ADManager_InterstitialID;
            }

            ADManager_NativeID = GetStringData(context, SP_KEY_ADManager_NativeID);
            ADManager_Rewarded = GetStringData(context, SP_KEY_ADManager_Rewarded);
            ADManager_AD_Status = GetStringData(context, SP_KEY_ADManager_AD_Status).contains("1") ? 1 : 0;

            FB_InterstitialID = GetStringData(context, SP_KEY_FB_InterstitialID);
            if (FB_InterstitialID.contains("&&")) {
                String[] separated = FB_InterstitialID.split("&&");
                FB_InterstitialID = separated[0].trim();
                FB_InterstitialID2 = separated[1].trim();
            } else {
                FB_InterstitialID2 = FB_InterstitialID;
            }
            FB_NativeID = GetStringData(context, SP_KEY_FB_NativeID);
            FB_BannerID = GetStringData(context, SP_KEY_FB_BannerID);
            Fb_Rewarded = GetStringData(context, SP_KEY_Fb_Rewarded);
            Fb_Native_Banner = GetStringData(context, SP_KEY_Fb_Native_Banner);
            FB_AD_Status = GetStringData(context, SP_KEY_FB_AD_Status).contains("1") ? 1 : 0;

            MAX_InterstitialID = GetStringData(context, SP_KEY_MAX_InterstitialID);
            if (MAX_InterstitialID.contains("&&")) {
                String[] separated = MAX_InterstitialID.split("&&");
                MAX_InterstitialID = separated[0].trim();
                MAX_InterstitialID2 = separated[1].trim();
            } else {
                MAX_InterstitialID2 = MAX_InterstitialID;
            }
            MAX_NativeID = GetStringData(context, SP_KEY_MAX_NativeID);
            MAX_BannerID = GetStringData(context, SP_KEY_MAX_BannerID);
            MAX_RewardedID = GetStringData(context, SP_KEY_MAX_Rewarded);
            MAX_NativeID = GetStringData(context, SP_KEY_MAX_NativeID);
            MAX_AD_Status = GetStringData(context, SP_KEY_MAX_AD_Status).contains("1") ? 1 : 0;

            UNITY_InterstitialID = GetStringData(context, SP_KEY_UNITY_InterstitialID);
            if (UNITY_InterstitialID.contains("&&")) {
                String[] separated = UNITY_InterstitialID.split("&&");
                UNITY_InterstitialID = separated[0].trim();
                UNITY_InterstitialID2 = separated[1].trim();
            } else {
                UNITY_InterstitialID2 = UNITY_InterstitialID;
            }
            UNITY_RewardedID = GetStringData(context, SP_KEY_UNITY_Rewarded);
            UNITY_GameID = GetStringData(context, SP_KEY_UNITY_GameID);
            UNITY_AD_Status = GetStringData(context, SP_KEY_UNITY_AD_Status).contains("1") ? 1 : 0;


            StartApp_App_ID = GetStringData(context, SP_KEY_StartApp_App_ID);
            StartApp_App_Status = GetStringData(context, SP_KEY_StartApp_App_Status).contains("1") ? 1 : 0;
            Ironsorsource = GetStringData(context, SP_KEY_Ironsorsource);
            StartApp = GetStringData(context, SP_KEY_StartApp);
            isInitialized = true;
            DataisLoaded(true);

        } catch (Exception e) {
            DataisLoaded(true);
        }
    }

    static Dialog dialog;

    public static void ReloadData(Context context) {
        try {
            dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setTitle("Plese Wait..!");
            dialog.show();

            RequestQueue newRequestQueue = Volley.newRequestQueue(context);
            StringBuilder stringBuilder = new StringBuilder();
            if (!apiList.contains("http://api.videocall.envisioninfo.tech/")) {
                apiList.add("http://api.videocall.envisioninfo.tech/");
                stringBuilder.append("http://api.videocall.envisioninfo.tech/");
            } else {
                apiList.add("http://hdgamesltd.com/hetech_apps/");
                stringBuilder.append("http://hdgamesltd.com/hetech_apps/");
            }


            stringBuilder.append("api_get_appData.php?appid=");

            if (BuildConfig.DEBUG) {
                stringBuilder.append("com.envision.test");
            } else {
                stringBuilder.append(context.getPackageName());
            }
            stringBuilder.append("&pkg=");
            stringBuilder.append(context.getPackageName());

            newRequestQueue.add(new StringRequest(1, stringBuilder.toString(), new Response.Listener<String>() {
                public void onResponse(String str) {
                    try {
                        JSONArray jSONArray = new JSONObject(str).getJSONArray("message");
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);

                            //ADMob
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_AdMob_AppID, jSONObject.getString(SettingsClass.SP_KEY_AdMob_AppID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_AdMob_BannerID, jSONObject.getString(SettingsClass.SP_KEY_AdMob_BannerID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_AdMob_InterstitialID, jSONObject.getString(SettingsClass.SP_KEY_AdMob_InterstitialID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_AdMob_NativeID, jSONObject.getString(SettingsClass.SP_KEY_AdMob_NativeID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_AdMob_Rewarded, jSONObject.getString(SettingsClass.SP_KEY_AdMob_Rewarded).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_AdMob_AppOpen_ID, jSONObject.getString(SettingsClass.SP_KEY_AdMob_AppOpen_ID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_admob_stop, jSONObject.getString(SettingsClass.SP_KEY_admob_stop).trim());

                            //ADManager
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ADManager_BannerID, jSONObject.getString(SettingsClass.SP_KEY_ADManager_BannerID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ADManager_InterstitialID, jSONObject.getString(SettingsClass.SP_KEY_ADManager_InterstitialID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ADManager_NativeID, jSONObject.getString(SettingsClass.SP_KEY_ADManager_NativeID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ADManager_Rewarded, jSONObject.getString(SettingsClass.SP_KEY_ADManager_Rewarded).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ADManager_AppOpen_ID, jSONObject.getString(SettingsClass.SP_KEY_ADManager_AppOpen_ID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ADManager_AD_Status, jSONObject.getString(SettingsClass.SP_KEY_ADManager_AD_Status).trim());

                            //FB
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_FB_InterstitialID, jSONObject.getString(SettingsClass.SP_KEY_FB_InterstitialID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_FB_NativeID, jSONObject.getString(SettingsClass.SP_KEY_FB_NativeID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_FB_BannerID, jSONObject.getString(SettingsClass.SP_KEY_FB_BannerID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Fb_Rewarded, jSONObject.getString(SettingsClass.SP_KEY_Fb_Rewarded).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Fb_Native_Banner, jSONObject.getString(SettingsClass.SP_KEY_Fb_Native_Banner).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_FB_AD_Status, "" + jSONObject.getInt(SettingsClass.SP_KEY_FB_AD_Status));

                            //AppLovin_MAX
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_MAX_InterstitialID, jSONObject.getString(SettingsClass.SP_KEY_MAX_InterstitialID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_MAX_NativeID, jSONObject.getString(SettingsClass.SP_KEY_MAX_NativeID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_MAX_BannerID, jSONObject.getString(SettingsClass.SP_KEY_MAX_BannerID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_MAX_Rewarded, jSONObject.getString(SettingsClass.SP_KEY_MAX_Rewarded).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_MAX_AD_Status, "" + jSONObject.getInt(SettingsClass.SP_KEY_MAX_AD_Status));

                            //UNITY_ADS
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_UNITY_InterstitialID, jSONObject.getString(SettingsClass.SP_KEY_UNITY_InterstitialID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_UNITY_Rewarded, jSONObject.getString(SettingsClass.SP_KEY_UNITY_Rewarded).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_UNITY_AD_Status, "" + jSONObject.getInt(SettingsClass.SP_KEY_UNITY_AD_Status));
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_UNITY_GameID, "" + jSONObject.getString(SettingsClass.SP_KEY_UNITY_GameID));

                            //StartApp
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_StartApp_App_ID, jSONObject.getString(SettingsClass.SP_KEY_StartApp_App_ID).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_StartApp_App_Status, "" + jSONObject.getInt(SettingsClass.SP_KEY_StartApp_App_Status));
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Ironsorsource, jSONObject.getString(SettingsClass.SP_KEY_Ironsorsource).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_StartApp, jSONObject.getString(SettingsClass.SP_KEY_StartApp).trim());

                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Allowtype, jSONObject.getString(SettingsClass.SP_KEY_Allowtype).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_App_Version, jSONObject.getString(SettingsClass.SP_KEY_App_Version).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_under_development, jSONObject.getString(SettingsClass.SP_KEY_under_development).trim());

                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Privecy_policy, jSONObject.getString(SettingsClass.SP_KEY_Privecy_policy).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Priority_AdNetwork, jSONObject.getString(SettingsClass.SP_KEY_Priority_AdNetwork).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_Ad_Interval, jSONObject.getString(SettingsClass.SP_KEY_Ad_Interval).trim());
                            SettingsClass.UpdateStringData(context, SettingsClass.SP_KEY_ACCOUNT_NAME, jSONObject.getString(SettingsClass.SP_KEY_ACCOUNT_NAME).trim());

                        }
                        SettingsClass.adshow_Calls = SettingsClass.Ad_Interval - 1;
                        dialog.dismiss();
                        isInitialized = true;
                        UpdateAdData(context);

//                        DataisLoaded(true);
//                        Update_My_Data(context, isDataLoaded);
                    } catch (JSONException e) {
                        dialog.dismiss();

                        e.printStackTrace();
                        if (!apiList.contains("http://hdgamesltd.com/hetech_apps/")) {
                            ReloadData(context);
                        } else {
                            SettingsClass.adshow_Calls = SettingsClass.App_Version - 1;
                            SettingsClass.adshow_Calls = SettingsClass.Ad_Interval - 1;
//                            DataisLoaded(true);
                            UpdateAdData(context);
                        }
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    dialog.dismiss();
                    Log.d("Error=", String.valueOf(volleyError));

                    if (!apiList.contains("http://hdgamesltd.com/hetech_apps/")) {
                        ReloadData(context);
                    } else {
//                        DataisLoaded(true);

                        SettingsClass.adshow_Calls = SettingsClass.Ad_Interval - 1;
                        UpdateAdData(context);

                    }
                }
            }));
        } catch (Exception e) {
            dialog.dismiss();
            UpdateAdData(context);
        }
    }

    public static void UpdateStringData(Context context, String id, String value) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        ed = sp.edit();
        ed.putString(id, value);
        ed.commit();
    }

    public static String GetStringData(Context context, String id) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(id, "");
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void RateUsFiveStar(Activity _activity, final int value, Context context) {
        if (!Allowtype) return;
        int val = sp.getInt("RateShow", value);
        if (val < 1 && new Random().nextInt(4) == 0) {
            final RatingDialog ratingDialog = new RatingDialog.Builder(context)
                    .session(1)
                    .title("If You Like Our Work Please Rate Us 5 Star..!")
                    .ratingBarColor(R.color.colorPrimary)

                    .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                        @Override
                        public void onRatingSelected(float rating, boolean thresholdCleared) {
                            ed.putInt("RateShow", 2);
                            ed.commit();
                        }
                    })
                    .playstoreUrl("https://play.google.com/store/apps/details?id=" + context.getPackageName())
                    .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                        @Override
                        public void onFormSubmitted(String feedback) {
                            ed.putInt("RateShow", 2);
                            ed.commit();
                        }
                    }).build();

            ratingDialog.show();
        } else {
            Show_Promotion(context);
        }
    }

    public static void ShowPrivecy_Policy(final Context context, final Activity activity, AppCompatActivity appCompatActivity, final Intent intent) {

        PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(appCompatActivity, Privecy_policy, Privecy_policy);

        dialog.addPoliceLine("This application uses a unique user identifier for advertising purposes, it is shared with third-party companies.");
        dialog.addPoliceLine("This application sends error reports, installation and send it to a server of the Fabric.io company to analyze and process it.");
        dialog.addPoliceLine("This application requires internet access and must collect the following information: Installed applications and history of installed applications, ip address, unique installation id, token to send notifications, version of the application, time zone and information about the language of the device.");
        dialog.addPoliceLine("All details about the use of data are available in our Privacy Policies, as well as all Terms of Service links below.");
        dialog.show();

        dialog.setOnClickListener(new PrivacyPolicyDialog.OnClickListener() {
            @Override
            public void onAccept(boolean isFirstTime) {
                Log.e("MainActivity", "Policies accepted");
                UpdateStringData(context, SP_KEY_Privecy_policy, "True");
                context.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onCancel() {
                Log.e("MainActivity", "Policies not accepted");
                activity.finish();
            }
        });
    }

//    public static void GotoNextActivity(Context context, Activity activity, Class nextActivity, final String msg, boolean isFinish, boolean isShowAd) {
//        if (isShowAd) {
//            MyAddManager.getInstance(context).showInterstitialAd(context, activity, (MyAddManager.MyCallback) new MyAddManager.MyCallback() {
//                @Override
//                public void callbackCall(boolean isSuccess) {
//                    if (context != null && nextActivity != null) {
//
//                        Intent intent = new Intent(context, nextActivity);
//                        intent.putExtra("Intent", msg);
//                        context.startActivity(intent);
//                        if (isFinish) activity.finish();
//                    }
//                }
//            });
//        } else {
//            if (context != null && nextActivity != null) {
//                Intent intent = new Intent(context, nextActivity);
//                intent.putExtra("Intent", msg);
//                context.startActivity(intent);
//                if (isFinish) activity.finish();
//            }
//        }
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void setStatusBarTransparentFlag(Activity context) {

        View decorView = context.getWindow().getDecorView();
        decorView.setOnApplyWindowInsetsListener((v, insets) -> {

            WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
            return defaultInsets.replaceSystemWindowInsets(
                    defaultInsets.getSystemWindowInsetLeft(),
                    0,
                    defaultInsets.getSystemWindowInsetRight(),
                    defaultInsets.getSystemWindowInsetBottom());

        });
        ViewCompat.requestApplyInsets(decorView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    public static boolean verifyInstallerId(Context context) {
        List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        return installer != null && validInstallers.contains(installer);
    }

    public static void RateApp(Activity context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    public static void MoreApp(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + Account_Name)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + Account_Name)));
        }
        Toast.makeText(context, "" + Account_Name, Toast.LENGTH_SHORT).show();
    }

    public static void ShareApp(Context context) {
        final String appLink = "\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app_message) + appLink);
        sendInt.setType("text/plain");
        context.startActivity(Intent.createChooser(sendInt, "Share"));
    }

    public static void PrivacyPolicy(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(Privecy_policy));
        activity.startActivity(intent);
    }

    public static boolean appInstalled = false;
    static int r = 0;

    public static void Show_Promotion(Context context) {
        if (!SettingsClass.Allowtype || SettingsClass.StartApp.contains("Stop")) return;
        if (appInstalled) {
            CheckUpdate(context);
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://envisioninfo.tech/hetech_apps/get_promotion_apps.php?appid=" + context.getPackageName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<String> appUrl = new ArrayList<>();
                    ArrayList<String> appName = new ArrayList<>();
                    ArrayList<String> appBanner = new ArrayList<>();

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray mainData = jsonResponse.getJSONArray("message");
                    for (int i = 0; i < mainData.length(); i++) {
                        JSONObject obj = mainData.getJSONObject(i);
                        String string = obj.getString("AppName");
                        String string2 = obj.getString("AppLink");
                        String string3 = "http://envisioninfo.tech/hetech_apps/" + obj.getString("AppIcon");
                        appUrl.add(string2);
                        appName.add(string);
                        appBanner.add(string3);
                    }

                    if (appUrl.size() > 0) {
                        for (int x = 0; x < appUrl.size(); x++) {
                            r = x;
                            if (!appUrl.get(r).equals(context.getPackageName())) {
                                if (!isPackageInstalled(appUrl.get(r), context)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    View view = LayoutInflater.from(context).inflate(R.layout.ads_promotion_dialog, null);

                                    builder.setView(view);

                                    ImageView promoImage = ((ImageView) view.findViewById(R.id.textMessage));
                                    Glide.with(context).load(appBanner.get(r)).placeholder(R.mipmap.ic_launcher).into(promoImage);

                                    ((Button) view.findViewById(R.id.buttonAction)).setText("Install");
                                    ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_close);
                                    final AlertDialog alertDialog = builder.create();
                                    alertDialog.setCancelable(false);
                                    view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.dismiss();

                                            appInstalled = true;

                                            final String appName = appUrl.get(r);
                                            try {
                                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
                                            } catch (ActivityNotFoundException anfe) {
                                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
                                            }
                                        }
                                    });
                                    view.findViewById(R.id.imageIcon).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.dismiss();

                                            CheckUpdate(context);
                                        }
                                    });
                                    if (alertDialog.getWindow() != null) {
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                    }
                                    alertDialog.show();
                                    break;
                                }
                            }
                        }


                    } else {
                        CheckUpdate(context);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    CheckUpdate(context);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Server Error!!!", Toast.LENGTH_SHORT).show();
                        CheckUpdate(context);
                    }
                });
        queue.add(stringRequest);
    }

    public static void CheckUpdate(Context context) {
        AppUpdater appUpdater = new AppUpdater(context)
                .setTitleOnUpdateAvailable(SettingsClass.updateTitle)
                .setContentOnUpdateAvailable(SettingsClass.updateMessage)
                .setCancelable(false)
                .setButtonDoNotShowAgain(null)
                .setButtonUpdate("Update Now")
                .setButtonDismiss(null);
        //.showAppUpdated(true);
        if (appUpdater.equals(false))
            appUpdater.start();
    }

    private static boolean isPackageInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void ShowPrivecyPolicy(Context context) {
        Uri uri = Uri.parse(SettingsClass.Privecy_policy);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}