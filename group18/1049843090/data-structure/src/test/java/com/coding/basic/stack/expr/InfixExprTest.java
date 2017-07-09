package com.coding.basic.stack.expr;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author yang.dd
 */
public class InfixExprTest {
    @Test
    public void evaluate() throws Exception {
        {
            InfixExpr expr = new InfixExpr("2+3*4+5");
            Assert.assertEquals(19.0, expr.evaluate(), 0.001f);
        }
        {
            InfixExpr expr = new InfixExpr("3*20+12*5-40/2");
            Assert.assertEquals(100.0, expr.evaluate(), 0.001f);
        }

        {
            InfixExpr expr = new InfixExpr("3*20/2");
            Assert.assertEquals(30, expr.evaluate(), 0.001f);
        }

        {
            InfixExpr expr = new InfixExpr("20/2*3");
            Assert.assertEquals(30, expr.evaluate(), 0.001f);
        }

        {
            InfixExpr expr = new InfixExpr("10-30+50");
            Assert.assertEquals(30, expr.evaluate(), 0.001f);
        }
        //负数测试
        {
            InfixExpr expr = new InfixExpr("-2+-3*-4+-5");
            Assert.assertEquals(5.0, expr.evaluate(), 0.001f);
        }
        {
            InfixExpr expr = new InfixExpr("-3*-20+12*-5--40/-2");
            Assert.assertEquals(-20, expr.evaluate(), 0.001f);
        }

        {
            InfixExpr expr = new InfixExpr("-10-30+-50");
            Assert.assertEquals(-90, expr.evaluate(), 0.001f);
            Assert.assertEquals(-90, expr.evaluate(), 0.001f);
        }

    }

}