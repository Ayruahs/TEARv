package com.tearvandroid.tearvandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button button = (Button) view.findViewById(R.id.sign_out_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                returnToLogin();
            }
        });
        return view;
    }

    private void returnToLogin(){
        FirebaseAuth.getInstance().signOut();
        onDetach();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        Toast.makeText(getActivity(), "Signed out " , Toast.LENGTH_SHORT).show();
    }
}

