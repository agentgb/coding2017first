package com.coding.basic.stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author yang.dd
 */
public class StackUtilTest {

    Stack stack = null;

    @Before
    public void setUp() throws Exception {
        stack = new Stack();
    }

    @After
    public void tearDown() throws Exception {
        stack = null;
    }

    @Test
    public void reverse() throws Exception {
        for (int i = 1; i < 6; i++) {
            stack.push(i);
        }
        StackUtil.reverse(stack);
        for (int i = 1; i <= stack.size(); i++) {
            assertEquals(i, stack.pop());
        }
    }

    @Test
    public void remove() throws Exception {
        assertEquals(false, StackUtil.remove(stack, 1));
        for (int i = 1; i < 6; i++) {
            stack.push(i);
        }
        assertEquals(false, StackUtil.remove(stack, null));
        assertEquals(true, StackUtil.remove(stack, 1));
        assertEquals(true, StackUtil.remove(stack, 5));
        assertEquals(false, StackUtil.remove(stack, 6));

    }

    @Test
    public void getTop() throws Exception {
        for (int i = 1; i < 6; i++) {
            stack.push(i);
        }
        Object[] objects = StackUtil.getTop(stack, 2);
        assertEquals(5, objects[0]);
        assertEquals(4, objects[1]);
        for (int i = 5; i > 0; i--) {
            assertEquals(i, stack.pop());
        }
    }

    @Test
    public void isValidPairs() throws Exception {
        String s = null;
        assertEquals(false, StackUtil.isValidPairs(s));
        s = "";
        assertEquals(false, StackUtil.isValidPairs(s));
        s = "([e{d}f])";
        assertEquals(true, StackUtil.isValidPairs(s));
        s = "([b{x]y})";
        assertEquals(false, StackUtil.isValidPairs(s));
    }

}