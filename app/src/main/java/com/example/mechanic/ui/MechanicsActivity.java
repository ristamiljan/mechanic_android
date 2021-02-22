package com.example.mechanic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mechanic.R;
import com.example.mechanic.adapter.QuestionsForMechanicListAdapter;
import com.example.mechanic.data.AppViewModel;
import com.example.mechanic.data.Question;
import com.example.mechanic.databinding.ActivityMechanicsBinding;
import static com.example.mechanic.ui.MechanicLoginFragment.LOGGED_MECHANIC_ID;

public class MechanicsActivity extends AppCompatActivity {
    protected AppViewModel viewModel;
    protected long loggedMechanicId;
    protected Question q;
    protected ActivityMechanicsBinding binding;
    public QuestionsForMechanicListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            loggedMechanicId = b.getLong(LOGGED_MECHANIC_ID);
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_mechanics);

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_mechanic);

        LocalBroadcastManager.getInstance(this).registerReceiver(mQuestionReceiver,new IntentFilter("question-for-answer"));

        adapter = new QuestionsForMechanicListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        viewModel.getQuestionsForMechanic(loggedMechanicId).observe(this,data -> {
            adapter.setData(data.get(0));
        });

        viewModel.getSelectedQuestion().observe(this,question -> {
            binding.setQuestion(question);
        });

        TextView nVotes  = findViewById(R.id.num_of_votes);
        TextView rating  = findViewById(R.id.avg_rating);
        rating.setText("Rating(Number of votes): " + Double.toString(viewModel.getAvgRating(loggedMechanicId)));
        nVotes.setText("("+Integer.toString(viewModel.getNumOfVotes(loggedMechanicId))+") ");
    }

    public BroadcastReceiver mQuestionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            q = (Question) intent.getSerializableExtra("question");
            viewModel.selectQuestion(q);
        }
    };

    public void answerOnQuestion(View view) {

        String answer = ((EditText) findViewById(R.id.answer)).getText().toString();
        String q = ((TextView) findViewById(R.id.question_for_answering)).getText().toString();
        int questionMarkPos = q.lastIndexOf('?');
        q = q.substring(0,questionMarkPos);
        viewModel.setAnswer(answer, q);
        Log.i("problem: ", answer+" "+q);
        ((EditText) findViewById(R.id.answer)).setText("");
    }
}
