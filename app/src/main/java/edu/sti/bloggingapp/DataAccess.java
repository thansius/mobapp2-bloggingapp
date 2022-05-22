package edu.sti.bloggingapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DataAccess {

    public DatabaseReference dbReference;
    public DatabaseReference userRef;

    public DataAccess(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbReference = db.getReference(Post.class.getSimpleName());
        userRef = db.getReference("Users");
    }

    public Task<Void> insertPost(Post post){
        return dbReference.push().setValue(post);
    }

    public Task<Void> updatePost(String key, HashMap<String, Object> hashMap){
        return dbReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> deletePost(String key){
        return dbReference.child(key).removeValue();
    }

    public Query getCurrentUserPosts(String userID){
        return dbReference.orderByChild("userID").equalTo(userID);

    }

    public Query getAllPosts(){
        return dbReference.orderByKey();
    }

    public Query getUserName(String userID){
        return userRef.orderByKey().equalTo(userID);
    }
}
