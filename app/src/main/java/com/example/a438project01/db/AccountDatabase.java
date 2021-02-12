package com.example.a438project01.db;

import android.content.Context;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a438project01.Account;

@Database(entities = {
        Account.class
}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    private static AccountDatabase sInstance;

    public static final String DB_NAME = "USER_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    private AccountDAO dao;

    public abstract AccountDAO getAccountDAO();

//    public static synchronized AccountDatabase getInstance(Context context) {
//        if (sInstance == null) {
//            sInstance = Room
//                    .databaseBuilder(context.getApplicationContext(),
//                            AccountDatabase.class,
//                            "users.db")
//                    .allowMainThreadQueries()
//                    .build();
//        }
//        return sInstance;
//    }

    public static AccountDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class,
                            "users.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
}