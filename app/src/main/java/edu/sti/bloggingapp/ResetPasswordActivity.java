package edu.sti.bloggingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText et_email;
    private Button btn_reset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        et_email = findViewById(R.id.et_email);
        btn_reset = findViewById(R.id.btn_reset);

        auth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(v->{
            resetPassword();
        });

    }

    private void resetPassword() {
        String email = et_email.getText().toString().trim();
        if(email.isEmpty()){
            et_email.setError("Email Address is required!");
            et_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Enter a valid email address!");
            et_email.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnSuccessListener(suc->{
            Toast.makeText(ResetPasswordActivity.this, "Check your email to reset your password.", Toast.LENGTH_LONG).show();
        }).addOnFailureListener(fail -> {
            Toast.makeText(ResetPasswordActivity.this, ""+fail.getMessage(), Toast.LENGTH_LONG).show();
        });

    }
}