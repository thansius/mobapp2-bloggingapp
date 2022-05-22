package edu.sti.bloggingapp.ui.newsfeed;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.sti.bloggingapp.AllPost;
import edu.sti.bloggingapp.AllPostAdapter;
import edu.sti.bloggingapp.DataAccess;
import edu.sti.bloggingapp.Post;
import edu.sti.bloggingapp.PostAdapter;
import edu.sti.bloggingapp.R;
import edu.sti.bloggingapp.databinding.FragmentNewsfeedBinding;

public class NewsFeedFragment extends Fragment {

    private NewsFeedViewModel newsFeedViewModel;
    private FragmentNewsfeedBinding binding;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AllPostAdapter postAdapter;
    public static String fullName;
    private DataAccess da;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsFeedViewModel =
                new ViewModelProvider(this).get(NewsFeedViewModel.class);

        binding = FragmentNewsfeedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        swipeRefreshLayout = root.findViewById(R.id.swipe);
        recyclerView = root.findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmanager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(llmanager);

        postAdapter =new AllPostAdapter(root.getContext());
        recyclerView.setAdapter(postAdapter);

        da = new DataAccess();

        loadData();

        return root;
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        da.getAllPosts().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<AllPost> postList = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    AllPost post = new AllPost();
                    post.setUserID(data.child("userID").getValue().toString());
                    post.setTitle(data.child("title").getValue().toString());
                    post.setContent(data.child("content").getValue().toString());
                    Log.e(TAG, "onDataChange: " + post.getUserID());

                    getUserName(post.getUserID(), new MyCallback() {
                        @Override
                        public void onCallback(String value) {
                            NewsFeedFragment.fullName = value;
                            post.setFullName(value);
                        }
                    });

                    Log.e(TAG, "fullname: " + NewsFeedFragment.fullName);

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

    public void getUserName(String userID, MyCallback callback){
        da.getUserName(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ln="",fn="";
                for(DataSnapshot data: snapshot.getChildren()){
                    Log.e(TAG, "fname: " + data.child("firstName").getValue().toString());
                    Log.e(TAG, "fname: " + data.child("lastName").getValue().toString());
                    fn = data.child("firstName").getValue().toString();
                    ln = data.child("lastName").getValue().toString();
                }
                callback.onCallback(fn + " " + ln);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface MyCallback{
        void onCallback(String value);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}