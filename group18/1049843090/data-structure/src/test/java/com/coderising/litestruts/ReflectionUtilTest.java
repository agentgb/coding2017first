package com.coderising.litestruts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * ReflectionUtil Test
 */
public class ReflectionUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSetParameters() {
        String clzName = "com.coderising.litestruts.LoginAction";
        try {
            Class clz = Class.forName(clzName);
            Object o = clz.newInstance();

            Map<String, String> params = new HashMap();
            params.put("name", "test");
            params.put("password", "1234");
            ReflectionUtil.setParameters(o, params);

            Field field = clz.getDeclaredField("name");
            field.setAccessible(true);
            assertEquals("test", field.get(o));
            field = clz.getDeclaredField("password");
            field.setAccessible(true);
            assertEquals("1234", field.get(o));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetParameters() {
        LoginAction loginAction = new LoginAction();
        loginAction.setName("test");
        loginAction.setPassword("123456");

        Map<String, Object> map = ReflectionUtil.getParameters(loginAction);
        assertEquals(3, map.size());

        assertEquals(null, map.get("messaage"));
        assertEquals("test", map.get("name"));
        assertEquals("123456", map.get("password"));

    }
}