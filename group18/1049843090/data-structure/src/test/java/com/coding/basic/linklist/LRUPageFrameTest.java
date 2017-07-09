package com.coding.basic.linklist;

import com.sun.nio.sctp.IllegalUnbindException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 * @author yang.dd
 */
public class LRUPageFrameTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void LRUPageFrame(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("输入的参数不合法");
        LRUPageFrame frame_exception = new LRUPageFrame(-1);
    }

    @Test
    public void access() throws Exception {

        LRUPageFrame frame = new LRUPageFrame(3);
        frame.access(7);
        Assert.assertEquals("7", frame.toString());
        frame.access(7);
        Assert.assertEquals("7", frame.toString());
        frame.access(0);
        Assert.assertEquals("0,7", frame.toString());
        frame.access(7);
        Assert.assertEquals("7,0", frame.toString());
        frame.access(0);
        Assert.assertEquals("0,7", frame.toString());
        frame.access(1);
        Assert.assertEquals("1,0,7", frame.toString());
        frame.access(2);
        Assert.assertEquals("2,1,0", frame.toString());
        frame.access(0);
        Assert.assertEquals("0,2,1", frame.toString());
        frame.access(0);
        Assert.assertEquals("0,2,1", frame.toString());
        frame.access(3);
        Assert.assertEquals("3,0,2", frame.toString());
        frame.access(0);
        Assert.assertEquals("0,3,2", frame.toString());
        frame.access(4);
        Assert.assertEquals("4,0,3", frame.toString());

    }

}