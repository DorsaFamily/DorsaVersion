
package com.psb.versioncontrol.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseVersion {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("extra")
    @Expose
    private ExtraVersion extra;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ExtraVersion getExtraVersion() {
        return extra;
    }

    public void setExtraVersion(ExtraVersion extraVersion) {
        this.extra = extraVersion;
    }

}
