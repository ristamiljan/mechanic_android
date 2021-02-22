package com.example.mechanic.data;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class AppRepository {

    private QuestionDAO mQuestionDao;
    private UserAccountDAO mUserDao;
    private MechanicDAO mMechanicDao;

    AppRepository(Context application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        mMechanicDao = db.mechanicDAO();
        mUserDao = db.usersDAO();
        mQuestionDao = db.questionDAO();

    }

    public List<Question> getAllQuestions(){

        return mQuestionDao.getAllQuestions();
    }

    public LiveData<List<Mechanic>> getmAllMechanics() {

        return mMechanicDao.getAllMechanics();
    }

    public LiveData<List<QuestionWithUsersAndMechanics>> getUsersAndMechanicsWithQuestions(int id) {

        return mUserDao.getTriple(id);
    }

    public LiveData<List<QuestionsForMechanic>> getQuestionsForMechanic(long id) {

        return mMechanicDao.getTriple(id);
    }

    public LiveData<List<UserAccount>> getmAllUsers() {

        return mUserDao.getUsers();
    }

    public void setAnswer(String a, String q){

        mQuestionDao.setAnswer(a, q);
    }

    public void askQuestion(Question q){

        mQuestionDao.insert(q);
    }

    public void rateMechanic(int rating, long mechanicrId) {

        mMechanicDao.increseVotesAndSum(mechanicrId,rating);
    }

    public double getAvgRating(long mechanicrId) {

        try{
            double avg = mMechanicDao.getSum(mechanicrId)/ mMechanicDao.getNumOfVotes(mechanicrId);
            return avg;
        } catch (Exception e) {

        }
        return 0.0;
    }

    //USER REPO
    public void insertUser(UserAccount user) {

        mUserDao.insert(user);
    }

    public void deleteAllUsers() {

        mUserDao.deleteAllUsers();
    }

    public UserAccount checkUser(String userName, String pw) {

        return mUserDao.checkUser(userName,pw);
    }

    public UserAccount getUser(int id) {
        return mUserDao.getUser(id);
    }

    /// MECHANIC REPO
    public Mechanic[] getMechanic(){

        return mMechanicDao.getAnyMechanic();
    }
    public void insert(Mechanic mechanic) {

        new insertAsyncTask(mMechanicDao).execute(mechanic);
    }

    public List<UserWithMechanics> getMechanicsByUser(int userId) {

        return mUserDao.getMechanicsByUser(userId);
    }

    public List<MechanicWithUsers> getUsersByMechanic(long mechanicId){

        return mMechanicDao.getUsersByMechanic(mechanicId);
    }

    public Mechanic checkMechanic(String username, String pass) {

        return mMechanicDao.checkMechanic(username, pass);
    }

    public Mechanic getMechanicById(long loggedMechanicId) {

        return mMechanicDao.getMechanicById(loggedMechanicId);
    }

    public int getNumOfVotes(long loggedMechanicId) {

        return mMechanicDao.getNumOfVotes(loggedMechanicId);
    }

    private static class insertAsyncTask extends AsyncTask<Mechanic, Void, Void> {

        private MechanicDAO mAsyncTaskDao;

        insertAsyncTask(MechanicDAO mMechanicDao) {
            mAsyncTaskDao = mMechanicDao;
        }

        @Override
        protected Void doInBackground(final Mechanic... mechanics) {
            mAsyncTaskDao.insert(mechanics[0]);
            return null;
        }
    }

    private class deleteAllMechanicsAsyncTask extends AsyncTask<Void,Void,Void> {

        private MechanicDAO mAsyncTaskDao;

        public deleteAllMechanicsAsyncTask(MechanicDAO mWordDao) {
            mAsyncTaskDao = mWordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteMechanicAsyncTask extends AsyncTask<Mechanic, Void, Void> {

        private MechanicDAO mAsyncTaskDao;

        deleteMechanicAsyncTask(MechanicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mechanic... params) {
            mAsyncTaskDao.deleteMechanic(params[0]);
            return null;
        }
    }

    public void deleteAll(){

        new deleteAllMechanicsAsyncTask(mMechanicDao).execute();
    }

    public void deleteMechanic(Mechanic mechanic){

        new deleteMechanicAsyncTask(mMechanicDao).execute(mechanic);
    }

    public void insertPairMechanicUser(UserMechanicCrossRef userWithMechanic) {

        this.mUserDao.insertPairs(userWithMechanic);
    }
}
