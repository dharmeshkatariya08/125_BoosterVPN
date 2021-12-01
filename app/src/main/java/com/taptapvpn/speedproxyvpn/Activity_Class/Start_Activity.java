package com.taptapvpn.speedproxyvpn.Activity_Class;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anchorfree.partner.api.response.RemainingTraffic;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.vpnsdk.callbacks.Callback;
import com.anchorfree.vpnsdk.exceptions.VpnException;
import com.anchorfree.vpnsdk.vpnservice.VPNState;

import com.taptapvpn.speedproxyvpn.AdsClass.MyAddManager;
import com.taptapvpn.speedproxyvpn.AdsClass.MyApplication;
import com.taptapvpn.speedproxyvpn.AdsClass.SettingsClass;
import com.taptapvpn.speedproxyvpn.Utills_Class.Preference_Class;
import com.taptapvpn.speedproxyvpn.R;
import com.taptapvpn.speedproxyvpn.APIClient_Class.APIClient_Class;
import com.taptapvpn.speedproxyvpn.APIClient_Class.ApiResponse_Class;
import com.taptapvpn.speedproxyvpn.APIClient_Class.RestApis_Class;
import com.taptapvpn.speedproxyvpn.Utills_Class.Converter_Class;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public abstract class Start_Activity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG = Main_Activity.class.getSimpleName();

    TextView server_ip;
    ImageView img_connect;
    TextView connectionStateTextView;
    LinearLayout currentServerBtn;
    TextView selectedServerTextView;
    ImageView country_flag;
    TextView uploading_speed_textview;
    TextView downloading_speed_textview;
    TextView privecyPolicy;

    Preference_Class preference;
    boolean connected = false;

    int[] Onconnect = {R.drawable.ic_on};
    int[] Ondisconnect = {R.drawable.ic_off};
    private Handler mUIHandler = new Handler(Looper.getMainLooper());

    final Runnable mUIUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            updateUI();
            checkRemainingTraffic();
            mUIHandler.postDelayed(mUIUpdateRunnable, 10000);
        }
    };

    protected abstract void isLoggedIn(Callback<Boolean> callback);

    protected abstract void loginToVpn();

    protected abstract void isConnected(Callback<Boolean> callback);

    protected abstract void connectToVpn();

    protected abstract void disconnectFromVnp();

    protected abstract void chooseServer();

    protected abstract void getCurrentServer(Callback<String> callback);

    protected abstract void checkRemainingTraffic();

    void alert(String message) {
        android.app.AlertDialog.Builder bld = new android.app.AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SettingsClass.RateUsFiveStar(this, 0, Start_Activity.this);
        MyAddManager.getInstance(Start_Activity.this).loadintertialads(Start_Activity.this, false, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        MyAddManager.getInstance(this).show_NATIVE((ViewGroup) findViewById(R.id.native_container), i2);

        loginToVpn();
        privecyPolicy = findViewById(R.id.tvPrivecyPolicy);
        server_ip = findViewById(R.id.server_ip);
        img_connect = findViewById(R.id.img_connect);
        connectionStateTextView = findViewById(R.id.connection_state);
        currentServerBtn = findViewById(R.id.optimal_server_btn);
        selectedServerTextView = findViewById(R.id.selected_server);
        country_flag = findViewById(R.id.country_flag);
        uploading_speed_textview = findViewById(R.id.txt_upload);
        downloading_speed_textview = findViewById(R.id.txt_download);

        privecyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsClass.PrivacyPolicy(Start_Activity.this);
            }
        });

        preference = new Preference_Class(this);
        getip();
    }

    private void getip() {
        RestApis_Class mRestApis = APIClient_Class.getRetrofitInstance("https://api.ipify.org").create(RestApis_Class.class);
        Call<ApiResponse_Class> userAdd = mRestApis.requestip("json");
        userAdd.enqueue(new retrofit2.Callback<ApiResponse_Class>() {
            @Override
            public void onResponse(Call<ApiResponse_Class> call, Response<ApiResponse_Class> response) {
                Log.e(TAG, "onResponse: " + response.body().getIp());
                if (response != null) {
                    server_ip.setText(response.body().getIp());
                } else {
                    server_ip.setText(R.string.default_server_ip_text);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse_Class> call, Throwable t) {
                server_ip.setText(R.string.default_server_ip_text);
            }
        });
    }


    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        isConnected(new Callback<Boolean>() {
            @Override
            public void success(@NonNull Boolean aBoolean) {
                if (aBoolean) {
                    startUIUpdateTask();
                }
            }

            @Override
            public void failure(@NonNull VpnException e) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUIUpdateTask();
    }

    @OnClick(R.id.img_connect)
    public void onConnectBtnClick(View v) {
        isConnected(new Callback<Boolean>() {
            @Override
            public void success(@NonNull Boolean aBoolean) {
                if (aBoolean) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Start_Activity.this);

                    builder.setCancelable(false)
                            .setPositiveButton("Disconnect ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MyAddManager.getInstance(Start_Activity.this).showInterstitialAd(Start_Activity.this, Start_Activity.this, new MyAddManager.MyCallback() {
                                        @Override
                                        public void callbackCall(boolean isSuccess) {
                                            disconnectFromVnp();
                                            MyAddManager.getInstance(Start_Activity.this).loadintertialads(Start_Activity.this, false, true);

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MyAddManager.getInstance(Start_Activity.this).showInterstitialAd(Start_Activity.this, Start_Activity.this, new MyAddManager.MyCallback() {
                                        @Override
                                        public void callbackCall(boolean isSuccess) {
                                            dialog.cancel();
                                            MyAddManager.getInstance(Start_Activity.this).loadintertialads(Start_Activity.this, false, true);

                                        }
                                    });

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Are you sure you want to disconnect?");
                    alert.show();

                } else {
                    MyAddManager.getInstance(Start_Activity.this).showInterstitialAd(Start_Activity.this, Start_Activity.this, new MyAddManager.MyCallback() {
                        @Override
                        public void callbackCall(boolean isSuccess) {
                            connectToVpn();
                            MyAddManager.getInstance(Start_Activity.this).loadintertialads(Start_Activity.this, false, true);
                        }
                    });

                }
            }

            @Override
            public void failure(@NonNull VpnException e) {
            }
        });
    }


    @OnClick(R.id.optimal_server_btn)
    public void onServerChooserClick(View v) {
        MyAddManager.getInstance(Start_Activity.this).showInterstitialAd(Start_Activity.this, Start_Activity.this, new MyAddManager.MyCallback() {
            @Override
            public void callbackCall(boolean isSuccess) {
                chooseServer();
            }
        });
    }

    protected void startUIUpdateTask() {
        stopUIUpdateTask();
        mUIHandler.post(mUIUpdateRunnable);
    }

    protected void stopUIUpdateTask() {
        mUIHandler.removeCallbacks(mUIUpdateRunnable);
        updateUI();
    }

    protected void updateUI() {
        UnifiedSDK.getVpnState(new Callback<VPNState>() {
            @Override
            public void success(@NonNull VPNState vpnState) {
                switch (vpnState) {
                    case IDLE: {
                        Log.e(TAG, "success: IDLE");
                        connectionStateTextView.setText("Disconnected");
                        getip();
                        if (connected) {
                            connected = false;
                            animate(img_connect, Ondisconnect, 0, false);
                        }
                        country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                        selectedServerTextView.setText(R.string.select_country);
                        ChangeBlockVisibility();
                        uploading_speed_textview.setText("");
                        downloading_speed_textview.setText("");

                        hideConnectProgress();
                        break;
                    }
                    case CONNECTED: {
                        Log.e(TAG, "success: CONNECTED");
                        if (!connected) {
                            connected = true;
                            animate(img_connect, Onconnect, 0, false);
                        }
                        connectionStateTextView.setText("connected");
                        hideConnectProgress();
                        break;
                    }
                    case CONNECTING_VPN:
                    case CONNECTING_CREDENTIALS:
                    case CONNECTING_PERMISSIONS: {

                        connectionStateTextView.setText("Searching..");
                        ChangeBlockVisibility();
                        country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                        selectedServerTextView.setText(R.string.select_country);
                        showConnectProgress();
                        break;

                    }
                    case PAUSED: {
                        Log.e(TAG, "success: PAUSED");
                        ChangeBlockVisibility();
                        country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                        selectedServerTextView.setText(R.string.select_country);
                        break;
                    }
                }
            }

            @Override
            public void failure(@NonNull VpnException e) {
                country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                selectedServerTextView.setText(R.string.select_country);
            }
        });
        getCurrentServer(new Callback<String>() {
            @Override
            public void success(@NonNull final String currentServer) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                        selectedServerTextView.setText(R.string.select_country);
                        if (!currentServer.equals("")) {
                            Locale locale = new Locale("", currentServer);
                            Resources resources = getResources();
                            String sb = "drawable/" + currentServer.toLowerCase();
                            country_flag.setImageResource(resources.getIdentifier(sb, null, getPackageName()));
                            selectedServerTextView.setText(locale.getDisplayCountry());
                        } else {
                            country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                            selectedServerTextView.setText(R.string.select_country);
                        }
                    }
                });
            }

            @Override
            public void failure(@NonNull VpnException e) {
                country_flag.setImageDrawable(getResources().getDrawable(R.drawable.ic_earth));
                selectedServerTextView.setText(R.string.select_country);
            }
        });
    }

    private void ChangeBlockVisibility() {

    }

    private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {

        int fadeInDuration = 500;
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);

        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        animate(imageView, images, 0, forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }


    protected void updateTrafficStats(long outBytes, long inBytes) {
        String outString = Converter_Class.humanReadableByteCountOld(outBytes, false);
        String inString = Converter_Class.humanReadableByteCountOld(inBytes, false);

        uploading_speed_textview.setText(inString);
        downloading_speed_textview.setText(outString);

    }

    protected void updateRemainingTraffic(RemainingTraffic remainingTrafficResponse) {
        if (remainingTrafficResponse.isUnlimited()) {

        } else {
            String trafficUsed = Converter_Class.megabyteCount(remainingTrafficResponse.getTrafficUsed()) + "Mb";
            String trafficLimit = Converter_Class.megabyteCount(remainingTrafficResponse.getTrafficLimit()) + "Mb";

        }
    }

    protected void ShowIPaddera(String ipaddress) {
        server_ip.setText(ipaddress);
    }


    protected void showConnectProgress() {

    }

    protected void hideConnectProgress() {

    }

    protected void showMessage(String msg) {
        Toast.makeText(Start_Activity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View mView = this.getLayoutInflater().inflate(R.layout.dialog2, null);

        TextView no = mView.findViewById(R.id.no);
        TextView yes = mView.findViewById(R.id.yes);

        builder.setView(mView);
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        MyAddManager.getInstance(this).show_NATIVE((ViewGroup) mView.findViewById(R.id.native_container), i2);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                yes.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                yes.setText("Yes");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        finishAffinity();
                    }
                });
            }

        }.start();

        alertDialog.show();

    }

}
