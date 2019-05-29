package cartzy.iflexicon.com.cartzy.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cartzy.iflexicon.com.cartzy.Adapters.SearchFriendsAdapter;
import cartzy.iflexicon.com.cartzy.Models.User;
import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.util.FriendsFunctions;


public class SearchFriendsFragment extends Fragment {
    private DatabaseReference mDatabase;
    private View view;
    private RecyclerView recyclerview;
    private SearchFriendsAdapter adapter;
    private SearchView searchView;
    private FriendsFunctions ff;
    ArrayList<User> UserList = new ArrayList<User>();

    public SearchFriendsFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_search_friends,container,false);
        setHasOptionsMenu(true);
        recyclerview = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserList.clear();
                for (DataSnapshot ds :dataSnapshot.getChildren()) {
                    User user = new User();
                    String name = (String) ds.child("name").getValue();
                    String email = (String)ds.child("email").getValue();
                    String photo = (String)ds.child("photo").getValue();
                    user.setId(ds.getKey());
                    user.setUername(name);
                    user.setEmail(email);
                    user.setPhoto( Uri.parse(photo));

                    // remove current user from the serch list
                    if(!currentUser.getUid().equals(user.getId())){
                        UserList.add(user);
                    }

                }
                adapter = new SearchFriendsAdapter(getActivity(),UserList);
                recyclerview.setAdapter(adapter);
                adapter.setOnItemClickListener(new SearchFriendsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, User obj, int position) {
                        Toast.makeText(getContext(),"friend added",Toast.LENGTH_LONG);
                    }
                });
                adapter.getItemCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_explore, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search Friends...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                  adapter.getFilter().filter(s);
                } catch (Exception e) {
                }
                return true;
            }
        });
        // Detect SearchView icon clicks
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemsVisibility(menu, searchItem, false);
            }
        });
        // Detect SearchView close
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItemsVisibility(menu, searchItem, true);
                return false;
            }
        });

        searchView.onActionViewCollapsed();
        super.onCreateOptionsMenu(menu, inflater);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Snackbar.make(view, item.getTitle() + " clicked", Snackbar.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }
}


