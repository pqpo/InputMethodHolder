package pw.qlm.softinputmethodholder;

import android.content.Context;

import java.lang.reflect.Method;

import pw.qlm.softinputmethodholder.hook.InputMethodManagerHook;

/**
 *
 * Created by qiulinmin on 2017/3/6.
 */
public class SoftInputMethodHolder {

    private static InputMethodManagerHook inputMethodManagerHook;

    private static SoftInputMethodListener mSoftInputMethodListener;

    public static void registerListener(OnInputMethodListener listener) {
        mSoftInputMethodListener.registerListener(listener);
    }

    public static void unregisterListener(OnInputMethodListener listener) {
        mSoftInputMethodListener.unregisterListener(listener);
    }

    public static void init(final Context context) {
        if (inputMethodManagerHook != null) {
            return;
        }
        try {
            mSoftInputMethodListener = new SoftInputMethodListener();
            inputMethodManagerHook = new InputMethodManagerHook(context);
            inputMethodManagerHook.onHook(context.getClassLoader());
            inputMethodManagerHook.setMethodInvokeListener(new InputMethodManagerHook.MethodInvokeListener() {
                @Override
                public void onMethod(Object obj, Method method, Object result) {
                    mSoftInputMethodListener.onMethod(obj, method, result);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void release() {
        mSoftInputMethodListener.clear();
        inputMethodManagerHook = null;
        mSoftInputMethodListener = null;
    }

}
