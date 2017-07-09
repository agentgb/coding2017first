package com.coding.basic.linklist;

/**
 * 用双向链表实现LRU算法
 *
 * @author liuxin
 */
public class LRUPageFrame {

    private static class Node {

        Node prev;
        Node next;
        int pageNum;

        Node() {
        }
    }

    private int capacity;
    private int size;


    private Node first;// 链表头
    private Node last;// 链表尾


    public LRUPageFrame(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("输入的参数不合法");
        }
        this.capacity = capacity;

    }

    /**
     * 获取缓存中对象
     *
     * @param pageNum
     * @return
     */
    public void access(int pageNum) {
        Node exist = index(pageNum);
        if (exist != null) {
            moveToFirst(exist);
        } else {
            addFirst(pageNum);
        }

    }

    private void moveToFirst(Node node) {
        if (first.pageNum != node.pageNum) {
            Node prev = node.prev;
            Node next = node.next;

            prev.next = next;
            if (next != null) {
                next.prev = prev;
            }
            if (last.pageNum == node.pageNum) {
                last = prev;
            }

            node.prev = null;
            node.next = first;
            first.prev = node;
            first = node;

        }
    }

    private void addFirst(int pageNum) {
        if (size == capacity) {
            removeLast();
        }
        Node node = new Node();
        node.pageNum = pageNum;
        node.next = first;
        if (first == null) {
            last = node;
        } else {
            first.prev = node;
        }
        first = node;
        size++;
    }

    private void removeLast() {
        Node l = last;
        Node prev = l.prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        last = prev;
        l.prev = null;
        size--;
    }


    private Node index(int pageNum) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (node != null) {
                if (node.pageNum == pageNum) {
                    break;
                } else {
                    node = node.next;
                }
            }
        }
        return node;
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Node node = first;
        while (node != null) {
            buffer.append(node.pageNum);

            node = node.next;
            if (node != null) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

}