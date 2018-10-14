package com.tearvandroid.tearvandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mAuth = FirebaseAuth.getInstance();

        Button signout_button = (Button) view.findViewById(R.id.sign_out_button);
        signout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLogin();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        final String email = user.getEmail().toString();
        TextView mEmailView = (TextView) view.findViewById(R.id.email_title);
        mEmailView.setText(email);

        Button change_pass_button = (Button) view.findViewById(R.id.change_password_button);
        change_pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(email);
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

