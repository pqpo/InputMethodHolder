package pw.qlm.sample;

import android.app.Application;
import android.content.Context;

import pw.qlm.softinputmethodholder.SoftInputMethodHolder;

/**
 * Created by Administrator on 2017/3/7.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        SoftInputMethodHolder.init(base);
        super.attachBaseContext(base);
    }
}
