package cartzy.iflexicon.com.cartzy.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cartzy.iflexicon.com.cartzy.Models.User;

/**
 * Created by vishva ratnayake on 11/10/2016.
 */

public class FriendsFunctions {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public boolean hideUser(User user){

        if(user.getId() == currentUser.getUid()){
            return false;
        }
        else{
            return true;
        }

    }
}
