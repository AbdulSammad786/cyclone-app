package com.example.samad786.cyclone.Activities.DataProviers;

/**
 * Created by samad786 on 4/19/2017.
 */

public class RestutantsListDataProvider {
    String id,namelocation,details,time;
    int logo,hotel_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamelocation() {
        return namelocation;
    }

    public void setNamelocation(String namelocation) {
        this.namelocation = namelocation;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getHotel_image() {
        return hotel_image;
    }

    public void setHotel_image(int hotel_image) {
        this.hotel_image = hotel_image;
    }

    public RestutantsListDataProvider(String id, String namelocation, String details, String time, int logo, int hotel_image) {

        this.id = id;
        this.namelocation = namelocation;
        this.details = details;
        this.time = time;
        this.logo = logo;
        this.hotel_image = hotel_image;
    }
}
