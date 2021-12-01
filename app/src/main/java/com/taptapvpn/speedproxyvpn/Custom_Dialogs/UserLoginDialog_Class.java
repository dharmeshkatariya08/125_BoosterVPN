package com.taptapvpn.speedproxyvpn.Custom_Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.taptapvpn.speedproxyvpn.AdsClass.MyApplication;
import com.taptapvpn.speedproxyvpn.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginDialog_Class extends DialogFragment {

    @BindView(R.id.host_url_ed)
    EditText hostUrlEditText;

    @BindView(R.id.carrier_id_ed)
    EditText carrierIdEditText;

    private LoginConfirmationInterface loginConfirmationInterface;

    public UserLoginDialog_Class() {
    }

    public static UserLoginDialog_Class newInstance() {
        UserLoginDialog_Class frag = new UserLoginDialog_Class();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_login, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        SharedPreferences prefs = ((MyApplication) getActivity().getApplication()).getPrefs();

        hostUrlEditText.setText(prefs.getString("com.booster.vpn.STORED_HOST_KEY", "https://d2isj403unfbyl.cloudfront.net"));
        carrierIdEditText.setText(prefs.getString("com.booster.vpn.CARRIER_ID_KEY", "1_firevpn1"));

        hostUrlEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        if (ctx instanceof LoginConfirmationInterface) {
            loginConfirmationInterface = (LoginConfirmationInterface) ctx;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginConfirmationInterface = null;
    }

    @OnClick(R.id.login_btn)
    void onLoginBtnClick(View v) {
        String hostUrl = hostUrlEditText.getText().toString();
        if (hostUrl.equals("")) hostUrl = "https://d2isj403unfbyl.cloudfront.net";
        String carrierId = carrierIdEditText.getText().toString();
        if (carrierId.equals("")) carrierId = "1_firevpn1";
        loginConfirmationInterface.setLoginParams(hostUrl, carrierId);
        loginConfirmationInterface.loginUser();
        dismiss();
    }

    public interface LoginConfirmationInterface {
        void setLoginParams(String hostUrl, String carrierId);

        void loginUser();
    }
}
