package com.example.samad786.cyclone.Activities.DataProviers;

/**
 * Created by samad786 on 4/19/2017.
 */

public class RestutantsListDataProvider {
    String id,name,details,time;
    String logo_url,hotel_image_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamelocation() {
        return name;
    }

    public void setNamelocation(String namelocation) {
        this.name = namelocation;
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

    public String getLogo() {
        return logo_url;
    }

    public void setLogo(String logo) {
        this.logo_url = logo;
    }

    public String getHotel_image() {
        return hotel_image_url;
    }

    public void setHotel_image(String hotel_image) {
        this.hotel_image_url = hotel_image;
    }

    public RestutantsListDataProvider(String id, String name, String details, String time, String logo, String hotel_image) {

        this.id = id;
        this.name = name;
        this.details = details;
        this.time = time;
        this.logo_url = logo;
        this.hotel_image_url = hotel_image;
    }
}
