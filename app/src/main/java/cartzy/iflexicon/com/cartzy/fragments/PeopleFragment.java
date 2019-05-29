package cartzy.iflexicon.com.cartzy.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.Console;

import cartzy.iflexicon.com.cartzy.R;


public class PeopleFragment extends Fragment {

    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public PeopleFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_people,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Friends").setIndicator("Friends"),
                FriendsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Search").setIndicator("Search"),
                SearchFriendsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Waiting").setIndicator("New"),
                NotificationFragment.class,null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if(tabId == "Search"){
                    Log.d("id","this is"+tabId);
                }
            }
        });


        return rootView;
    }

}
