package com.example.mechanic.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.TextView;
import com.example.mechanic.R;
import com.example.mechanic.adapter.AppointsmentListAdapter;
import com.example.mechanic.data.AppViewModel;
import com.example.mechanic.data.Mechanic;
import com.example.mechanic.data.Question;
import com.example.mechanic.data.UserAccount;
import com.example.mechanic.data.UserWithMechanics;
import com.example.mechanic.databinding.ActivityDisplayAppointmentsBinding;
import java.util.List;
import static com.example.mechanic.MainActivity.LOGGED_USER_ID;

public class DisplayAppointmentsActivity extends AppCompatActivity {

    protected AppViewModel viewModel ;
    protected ActivityDisplayAppointmentsBinding binding;
    private int loggedUserId;
    public AppointsmentListAdapter adapter;
    public static String LOGGED_USER="user_log";
    protected static UserAccount user;
    protected static List<UserWithMechanics> mechanicssByUser;
    protected long selectedMechanic;
    protected Mechanic d;
    protected Question q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_appointments);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("clicked-question"));

        if(b!=null) {
            loggedUserId = b.getInt(LOGGED_USER_ID);
        }

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_display_appointments);

        user = viewModel.getUser(loggedUserId);

        mechanicssByUser = viewModel.getMechanicsByUser(1);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_next);
        adapter = new AppointsmentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        viewModel.getUsersAndMechanicsWithQuestions(loggedUserId).observe(this, data ->
                adapter.setData(data.get(0))
        );

        binding.setUser(user);
        binding.setLifecycleOwner(this);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            d = (Mechanic) intent.getSerializableExtra("userWithMechanic");
            q = (Question) intent.getSerializableExtra("question");
            String s = intent.getStringExtra("lala");
            viewModel.selectQuestion(q);
            viewModel.selectMechanic(d);
        }
    };

    @Override
    protected void onStart() {

        super.onStart();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        NextAppointmentsFragment fragment = new NextAppointmentsFragment();

        fragmentTransaction.add(R.id.next_appointments,fragment);
        fragmentTransaction.commit();
    }

    public void chooseMechanic(View view) {

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ChooseMechanic.class);
        intent.putExtra(LOGGED_USER,user);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2){
            {
                this.selectedMechanic = data.getLongExtra("selectedMechanic", -1);

                TextView text = findViewById(R.id.textView);
                text.setText("You selected mechanic.");
            }
        } else if(resultCode == 3){
            this.loggedUserId = data.getIntExtra("user_id",-1);
        }
    }

    public void contactForm(View view) {

        Intent intent = new Intent();
        intent.putExtra("logged-user",loggedUserId);
        intent.putExtra("selectedMechanic", this.selectedMechanic);
        intent.setClass(getApplicationContext(), AskAndRateMechanic.class);
        startActivity(intent);
    }
}
