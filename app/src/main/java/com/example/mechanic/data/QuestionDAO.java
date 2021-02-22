package com.example.mechanic.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface QuestionDAO {

    @Insert
    public void insert(Question q);

    @Query("UPDATE questions SET answer=:a WHERE question=:q")
    void setAnswer(String a, String q);


    @Query("SELECT * FROM questions LIMIT 1")
    public Question[] getAnyQuestion();

    @Query("SELECT * FROM questions")
    public List<Question> getAllQuestions();
}
