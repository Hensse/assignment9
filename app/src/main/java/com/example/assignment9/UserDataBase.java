package com.example.assignment9;

import android.app.Application;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {User.class},version = 1)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase instance;
    abstract UserDao userDao();

    public  static synchronized UserDataBase getInstance(Application application)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(application.getApplicationContext()
                    ,UserDataBase.class,"User_Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
