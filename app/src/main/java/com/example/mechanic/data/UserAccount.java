package com.example.mechanic.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "user")
public class UserAccount implements Serializable{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int user_id;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name="last_name")
    private String lastName;

    @ColumnInfo(name = "user_name")
    private String userName;

    private String password;

    private String email;

    @Ignore
    public UserAccount(String userName,String password){
        this.userName=userName;
        this.password=password;

    }

    public UserAccount(String firstName,String lastName,String userName,String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.password=password;
    }

    public int getUserId(){ return user_id;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
