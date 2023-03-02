package com.example.parkingapp.Booking;


import com.example.parkingapp.Driver.Driver;
import com.example.parkingapp.ParkingLot.ParkingLotDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Booking {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("initialTime")
    @Expose
    private String initialTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("parkingAttendant")
    @Expose
    private Object parkingAttendant;
    @SerializedName("parkingLot")
    @Expose
    private ParkingLotDto parkingLotDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Object getParkingAttendant() {
        return parkingAttendant;
    }

    public void setParkingAttendant(Object parkingAttendant) {
        this.parkingAttendant = parkingAttendant;
    }

    public ParkingLotDto getParkingLot() {
        return parkingLotDto;
    }

    public void setParkingLot(ParkingLotDto parkingLotDto) {
        this.parkingLotDto = parkingLotDto;
    }

}
