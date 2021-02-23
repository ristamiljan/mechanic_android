package com.example.mechanic.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Mechanic.class, UserAccount.class,Question.class, UserMechanicCrossRef.class}, version = 22, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDAO questionDAO();
    public abstract MechanicDAO mechanicDAO();
    public abstract UserAccountDAO usersDAO();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {

        if(INSTANCE==null){
            synchronized (AppDatabase.class){
                if(INSTANCE ==  null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"mechanic")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static AppDatabase.Callback sRoomDatabaseCallback = new AppDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {

            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private MechanicDAO mMechanic;
        private QuestionDAO mQuestion;
        private UserAccountDAO mUser;

        /*Questions*/
        String [] qs = {
                "21.12.2020 at 2pm service?",
                "11..2020 at 2pm service?",
                "14.03.2020 at 10am service?"
        };

        String [] ans = {
                "Deal.",
                "Come at 3pm.",
                "Deal."
        };

        /*Mechanics*/
        String [] mechanicFirstName = {"Lazar", "Branislav","Vaso"};
        String [] mechanicLastName = {"Jovanovic","Smijic","Bakocevic"};
        String [] mechanicUsername = {"laza","bane","vasko"};
        String [] mechanicPassword = {"mechanic123","mechanic123","mechanic123"};

        /*Users*/
        String [] fNameUser = {"Dalibor","Srdjan","Nevena"};
        String [] uNameUser = {"user1","user2","user3"};
        String [] passwords = {"user1","user2","user3"};

        public PopulateDbAsync(AppDatabase db) {

            mMechanic = db.mechanicDAO();
            mQuestion = db.questionDAO();
            mUser = db.usersDAO();
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... voids) {

            if(mMechanic.getAnyMechanic().length < 1){
                for (int i = 0; i<mechanicFirstName.length;i++){
                    Mechanic d = new Mechanic(mechanicFirstName[i],mechanicLastName[i],mechanicUsername[i],mechanicPassword[i]);
                    mMechanic.insert(d);
                    Log.i("mechanic-added", mechanicFirstName[i]);
                }
            }

            if(mUser.getAnyUser().length < 1){
                for (int i = 0; i<fNameUser.length;i++){
                    UserAccount ua = new UserAccount(fNameUser[i],mechanicLastName[i],uNameUser[i],passwords[i]);
                    mUser.insert(ua);
                    Log.i("user-added with username: ", uNameUser[i]);

                }
            }

            int [] userIds = {1,2,3};
            int [] mechanicIds = {1,1,1};

            if(mQuestion.getAnyQuestion().length < 1){
                 for (int i = 0; i<qs.length;i++){
                     Question q;

                     if(ans[i].equals("")){
                         q = new Question(qs[i]);
                     } else {
                         q = new Question(qs[i],ans[i]);
                     }

                    UserMechanicCrossRef u = new UserMechanicCrossRef(userIds[i],mechanicIds[i]);
                    mUser.insertPairs(u);

                    q.setUserId(userIds[i]);
                    q.setMechanicId(mechanicIds[i]);

                    mQuestion.insert(q);
                    Log.i("question-added", qs[i]);
                 }
             }

             return null;
        }
    }
}
