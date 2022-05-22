package edu.sti.bloggingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllPostViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_user, txt_title, txt_content;

    public AllPostViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_user = itemView.findViewById(R.id.txt_user);
        txt_title = itemView.findViewById(R.id.txt_title);
        txt_content = itemView.findViewById(R.id.txt_content);
    }
}
