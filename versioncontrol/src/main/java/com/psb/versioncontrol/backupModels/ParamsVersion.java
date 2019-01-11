package com.psb.versioncontrol.backupModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsVersion implements Serializable {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("isForce")
    @Expose
    private boolean isForce;
    @SerializedName("versionType")
    @Expose
    private String versionType;

    private final static long serialVersionUID = -8596167243326914395L;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getVersion() {
        return Integer.parseInt(version);
    }

    public void setVersion(Integer version) {
        this.version = ""+version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }
}
