package com.coding.basic.linklist.ext;
public class Node{  
    public Node data;   //数据区  
    public Node next;   //指针区  
public Node (Node data,Node next){  
    this.data = data ;  
    this.next = next;  
}  
public Node(){  
}  
public void setData(Node data){  
    this.data = data;  
}  
public Node getData(){  
    return data;  
}  
public void setNext(Node next){  
        this.next=next;  
    }  
    public Node getNext(){  
        return next;  
    }
	public void print() {
		// TODO Auto-generated method stub
		
	}  
}  
