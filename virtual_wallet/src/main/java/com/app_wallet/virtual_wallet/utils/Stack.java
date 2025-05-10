package com.app_wallet.virtual_wallet.utils;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    private Node<T> peak;
    private int size;

    public Stack() {
        super();
        peak = null;
        size = 0;
    }

    public Node<T> getPeak() {
        return peak;
    }

    public void setPeak(Node<T> peak) {
        this.peak = peak;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void push(T data) {
        Node<T> aux = new Node<>(data);
        aux.setNext(peak);
        peak = aux;
        size++;
    }

    public T peek(){
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return peak.getData();
    }

    public T pop(){
        if (isEmpty()) {
            throw new RuntimeException("La pila esta vacia");
        }
        T aux;
        aux = peak.getData();
        peak = peak.getNext();
        size--;
        return aux;
    }

    public void print(){
        if (isEmpty()){
            System.out.println("La lista esta vacia");
        }else {
            Node aux = peak;
            boolean fin = false;
            while (!fin) {
                System.out.println(aux);
                aux = aux.getNext();
                if (aux == null){
                    fin = true;
                }
            }
        }
    }

    public void clean(){
        peak = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public List<T> toJavaList() {
        List<T> list = new ArrayList<>();
        Node<T> current = peak;
        while (current != null) {
            list.add(current.getData());
            current = current.getNext();
        }
        return list;
    }

    public static <T> Stack<T> fromJavaList(List<T> javaList) {
        Stack<T> stack = new Stack<>();
        for (int i = javaList.size() - 1; i >= 0; i--) {
            stack.push(javaList.get(i));
        }
        return stack;
    }
}
