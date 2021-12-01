package com.taptapvpn.speedproxyvpn.Activity_Class;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anchorfree.partner.api.response.AvailableCountries;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.vpnsdk.callbacks.Callback;
import com.anchorfree.vpnsdk.exceptions.VpnException;

import com.taptapvpn.speedproxyvpn.AdsClass.MyAddManager;
import com.taptapvpn.speedproxyvpn.R;
import com.taptapvpn.speedproxyvpn.Adapter_Class.ServerList_Adapter;
import com.taptapvpn.speedproxyvpn.Custom_Dialogs.GetCountryData_Class;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taptapvpn.speedproxyvpn.Utills_Class.Glob.BUNDLE;
import static com.taptapvpn.speedproxyvpn.Utills_Class.Glob.COUNTRY_DATA;


public class ListOfServers_Activity extends AppCompatActivity {

    @BindView(R.id.regions_recycler_view)
    RecyclerView regionsRecyclerView;

    @BindView(R.id.regions_progress)
    ProgressBar regionsProgressBar;

    private ServerList_Adapter regionAdapter;
    private RegionChooserInterface regionChooserInterface;
    ImageView backToActivity;
    TextView activity_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        ButterKnife.bind(this);

        MyAddManager.getInstance(this).show_BANNER((ViewGroup) findViewById(R.id.banner_container), this, getWindowManager().getDefaultDisplay());
        activity_name = (TextView) findViewById(R.id.activity_name);
        backToActivity = (ImageView) findViewById(R.id.finish_activity);
        activity_name.setText("Servers");
        backToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        regionChooserInterface = new RegionChooserInterface() {
            @Override
            public void onRegionSelected(GetCountryData_Class item) {
                    Intent intent = new Intent();
                    Bundle args = new Bundle();
                    Gson gson = new Gson();
                    String json = gson.toJson(item);

                    args.putString(COUNTRY_DATA, json);
                    intent.putExtra(BUNDLE, args);
                    setResult(RESULT_OK, intent);
                    finish();

            }
        };

        regionsRecyclerView.setHasFixedSize(true);
        regionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        regionAdapter = new ServerList_Adapter(new ServerList_Adapter.RegionListAdapterInterface() {
            @Override
            public void onCountrySelected(GetCountryData_Class item) {
                regionChooserInterface.onRegionSelected(item);
            }
        }, ListOfServers_Activity.this);
        regionsRecyclerView.setAdapter(regionAdapter);
        loadServers();

    }

    private void loadServers() {
        showProgress();
        UnifiedSDK.getInstance().getBackend().countries(new Callback<AvailableCountries>() {
            @Override
            public void success(@NonNull final AvailableCountries countries) {
                hideProress();
                regionAdapter.setRegions(countries.getCountries());
            }

            @Override
            public void failure(@NonNull VpnException e) {
                hideProress();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MyAddManager.getInstance(ListOfServers_Activity.this).loadintertialads(ListOfServers_Activity.this, false, true);
    }

    private void showProgress() {
        regionsProgressBar.setVisibility(View.VISIBLE);
        regionsRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideProress() {
        regionsProgressBar.setVisibility(View.GONE);
        regionsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public interface RegionChooserInterface {
        void onRegionSelected(GetCountryData_Class item);
    }
}
