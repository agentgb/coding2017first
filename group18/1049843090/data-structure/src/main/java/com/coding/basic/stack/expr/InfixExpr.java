package com.coding.basic.stack.expr;

import com.coding.basic.List;
import com.coding.basic.array.ArrayList;
import com.coding.basic.stack.Stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yang.dd
 */
public class InfixExpr {


    private static final String EXPR_REGEX = "^(-|\\+)?(0|[1-9][0-9]*)([\\+|\\-|\\*|\\/](-|\\+)?(0|[1-9][0-9]*))+$";

    private static final String OPERAND_REGEX = "^(-|\\+)?\\d+";

    private static final String OPERATOR_REGEX = "^[\\+|\\-|\\*|\\/]";

    private String expr = null;

    private Stack<Float> operandStack;

    private Stack<String> operatorStack;

    public InfixExpr(String expr) {
        if (!Pattern.matches(EXPR_REGEX, expr)) {
            throw new IllegalArgumentException("expr error");
        }
        this.expr = expr;
    }

    public float evaluate() {
        exprParse();
        calcMulOrDiv();
        calcAddOrSub();
        float result = operandStack.pop();
        return result;
    }

    private void exprParse() {
        List<String> operatorList = new ArrayList<>();
        List<Float> operandList = new ArrayList<>();
        Pattern operandPattern = Pattern.compile(OPERAND_REGEX);
        Pattern operatorPattern = Pattern.compile(OPERATOR_REGEX);
        String str = expr;
        while (true) {
            if (str.length() > 0) {
                Matcher operandMatcher = operandPattern.matcher(str);
                if (operandMatcher.find()) {
                    operandList.add(Float.parseFloat(operandMatcher.group()));
                    str = str.replaceFirst(OPERAND_REGEX, "");
                    if (str.length() > 0) {
                        Matcher operatorMatcher = operatorPattern.matcher(str);
                        if (operatorMatcher.find()) {
                            operatorList.add(operatorMatcher.group());
                            str = str.replaceFirst(OPERATOR_REGEX, "");
                        } else {
                            throw new RuntimeException("");
                        }
                    }
                } else {
                    throw new RuntimeException();
                }
            } else {
                break;
            }
        }
        operatorStack = new Stack<>();
        for (int i = operatorList.size() - 1; i >= 0; i--) {
            operatorStack.push(operatorList.get(i));
        }
        operandStack = new Stack<>();
        for (int i = operandList.size() - 1; i >= 0; i--) {
            operandStack.push(operandList.get(i));
        }

    }

    private void calcMulOrDiv() {
        Stack<String> addOrSubOperator = new Stack<>();
        Stack<Float> operand = new Stack<>();
        while (true) {
            if (operatorStack.isEmpty()) {
                break;
            }
            if ("*".equals(operatorStack.peek()) || "/".equals(operatorStack.peek())) {
                float result = calc(operandStack.pop(), operatorStack.pop(), operandStack.pop());
                operandStack.push(result);
            } else {
                addOrSubOperator.push(operatorStack.pop());
                operand.push(operandStack.pop());
            }
        }
        while (true) {
            if (addOrSubOperator.isEmpty()) {
                break;
            }
            operatorStack.push(addOrSubOperator.pop());
            operandStack.push(operand.pop());

        }

    }

    private void calcAddOrSub() {
        while (true) {
            if (operatorStack.isEmpty()) {
                break;
            }
            float result = calc(operandStack.pop(), operatorStack.pop(), operandStack.pop());
            operandStack.push(result);
        }
    }

    private float calc(Float before, String operator, Float after) {
        switch (operator) {
            case "+":
                return before + after;
            case "-":
                return before - after;
            case "*":
                return before * after;
            case "/":
                return before / after;
            default:
                throw new RuntimeException("unknown operator: " + operator);
        }
    }

}