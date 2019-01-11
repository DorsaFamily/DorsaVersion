package com.psb.versioncontrol.backupModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ResponseVersion implements Serializable {

    private final static long serialVersionUID = -1308527402695605575L;
    @SerializedName("Result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("extra")
    @Expose
    private ParamsVersion extra = null;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParamsVersion getExtra() {
        return extra;
    }

    public void setExtra(ParamsVersion extra) {
        this.extra = extra;
    }

}
