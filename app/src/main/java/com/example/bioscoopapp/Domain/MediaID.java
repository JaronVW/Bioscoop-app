package com.example.bioscoopapp.Domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaID {

    @SerializedName("media_id")
    @Expose
    private Integer mediaId;

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public MediaID(Integer mediaId) {
        this.mediaId = mediaId;
    }
}