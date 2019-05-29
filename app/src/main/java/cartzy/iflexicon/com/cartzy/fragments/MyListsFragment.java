package cartzy.iflexicon.com.cartzy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cartzy.iflexicon.com.cartzy.LoginActivity;
import cartzy.iflexicon.com.cartzy.MainActivity;
import cartzy.iflexicon.com.cartzy.NewListActivity;
import cartzy.iflexicon.com.cartzy.R;

public class MyListsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_lists, container, false);
        Button button = (Button) view.findViewById(R.id.bt_checkout);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), NewListActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }




}
