package cartzy.iflexicon.com.cartzy.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import cartzy.iflexicon.com.cartzy.Cartzy;
import cartzy.iflexicon.com.cartzy.R;

/**
 * Created by Gayan Kalhara on 9/23/2016.
 */

public class Tools {

    private static float getApiVersion() {

        Float f = null;
        try {
            f = Float.valueOf(android.os.Build.VERSION.RELEASE.substring(0, 2));
        } catch (NumberFormatException e) {
            Log.e("", "API Error" + e.getMessage());
        }

        assert f != null;
        return f;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void systemBarLollipop(Activity act, Context context){
        if (getApiVersion() >= 5.0) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
    }

    /**
     *  Making notification bar transparent
     * */
    public static void changeStatusBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

}
