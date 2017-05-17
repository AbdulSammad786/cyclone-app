package com.example.samad786.cyclone.Activities.DataProviers;

/**
 * Created by samad786 on 4/20/2017.
 */

public class FoodsDataProvider {
    String id,price;
    String image;

    public FoodsDataProvider(String id, String price, String image) {
        this.id = id;
        this.price = price;
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
