package pw.qlm.inputmethodholder.compat;

import java.util.Map;

import pw.qlm.inputmethodholder.util.ReflectUtil;

/**
 * Created by Administrator on 2017/3/8.
 */

public class SystemServiceRegistryCompat {

    private static Class sClass = null;
    private static boolean foundClassSystemServiceRegistry = false;

    public static Class Class() throws ClassNotFoundException {
        if (sClass == null) {
            try {
                sClass = Class.forName("android.app.SystemServiceRegistry");
                foundClassSystemServiceRegistry = true;
            }catch (Exception e) {
                sClass = Class.forName("android.app.ContextImpl");
                foundClassSystemServiceRegistry = false;
            }
        }
        return sClass;
    }

    public static Object getSystemFetcher(String serviceName) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object serviceFetchers;
        Class aClass = Class();
        if (foundClassSystemServiceRegistry) {
            serviceFetchers = ReflectUtil.getStaticFiled(aClass, "SYSTEM_SERVICE_FETCHERS");
        } else {
            serviceFetchers = ReflectUtil.getStaticFiled(aClass, "SYSTEM_SERVICE_MAP");
        }
        if (serviceFetchers instanceof Map) {
            Map fetcherMap = (Map) serviceFetchers;
            return fetcherMap.get(serviceName);
        }
        return null;
    }

}
