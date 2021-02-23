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
import com.example.mechanic.data.Question;
import com.example.mechanic.data.QuestionWithUsersAndMechanics;
import com.example.mechanic.data.UserWithMechanics;
import java.util.List;

public class AppointsmentListAdapter extends RecyclerView.Adapter<AppointsmentListAdapter.AppointsmentViewHolder> {
    private final LayoutInflater mInflater;
    protected List<Question> mQuestions;
    protected UserWithMechanics mUserWithMechanics;
    protected Context mContext;
    protected Intent intent;
    protected QuestionWithUsersAndMechanics data;

    public AppointsmentListAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setQuestions(List<Question> questions) {

        mQuestions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppointsmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recyclerview_appointments_item,parent,false);

        return new AppointsmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointsmentViewHolder holder, int position) {

        if(mQuestions!=null){
            Question q = mQuestions.get(position);
            holder.questionItemView.setText(q.question);

            if (q.answer!=null){
                holder.questionItemView.setBackgroundResource(R.color.colorPrimary);
            }
        }

        if(mQuestions == null || mQuestions.size()==0){
                holder.questionItemView.setText("No active questions for mechanic.");

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


    public void setUserWithMechanics(UserWithMechanics users) {

        mUserWithMechanics = users;
        notifyDataSetChanged();
    }

    public void setData(QuestionWithUsersAndMechanics data) {

        this.data= data;
        setQuestions(data.questions);
        setUserWithMechanics(data.users);
    }


    public class AppointsmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView questionItemView;
        private Mechanic tmpMechanic;

        public AppointsmentViewHolder(View view) {
            super(view);
            questionItemView = view.findViewById(R.id.question_textView);
            questionItemView.setOnClickListener(v-> {
                intent = new Intent("clicked-question");

                intent.putExtra("question", mQuestions.get(getAdapterPosition()));
                int k = getAdapterPosition();
                long l = mQuestions.get(k).getMechanicId();

                for (int i = 0; i< mUserWithMechanics.mechanics.size(); i++){
                    if(mUserWithMechanics.mechanics.get(i).mechanic_id == l){
                        tmpMechanic = mUserWithMechanics.mechanics.get(i);
                    }
                }

                intent.putExtra("userWithMechanic", tmpMechanic);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            });
        }
    }
}
