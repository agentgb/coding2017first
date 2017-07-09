package com.coderising.jvm.loader;

import com.coderising.jvm.util.Util;

public class ByteCodeIterator {

    private byte[] all;

    private int pos;

    public ByteCodeIterator(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            throw new IllegalArgumentException("byte array is null or empty");
        }
        this.all = bytes;
    }

    public String nextLenByteToHexStr(int len) {
        return Util.byteToHexString(getLenByte(len));
    }


    public String nextLenByteToStr(int len) {
        return new String(getLenByte(len));
    }


    public void goBack(int len) {
        for (int i = 0; i < len; i++) {
            pos--;
        }
    }

    private byte[] getLenByte(int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = all[pos++];
        }
        return bytes;
    }


    public int next2ByteToInt() {
        return Util.byteToInt(getLenByte(2));
    }

    public int next4ByteToInt() {
        return Util.byteToInt(getLenByte(4));
    }

    public int next1ByteToInt() {
        return Util.byteToInt(getLenByte(1));
    }

}