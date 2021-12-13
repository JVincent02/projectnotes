package com.example.projectnotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.projectnotes.Utils.KeyboardUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    View signInBtn;
    View signUpBtn;
    View loginCon;

    View confirmSignBtn;
    View confirmExitBtn;

    EditText usernameTV;
    EditText passwordTV;
    EditText confirmPasswordTV;
    TextView loginTitleTV;
    TextView labelPasswordTV;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginCon = findViewById(R.id.loginCon);
        confirmSignBtn = findViewById(R.id.confirmSignBtn);
        confirmExitBtn = findViewById(R.id.confirmExitBtn);
        usernameTV = findViewById(R.id.usernameTV);
        passwordTV = findViewById(R.id.passwordTV);
        confirmPasswordTV = findViewById(R.id.confirmPasswordTV);
        loginTitleTV = findViewById(R.id.loginTitleTV);
        labelPasswordTV = findViewById(R.id.labelConfirmPasswordTV);

        signInBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        confirmSignBtn.setOnClickListener(this);
        confirmExitBtn.setOnClickListener(this);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            openMainAct(currentUser);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInBtn:
                openLoginCon(true);
                break;
            case R.id.signUpBtn:
                openLoginCon(false);

                break;
            case R.id.confirmSignBtn:
                login();
                break;
            case R.id.confirmExitBtn:
                loginCon.setVisibility(View.GONE);
                KeyboardUtil.hideKeyboard(this);
                break;
        }
    }
    private void login(){
        boolean cleared = true;
        boolean signin=true;
        if(usernameTV.getText().length()<8){
            usernameTV.setError("Username must be more than 8 characters");
            cleared=false;
        }
        if(passwordTV.getText().length()<8){
            passwordTV.setError("Password must be more than 8 characters");
            cleared=false;
        }

        if(confirmPasswordTV.getVisibility()==View.VISIBLE){
            signin=false;
            if(confirmPasswordTV.getText().length()<8){
                confirmPasswordTV.setError("Password must be more than 8 characters");
                cleared=false;
            }
            String pass = passwordTV.getText().toString();
            String cpass = confirmPasswordTV.getText().toString();
            if(!pass.equals(cpass)){
                Toast.makeText(LoginActivity.this, passwordTV.getText()+","+confirmPasswordTV.getText(),
                        Toast.LENGTH_LONG).show();
                passwordTV.setError("Password must be the same");
                confirmPasswordTV.setError("Password must be the same");
                cleared=false;
            }
        }

        if(cleared&&!signin){
            mAuth.createUserWithEmailAndPassword(usernameTV.getText().toString().toLowerCase(), confirmPasswordTV.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("LoginAct", "createUserWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("LoginAct", user.getEmail());
                                openMainAct(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("LoginAct", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }else if(cleared&&signin){
            mAuth.signInWithEmailAndPassword(usernameTV.getText().toString().toLowerCase(), passwordTV.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("LoginAct", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("LoginAct", user.getEmail());
                                openMainAct(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("LoginAct", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void openMainAct(FirebaseUser u){
        Intent intent = new Intent(this, MainActivity.class);// New activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("uid", u.getUid());
        bundle.putString("email", u.getEmail());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void openLoginCon(boolean signin) {
        loginCon.setVisibility(View.VISIBLE);
        usernameTV.setText("");
        passwordTV.setText("");
        confirmPasswordTV.setText("");
        if(signin){
            loginTitleTV.setText("Enter Login Credentials");
            confirmPasswordTV.setVisibility(View.GONE);
            labelPasswordTV.setVisibility(View.GONE);
            ((Button)confirmSignBtn).setText("Login");
        }else{
            loginTitleTV.setText("Create New Account");
            confirmPasswordTV.setVisibility(View.VISIBLE);
            ((Button)confirmSignBtn).setText("Sign Up");
            labelPasswordTV.setVisibility(View.VISIBLE);

        }
    }
}
