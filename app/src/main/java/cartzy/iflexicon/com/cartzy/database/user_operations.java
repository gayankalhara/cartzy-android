package cartzy.iflexicon.com.cartzy.database;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import cartzy.iflexicon.com.cartzy.Models.User;

/**
 * Created by vishva ratnayake on 10/19/2016.
 */

public class user_operations {


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase,point;
    private ArrayList<User> UserList = new ArrayList<User>();
    private final CountDownLatch time = new CountDownLatch(1);


    public  void onAuthSuccess(FirebaseUser user) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        point = mDatabase.child("users").child(user.getUid());
        point.child("name").setValue(user.getDisplayName());
        point.child("email").setValue(user.getEmail().toString());
        point.child("photo").setValue(user.getPhotoUrl().toString());
    }

    public void sendRequset(User user) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        point = mDatabase.child("users").child(user.getId()).child("requests").child(currentUser.getUid());
        Map<String,Object> newFriend = new HashMap<String,Object>();
        newFriend.put("name",currentUser.getDisplayName());
        newFriend.put("email",currentUser.getEmail());
        newFriend.put("photo",currentUser.getPhotoUrl().toString());
        point.updateChildren(newFriend);
        /*point.child("name").setValue(user.getUername());
        point.child("email").setValue(user.getEmail());*/
    }

    public void removeFromRequests(User user){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        point = mDatabase.child("users").child(currentUser.getUid()).child("requests").child(user.getId());
        point.removeValue();
    }

    public void addToFriendList(User user){
        removeFromRequests(user);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //add friend to received friend's list
        point = mDatabase.child("users").child(currentUser.getUid()).child("friends").child(user.getId());
        Map<String,Object> newFriend = new HashMap<String,Object>();
        newFriend.put("name",user.getUername());
        newFriend.put("email",user.getEmail());
        newFriend.put("photo",user.getPhoto().toString());
        point.updateChildren(newFriend);

        //add friend to requested friend's list
        point = mDatabase.child("users").child(user.getId()).child("friends").child(currentUser.getUid());
        Map<String,Object> reqFriend = new HashMap<String,Object>();
        newFriend.put("name",currentUser.getDisplayName());
        newFriend.put("email",currentUser.getEmail());
        newFriend.put("photo",currentUser.getPhotoUrl().toString());
        point.updateChildren(newFriend);

    }

    public void removeFromFriends(User user){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //remove friend from current user
        point = mDatabase.child("users").child(currentUser.getUid()).child("friends").child(user.getId());
        point.removeValue();
        //remove friend from removed friend
        point = mDatabase.child("users").child(user.getId()).child("friends").child(currentUser.getUid());
        point.removeValue();
    }

    public long getFriends(){
        long count = 0 ;
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("requests");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return count;
    }



}
