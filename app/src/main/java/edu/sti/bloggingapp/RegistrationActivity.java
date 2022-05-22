package edu.sti.bloggingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btn_register;
    private EditText et_firstName, et_lastName, et_email, et_password;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        et_firstName = findViewById(R.id.et_firstName);
        et_lastName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v->{
            registerUser();
        });

        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(v->{
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });

    }

    private void registerUser() {
        String firstName = et_firstName.getText().toString().trim();
        String lastName = et_lastName.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(firstName.isEmpty()){
            et_firstName.setError("First Name is required!");
            et_firstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            et_lastName.setError("Last Name is required!");
            et_lastName.requestFocus();
            return;
        }
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authSuc->{
                    User user = new User(firstName, lastName, email, password);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnSuccessListener(dbSuc->{
                                Toast.makeText(this, "User is registered successfully!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }).addOnFailureListener(dbFail->{
                        Toast.makeText(this, ""+dbFail.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }).addOnFailureListener(authFail->{
                    Toast.makeText(this, ""+authFail.getMessage(), Toast.LENGTH_LONG).show();
        });

    }
}