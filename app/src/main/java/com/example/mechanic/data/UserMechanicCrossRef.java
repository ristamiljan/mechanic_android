package com.example.mechanic.data;

import androidx.room.Entity;

@Entity(primaryKeys = {"user_id", "mechanic_id"})
public class UserMechanicCrossRef {

   public int user_id;
   public long mechanic_id;

    public UserMechanicCrossRef(int user_id, long mechanic_id) {
        this.user_id = user_id;
        this.mechanic_id = mechanic_id;
    }
}
