
package com.tearvandroid.tearvandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CarsInfoFragment extends Fragment {

    RelativeLayout carPageLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        TextView textView = (TextView) view.findViewById(R.id.help);
        textView.setMovementMethod(new ScrollingMovementMethod());
        //FloatingActionButton add = view.findViewById(R.id.add_car_button);
//        Button signout_button = (Button) view.findViewById(R.id.sign_out_button);

        //add.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
             //   addNewCar();
            //}
        //});
//        add.setOnClickListener(new View.OnClickListener() { //errors all over View, not sure why
//            @Override
//            public void onClick(View v) {
//                System.out.println("hello");
//            }
//        });
//        carPageLayout = view.findViewById(R.id.fragment_carsID);

        return view; //inflater.inflate(R.layout.fragment_cars, container, false);
    }

//    private void addNewCar(){
        //carPageLayout.setBackgroundColor(Color.argb(255,255,255,255));
        /*Toast.makeText(getActivity(), "debug" , Toast.LENGTH_SHORT).show();
        System.out.println("add button clicked");
        */
        //Intent intent = new Intent(getActivity(), PopActivity.class);
        //startActivity(intent);
        //startActivity(new Intent(getActivity(),PopUpActivity.class));
        //Dom added, Pop Window
        //carPageLayout

  //  }

}
