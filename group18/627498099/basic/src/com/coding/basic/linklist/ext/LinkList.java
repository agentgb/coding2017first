package com.coding.basic.linklist.ext;
public class LinkList{  
    public  Node  head; //头节点  
    public  int  count;     //记录节点的长度  
    public  LinkList(){ //构造函数用来初始化  
        head = null;  
        count = 0;  
    }  
    //节点的添加  
    public void addNode(Node data){  
        Node node = new Node(data,null);  
        Node temp = null;  
        if(head!= null){  
            temp = head;  
            while(temp.getNext()!=null){  
            	temp = temp.getNext();  
            }  
        temp.setNext(node);  
        }else{  
            head = node;  
            temp = node;  
        }  
    count++;  
    }  
    //节点的删除  
    public void delNode(Node data){  
          
            Node  front = null; //定义一个空节点，用于接收和判断被删除节点的前面还有没有节点  
            while(head!= null){  //当此链表头有数据时进入循环
                if(head.equals(data)){  //如果表头就是当前要删除的节点就跳出循环
                    break;  
                }  
                front=head;  //空节点指向头节点
                head = head.getNext();  //头结点指向下一个节点
            }  
            if(head!=null){  
                if(front==null){  
                    head = head.getNext();  
                }else{  
                    front.setNext(head.getNext());  
                }  
                count--;  
            }else{  
                count--;  
            }  
          
    }  
    //给定下标删除节点  
    public void delNode_count(int index){  
        if(index<0||index>count-1){  
            System.out.println("链表索引越界");  
        }  
        Node temp = head;   //作用同上  
        //找到要删除节点的前一个节点  
        for(int i=0;i<index-1;i++){  
            temp=temp.getNext();  
        }  
                                //找到之后 此节点的前节点和此节点的后节点进行连接  
                                //让要删除节点的前一个节点，指向被删除节点的后一个节点，也就是指向要删除节点的后后一个节点  
        temp.setNext(temp.getNext().getNext());     //把要删除的节点隔过去进行连接，也就是实现了删除节点的操作  
        //删除之后链表的长度变短了1位  
        count--;  
    }  
        //以给出的index 查找节点  
      
      
    public Node findNode(int index){  
        if(index<0||index>count-1){  
            System.out.println("链表索引越界");             
        }  
        Node temp = head;  
        for(int i=0;i<index-1;i++){  
            temp=temp.getNext();    //找到之后获取index在链表中的位置，表示链表中第index个节点的值是temp.getData;  
        }  
        return temp;    //根据需要可返回找到的数据对象，也可不返回，此处建议返回，这样可以把链表封装起来  
    }  
      
    //以对象查找节点  
    public Node findNode(Node data){  
            Node temp = head;  
            while(temp!=null){  
                if(temp.equals(data)){  
                    return temp;  
                }  
                temp.setNext(temp.getNext());  
            }  
            return null;  
    }  
    //修改  
    public void updateNode(Node data){   
        Node temp = findNode(data);  
        if(temp!=null){  
            temp.setData(data);  
        }  
    }  
    //打印  
    public void print(){  
        Node temp=head;  
        while(temp!=null){  
            temp.print();  
            //temp.print(temp.getData().toString());  
            temp=temp.getNext();  
        }  
    }  
}  