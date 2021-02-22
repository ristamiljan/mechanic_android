package com.example.mechanic.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "questions")
public class Question implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int questionId;

    private int userId;

    private long mechanicId;

    public String question;

    @ColumnInfo(defaultValue = "")
    public String answer;

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public void setMechanicId(long mechanicId) {

        this.mechanicId = mechanicId;
    }

    public int getUserId() {

        return userId;
    }

    public long getMechanicId() {

        return mechanicId;
    }

    @Ignore
    public Question() {
    }

    @Ignore
    public Question(String question) {

        this.question=question;
    }

    public Question(String question, String answer) {

        this.question=question;
        this.answer=answer;
    }
}
