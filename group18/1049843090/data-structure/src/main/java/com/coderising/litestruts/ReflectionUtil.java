package com.coderising.litestruts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ReflectionUtil
 */
public class ReflectionUtil {

    private ReflectionUtil() {

    }

    public static void setParameters(Object o, Map<String, String> params) {
        List<Method> setMethodList = getMethod(o.getClass(), "set");
        for (String name : params.keySet()) {
            for (Method method : setMethodList) {
                if (method.getName().equalsIgnoreCase("set" + name)) {
                    try {
                        method.invoke(o, params.get(name));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Map<String, Object> getParameters(Object o) {
        Map<String, Object> parameter = new HashMap<>();
        List<Method> getMethodList = getMethod(o.getClass(), "get");
        for (Method method : getMethodList) {
            try {
                parameter.put(method.getName().replaceFirst("get", "").toLowerCase(), method.invoke(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return parameter;
    }


    private static List<Method> getMethod(Class clz, String startWithName) {
        List<Method> list = new ArrayList<>();

        for (Method method : clz.getDeclaredMethods()) {
            if (method.getName().startsWith(startWithName)) {
                list.add(method);
            }
        }
        return list;
    }
}



