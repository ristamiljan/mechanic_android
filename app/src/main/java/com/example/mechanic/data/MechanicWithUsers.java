package com.example.mechanic.data;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;

public class MechanicWithUsers {

    @Embedded
    public Mechanic mechanic;

    @Relation(parentColumn = "mechanic_id", entityColumn = "user_id", associateBy = @Junction(UserMechanicCrossRef.class))
    public List<UserAccount> users;
}
