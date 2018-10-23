package com.tearvandroid.tearvandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;


public class SignupActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Button mCancelButton = (Button) findViewById(R.id.cancel_action);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToLogin();
            }
        });

        mUsernameView = (EditText)findViewById(R.id.username);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mRegisterAction = (Button) findViewById(R.id.register_action);
        mRegisterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    private void returnToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void attemptRegister(){
        // Reset errors.
        mUsernameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String username = mUsernameView.getText().toString();
        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //TODO: firebase regiter and data upload
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up success, update UI with the signed-in user's information
                                Log.d("Success", "createUserWithEmail:success");
                                Toast.makeText(SignupActivity.this, "Signed in.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                try {
                                    user.sendEmailVerification();
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }

                                //store user info to database
                                db.collection("users").add(email).addOnSuccessListener(
                                        new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("create", "new document " + email + "successfully created");
                                            }
                                        }
                                );

                                Map<String, Object> data = new HashMap<>();
                                if (username.equals("") || username.length() == 0) {
                                    data.put("username", "User");
                                }
                                else {
                                    data.put("username", username);
                                }
                                db.collection("users").document(email).set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("upload", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("upload", "Error writing document", e);
                                            }
                                        });

                                startActivity(new Intent(SignupActivity.this, TabsActivity.class));
                            } else {
                                // If sign up fails, display a message to the user.
                                FirebaseException e = (FirebaseException) task.getException();
                                Toast.makeText(SignupActivity.this, "Failed to sign up: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.w("Error", "createUserWithEmail:failure", task.getException());
                            }

                            // ...
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with firebase email validation
        return email.contains("@");
    }
}
