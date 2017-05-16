package com.example.samad786.cyclone.Activities.DataProviers;

/**
 * Created by mediapark on 16/05/2017.
 */

public class FoodsCategories {
    String id,title;

    public FoodsCategories(String id, String title) {
        this.id = id;
        this.title= title;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String gettitle() {
        return title;
    }
    public void settitle(String price) {
        this.title = price;
    }

}
