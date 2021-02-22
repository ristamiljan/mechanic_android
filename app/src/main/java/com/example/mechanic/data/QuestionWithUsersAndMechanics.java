package com.example.mechanic.data;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class QuestionWithUsersAndMechanics {
    @Embedded
    public UserWithMechanics users;

    @Relation(entity=Question.class,parentColumn = "user_id",entityColumn = "userId")
    public List<Question> questions;
}
