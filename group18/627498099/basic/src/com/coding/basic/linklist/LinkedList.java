package com.coding.basic.linklist;

import com.coding.basic.Iterator;
import com.coding.basic.List;

public class LinkedList implements List {
	
	private Node head;
	private Node last;
	private Node preNode;
	private Node afterNode;
	private int size=0;
	private int pos = 0; // 节点的位置
	

	
	public void add(Object o){
		Node node = new Node(o);
		//如果是第一次添加
		if(head==null){
			head = node;
			last = head;
			size++;
		}else{// 如果是第二次添加
			last.next = node;
			last = node;
			size++;
		}
	}
	public void add2(int index , Object o){
		Node node = new Node(o);
		Node current=head;
		Node previous = head;
		while(pos!=index){
			//前一个节点引用当前节点;
			previous = current;
			//当前节点引用下一个节点;
			current = current.next;
			pos++;
		}
		node.next=current;
		previous.next=node;
		pos=0;
		size++;
	}
	
	public void add(int index,Object o){
		
		Node pre = head;
		
		for(int i =0;i<index;i++){
			pre = pre.next;
			
		}
		Node post = pre.next;
		Node node =  new Node(o);
		
		pre.next = node;
		node.next = post;
		
	}
	
	public Object get(int index){
		Node node = head;
		
		for(int i =0;i<index;i++){
			node = node.next;
			
		}
		
		return node;
	}
	public Object remove(int index){
		Node pre = head;
		
		for(int i =0;i<index-1;i++){
			pre = pre.next;
		}
		Node current = pre.next;
		Node post = current.next;
		pre.next=post;
		return current;
	}
	
	public int size(){
		return size;
	}
	
	public void addFirst(Object o){
		Node node = new Node(o);
		node.next=head;
		head=node;
		size++;
	}
	public void addLast(Object o){
		
	}
	public Object removeFirst(){
		Node tempNode = head;
		head=tempNode.next;
		size--;
		return tempNode;
	}
	public Object removeLast(){
		Node tempNode = last;
		last = null;
		size--;
		return tempNode;
	}
	public Iterator iterator(){
		return null;
	}
	
	
	private static  class Node{
		Object data;
		Node next;
		Node(Object data) {
			this.data = data;
		}
		public void display(){
			System.out.println(data+" ");
		}
		
	}
	
	/**
	 * 把该链表逆置
	 * 例如链表为 3->7->10 , 逆置后变为  10->7->3
	 */
	public  void reverse(){		
		
	}
	
	/**
	 * 删除一个单链表的前半部分
	 * 例如：list = 2->5->7->8 , 删除以后的值为 7->8
	 * 如果list = 2->5->7->8->10 ,删除以后的值为7,8,10

	 */
	public  void removeFirstHalf(){
		
	}
	
	/**
	 * 从第i个元素开始， 删除length 个元素 ， 注意i从0开始
	 * @param i
	 * @param length
	 */
	public  void remove(int i, int length){
		
	}
	/**
	 * 假定当前链表和listB均包含已升序排列的整数
	 * 从当前链表中取出那些listB所指定的元素
	 * 例如当前链表 = 11->101->201->301->401->501->601->701
	 * listB = 1->3->4->6
	 * 返回的结果应该是[101,301,401,601]  
	 * @param list
	 */
	public  int[] getElements(LinkedList list){
		return null;
	}
	
	/**
	 * 已知链表中的元素以值递增有序排列，并以单链表作存储结构。
	 * 从当前链表中中删除在listB中出现的元素 

	 * @param list
	 */
	
	public  void subtract(LinkedList list){
		
	}
	
	/**
	 * 已知当前链表中的元素以值递增有序排列，并以单链表作存储结构。
	 * 删除表中所有值相同的多余元素（使得操作后的线性表中所有元素的值均不相同）
	 */
	public  void removeDuplicateValues(){
		
	}
	
	/**
	 * 已知链表中的元素以值递增有序排列，并以单链表作存储结构。
	 * 试写一高效的算法，删除表中所有值大于min且小于max的元素（若表中存在这样的元素）
	 * @param min
	 * @param max
	 */
	public  void removeRange(int min, int max){
		
	}
	
	/**
	 * 假设当前链表和参数list指定的链表均以元素依值递增有序排列（同一表中的元素值各不相同）
	 * 现要求生成新链表C，其元素为当前链表和list中元素的交集，且表C中的元素有依值递增有序排列
	 * @param list
	 */
	public  LinkedList intersection( LinkedList list){
		return null;
	}
}
