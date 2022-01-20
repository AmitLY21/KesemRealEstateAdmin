package com.aldev.kesemrealestateadmin;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Property implements Serializable {
    String description;
    private String propertyNumber;

    public Property() {
    }

    private ArrayList<Uri> propertyImages = new ArrayList<>();
    private int numOfRooms;
    private int numOfBathrooms;
    private int squareFoot;
    private int numOfParkingSpaces;
    private int numOfFloors;
    private boolean isLiked = false;
    private boolean elevator;
    private boolean storage;
    private boolean balcony;
    private boolean mamad; //residential secure space
    private String address;

    @Override
    public String toString() {
        return "Property{" +
                "propertyNumber=" + propertyNumber +
                ", propertyImages=" + propertyImages +
                ", numOfRooms=" + numOfRooms +
                ", numOfBathrooms=" + numOfBathrooms +
                ", squareFoot=" + squareFoot +
                ", numOfParkingSpaces=" + numOfParkingSpaces +
                ", numOfFloors=" + numOfFloors +
                ", isLiked=" + isLiked +
                ", elevator=" + elevator +
                ", storage=" + storage +
                ", balcony=" + balcony +
                ", mamad=" + mamad +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public Property setAddress(String address) {
        this.address = address;
        return this;
    }

    public Property setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
        return this;
    }

    public Property setNumOfBathrooms(int numOfBathrooms) {
        this.numOfBathrooms = numOfBathrooms;
        return this;
    }

    public Property setSquareFoot(int squareFoot) {
        this.squareFoot = squareFoot;
        return this;
    }

    public Property setNumOfParkingSpaces(int numOfParkingSpaces) {
        this.numOfParkingSpaces = numOfParkingSpaces;
        return this;
    }

    public Property setNumOfFloors(int numOfFloors) {
        this.numOfFloors = numOfFloors;
        return this;
    }

    public Property setElevator(boolean elevator) {
        this.elevator = elevator;
        return this;
    }

    public Property setStorage(boolean storage) {
        this.storage = storage;
        return this;
    }

    public Property setBalcony(boolean balcony) {
        this.balcony = balcony;
        return this;
    }

    public Property setMamad(boolean mamad) {
        this.mamad = mamad;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Property setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPropertyNumber() {
        return this.propertyNumber;
    }

    public Property setPropertyNumber(String propertyNumber) {
        this.propertyNumber = propertyNumber;
        return this;
    }

    public ArrayList<Uri> getPropertyImages() {
        return propertyImages;
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public int getSquareFoot() {
        return squareFoot;
    }

    public int getNumOfParkingSpaces() {
        return numOfParkingSpaces;
    }

    public int getNumOfFloors() {
        return numOfFloors;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public boolean isElevator() {
        return elevator;
    }

    public boolean isStorage() {
        return storage;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public boolean isMamad() {
        return mamad;
    }


}
