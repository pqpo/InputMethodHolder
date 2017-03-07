package pw.qlm.softinputmethodholder.hook;

import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import pw.qlm.softinputmethodholder.compat.IInputMethodManagerCompat;
import pw.qlm.softinputmethodholder.util.ReflectUtil;

/**
 *
 * Created by qiulinmin on 2017/3/7.
 */
public class InputMethodManagerHook extends Hook implements InvocationHandler {

    private MethodInvokeListener methodInvokeListener;

    public InputMethodManagerHook(Context context) {
        super(context);
    }

    public void setMethodInvokeListener(MethodInvokeListener methodInvokeListener) {
        this.methodInvokeListener = methodInvokeListener;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(mOriginObj,args);
        if (methodInvokeListener != null) {
            methodInvokeListener.onMethod(mOriginObj, method, invoke);
        }
        return invoke;
    }

    @Override
    public void onHook(ClassLoader classLoader) throws Throwable {
        //其实有其他的hook点，比如InputMethodManager的sInstance，初始化的时候可以将代理的IInputMethodManager传进构造函数
        //现在的这种方式是从获取Binder代理对象的唯一入口ServiceManager开始hook，方便以后hook其他服务
        ServiceManagerHook serviceManagerHook = new ServiceManagerHook(mContext, Context.INPUT_METHOD_SERVICE);
        serviceManagerHook.onHook(classLoader);
        Object originBinder = serviceManagerHook.getOriginObj();
        if (originBinder instanceof IBinder) {
            mOriginObj = IInputMethodManagerCompat.asInterface((IBinder) originBinder);
            Object proxyInputMethodInterface = ReflectUtil.makeProxy(classLoader, mOriginObj.getClass(), this);
            serviceManagerHook.setProxyIInterface(proxyInputMethodInterface);
        }
    }

    public interface MethodInvokeListener {
        void onMethod(Object obj, Method method, Object result);
    }

}
