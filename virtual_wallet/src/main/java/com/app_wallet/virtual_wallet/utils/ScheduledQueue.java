package com.app_wallet.virtual_wallet.utils;

import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;

public class ScheduledQueue {
    private Node<ScheduledTransactionEntity> front;
    private Node<ScheduledTransactionEntity> rear;
    private int size;

    public ScheduledQueue() {
        front = rear = null;
        size = 0;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    /**
     * la transacción más antigua queda en front.
     */
    public void enqueue(ScheduledTransactionEntity tx) {
        Node<ScheduledTransactionEntity> newNode = new Node<>(tx);

        // 1) Si está vacía, nuevo nodo es both front & rear
        if (isEmpty()) {
            front = rear = newNode;
        } else if (tx.getScheduledDatetime()
                     .isBefore(front.getData().getScheduledDatetime())) {
            // 2) Nuevo más antiguo que el front: va al frente
            newNode.setNext(front);
            front = newNode;
        } else {
            // 3) Inserción en medio o al final
            Node<ScheduledTransactionEntity> curr = front;
            while (curr.getNext() != null
                   && !tx.getScheduledDatetime()
                          .isBefore(curr.getNext().getData().getScheduledDatetime())) {
                curr = curr.getNext();
            }
            newNode.setNext(curr.getNext());
            curr.setNext(newNode);
            if (newNode.getNext() == null) {
                rear = newNode;
            }
        }
        size++;
    }

    /**
     * Quita y devuelve la transacción más antigua
     */
    public ScheduledTransactionEntity dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        ScheduledTransactionEntity data = front.getData();
        front = front.getNext();
        if (front == null) rear = null;
        size--;
        return data;
    }

    public ScheduledTransactionEntity peek() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return front.getData();
    }

    public void clear() {
        front = rear = null;
        size = 0;
    }

}
