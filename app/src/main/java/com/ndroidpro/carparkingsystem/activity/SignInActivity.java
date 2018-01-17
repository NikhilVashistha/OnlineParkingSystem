package com.ndroidpro.carparkingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.Session;
import com.ndroidpro.carparkingsystem.model.UserProfile;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private RadioGroup radioUserRoleGroup;
    private RadioButton radioUserRoleButton;
    private Session session;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        session = new Session(SignInActivity.this);

        if (auth.getCurrentUser() != null) {
            ActivityUtils.startActivity(CarParkingLocationListActivity.class);
            ActivityUtils.finishActivity(SignInActivity.this, true);
        }

        // set the view now
        setContentView(R.layout.activity_sign_in);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        radioUserRoleGroup = (RadioGroup) findViewById(R.id.radio_user_role);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(SignUpActivity.class);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(ResetPasswordActivity.class);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(SignInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
//                                    int selectedId = radioUserRoleGroup.getCheckedRadioButtonId();
//
//                                    // find the radiobutton by returned id
//                                    radioUserRoleButton = (RadioButton) findViewById(selectedId);
//                                    if( radioUserRoleButton.getText().toString().equals(getResources().getString(R.string.admin)) ){
//                                        session.setIsAdmin(true);
//                                    }else {
//                                        session.setIsAdmin(false);
//                                    }
                                    getUserDetails(auth);
                                }
                            }
                        });
            }
        });
    }

    public void getUserDetails(FirebaseAuth auth) {

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String mUserId = user.getUid();

                    DatabaseReference databaseReference =
                            mDatabase
                                    .child(Constants.DB_USERS)
                                    .child(mUserId)
                                    .child(Constants.DB_PROFILE);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                            if(userProfile != null) {
                                if(userProfile.getRole() == Constants.USER_ROLE_ADMIN){
                                    session.setIsAdmin(true);
                                }else {
                                    session.setIsAdmin(false);
                                }

                                Intent intent = new Intent(SignInActivity.this, CarParkingLocationListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                ActivityUtils.startActivity(intent);
                                ActivityUtils.finishActivity(SignInActivity.this, true);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignInActivity.this,
                                    "Authentication failed." + databaseError.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.i("AuthStateChanged", "No user is signed in.");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
