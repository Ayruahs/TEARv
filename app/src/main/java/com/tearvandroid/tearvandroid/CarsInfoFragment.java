package com.tearvandroid.tearvandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CarsInfoFragment extends Fragment {

    RelativeLayout carPageLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        Button add = (Button) view.findViewById(R.id.add_car_button);
        Button signout_button = (Button) view.findViewById(R.id.sign_out_button);
        add.setOnClickListener(new View.OnClickListener() { //errors all over View, not sure why
            @Override
            public void onClick(View v) {
                addNewCar();
            }
        });
        carPageLayout = view.findViewById(R.id.fragment_carsID);

        return view; //inflater.inflate(R.layout.fragment_cars, container, false);
    }

    private void addNewCar(){
        //carPageLayout.setBackgroundColor(Color.argb(255,255,255,255));
        /*Toast.makeText(getActivity(), "debug" , Toast.LENGTH_SHORT).show();
        System.out.println("add button clicked");
        */
        Intent intent = new Intent(getActivity(), PopActivity.class);
        startActivity(intent);
        //startActivity(new Intent(getActivity(),PopUpActivity.class));
        //Dom added, Pop Window
        //carPageLayout

    }

}