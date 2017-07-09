package com.coderising.jvm.loader;

import java.io.UnsupportedEncodingException;

import com.coderising.jvm.attr.AttributeInfo;
import com.coderising.jvm.attr.CodeAttr;
import com.coderising.jvm.clz.AccessFlag;
import com.coderising.jvm.clz.ClassFile;
import com.coderising.jvm.clz.ClassIndex;
import com.coderising.jvm.constant.ClassInfo;
import com.coderising.jvm.constant.ConstantPool;
import com.coderising.jvm.constant.FieldRefInfo;
import com.coderising.jvm.constant.MethodRefInfo;
import com.coderising.jvm.constant.NameAndTypeInfo;
import com.coderising.jvm.constant.NullConstantInfo;
import com.coderising.jvm.constant.StringInfo;
import com.coderising.jvm.constant.UTF8Info;
import com.coderising.jvm.field.Field;
import com.coderising.jvm.method.Method;

public class ClassFileParser {

    private static final int MAGIC_NUMBER_BYTE_LEN = 4;

    private static final String CLASS_MAGIC_NUMBER = "cafebabe";

    public ClassFile parse(byte[] codes) {
        if (codes == null || codes.length <= 0) {
            throw new IllegalArgumentException("byte array is null or empty");
        }
        ByteCodeIterator iter = new ByteCodeIterator(codes);
        String magicNumber = iter.nextLenByteToHexStr(MAGIC_NUMBER_BYTE_LEN);
        if (!CLASS_MAGIC_NUMBER.equals(magicNumber)) {
            throw new RuntimeException("not a class file");
        }
        ClassFile classFile = new ClassFile();
        classFile.setMinorVersion(iter.next2ByteToInt());
        classFile.setMajorVersion(iter.next2ByteToInt());

        classFile.setConstPool(parseConstantPool(iter));
        classFile.setAccessFlag(parseAccessFlag(iter));
        classFile.setClassIndex(parseClassIndex(iter));

        parseInterfaces(iter);
        parseFiled(classFile, iter);
        parseMethod(classFile, iter);
        return classFile;
    }

    private AccessFlag parseAccessFlag(ByteCodeIterator iter) {
        AccessFlag accessFlag = new AccessFlag(iter.next2ByteToInt());
        return accessFlag;
    }

    private ClassIndex parseClassIndex(ByteCodeIterator iter) {
        ClassIndex classIndex = new ClassIndex();
        classIndex.setThisClassIndex(iter.next2ByteToInt());
        classIndex.setSuperClassIndex(iter.next2ByteToInt());
        return classIndex;

    }

    private ConstantPool parseConstantPool(ByteCodeIterator iter) {
        ConstantPool constantPool = new ConstantPool();
        constantPool.addConstantInfo(new NullConstantInfo());
        int constantPoolSize = iter.next2ByteToInt();
        for (int i = 1; i < constantPoolSize; i++) {
            int tag = iter.next1ByteToInt();
            switch (tag) {
                case 10:
                    MethodRefInfo methodRefInfo = new MethodRefInfo(constantPool);
                    methodRefInfo.setClassInfoIndex(iter.next2ByteToInt());
                    methodRefInfo.setNameAndTypeIndex(iter.next2ByteToInt());
                    constantPool.addConstantInfo(methodRefInfo);
                    break;
                case 9:
                    FieldRefInfo fieldRefInfo = new FieldRefInfo(constantPool);
                    fieldRefInfo.setClassInfoIndex(iter.next2ByteToInt());
                    fieldRefInfo.setNameAndTypeIndex(iter.next2ByteToInt());
                    constantPool.addConstantInfo(fieldRefInfo);
                    break;
                case 8:
                    StringInfo stringInfo = new StringInfo(constantPool);
                    stringInfo.setIndex(iter.next2ByteToInt());
                    constantPool.addConstantInfo(stringInfo);
                    break;
                case 7:
                    ClassInfo classInfo = new ClassInfo(constantPool);
                    classInfo.setUtf8Index(iter.next2ByteToInt());
                    constantPool.addConstantInfo(classInfo);
                    break;
                case 1:
                    UTF8Info utf8Info = new UTF8Info(constantPool);
                    utf8Info.setLength(iter.next2ByteToInt());
                    utf8Info.setValue(iter.nextLenByteToStr(utf8Info.getLength()));
                    constantPool.addConstantInfo(utf8Info);
                    break;
                case 12:
                    NameAndTypeInfo nameAndTypeInfo = new NameAndTypeInfo(constantPool);
                    nameAndTypeInfo.setIndex1(iter.next2ByteToInt());
                    nameAndTypeInfo.setIndex2(iter.next2ByteToInt());
                    constantPool.addConstantInfo(nameAndTypeInfo);
                    break;
                default:
                    throw new RuntimeException("tag:" + tag + " not found");
            }
        }
        return constantPool;
    }

    private void parseInterfaces(ByteCodeIterator iter) {
        int interfacesCount = iter.next2ByteToInt();
        //TODO interfaces 待解析
        if(interfacesCount>0){
            throw new  RuntimeException("interfaces don't parse");
        }
    }

    private void parseFiled(ClassFile classFile, ByteCodeIterator iter) {
        int filedCount = iter.next2ByteToInt();
        for (int i = 0; i < filedCount; i++) {
            classFile.addField(Field.parse(classFile.getConstantPool(), iter));
        }
    }

    private void parseMethod(ClassFile classFile, ByteCodeIterator iter) {
        int methodCount = iter.next2ByteToInt();
        for (int i = 0; i < methodCount; i++) {
            classFile.addMethod(Method.parse(classFile, iter));
        }
    }


}