package com.coderising.jvm.field;

import com.coderising.jvm.constant.ConstantPool;
import com.coderising.jvm.constant.UTF8Info;
import com.coderising.jvm.loader.ByteCodeIterator;


public class Field {
    private int accessFlag;
    private int nameIndex;
    private int descriptorIndex;


    private ConstantPool pool;

    private Field(int accessFlag, int nameIndex, int descriptorIndex, ConstantPool pool) {

        this.accessFlag = accessFlag;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.pool = pool;
    }


    public static Field parse(ConstantPool pool, ByteCodeIterator iter) {
        Field field = new Field(iter.next2ByteToInt(), iter.next2ByteToInt(), iter.next2ByteToInt(), pool);
        int attributeCount = iter.next2ByteToInt();
        for (int y = 0; y < attributeCount; y++) {
            throw new RuntimeException("Field Attribute don't parse");
        }
        return field;
    }

    @Override
    public String toString() {
        return pool.getUTF8String(nameIndex)+":"+pool.getUTF8String(descriptorIndex);
    }
}