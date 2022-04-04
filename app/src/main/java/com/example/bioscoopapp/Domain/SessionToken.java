package com.example.bioscoopapp.Domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SessionToken implements Parcelable {

    @SerializedName("session_id")
    @Expose
    private String session_id;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    protected SessionToken(Parcel in) {
        session_id = in.readString();
    }

    public static final Creator<SessionToken> CREATOR = new Creator<SessionToken>() {
        @Override
        public SessionToken createFromParcel(Parcel in) {
            return new SessionToken(in);
        }

        @Override
        public SessionToken[] newArray(int size) {
            return new SessionToken[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(session_id);
    }
}
