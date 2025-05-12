package com.app_wallet.virtual_wallet.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value, this.head);
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head = newNode;
        }
        this.size++;
    }

    public void addTail(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public T getValue(int index) {
        Node<T> node = getNode(index);
        return node != null ? node.getData() : null;
    }

    private Node<T> getNode(int index) {
        if (index >= 0 && index < size) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
            return node;
        }
        return null;
    }

    public Node<T> deleteByIndex(int index) {
        if (index < 0 || head == null || getNode(index) == null) return null;

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
        if (toDelete == tail) tail = previous;

        toDelete.setNext(null);
        return head;
    }

    public Node<T> deleteFirst() {
        if (isEmpty()) return null;

        Node<T> toDelete = head;
        head = head.getNext();
        if (head == null) tail = null;
        toDelete.setNext(null);
        return head;
    }

    public Node<T> deleteLast() {
        if (isEmpty()) return null;

        if (head.getNext() == null) {
            Node<T> toDelete = head;
            head = null;
            tail = null;
            return toDelete;
        }

        Node<T> previous = getNode(size - 2);
        Node<T> toDelete = previous.getNext();
        previous.setNext(null);
        tail = previous;
        return toDelete;
    }

    public int getIndex(T value) {
        int i = 0;
        for (Node<T> aux = head; aux != null; aux = aux.getNext()) {
            if (aux.getData().equals(value)) return i;
            i++;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }

}
