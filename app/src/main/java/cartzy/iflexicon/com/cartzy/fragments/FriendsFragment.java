package cartzy.iflexicon.com.cartzy.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cartzy.iflexicon.com.cartzy.Adapters.FriendsAdapter;
import cartzy.iflexicon.com.cartzy.Models.User;
import cartzy.iflexicon.com.cartzy.R;


public class FriendsFragment extends Fragment {
    ArrayList<User> UserList = new ArrayList<User>();
    private DatabaseReference mDatabase;
    private View view;
    private RecyclerView recyclerview;
    private FriendsAdapter fadpter;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends,container,false);
        recyclerview = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);


        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("friends");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserList.clear(); // has to be addressed properway
                for (DataSnapshot usersSnapshot : dataSnapshot.getChildren()) {
                    User user = new User();
                    String name = (String) usersSnapshot.child("name").getValue();
                    String email = (String) usersSnapshot.child("email").getValue();
                    String photo = (String)usersSnapshot.child("photo").getValue();
                    user.setId(usersSnapshot.getKey());
                    user.setPhoto(Uri.parse(photo));
                    user.setUername(name);
                    user.setEmail(email);
                    UserList.add(user);
                }
                populateList();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void populateList() {
        fadpter = new FriendsAdapter(getActivity(),UserList);
        fadpter.setOnItemClickListener(new FriendsAdapter.ItemClick() {
            @Override
            public void onItemClick(View view, User obj, int position) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(fadpter);
    }

}
