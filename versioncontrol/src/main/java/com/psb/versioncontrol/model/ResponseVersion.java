package com.psb.versioncontrol.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ResponseVersion implements Serializable {

    private final static long serialVersionUID = -1308527402695605575L;
    @SerializedName("Result")
    @Expose
    private Boolean result;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Extra")
    @Expose
    private List<ParamsVersion> extra = null;

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

    public List<ParamsVersion> getExtra() {
        return extra;
    }

    public void setExtra(List<ParamsVersion> extra) {
        this.extra = extra;
    }

}
