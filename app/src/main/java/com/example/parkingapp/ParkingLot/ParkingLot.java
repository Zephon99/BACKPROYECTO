package com.example.parkingapp.ParkingLot;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ParkingLot {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("lot")
    @Expose
    private String lot;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("occupationStatus")
    @Expose
    private String occupationStatus;

    public String getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(String occupationStatus) {
        this.occupationStatus = occupationStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
