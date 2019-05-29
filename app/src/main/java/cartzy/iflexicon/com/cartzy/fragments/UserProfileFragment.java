package cartzy.iflexicon.com.cartzy.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import cartzy.iflexicon.com.cartzy.LoginActivity;
import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.util.CircleTransform;

public class UserProfileFragment extends Fragment implements View.OnClickListener {
    private View view;

    private TextView displayName,  email;
    private ImageView profilePicture;
    private Button btDeactivate;

    //user details
    private FirebaseUser user;
    private String susername, semail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        btDeactivate = (Button) view.findViewById(R.id.deactivate);
        displayName = (TextView) view.findViewById(R.id.displayNameTxt);
        email = (TextView) view.findViewById(R.id.emailTxt);
        profilePicture = (ImageView) view.findViewById(R.id.profilePic);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
           // Log.d("profile", user.getPhotoUrl().toString());
            displayName.setText(user.getDisplayName());
            email.setText(user.getEmail());

            //set image view
            if(user.getPhotoUrl() != null) {
                Picasso.with(view.getContext()).load(user.getPhotoUrl())
                        .transform(new CircleTransform())
                        .into(profilePicture);
            }

        } else {
            Toast.makeText(view.getContext(), "oops!!something went connection", Toast.LENGTH_SHORT).show();
        }

        btDeactivate.setOnClickListener(this);

        return view;

    }

    //list for profile onclick methodes
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deactivate:
                showDeleteConfirmationDialog(view);
                break;
            default:
                return;
        }

    }

    //before delete account this dialog box appears
    private void showDeleteConfirmationDialog(View view) {
        final Context ac = view.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to delete your account? All of your data will be lost.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        FirebaseAuth.getInstance().signOut();
                        user.delete();
                        Toast.makeText(ac, "Account successfully deleted!",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ac, LoginActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}


