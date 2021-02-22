package com.example.mechanic.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mechanic.R;
import com.example.mechanic.data.Mechanic;
import com.example.mechanic.data.MechanicWithUsers;
import com.example.mechanic.data.Question;
import com.example.mechanic.data.QuestionsForMechanic;
import com.example.mechanic.data.UserAccount;
import java.util.List;

public class QuestionsForMechanicListAdapter extends RecyclerView.Adapter<QuestionsForMechanicListAdapter.QuestionsViewHolder> {

    private final LayoutInflater mInflater;
    protected List<Question> mQuestions;
    protected MechanicWithUsers mMechanicWithUsers;
    protected Context mContext;
    protected Intent intent;
    protected Mechanic loggedMechanic;
    protected List<UserAccount> askedQuestionsBy;
    protected QuestionsForMechanic data;

    public QuestionsForMechanicListAdapter(Context context){

        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setQuestions(List<Question> questions) {

        mQuestions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recyclerview_appointments_item,parent,false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {

        if(mQuestions!=null){
            Question q = mQuestions.get(position);
            if(q.answer == null) {
                holder.questionItemView.setText(q.question);
            }
            else{
                holder.questionItemView.setText("Answered: " +q.question);
            }
        }
        else{
            holder.questionItemView.setText("No new questions.");
        }
    }

    @Override
    public int getItemCount() {

        if(mQuestions==null) {
            return 0;
        }
        else{
            return mQuestions.size();
        }
    }

    public void setData(QuestionsForMechanic qfd){

        data = qfd;
        setQuestions(data.questions);
        setMechanicWithUsers(data.mechanics);
    }

    private void setMechanicWithUsers(MechanicWithUsers mechanics) {

        mMechanicWithUsers = mechanics;
        loggedMechanic = mMechanicWithUsers.mechanic;
        askedQuestionsBy = mMechanicWithUsers.users;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder{

        private final TextView questionItemView;

        public QuestionsViewHolder(View view) {

            super(view);
            questionItemView = view.findViewById(R.id.question_textView);
            questionItemView.setOnClickListener(v-> {
                Log.i("clicked adapter at: ",Integer.toString(getAdapterPosition()));

                intent = new Intent("question-for-answer");

                intent.putExtra("question", mQuestions.get(getAdapterPosition()));
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            });
        }
    }
}
