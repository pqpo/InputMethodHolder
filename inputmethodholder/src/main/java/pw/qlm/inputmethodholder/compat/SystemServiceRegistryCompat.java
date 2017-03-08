package pw.qlm.inputmethodholder.compat;

import java.util.Map;

import pw.qlm.inputmethodholder.util.ReflectUtil;

/**
 * Created by Administrator on 2017/3/8.
 */

public class SystemServiceRegistryCompat {

    private static Class sClass = null;

    public static Class Class() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.app.SystemServiceRegistry");
        }
        return sClass;
    }

    public static Object getSystemFetcher(String serviceName) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object serviceFetchers = ReflectUtil.getStaticFiled(Class(), "SYSTEM_SERVICE_FETCHERS");
        if (serviceFetchers instanceof Map) {
            Map fetcherMap = (Map) serviceFetchers;
            return fetcherMap.get(serviceName);
        }
        return null;
    }

}
