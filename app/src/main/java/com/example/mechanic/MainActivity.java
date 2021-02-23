package com.example.mechanic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mechanic.data.AppViewModel;
import com.example.mechanic.data.UserAccount;
import com.example.mechanic.databinding.ActivityMainBinding;
import com.example.mechanic.ui.DisplayAppointmentsActivity;
import com.example.mechanic.ui.MechanicLoginFragment;
import com.example.mechanic.ui.RegisterFragment;

public class MainActivity extends AppCompatActivity implements RegisterFragment.AddUserDialogListener {

    private AppViewModel viewModel;
    private ActivityMainBinding binding;
    public static String LOGGED_USER_ID = "user_id";
    private static boolean startedDL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        startedDL = false;
    }

    public void showAddUserDialog(View view) {

        Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_LONG).show();
        DialogFragment dialog = new RegisterFragment();
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_add_user));

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        Dialog d = dialog.getDialog();
        String username = ((EditText)d.findViewById(R.id.edit_text_dialog_username)).getText().toString();
        String password = ((EditText)d.findViewById(R.id.edit_text_dialog_password)).getText().toString();

        UserAccount ua = new UserAccount(username,password);
        viewModel.insertUser(ua);

        Toast.makeText(getApplicationContext(),"User added",Toast.LENGTH_LONG);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void tryLogin(View view) {

        String user = ((EditText) findViewById(R.id.txtEmailAddress)).getText().toString();
        String pw = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        UserAccount tmp_user = viewModel.checkUser(user,pw);

        if(tmp_user!=null){
            Toast.makeText(getApplicationContext(),"logged",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DisplayAppointmentsActivity.class);
            intent.putExtra(LOGGED_USER_ID,tmp_user.user_id);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Incorrect Username or Password ",Toast.LENGTH_LONG).show();
        }

    }

    public void showMechanicLogin(View view) {

        if(!startedDL) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();

            MechanicLoginFragment fragment = new MechanicLoginFragment();

            fragmentTransaction.add(R.id.mechanic_login_container, fragment);
            fragmentTransaction.commit();
            startedDL = true;
        }
    }
}
