package edu.sti.bloggingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CreatePostActivity extends AppCompatActivity {

    private EditText et_title, et_content;
    private Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        DataAccess da = new DataAccess();

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        btn_create = findViewById(R.id.btn_create);

        Post post_edit = (Post) getIntent().getSerializableExtra("EDIT");

        if(post_edit != null){
            btn_create.setText("Update");
            et_title.setText(post_edit.getTitle());
            et_content.setText(post_edit.getContent());
        }
        else{
            btn_create.setText("Submit");
        }

        btn_create.setOnClickListener(v->{
            Post post = new Post(User.userID, et_title.getText().toString(), et_content.getText().toString());

            if(post_edit == null){
                da.insertPost(post).addOnSuccessListener(suc->{
                    Toast.makeText(this, "Post created successfully!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
                }).addOnFailureListener(fail->{
                    Toast.makeText(this, ""+fail.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
            else{
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("title", et_title.getText().toString());
                hashMap.put("content", et_content.getText().toString());
                da.updatePost(post_edit.getKey(), hashMap).addOnSuccessListener(suc->{
                    Toast.makeText(this, "Post updated successfully!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
                }).addOnFailureListener(fail->{
                    Toast.makeText(this, ""+fail.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}