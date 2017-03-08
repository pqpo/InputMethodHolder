package pw.qlm.sample;

import android.app.Application;
import android.content.Context;

import pw.qlm.inputmethodholder.InputMethodHolder;

/**
 * Created by Administrator on 2017/3/7.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        //为了验证 InputMethodManagerHook 中的 clearCachedService() 方法生效
        base.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 建议在此处初始化
        InputMethodHolder.init(base);
        super.attachBaseContext(base);
    }

}
