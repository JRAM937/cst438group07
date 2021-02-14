package com.example.a438project01.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a438project01.Account;

import java.util.List;

@Dao
public interface AccountDAO {

    @Insert
    void addAccount(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("SELECT * FROM " + AccountDatabase.USER_TABLE)
    List<Account> getAll();

    @Query("SELECT * FROM " + AccountDatabase.USER_TABLE + " WHERE Username = :Username")
    Account getUserByUsername(String Username);
}
