package com.example.parkingapp.Booking;

public class BookingDto {
    private String date;
    private String initialTime;
    private String endTime;
    private String status;
    private int parkingLotId;
    private int driverId;
    private int parkingAttendantId;

    public BookingDto() {
    }

    public BookingDto(String date, String initialTime, String endTime, String status, int parkingLotId, int driverId, int parkingAttendantId) {
        this.date = date;
        this.initialTime = initialTime;
        this.endTime = endTime;
        this.status = status;
        this.parkingLotId = parkingLotId;
        this.driverId = driverId;
        this.parkingAttendantId = parkingAttendantId;
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

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getParkingAttendantId() {
        return parkingAttendantId;
    }

    public void setParkingAttendantId(int parkingAttendantId) {
        this.parkingAttendantId = parkingAttendantId;
    }
}
