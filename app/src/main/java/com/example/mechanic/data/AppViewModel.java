package com.example.mechanic.data;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mechanic.BaseApp;
import java.util.List;

public class AppViewModel extends ViewModel {

    private AppRepository mRepository;
    public LiveData<List<UserAccount>> getAllUsers;
    public MutableLiveData<Mechanic> mechanicMLD = new MutableLiveData<>();
    private MutableLiveData<Question> questionMLD=  new MutableLiveData<>();
    private final MutableLiveData<QuestionWithUsersAndMechanics> selectedItem = new MutableLiveData<>();
    public Mechanic[] mMechanic;

    public void selectItem(QuestionWithUsersAndMechanics item) {

        selectedItem.setValue(item);
    }

    public LiveData<QuestionWithUsersAndMechanics> getSelectedItem() {

        return selectedItem;
    }

    public double getAvgRating(long mechanicId) {

        return mRepository.getAvgRating(mechanicId);
    }

    public LiveData<List<Mechanic>> getAllMechanics() {

        return mRepository.getmAllMechanics();
    }
    public List<Question> getAllQuestions() {

        return mRepository.getAllQuestions();
    }

    public LiveData<List<QuestionWithUsersAndMechanics>> getUsersAndMechanicsWithQuestions(int id) {

        return mRepository.getUsersAndMechanicsWithQuestions(id);
    }

    public LiveData<List<QuestionsForMechanic>> getQuestionsForMechanic(long id) {

        return mRepository.getQuestionsForMechanic(id);
    }

    public void askQuestion(Question q) {

        mRepository.askQuestion(q);
    }

    public AppViewModel() {

        super();
        mRepository= new AppRepository(BaseApp.getAppContext());
        Log.i("repo", "AppRepository: constuctore");
        mMechanic = mRepository.getMechanic();
    }

    LiveData<List<Mechanic>> getmAllMechanics() {

        return mRepository.getmAllMechanics();
    }

    public void insert(Mechanic mechanic) {

        mRepository.insert(mechanic);
    }

    public void insertUser(UserAccount user) {

        mRepository.insertUser(user);
    }

    public LiveData<List<UserAccount>> getAllUsers() {

        return mRepository.getmAllUsers();
    }

    public void deleteAllUsers() {

        mRepository.deleteAllUsers();
    }

    public UserAccount checkUser(String userName, String pw) {

        return mRepository.checkUser(userName,pw);
    }

    public UserAccount getUser(int id) {

        return mRepository.getUser(id);
    }

    public List<UserWithMechanics> getMechanicsByUser(int userId) {

        return mRepository.getMechanicsByUser(userId);
    }

    public List<MechanicWithUsers> getUsersByMechanic(long mechanicId) {

        return mRepository.getUsersByMechanic(mechanicId);
    }

    public void selectMechanic(Mechanic d) {

        mechanicMLD.setValue(d);
    }

    public MutableLiveData<Mechanic> getSelectedMechanic(){

        return mechanicMLD;
    }

    public void rateMechanic(int rating, long mechanicId) {

        mRepository.rateMechanic(rating,mechanicId);
    }

    public void selectQuestion(Question q) {

        questionMLD.setValue(q);
    }

    public MutableLiveData<Question> getSelectedQuestion(){

        return questionMLD;
    }

    public void setAnswer(String a, String q) {

        mRepository.setAnswer(a,q);
    }

    public Mechanic checkMechanic(String username, String pass) {

        return mRepository.checkMechanic(username, pass);
    }

    public Mechanic[] getAnyMechanic() {

        return mRepository.getMechanic();
    }

    public Mechanic getMechanicById(long loggedMechanicId) {

        return mRepository.getMechanicById(loggedMechanicId);
    }

    public int getNumOfVotes(long loggedMechanicId) {

        return mRepository.getNumOfVotes(loggedMechanicId);
    }

    public void insertPairMechanicUser(UserMechanicCrossRef userWithMechanic) {

        mRepository.insertPairMechanicUser(userWithMechanic);
    }
}
