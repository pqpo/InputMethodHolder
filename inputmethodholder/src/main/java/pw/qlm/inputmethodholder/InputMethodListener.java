package pw.qlm.inputmethodholder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qiulinmin on 2017/3/7.
 */
public class InputMethodListener {

    private final List<OnInputMethodListenerWrapper> mListeners = new ArrayList<>();

    public void registerListener(OnInputMethodListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (mListeners) {
            boolean contain = false;
            for (OnInputMethodListenerWrapper listenerWrapper : mListeners) {
                if (listenerWrapper.methodListener == listener) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                mListeners.add(new OnInputMethodListenerWrapper(listener));
            }
        }
    }

    public void unregisterListener(OnInputMethodListener listener) {
        if (listener == null) {
            return;
        }
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

    public void onMethod(Object obj, Method method, Object result) {
        String name = method.getName();
        if ("showSoftInput".equals(name)) {
            notifyListeners(OnInputMethodListenerWrapper.METHOD_SHOW, (Boolean) result);
        } else if ("hideSoftInput".equals(name)){
            // 主动调用hideSoftInputFromWindow会走到该方法，通过系统按钮关闭软键盘的时候未调用，因为键盘在一个独立的进程中，我们hook的只是本进程的一个binder代理
            notifyListeners(OnInputMethodListenerWrapper.METHOD_HIDDEN, (Boolean) result);
        }
    }

    private void notifyListeners(int method, boolean result) {
        synchronized (mListeners) {
            for (OnInputMethodListenerWrapper listener : mListeners) {
                listener.onMethod(method, result);
            }
        }
    }

    public void clear() {
        synchronized (mListeners) {
            mListeners.clear();
        }
    }

    private static class OnInputMethodListenerWrapper {

        static final int METHOD_SHOW = 0x1;
        static final int METHOD_HIDDEN = 0x2;

        private OnInputMethodListener methodListener;

        OnInputMethodListenerWrapper(OnInputMethodListener methodListener) {
            this.methodListener = methodListener;
        }

        void onMethod(int method, Object result) {
            switch (method) {
                case METHOD_HIDDEN :
                    methodListener.onHide((Boolean) result);
                    break;
                case METHOD_SHOW :
                    methodListener.onShow((Boolean) result);
                    break;
            }
        }

    }

}
