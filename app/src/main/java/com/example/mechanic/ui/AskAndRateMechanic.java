package com.example.mechanic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.mechanic.R;
import com.example.mechanic.data.AppViewModel;
import com.example.mechanic.data.Question;
import com.example.mechanic.data.UserMechanicCrossRef;

public class AskAndRateMechanic extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AppViewModel viewmodel;
    private int loggedUser;
    private long sMechanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_rate_mechanic);

        loggedUser = getIntent().getIntExtra("logged-user",-1);
        sMechanic = getIntent().getLongExtra("selectedMechanic", -1);

        Spinner spinner = (Spinner) findViewById(R.id.ratings_spinner);
        Button ask = (Button)findViewById(R.id.askButton);
        Button rate = (Button)findViewById(R.id.rateButton);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this,R.array.ratings,android.R.layout.simple_spinner_item);

        viewmodel = new ViewModelProvider(this).get(AppViewModel.class);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String question = ((EditText) findViewById(R.id.question)).getText().toString();
                Question q = new Question();
                q.question = question;

                if(loggedUser == -1 || sMechanic<1){
                    Toast.makeText(AskAndRateMechanic.this, "Please choose the mechanic first.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{

                    q.setUserId(loggedUser);

                    q.setMechanicId(sMechanic);

                    viewmodel.askQuestion(q);

                    UserMechanicCrossRef u = new UserMechanicCrossRef(loggedUser,sMechanic);
                    viewmodel.insertPairMechanicUser(u);

                    Toast.makeText(AskAndRateMechanic.this, "You successfully asked mechanic.", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sMechanic<1) {
                    Toast.makeText(AskAndRateMechanic.this, "Please choose the mechanic first.", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    String rating = spinner.getSelectedItem().toString();
                    int r = Integer.parseInt(rating);

                    viewmodel.rateMechanic(r, sMechanic);
                    Toast.makeText(AskAndRateMechanic.this, "You successfully rated mechanic with " + rating, Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

     // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item + ' ' + position + ' '+ id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
