package com.coderising.jvm.attr;

import java.util.ArrayList;
import java.util.List;

import com.coderising.jvm.loader.ByteCodeIterator;

public class LineNumberTable extends AttributeInfo {
    List<LineNumberItem> items = new ArrayList<LineNumberItem>();

    private static class LineNumberItem {
        int startPC;
        int lineNum;

        public int getStartPC() {
            return startPC;
        }

        public void setStartPC(int startPC) {
            this.startPC = startPC;
        }

        public int getLineNum() {
            return lineNum;
        }

        public void setLineNum(int lineNum) {
            this.lineNum = lineNum;
        }
    }

    public void addLineNumberItem(LineNumberItem item) {
        this.items.add(item);
    }

    private LineNumberTable(int attrNameIndex, int attrLen) {
        super(attrNameIndex, attrLen);

    }

    public static LineNumberTable parse(ByteCodeIterator iter) {
        int attributeNameIndex = iter.next2ByteToInt();
        int attributeLength = iter.next4ByteToInt();
        LineNumberTable lineNumberTable = new LineNumberTable(attributeNameIndex, attributeLength);
        parseLineNumberItem(lineNumberTable, iter);
        return lineNumberTable;
    }

    private static void parseLineNumberItem(LineNumberTable lineNumberTable, ByteCodeIterator iter) {
        int lineNumberTableLength = iter.next2ByteToInt();
        for (int i = 0; i < lineNumberTableLength; i++) {
            LineNumberItem lineNumberItem = new LineNumberItem();
            lineNumberItem.setStartPC(iter.next2ByteToInt());
            lineNumberItem.setLineNum(iter.next2ByteToInt());
            lineNumberTable.addLineNumberItem(lineNumberItem);
        }
    }


}