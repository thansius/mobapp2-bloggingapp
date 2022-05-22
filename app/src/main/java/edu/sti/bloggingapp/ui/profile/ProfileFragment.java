package edu.sti.bloggingapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.sti.bloggingapp.LoginActivity;
import edu.sti.bloggingapp.R;
import edu.sti.bloggingapp.User;
import edu.sti.bloggingapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private Button btn_logout;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;


    private TextView tv_fullName, tv_email;

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv_fullName = root.findViewById(R.id.user_name);
        tv_email = root.findViewById(R.id.user_email);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");


        reference.child(User.userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile!= null){
                    String fullName = userProfile.getFirstName() + " " + userProfile.getLastName();
                    String email = userProfile.getEmail();

                    tv_fullName.setText(fullName);
                    tv_email.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(root.getContext(), ""+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btn_logout = root.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v->{
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}