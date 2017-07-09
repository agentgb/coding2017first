package com.coderising.jvm.attr;

import com.coderising.jvm.clz.ClassFile;
import com.coderising.jvm.constant.ConstantPool;
import com.coderising.jvm.loader.ByteCodeIterator;


public class CodeAttr extends AttributeInfo {
	private int maxStack ;
	private int maxLocals ;
	private int codeLen ;
	private String code;
	public String getCode() {
		return code;
	}

	//private ByteCodeCommand[] cmds ;
	//public ByteCodeCommand[] getCmds() {
	//	return cmds;
	//}
	private LineNumberTable lineNumTable;
	private LocalVariableTable localVarTable;
	private StackMapTable stackMapTable;
	
	private CodeAttr(int attrNameIndex, int attrLen, int maxStack, int maxLocals, int codeLen,String code /*ByteCodeCommand[] cmds*/) {
		super(attrNameIndex, attrLen);
		this.maxStack = maxStack;
		this.maxLocals = maxLocals;
		this.codeLen = codeLen;
		this.code = code;
		//this.cmds = cmds;
	}

	public void setLineNumberTable(LineNumberTable t) {
		this.lineNumTable = t;
	}

	public void setLocalVariableTable(LocalVariableTable t) {
		this.localVarTable = t;		
	}
	
	public static CodeAttr parse(ClassFile clzFile, ByteCodeIterator iter){
		int attributeNameIndex = iter.next2ByteToInt();
		int attributeLength = iter.next4ByteToInt();
		int maxStack = iter.next2ByteToInt();
		int maxLocals = iter.next2ByteToInt();
		int codeLength = iter.next4ByteToInt();
		String code = iter.nextLenByteToHexStr(codeLength);
		CodeAttr codeAttr = new CodeAttr(attributeNameIndex, attributeLength, maxStack, maxLocals, codeLength, code);
		parseExceptions(codeAttr,iter);
		parseAttributes(codeAttr,iter,clzFile.getConstantPool());
		return codeAttr;
	}
	private void setStackMapTable(StackMapTable t) {
		this.stackMapTable = t;
	}


	private static void parseExceptions(CodeAttr codeAttr, ByteCodeIterator iter){
		int exceptionTableLength = iter.next2ByteToInt();
		if(exceptionTableLength>0){
			throw new RuntimeException("exceptionTable don't parse");
		}
	}

	private static void parseAttributes(CodeAttr codeAttr,ByteCodeIterator iter,ConstantPool constantPool){
		int attributesCount = iter.next2ByteToInt();
		for (int i = 0; i < attributesCount; i++) {
			int attributeNameIndex = iter.next2ByteToInt();
			iter.goBack(2);
			String attributeName = constantPool.getUTF8String(attributeNameIndex);
			switch (attributeName){
				case AttributeInfo.STACK_MAP_TABLE:
					codeAttr.setStackMapTable(StackMapTable.parse(iter));
					break;
				case AttributeInfo.LINE_NUM_TABLE:
					codeAttr.setLineNumberTable(LineNumberTable.parse(iter));
					break;
				case AttributeInfo.LOCAL_VAR_TABLE:
					codeAttr.setLocalVariableTable(LocalVariableTable.parse(iter));
					break;
				default:
					throw new RuntimeException(attributeName+" don't parse");
			}
		}
	}

	
	
	
	
}