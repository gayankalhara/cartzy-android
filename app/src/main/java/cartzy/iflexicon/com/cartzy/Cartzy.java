package cartzy.iflexicon.com.cartzy;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Gayan Kalhara on 9/23/2016.
 */

public class Cartzy extends Application {

    private static Cartzy mInstance;

    public static synchronized Cartzy getInstance() {
        return mInstance;
    }

    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}