package com.taptapvpn.speedproxyvpn.APIClient_Class;

import com.google.gson.annotations.SerializedName;

public class ApiResponse_Class {
    @SerializedName("ip")
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
