package edu.sti.bloggingapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Post> postList = new ArrayList<>();

    public PostAdapter(Context ctx){
        this.context = ctx;
    }

    public void setItems(ArrayList<Post> post){
        this.postList.addAll(post);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post p = null;
        this.onBindViewHolder(holder, position, p);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position, Post p){
        PostViewHolder pvh = (PostViewHolder) holder;
        Post post = postList.get(position);
        pvh.txt_user.setText(post.getUserID());
        pvh.txt_title.setText(post.getTitle());
        pvh.txt_content.setText(post.getContent());
        pvh.txt_options.setOnClickListener(v->{
            Context wrapper = new ContextThemeWrapper(context, R.style.custom_PopupMenu);
            PopupMenu popupMenu = new PopupMenu(wrapper, pvh.txt_options);
            popupMenu.inflate(R.menu.options_menu);
            popupMenu.setOnMenuItemClickListener(item->{
                switch (item.getItemId()){
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, CreatePostActivity.class);
                        intent.putExtra("EDIT", post);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        new AlertDialog.Builder(context)
                                .setMessage("Are you sure to delete this post?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DataAccess da = new DataAccess();
                                        da.deletePost(post.getKey()).addOnSuccessListener(suc-> {
                                            Toast.makeText(context, "Employee record is deleted!", Toast.LENGTH_LONG).show();
                                            notifyItemRemoved(position);
                                            postList.remove(post);
                                        }).addOnFailureListener(fail -> {
                                            Toast.makeText(context, "" + fail.getMessage() , Toast.LENGTH_LONG).show();
                                        });
                                    }
                                }).setNegativeButton("Cancel", null).show();

                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
