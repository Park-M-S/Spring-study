package com.hospital.model;

public class Hospital {
    private String hospitalName;
    private double coordinateX;
    private double coordinateY;

    public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public double getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(double coordinateX) {
		this.coordinateX = coordinateX;
	}

	public double getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(double coordinateY) {
		this.coordinateY = coordinateY;
	}

	public Hospital(String hospitalName, double coordinateX, double coordinateY) {
        this.hospitalName = hospitalName;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }


}
