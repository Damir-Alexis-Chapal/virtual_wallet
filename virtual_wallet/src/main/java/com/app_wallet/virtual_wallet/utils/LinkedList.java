package com.app_wallet.virtual_wallet.utils;

public class LinkedList<T>{

    private Node<T> head;
    private Node<T> tail;
    private int size;
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    //Method to add first
    public void add(T value) {
        Node<T> newNode = new Node<>(value, this.head);
        if(isEmpty()){
            this.head = newNode;
        }
        else{
            newNode.setNext(this.head);
            this.head = newNode;
        }
        this.size++;

    }

    //Method to verify if this list is empty
    public boolean isEmpty() {
        return this.size == 0;
    }

    //Method to add in the end of the list
    public void addTail(T value) {
        Node<T> newNode = new Node<>(value, this.tail);
        if(isEmpty()){
            this.head = newNode;
            this.tail = newNode;
        }else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }
    //Method to get value of a Node
    public T getValue( int index ) {
        Node<T> node=null;
        if(isEmpty()){
            return null;
        }
        else if(index >=0 && index < size){
            node = head;
            for(int i = 0; i < index; i++){
                node = node.getNext();
            }
        }
        if(node!=null){
            return node.getValue();
        }else{
            return null;
        }
    }
    //Method to get a Node
    private Node<T> getNode(int index) {
        if(index>=0 && index<size) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            return node;
        }
        return null;
    }

    //Method to delete a Node by index
    public Node<T> deleteByIndex(int index) {
        if (index < 0 || head == null || getNode(index) == null) {
            return null;
        }
        if (index == 0) {
            Node<T> toDelete = head;
            head = head.getNext();
            toDelete.setNext(null);
            return head;
        }

        Node<T> previous = getNode(index - 1);
        Node<T> toDelete = previous.getNext();

        if (toDelete == null) return null;

        previous.setNext(toDelete.getNext());
        toDelete.setNext(null);
        return head;
    }
    //Method to delete first node
    public Node<T> deleteFirst() {
        if(isEmpty()){
            return null;
        }
        Node<T> toDelete = head;
        head = head.getNext();
        toDelete.setNext(null);
        return head;
    }
    //Method to delete last node
    public Node<T> deleteLast() {
        if (isEmpty()) {
            return null;
        }
        if (head.getNext() == null) {
            head = null;
            tail = null;
            return null;
        }

        Node<T> previous = getNode(size - 2);
        previous.setNext(null);
        tail = previous;
        return head;
    }

    //Method to get index of a node
    public int getIndex(T value) {
        int i = 0;
        for( Node<T> aux = head ; aux!=null ; aux = aux.getNext() ) {
            if( aux.getValue().equals(value) ) {
                return i;
            }
            i++;
        }
        return -1;
    }






}
