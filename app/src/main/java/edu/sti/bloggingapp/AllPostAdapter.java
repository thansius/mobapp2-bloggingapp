package edu.sti.bloggingapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AllPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<AllPost> postList = new ArrayList<>();

    public AllPostAdapter(Context ctx){
        this.context = ctx;
    }

    public void setItems(ArrayList<AllPost> post){
        this.postList.addAll(post);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_all_items, parent, false);
        return new AllPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AllPostViewHolder pvh = (AllPostViewHolder) holder;
        AllPost post = postList.get(position);
        pvh.txt_user.setText(post.getFullName());
        Log.e(TAG, "onBindViewHolder: " + post.getFullName());
        pvh.txt_title.setText(post.getTitle());
        pvh.txt_content.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
