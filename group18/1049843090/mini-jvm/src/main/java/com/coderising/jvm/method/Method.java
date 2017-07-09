package com.coderising.jvm.method;

import com.coderising.jvm.clz.ClassFile;
import com.coderising.jvm.attr.AttributeInfo;
import com.coderising.jvm.attr.CodeAttr;
import com.coderising.jvm.loader.ByteCodeIterator;


public class Method {

    private int accessFlag;
    private int nameIndex;
    private int descriptorIndex;

    private CodeAttr codeAttr;

    private ClassFile clzFile;


    public ClassFile getClzFile() {
        return clzFile;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public CodeAttr getCodeAttr() {
        return codeAttr;
    }

    public void setCodeAttr(CodeAttr code) {
        this.codeAttr = code;
    }

    private Method(ClassFile clzFile, int accessFlag, int nameIndex, int descriptorIndex) {
        this.clzFile = clzFile;
        this.accessFlag = accessFlag;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }


    public static Method parse(ClassFile clzFile, ByteCodeIterator iter) {
        Method method = new Method(clzFile, iter.next2ByteToInt(), iter.next2ByteToInt(), iter.next2ByteToInt());
        int attributeCount = iter.next2ByteToInt();
        for (int y = 0; y < attributeCount; y++) {
            int attributeNameIndex = iter.next2ByteToInt();
            iter.goBack(2);
            String attributeName = clzFile.getConstantPool().getUTF8String(attributeNameIndex);
            switch (attributeName) {
                case AttributeInfo.CODE:
                    method.setCodeAttr(CodeAttr.parse(clzFile, iter));
                    break;
                default:
                    throw new RuntimeException(attributeName + " don't parse ");
            }

        }
        return method;

    }

    @Override
    public String toString() {
        return clzFile.getConstantPool().getUTF8String(nameIndex)+":"+clzFile.getConstantPool().getUTF8String(descriptorIndex);
    }
}