package com.orange.flickrtask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("photos")
    @Expose
    private PhotoDBResponse photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotoDBResponse getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoDBResponse photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
