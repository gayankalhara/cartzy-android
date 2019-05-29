package cartzy.iflexicon.com.cartzy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.iflexicon.intro.IntroActivity;
import com.iflexicon.intro.IntroFragment;

import cartzy.iflexicon.com.cartzy.fragments.intro.AnimationIntroFragment;

public class HelpActivity extends IntroActivity {

    @Override
    protected void initialize() {
        // Custom intro screen
        addIntroScreen(
                new AnimationIntroFragment(),
                ContextCompat.getColor(this, R.color.material_blue_grey)
        );

        addIntroScreen(
                IntroFragment.newInstance("Quickly Add Items", Color.WHITE, "Type, scan, speak or use smart suggestions based on the items you put on the list", Color.WHITE,
                        R.layout.layout_viewstub_1, IntroFragment.RESOURCE_TYPE_LAYOUT),
                ContextCompat.getColor(this, R.color.material_blue)
        );

        addIntroScreen(
                IntroFragment.newInstance("Sharing is super easy", Color.WHITE, "Share your grocery list split the work and stay in sync", Color.WHITE),
                ContextCompat.getColor(this, R.color.material_light_green)
        );

        addIntroScreen(
                IntroFragment.newInstance("Smart Iconization", Color.WHITE, "Using our smart iconization engine all of your shopping items will be assigned an icon", Color.WHITE),
                ContextCompat.getColor(this, R.color.material_green)
        );

        addIntroScreen(
                IntroFragment.newInstance("Smart Categorization", Color.WHITE, "Using our smart categorization engine all of your shopping items will be automatically categorized", Color.WHITE),
                ContextCompat.getColor(this, R.color.material_teal)
        );

        addIntroScreen(
                IntroFragment.newInstance("Let others know when you're shopping", Color.WHITE, "You can let your family/friends know when you decide to go shopping so that they can made final amendments", Color.WHITE),
                ContextCompat.getColor(this, R.color.material_purple)
        );

        addIntroScreen(
                IntroFragment.newInstance("Create your first list", Color.WHITE, "Join the smart shoppers club and enjoy daily shopping", Color.WHITE),
                ContextCompat.getColor(this, R.color.material_orange)
        );

        setShowSkipButton(true);
        setShowNextButton(true);
        setSkipButtonTextColor(Color.WHITE);
        setNextButtonBackgroundColor(Color.WHITE);
        setNextButtonIconColor(Color.WHITE);
        setProgressCircleColor(Color.WHITE);
    }

    @Override
    protected void onSkipPressed() {
        showMainActivity();
    }

    @Override
    protected void onNextPressed(int pagePosition) {

    }

    @Override
    protected void onDonePressed() {
        showMainActivity();
    }

    private void showMainActivity(){
        Intent i = new Intent(HelpActivity.this, MainActivity.class);
        startActivity(i);
        // Kill Current Activity
        finish();
    }

}
