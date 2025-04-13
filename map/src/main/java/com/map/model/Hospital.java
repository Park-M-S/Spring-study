package com.map.model;

public class Hospital {
    private int id;
    private String name;
    private String address;
    private String phone;
    private double latitude;
    private double longitude;
    private double distance; // 사용자의 현재 위치와의 거리 (km)

    // 기본 생성자
    public Hospital() {
        this.distance = 0.0; // 기본 거리값 설정
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Hospital{id=" + id + ", name='" + name + "', address='" + address + "', phone='" + phone + "', latitude=" + latitude + ", longitude=" + longitude + ", distance=" + distance + "}";
    }
}
