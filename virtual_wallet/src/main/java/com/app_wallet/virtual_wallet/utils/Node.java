package com.app_wallet.virtual_wallet.utils;

public class Node <T> {
    public T data;
    public Node<T> next;

    public Node(T value) {
        this.data = value;
        this.next = null;
    }

    public Node(T value, Node<T> next) {
        this.data = value;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T value) {
        this.data = data;
    }
    public Node<T> getNext() {
        return next;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
