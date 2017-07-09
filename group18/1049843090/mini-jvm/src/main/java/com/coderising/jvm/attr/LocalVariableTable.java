package com.coderising.jvm.attr;


import java.util.ArrayList;
import java.util.List;

import com.coderising.jvm.constant.ConstantPool;

import com.coderising.jvm.loader.ByteCodeIterator;

public class LocalVariableTable extends AttributeInfo {

    List<LocalVariableItem> items = new ArrayList<LocalVariableItem>();

    private LocalVariableTable(int attrNameIndex, int attrLen) {
        super(attrNameIndex, attrLen);
    }

    public static LocalVariableTable parse(ByteCodeIterator iter) {
        int attributeNameIndex = iter.next2ByteToInt();
        int attributeLength = iter.next4ByteToInt();
        LocalVariableTable localVariableTable = new LocalVariableTable(attributeNameIndex, attributeLength);
        parseLocalVariableItem(localVariableTable, iter);
        return localVariableTable;
    }

    private void addLocalVariableItem(LocalVariableItem item) {
        this.items.add(item);
    }

    private static void parseLocalVariableItem(LocalVariableTable localVariableTable, ByteCodeIterator iter) {
        int localVariableTableLength = iter.next2ByteToInt();
        for (int i = 0; i < localVariableTableLength; i++) {
            LocalVariableItem localVariableItem = new LocalVariableItem();
            localVariableItem.setStartPC(iter.next2ByteToInt());
            localVariableItem.setLength(iter.next2ByteToInt());
            localVariableItem.setNameIndex(iter.next2ByteToInt());
            localVariableItem.setDescIndex(iter.next2ByteToInt());
            localVariableItem.setIndex(iter.next2ByteToInt());
            localVariableTable.addLocalVariableItem(localVariableItem);
        }
    }


}