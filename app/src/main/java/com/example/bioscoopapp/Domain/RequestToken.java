package com.example.bioscoopapp.Domain;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestToken implements Parcelable {

    @SerializedName("request_token")
    @Expose
    private String requestToken;
    public final static Creator<RequestToken> CREATOR = new Creator<RequestToken>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RequestToken createFromParcel(android.os.Parcel in) {
            return new RequestToken(in);
        }

        public RequestToken[] newArray(int size) {
            return (new RequestToken[size]);
        }

    };

    protected RequestToken(android.os.Parcel in) {
        this.requestToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RequestToken() {
    }


    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(requestToken);
    }

    public int describeContents() {
        return 0;
    }

}
