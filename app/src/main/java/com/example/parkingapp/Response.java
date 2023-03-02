package com.example.parkingapp;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Response {

    @SerializedName("success")
    @Expose
    private Object success;
    @SerializedName("statusMessage")
    @Expose
    private Object statusMessage;
    @SerializedName("data")
    @Expose
    private Object data = null;

    public Object getSuccess() {
        return success;
    }

    public void setSuccess(Object success) {
        this.success = success;
    }

    public Object getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(Object statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
