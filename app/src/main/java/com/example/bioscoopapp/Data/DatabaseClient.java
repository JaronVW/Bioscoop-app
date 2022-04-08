package com.example.bioscoopapp.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

public class DatabaseClient {
    @SuppressLint("StaticFieldLeak")
    private static DatabaseClient instance;

    private AppDatabase appDatabase;

    public DatabaseClient(Context context) {

        try {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "App_database").build();
            Log.d("Database_instance", "Succeded");
        } catch (Exception e) {
            Log.d("Database_instance", e.toString());
        }
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (instance == null) {
            instance = new DatabaseClient(mCtx);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
