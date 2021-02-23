package com.example.mechanic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.mechanic.R;
import com.example.mechanic.adapter.MechanicListAdapter;
import com.example.mechanic.data.AppViewModel;
import com.example.mechanic.data.Mechanic;
import com.example.mechanic.data.UserAccount;

public class ChooseMechanic extends AppCompatActivity{
    protected int choosenMechanicId=-1;
    protected UserAccount loggedUser;
    protected AppViewModel mViewModel;
    private static long selectedMechanic;
    public MechanicListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mechanic);
        loggedUser = (UserAccount) getIntent().getExtras().getSerializable(DisplayAppointmentsActivity.LOGGED_USER);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        adapter = new MechanicListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        mViewModel.getAllMechanics().observe(this, mechanics -> {
            // Update the cached copy of the words in the adapter.
            adapter.setMechanics(mechanics);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra("user_id",loggedUser.getUserId());
        intent.setClass(getApplicationContext(),DisplayAppointmentsActivity.class);
        setResult(3, intent);
        finish();

        return true;
    }

    public void chooseMechanic(View view) {

        choosenMechanicId = adapter.getSelectedMechanicId();

        Mechanic mechanic = adapter.getMechanicAtPosition(choosenMechanicId);

        Toast.makeText(this, "You successfully chosed the mechanic " + mechanic.getFirstName() + " " +
                mechanic.getLastName(),Toast.LENGTH_SHORT).show();

        selectedMechanic = mechanic.mechanic_id;
        Intent intent = new Intent();
        intent.putExtra("selectedMechanic",selectedMechanic);
        intent.setClass(getApplicationContext(),DisplayAppointmentsActivity.class);
        setResult(2, intent);

        finish();
    }
}
