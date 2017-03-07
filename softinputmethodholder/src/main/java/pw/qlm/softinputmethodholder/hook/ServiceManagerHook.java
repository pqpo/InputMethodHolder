package pw.qlm.softinputmethodholder.hook;

import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import pw.qlm.softinputmethodholder.compat.ServiceManagerCompat;
import pw.qlm.softinputmethodholder.util.ReflectUtil;

/**
 * Created by qiulinmin on 2017/3/7.
 */
public class ServiceManagerHook extends Hook implements InvocationHandler {

    private String mServiceName;
    private Object mProxyIInterface;

    public ServiceManagerHook(Context context, String serviceName) {
        super(context);
        mServiceName = serviceName;
    }

    public void setProxyIInterface(Object proxyIInterface) {
        this.mProxyIInterface = proxyIInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals(ServiceManagerCompat.METHOD_QUERY_LOCAL_INTERFACE)) {
            if (mProxyIInterface != null) {
                return mProxyIInterface;
            }
        }
        return method.invoke(mOriginObj, args);
    }

    @Override
    public void onHook(ClassLoader classLoader) throws Throwable {
        Map sCache = ServiceManagerCompat.sCache();
        mOriginObj = sCache.get(mServiceName);
        if (mOriginObj == null) {
            mOriginObj = ServiceManagerCompat.getService(mServiceName);
        }
        if (mOriginObj instanceof IBinder) {
            Object proxyBinder = ReflectUtil.makeProxy(classLoader, mOriginObj.getClass(), this);
            sCache.put(mServiceName, proxyBinder);
        }
    }
}
