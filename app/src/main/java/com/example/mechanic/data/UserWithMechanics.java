package com.example.mechanic.data;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;

public class UserWithMechanics {

    @Embedded
    public UserAccount user;

    @Relation(parentColumn = "user_id",entityColumn = "mechanic_id",associateBy = @Junction(UserMechanicCrossRef.class))
    public List<Mechanic> mechanics;
}
