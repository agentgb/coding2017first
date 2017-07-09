package com.coding.basic.stack;

import javax.sound.midi.Soundbank;

/**
 * @author yang.dd
 */
public class StackUtil {


    /**
     * 假设栈中的元素是Integer, 从栈顶到栈底是 : 5,4,3,2,1 调用该方法后， 元素次序变为: 1,2,3,4,5
     * 注意：只能使用Stack的基本操作，即push,pop,peek,isEmpty， 可以使用另外一个栈来辅助
     */
    public static void reverse(Stack s) {
        Object[] temp = new Object[s.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = s.pop();
        }
        for (int i = 0; i < temp.length; i++) {
            s.push(temp[i]);
        }
    }

    /**
     * 删除栈中的某个元素 注意：只能使用Stack的基本操作，即push,pop,peek,isEmpty， 可以使用另外一个栈来辅助
     *
     * @param o
     */
    public static boolean remove(Stack s, Object o) {
        if (s.isEmpty()) {
            return false;
        }
        int originSize = s.size();
        Stack temp = new Stack();
        while (true) {
            if ((o != null && o.equals(s.peek())) || o == s.peek()) {
                s.pop();
            } else {
                temp.push(s.pop());
            }
            if (s.isEmpty()) {
                break;
            }
        }
        boolean result = temp.size()<originSize;

        while (true) {
            s.push(temp.pop());
            if (temp.isEmpty()) {
                break;
            }
        }
        return result;
    }

    /**
     * 从栈顶取得len个元素, 原来的栈中元素保持不变
     * 注意：只能使用Stack的基本操作，即push,pop,peek,isEmpty， 可以使用另外一个栈来辅助
     *
     * @param len
     * @return
     */
    public static Object[] getTop(Stack s, int len) {
        if (len <= 0 || s.isEmpty()) {
            return null;
        }
        if (len > s.size()) {
            throw new RuntimeException("Stack中元素不够");
        }
        Object[] objects = new Object[len];
        for (int i = 0; i < len; i++) {
            objects[i] = s.pop();
        }
        for (int i = len - 1; i >= 0; i--) {
            s.push(objects[i]);
        }
        return objects;
    }

    /**
     * 字符串s 可能包含这些字符：  ( ) [ ] { }, a,b,c... x,yz
     * 使用堆栈检查字符串s中的括号是不是成对出现的。
     * 例如s = "([e{d}f])" , 则该字符串中的括号是成对出现， 该方法返回true
     * 如果 s = "([b{x]y})", 则该字符串中的括号不是成对出现的， 该方法返回false;
     *
     * @param s
     * @return
     */
    public static boolean isValidPairs(String s) {
        if (s != null) {
            s = s.replaceAll("[a-z]", "");
            if (s.length() <= 0 || s.length() % 2 != 0) {
                return false;
            }
            Stack stack = new Stack();
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if (isLeftBracket(c)) {
                    stack.push(c);
                } else if (isRightBracket(c)) {
                    if (stack.isEmpty()) {
                        return false;
                    } else {
                        if (!isBracketMatch((Character) stack.pop(), c)) {
                            return false;
                        }
                    }
                }
            }
            return stack.isEmpty();

        }
        return false;
    }

    private static boolean isLeftBracket(char c) {
        if (c == '{' || c == '[' || c == '(') {
            return true;
        }
        return false;
    }

    private static boolean isRightBracket(char c) {
        if (c == ')' || c == ']' || c == '}') {
            return true;
        }
        return false;
    }

    private static boolean isBracketMatch(char left, char right) {
        if (left == '(' && right == ')') {
            return true;
        }
        if (left == '[' && right == ']') {
            return true;
        }
        if (left == '{' && right == '}') {
            return true;
        }
        return false;
    }


}