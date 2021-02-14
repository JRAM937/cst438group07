package com.example.a438project01;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.a438project01.db.AccountDatabase;

@Entity(tableName = AccountDatabase.USER_TABLE)
public class Account {
    @PrimaryKey(autoGenerate=true)
    private int accountId;

    @ColumnInfo(name = "username")
    private String Username;

    @ColumnInfo(name = "password")
    private String Password;

    private String Bio;

    public Account(String Username, String Password) {
        this.Username = Username; this.Password = Password;
        this.Bio = "";
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }
}

