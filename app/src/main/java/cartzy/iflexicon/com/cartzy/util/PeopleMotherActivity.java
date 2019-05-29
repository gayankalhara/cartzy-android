package cartzy.iflexicon.com.cartzy.util;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.fragments.PeopleFragment;

public class PeopleMotherActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_mother);
        PeopleFragment fragmenttab = new PeopleFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragmenttab).commit();
    }
}
