package com.airit.test.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.airit.test.Models.HomeModel;

/**
 * Database Name : airit_database
 * Version : 1
 */
@Database(entities = HomeModel.class, version = 1, exportSchema = false)
public abstract class HomeDatabase extends RoomDatabase {

    public abstract HomeDao homeDao();
    private static HomeDatabase instance;

    /**
     * initilized the database
     * @param context
     * @return
     */
    public static synchronized HomeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HomeDatabase.class, "airit_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
