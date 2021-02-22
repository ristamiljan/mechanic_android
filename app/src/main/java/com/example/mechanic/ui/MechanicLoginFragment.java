package com.example.mechanic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.mechanic.R;
import com.example.mechanic.data.AppViewModel;
import com.example.mechanic.data.Mechanic;

public class MechanicLoginFragment extends Fragment {

    private AppViewModel viewModel;
    private Button mButton;

    public static String LOGGED_MECHANIC_ID = "mechanic_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mechanic_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mButton = view.findViewById(R.id.btn_mechanic_login);
        mButton.setOnClickListener(l -> {
            tryMechanicLogin(view);
        });
    }

    public void tryMechanicLogin(View view){

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        EditText p = view.findViewById(R.id.mechanic_password);
        String pass = p.getText().toString();

        EditText n =  view.findViewById(R.id.mechanic_username);
        String username = n.getText().toString();

        Mechanic tmp_mechanic = viewModel.checkMechanic(username, pass);

        if(tmp_mechanic !=null){
            Toast.makeText(getContext(),"Mechanic - logged",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(requireActivity(), MechanicsActivity.class);
            intent.putExtra(LOGGED_MECHANIC_ID, tmp_mechanic.mechanic_id);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(),"Incorrect Username or Password ",Toast.LENGTH_LONG).show();
        }
    }
}
