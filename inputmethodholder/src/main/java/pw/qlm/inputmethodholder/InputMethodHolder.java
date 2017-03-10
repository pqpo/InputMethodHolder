package pw.qlm.inputmethodholder;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

import pw.qlm.inputmethodholder.hook.InputMethodManagerHook;

/**
 *
 * Created by qiulinmin on 2017/3/6.
 */
public class InputMethodHolder {

    private static final String TAG = "InputMethodHolder";

    private static InputMethodManagerHook inputMethodManagerHook;

    private static InputMethodListener mInputMethodListener;

    public static void registerListener(OnInputMethodListener listener) {
        if(mInputMethodListener != null) {
            mInputMethodListener.registerListener(listener);
        }
    }

    public static void unregisterListener(OnInputMethodListener listener) {
        if (mInputMethodListener != null) {
            mInputMethodListener.unregisterListener(listener);
        }
    }

    public static void init(final Context context) {
        if (inputMethodManagerHook != null) {
            return;
        }
        try {
            mInputMethodListener = new InputMethodListener();
            inputMethodManagerHook = new InputMethodManagerHook(context);
            inputMethodManagerHook.onHook(context.getClassLoader());
            inputMethodManagerHook.setMethodInvokeListener(new InputMethodManagerHook.MethodInvokeListener() {
                @Override
                public void onMethod(Object obj, Method method, Object result) {
                    if (mInputMethodListener != null) {
                        mInputMethodListener.onMethod(obj, method, result);
                    }
                }
            });
        } catch (Throwable throwable) {
            Log.w(TAG, "hook failed! detail:" + Log.getStackTraceString(throwable));
        }
    }

    public static void release() {
        mInputMethodListener.clear();
        inputMethodManagerHook.setMethodInvokeListener(null);
        inputMethodManagerHook = null;
        mInputMethodListener = null;
    }

}
