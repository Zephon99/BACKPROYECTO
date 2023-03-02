package com.example.parkingapp.ParkingLot;

public class ParkingLotDto {
    private int id;
    private String time;
    private String area;
    private String lot;
    private Double price;
    private String occupationStatus;

    private void parkingLot (){}


    public ParkingLotDto(String time, String area,String lot, String occupationStatus,Double price) {
        this.time = time;
        this.area = area;
        this.lot = lot;
        this.occupationStatus = occupationStatus;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(String occupationStatus) {
        this.occupationStatus = occupationStatus;
    }
}
