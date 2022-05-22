package edu.sti.bloggingapp.ui.myposts;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.sti.bloggingapp.CreatePostActivity;
import edu.sti.bloggingapp.DataAccess;
import edu.sti.bloggingapp.Post;
import edu.sti.bloggingapp.PostAdapter;
import edu.sti.bloggingapp.R;
import edu.sti.bloggingapp.User;
import edu.sti.bloggingapp.databinding.FragmentMypostsBinding;

public class MyPostsFragment extends Fragment {

    private MyPostsViewModel myPostsViewModel;
    private FragmentMypostsBinding binding;
    private FloatingActionButton btn_add;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    boolean isLoading = false;
    private DataAccess da;
    private String key = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPostsViewModel =
                new ViewModelProvider(this).get(MyPostsViewModel.class);

        binding = FragmentMypostsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        swipeRefreshLayout = root.findViewById(R.id.swipe);
        recyclerView = root.findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmanager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(llmanager);

        postAdapter =new PostAdapter(root.getContext());
        recyclerView.setAdapter(postAdapter);

        da = new DataAccess();

        btn_add = root.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(v->{
            startActivity(new Intent(root.getContext(), CreatePostActivity.class));
        });

        loadData();

        return root;
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        da.getCurrentUserPosts(User.userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> postList = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    Post post = data.getValue(Post.class);
                    post.setKey(data.getKey());
                    postList.add(post);
                }

                postAdapter.setItems(postList);
                postAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}