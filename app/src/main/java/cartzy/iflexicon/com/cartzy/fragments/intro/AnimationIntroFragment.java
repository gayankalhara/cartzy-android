package cartzy.iflexicon.com.cartzy.fragments.intro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.iflexicon.intro.BaseIntroFragment;
import com.iflexicon.intro.CustomAnimationPageTransformerDelegate;

import cartzy.iflexicon.com.cartzy.R;

/**
 * Created by gayankalhara on 5/10/16.
 */
public class AnimationIntroFragment extends BaseIntroFragment implements CustomAnimationPageTransformerDelegate {

    private View mLogo;

    @Override
    protected String getTitle() {
        return "Welcome to Cartzy";
    }

    @Override
    protected int getTitleColor() {
        return Color.WHITE;
    }

    @Override
    protected String getDescription() {
        return "Cartzy is your smart shopping application. Get ready to revolutionize your shopping experience.";
    }

    @Override
    protected int getDescriptionColor() {
        return Color.WHITE;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_intro;
    }

    @Override
    protected int getDrawableId() {
        return 0;
    }

    @Override
    protected int getResourceType() {
        return RESOURCE_TYPE_LAYOUT;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // We MUST call the super method in order for
        // the library to set up the intro page
        super.onViewCreated(view, savedInstanceState);

        mLogo = view.findViewById(R.id.logo);
    }

    @Override
    public void onPageSelected() {
//        // Animate a jump
//        ObjectAnimator animation = ObjectAnimator.ofFloat(mLogo, "translationY",
//                -20f, 20f, -20f, 20f, -20f, 20f, 0f);
//        animation.setDuration(1000);
//        animation.start();
    }

    @Override
    public void onPageScrolled(View page, float position) {
        int pageWidth = page.getWidth();
        float absPosition = Math.abs(position);
        float pageWidthTimesPosition = pageWidth * absPosition;

        mLogo.setTranslationX(pageWidthTimesPosition / 4f);
        mLogo.setTranslationY(-pageWidthTimesPosition / 2f);
        mLogo.setRotation(-35 * absPosition);
        mLogo.setAlpha(1f - absPosition);
    }

    @Override
    public void onPageInvisible(float position) {}
}