package com.example.bioscoopapp.Domain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MovieListResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("list_id")
    @Expose
    private Integer list_id;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getList_id() {
        return list_id;
    }

    public void setList_id(Integer list_id) {
        this.list_id = list_id;
    }

}
