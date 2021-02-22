package com.example.mechanic.data;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class QuestionsForMechanic {
    @Embedded
    public MechanicWithUsers mechanics;

    @Relation(entity = Question.class,parentColumn = "mechanic_id",entityColumn = "mechanicId")
    public List<Question> questions;
}
