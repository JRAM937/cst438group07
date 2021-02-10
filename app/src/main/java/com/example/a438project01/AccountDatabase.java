package com.example.a438project01;

import android.content.Context;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    public abstract AccountDao account();
    private static AccountDatabase sInstance;
    public static synchronized AccountDatabase getInstance(Context context) {
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

    /* populateInitialData is not used yet, may use it in future
    public void populateInitialData() {

    }
     */

    @Dao
    public interface AccountDao {
        @Insert
        void addAccount(Account account);

        @Query("SELECT COUNT(*) FROM accounts")
        int count();

        @Query("SELECT * FROM accounts")
        List<Account> getAll();

        @Delete
        void delete(Account account);
    }


}

