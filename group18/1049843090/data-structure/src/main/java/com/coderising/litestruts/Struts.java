package com.coderising.litestruts;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Struts {

    private final static Configuration config = new Configuration("struts.xml");

    /*

    0. 读取配置文件struts.xml

     1. 根据actionName找到相对应的class ， 例如LoginAction,   通过反射实例化（创建对象）
    据parameters中的数据，调用对象的setter方法， 例如parameters中的数据是
    ("name"="test" ,  "password"="1234") ,
    那就应该调用 setName和setPassword方法

    2. 通过反射调用对象的exectue 方法， 并获得返回值，例如"success"

    3. 通过反射找到对象的所有getter方法（例如 getMessage）,
    通过反射来调用， 把值和属性形成一个HashMap , 例如 {"message":  "登录成功"} ,
    放到View对象的parameters

    4. 根据struts.xml中的 <result> 配置,以及execute的返回值，  确定哪一个jsp，
    放到View对象的jsp字段中。

    */
    public static View runAction(String actionName, Map<String, String> parameters) {
        //根据actionName取出对应的配置信息
        String className = config.getClassName(actionName);
        try {
            //找到对应的Class
            // 三种方法： 1.Class.forName('包名+类名') 2. 类名.Class  3. 对象名.getClass()
            Class cls = Class.forName(className);
            //通过反射创建一个对象
            //如果是没有无参构造方法 则用 Constructor 创建
            Object object = cls.newInstance();
            ReflectionUtil.setParameters(object,parameters);
            //调用execute
            Method execute = cls.getMethod("execute");
            String result = (String) execute.invoke(object);

            //通过反射对象的getter方法 将属性名和对象的值 存入map
            Map<String, Object> map = ReflectionUtil.getParameters(object);
            String jsp = config.getResultJsp(actionName,result);
            //组装View对象
            View view = new View();
            view.setJsp(jsp);
            view.setParameters(map);
            return view;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMethod(String propertyName) {
        return "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
    }

    private static String setMethod(String propertyName) {
        return "set" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
    }

    private static String propertyName(String methodName) {
        String propertyName = methodName.substring(3);
        return propertyName.toLowerCase().charAt(0) + propertyName.substring(1);
    }

}