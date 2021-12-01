package com.booster.vpn.Adapter_Class;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.anchorfree.partner.api.data.Country;
import com.booster.vpn.AdsClass.MyAddManager;
import com.booster.vpn.Utills_Class.Preference_Class;
import com.booster.vpn.R;
import com.booster.vpn.Activity_Class.ListOfServers_Activity;
import com.booster.vpn.Custom_Dialogs.GetCountryData_Class;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import static com.booster.vpn.Utills_Class.Glob.PRIMIUM_STATE;

public class ServerList_Adapter extends RecyclerView.Adapter<ServerList_Adapter.ViewHolder> {

    public Context context;
    private Preference_Class preference;
    private List<GetCountryData_Class> regions;
    private RegionListAdapterInterface listAdapterInterface;
    ListOfServers_Activity serverActivity;

    public ServerList_Adapter(RegionListAdapterInterface listAdapterInterface, ListOfServers_Activity cntec) {
        this.listAdapterInterface = listAdapterInterface;
        this.context = cntec;
        preference = new Preference_Class(this.context);
        serverActivity = cntec;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_list_free, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final GetCountryData_Class datanew = this.regions.get(holder.getAdapterPosition());
        final Country data = datanew.getCountryvalue();
        Locale locale = new Locale("", data.getCountry());

        if (position == 0) {
            holder.flag.setImageResource(context.getResources().getIdentifier("drawable/earthspeed", null, context.getPackageName()));
            holder.app_name.setText(R.string.best_performance_server);
            holder.limit.setVisibility(View.GONE);
        } else {
            ImageView imageView = holder.flag;
            Resources resources = context.getResources();
            String sb = "drawable/" + data.getCountry().toLowerCase();
            imageView.setImageResource(resources.getIdentifier(sb, null, context.getPackageName()));
            holder.app_name.setText(locale.getDisplayCountry());
            holder.limit.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAddManager.getInstance(serverActivity).showInterstitialAd(serverActivity, serverActivity, new MyAddManager.MyCallback() {
                    @Override
                    public void callbackCall(boolean isSuccess) {
                        listAdapterInterface.onCountrySelected(regions.get(holder.getAdapterPosition()));
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return regions != null ? regions.size() : 0;
    }

    public void setRegions(List<Country> list) {
        regions = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GetCountryData_Class newData = new GetCountryData_Class();
            newData.setCountryvalue(list.get(i));
            if (i % 2 == 0) {
//                newData.setPro(false);
                regions.add(newData);
            } else {
                if (list.get(i).getServers() > 0) {
                    if (true) {
                        if (preference.isBooleenPreference(PRIMIUM_STATE)) {
//                            newData.setPro(false);
                        } else {
//                            newData.setPro(true);
                        }
                    } else {
//                        newData.setPro(false);
                    }
                    regions.add(newData);
                } else {
                    regions.add(newData);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface RegionListAdapterInterface {
        void onCountrySelected(GetCountryData_Class item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView app_name;
        ImageView flag;
        ImageView limit;

        ViewHolder(View v) {
            super(v);
            this.app_name = itemView.findViewById(R.id.region_title);
            this.limit = itemView.findViewById(R.id.region_limit);
            this.flag = itemView.findViewById(R.id.country_flag);
        }
    }
}
