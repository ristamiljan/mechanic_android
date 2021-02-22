package com.example.mechanic.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mechanic.R;
import com.example.mechanic.data.Mechanic;

import java.util.List;

public class MechanicListAdapter extends RecyclerView.Adapter<MechanicListAdapter.MechanicViewHolder> {

    public static int selectedMechanicId = -1;
    public static boolean selected = false;
    private final LayoutInflater mInflater;
    private List<Mechanic> mMechanics;

    public MechanicListAdapter(Context context){

        mInflater=LayoutInflater.from(context);
    }

    public void setMechanics(List<Mechanic> mechanics){

        mMechanics = mechanics;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MechanicListAdapter.MechanicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MechanicViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MechanicListAdapter.MechanicViewHolder holder, int position) {

        if (mMechanics !=null){
            Mechanic current = mMechanics.get(position);
            holder.mechanicItemView.setText(current.getFirstName() + " " + current.getLastName());

        }
        else{
            holder.mechanicItemView.setText("No Avaliable Mechanic");
        }
    }

    @Override
    public int getItemCount() {

        if(mMechanics ==null){
            return 0;
        }
        else{
            return mMechanics.size();
        }
    }

    public static int getSelectedMechanicId() {

        return selectedMechanicId;
    }

    public Mechanic getMechanicAtPosition(int position) {

        return mMechanics.get(position);
    }

    class MechanicViewHolder extends RecyclerView.ViewHolder {

        private final TextView mechanicItemView;

        @SuppressLint("ResourceAsColor")
        public MechanicViewHolder(@NonNull View itemView) {

            super(itemView);
            mechanicItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> {

                if(!selected) {
                    mechanicItemView.setBackgroundColor(Color.GREEN);
                    selectedMechanicId = getLayoutPosition();
                    selected = true;

                }
                else if (selectedMechanicId == getLayoutPosition() && selected){
                    mechanicItemView.setBackgroundColor(R.color.colorPrimaryLight);
                    selectedMechanicId = -1;
                    selected = false;
                }
            });
        }

        private void selectMechanic(int position) {

                Mechanic sMechanic = getMechanicAtPosition(position);
        }
    }
}
