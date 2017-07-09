package com.coding.basic.linklist.ext;

import java.util.Hashtable;

public class LRUCache {
		/**
		 * 链表节点
		 * @author Administrator
		 *
		 */
	class CacheNode{
		CacheNode prev; //前一节点
		CacheNode next;	//后一节点
		Object value;	//值
		Object key;		//键
		CacheNode(){}
	}
	/**
	 * 构造缓存容器
	 * @param i
	 */
	public LRUCache(int i){
		currentSize = 0;
		cacheSize = i;
		nodes = new Hashtable(i);// 缓存容器
	}
	/**
	 * 移动到链表头,表示这个节点是最新使用过的
	 * @param node
	 */
	private void moveToHead(CacheNode node) {
		if(node ==first)
			return;
		if(node.prev!=null)
			node.prev.next = node.next;
		if(node.next!=null)
			node.next.prev=node.prev;
		if(last == node)
			last = node.prev;
		if(first!=null){
			node.next = first;
			first.prev = node;
		}
		first = node;
		node.prev=null;
		if(last==null)
			last=first;
	}
	/**
	 * 获取缓存中的对象,将使用过的对象先平移到链表头
	 * @param key
	 * @return
	 */
	public Object get(Object key){
		CacheNode node =  (CacheNode) nodes.get(key);
		if(node != null){
			moveToHead(node);
			return node.value;
		}else{
			return null;
		}
	}
	/**
	 * 删除链表尾部节点
	 * 表示  删除最少使用的缓存对象
	 */
	private void removeLast() {
		//链表尾不为空,则链表尾指向null, 删除连表尾(删除最少使用缓存对象)
		if(last!=null){
			if(last.prev!=null)
				last.prev.next = null;
			else
				first = null;
			last = last.prev;
		}
	}
	
	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 */
	public void put(Object key,Object value){
		CacheNode node = (CacheNode) nodes.get(key);
		
		if(node == null){
			//缓存容器是否已经超过大小
			if(currentSize>cacheSize){
				if(last != null){// 将最少使用删除
					nodes.remove(last.key);
					removeLast();
				}else{
					currentSize++;
				}
				node = new CacheNode();
			}
			node.value = value;
			node.key = key;
			//将最新使用的节点放到链表头,表示最新使用的,
			moveToHead(node);
			nodes.put(key, value);
		}
		
	}
	
	public Object remove (Object key){
		CacheNode node = (CacheNode) nodes.get(key);
		if(node!=null){
			if(node.prev!=null){
				node.prev.next=node.next;
			}
			if(node.next!=null){
				node.next.prev= node.prev;
			}
			if(last==node)
				last= node.prev;
			if(first==node)
				first = node.next;
			
		}
		return node;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	























	














































	/**
	 * 缓存大小
	 */
	private int cacheSize;
	/**
	 * 缓存容器
	 */
	private Hashtable nodes;
	/**
	 * 当前缓存对象数量
	 */
	private int currentSize;
	/**
	 * (实现双链表)链表头
	 */
	private CacheNode first;
	/**
	 * (实现双链表)链表尾
	 */
	private CacheNode last;
	
}
