package com.tearvandroid.tearvandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CarsInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.add_car_button);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addNewCar();
            }
        });

        return view;
    }

    public void addNewCar(){

    }


}
