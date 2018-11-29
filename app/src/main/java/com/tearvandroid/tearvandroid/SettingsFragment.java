package com.tearvandroid.tearvandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.support.constraint.Constraints.TAG;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText passwordView;
    private EditText password2View;
    private EditText usernameText;
    private TextView usernameView;
    private Button change_pass_button;
    private Button password_save_button;
    private Button password_cancel_button;
    private Button username_save_button;
    private Button username_cancel_button;
    private Button username_edit_button;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Button signout_button = (Button) view.findViewById(R.id.sign_out_button);
        signout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLogin();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        final String email = user.getEmail();
        TextView mEmailView = (TextView) view.findViewById(R.id.email_id);
        mEmailView.setText(email);

        passwordView = (EditText)view.findViewById(R.id.new_password);
        password2View = (EditText)view.findViewById(R.id.new_password2);
        passwordView.setVisibility(View.INVISIBLE);
        password2View.setVisibility(View.INVISIBLE);

        change_pass_button = (Button) view.findViewById(R.id.change_password_button);
        change_pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAuth.sendPasswordResetEmail(email);
                changePassword();
            }
        });

        password_save_button = (Button) view.findViewById(R.id.password_save_button);
        password_cancel_button = (Button) view.findViewById(R.id.password_cancel_button);
        password_save_button.setVisibility(View.INVISIBLE);
        password_cancel_button.setVisibility(View.INVISIBLE);

        username_save_button = (Button) view.findViewById(R.id.username_save_button);
        username_cancel_button = (Button) view.findViewById(R.id.username_cancel_button);
        username_edit_button = (Button) view.findViewById(R.id.edit_button);
        username_save_button.setVisibility(View.INVISIBLE);
        username_cancel_button.setVisibility(View.INVISIBLE);

        username_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserName();
            }
        });

        usernameText = (EditText) view.findViewById(R.id.username_edit_view);
        usernameView = (TextView) view.findViewById(R.id.username_text_view);
        usernameText.setVisibility(View.INVISIBLE);

        TextView faqView = (TextView)view.findViewById(R.id.faq_button);
        faqView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompFaq();
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

    public void changeUserName() {
        username_edit_button.setVisibility(View.INVISIBLE);
        username_save_button.setVisibility(View.VISIBLE);
        username_cancel_button.setVisibility(View.VISIBLE);

        usernameText.setVisibility(View.VISIBLE);
        usernameView.setVisibility(View.INVISIBLE);

        username_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: upload new user name to database
                String newname = usernameText.getText().toString();

                if (TextUtils.isEmpty(newname)) {
                    usernameText.setError(getString(R.string.error_field_required));
                    usernameText.requestFocus();
                }
                else {


                    username_edit_button.setVisibility(View.INVISIBLE);
                    username_save_button.setVisibility(View.VISIBLE);
                }
            }
        });

        username_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_edit_button.setVisibility(View.VISIBLE);
                username_save_button.setVisibility(View.INVISIBLE);
                username_cancel_button.setVisibility(View.INVISIBLE);

                usernameText.setVisibility(View.INVISIBLE);
                usernameView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void changePassword() {
        // change button to save
        change_pass_button.setVisibility(View.INVISIBLE);
        password_save_button.setVisibility(View.VISIBLE);
        password_cancel_button.setVisibility(View.VISIBLE);
        passwordView.setVisibility(View.VISIBLE);
        password2View.setVisibility(View.VISIBLE);

        passwordView.setError(null);
        password2View.setError(null);

        password_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_pass_button.setVisibility(View.VISIBLE);
                password_save_button.setVisibility(View.INVISIBLE);
                password_cancel_button.setVisibility(View.INVISIBLE);
                passwordView.setVisibility(View.INVISIBLE);
                password2View.setVisibility(View.INVISIBLE);
            }
        });

        password_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;

                String password = passwordView.getText().toString();
                String password2 = password2View.getText().toString();

                if (TextUtils.isEmpty(password)) {
                    passwordView.setError(getString(R.string.error_field_required));
                    focusView = passwordView;
                    cancel = true;
                }
                else if (TextUtils.isEmpty(password2)) {
                    password2View.setError(getString(R.string.error_field_required));
                    focusView = password2View;
                    cancel = true;
                }
                // Check for a valid password, if the user entered one.
                else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    passwordView.setError(getString(R.string.error_invalid_password));
                    focusView = passwordView;
                    cancel = true;
                }
                else if (!password.equals(password2)) {
                    password2View.setError(getString(R.string.error_unmatch_password));
                    focusView = password2View;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                }
                else {
                    setNewPassword(password);
                }
            }
        });
    }

    private void setNewPassword(String password){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_SHORT).show();
                            change_pass_button.setVisibility(View.VISIBLE);
                            password_save_button.setVisibility(View.INVISIBLE);
                            password_cancel_button.setVisibility(View.INVISIBLE);
                            passwordView.setVisibility(View.INVISIBLE);
                            password2View.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Toast.makeText(getActivity(), "Update password failed. Please try again later. ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void prompFaq() {

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 7;
    }
}

