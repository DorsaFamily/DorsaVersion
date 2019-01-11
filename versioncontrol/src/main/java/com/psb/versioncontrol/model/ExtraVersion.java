
package com.psb.versioncontrol.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtraVersion {

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
    private Boolean isForce;
    @SerializedName("versionType")
    @Expose
    private Long versionType;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVersion() {
        return Integer.parseInt(version);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getIsForce() {
        return isForce;
    }

    public void setIsForce(Boolean isForce) {
        this.isForce = isForce;
    }

    public Long getVersionType() {
        return versionType;
    }

    public void setVersionType(Long versionType) {
        this.versionType = versionType;
    }

}
