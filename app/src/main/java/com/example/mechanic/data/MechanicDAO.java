package com.example.mechanic.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;

@Dao
public interface MechanicDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mechanic mechanic);

    @Transaction
    @Insert
    long insertMechanic(Mechanic mechanic);

    @Insert
    void insertUsers(List<UserAccount> users);

    @Query("DELETE FROM mechanic_table")
    void deleteAll();

    @Query("SELECT * FROM mechanic_table")
    LiveData<List<Mechanic>> getAllMechanics();

    @Query("SELECT * FROM mechanic_table LIMIT 1")
    Mechanic[] getAnyMechanic();
    @Delete
    void deleteMechanic(Mechanic d);

    @Query("SELECT * FROM mechanic_table WHERE password=:pass AND username=:username")
    Mechanic checkMechanic(String username, String pass);

    @Transaction
    @Query("SELECT * FROM mechanic_table WHERE mechanic_id=:id;")
    LiveData<List<QuestionsForMechanic>> getTriple(long id);

    @Transaction
    @Query("SELECT * FROM mechanic_table WHERE mechanic_id=:userId;")
    List<MechanicWithUsers> getUsersByMechanic(long userId);

    @Query("SELECT * from mechanic_table where mechanic_id=:loggedMechanicId;")
    Mechanic getMechanicById(long loggedMechanicId);

    @Query("update mechanic_table set num_of_votes=num_of_votes+1, sum_rating=sum_rating+:rating where mechanic_id=:id;")
    void increseVotesAndSum(long id, int rating);

    @Query("select sum_rating from mechanic_table where mechanic_id=:id;")
    long getSum(long id);

    @Query("select num_of_votes from mechanic_table where mechanic_id=:id;")
    int getNumOfVotes(long id);
}
