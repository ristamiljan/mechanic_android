package com.example.mechanic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.mechanic.R;
import com.example.mechanic.data.AppViewModel;

public class NextAppointmentsFragment extends Fragment {

    private AppViewModel sharedViewModel;
    private TextView tv;
    private TextView tt;
    private TextView avgR;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_next_appointments,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        avgR = view.findViewById(R.id.rating);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        sharedViewModel.getSelectedMechanic().observe(getViewLifecycleOwner(),item -> {
            // update UI
            tv = view.findViewById(R.id.firstName);
            tv.setText("Asked mechanic "+item.getFirstName()+" ");
            tt = view.findViewById(R.id.lastName);
            tt.setText(item.getLastName());

            double n = sharedViewModel.getAvgRating(item.mechanic_id);
            if (n!=0.0){
                String s = "Mechanic's rating: " + n;
                avgR.setText(s);
            }
            else{
                avgR.setText("");
            }
                });

        sharedViewModel.getSelectedQuestion().observe(getViewLifecycleOwner(),question -> {
            tv = view.findViewById(R.id.answered_question);
            if(question.answer!=null) {
                tv.setText("Answer:" + question.answer);
            }
            else{
                tv.setText("");
            }
        });
    }
}
