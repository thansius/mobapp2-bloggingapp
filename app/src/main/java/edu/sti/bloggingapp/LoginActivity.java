package edu.sti.bloggingapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btn_Login;
    private EditText et_email, et_password;
    private TextView tv_signup, tv_forgetPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_Login = findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(v->{
            loginUser();
        });

        tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        tv_forgetPW = findViewById(R.id.tv_forgetPW);
        tv_forgetPW.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        });

    }

    private void loginUser() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(email.isEmpty()){
            et_email.setError("Email Address is required!");
            et_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please provide a valid email address!");
            et_email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            et_password.setError("Password is required!");
            et_password.requestFocus();
            return;
        }
        if(password.length()<6){
            et_password.setError("Password must have at least 6 characters!");
            et_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(suc->{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        User.userID = user.getUid();
                        Log.e(TAG,User.userID);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(this, "Check email for verification.", Toast.LENGTH_LONG).show();
                    }

                }).addOnFailureListener(fail->{
                    Toast.makeText(this, ""+fail.getMessage(), Toast.LENGTH_LONG).show();
        });

    }
}