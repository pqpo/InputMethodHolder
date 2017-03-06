package pw.qlm.softinputmethodlistener;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pqpo on 2017/3/6.
 */
public class SoftInputMethodListener {

    private static final List<OnInputMethodListenerWrapper> mListeners = new ArrayList<>();

    public static void register(OnInputMethodListener listener) {
        synchronized (mListeners) {
            mListeners.add(new OnInputMethodListenerWrapper(listener));
        }
    }

    public static void unregister(OnInputMethodListener listener) {
        synchronized (mListeners) {
            Iterator<OnInputMethodListenerWrapper> iterator = mListeners.iterator();
            while (iterator.hasNext()) {
                OnInputMethodListenerWrapper next = iterator.next();
                if (next.methodListener == listener) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public static void hook(Context context) {
        
    }

    static void notifyListeners(int method, boolean result) {
        synchronized (mListeners) {
            for (OnInputMethodListenerWrapper listener : mListeners) {
                listener.onMethod(method, result);
            }
        }
    }


    static class OnInputMethodListenerWrapper {

        static final int METHOD_SHOW = 0x1;
        static final int METHOD_HIDDEN = 0x2;

        private OnInputMethodListener methodListener;

        OnInputMethodListenerWrapper(OnInputMethodListener methodListener) {
            this.methodListener = methodListener;
        }

        void onMethod(int method, Object result) {
            switch (method) {
                case METHOD_HIDDEN :
                    methodListener.onHidden((Boolean) result);
                    break;
                case METHOD_SHOW :
                    methodListener.onShow((Boolean) result);
                    break;
            }
        }

    }




}
