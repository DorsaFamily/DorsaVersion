package com.psb.versioncontrol.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ParamsVersion implements Serializable {

    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("Msg")
    @Expose
    private String msg;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Version")
    @Expose
    private Integer version;
    @SerializedName("IsForce")
    @Expose
    private boolean isForce;
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
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}
