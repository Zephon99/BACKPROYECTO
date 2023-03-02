package com.example.parkingapp.Booking;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CountBooking {

    @SerializedName("success")
    @Expose
    private Object success;
    @SerializedName("statusMessage")
    @Expose
    private Object statusMessage;
    @SerializedName("data")
    @Expose
    private List<Integer> data = null;

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

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

}
