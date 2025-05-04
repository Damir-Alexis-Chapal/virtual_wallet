package com.app_wallet.virtual_wallet.utils;

public class Queue<T> {
	public Node<T> front;
	public Node<T> rear;
	public int size;

	public Queue() {
		front = null;
		rear = null;
		size = 0;
	}

	public void enqueue(T data) {
		Node<T> newNode = new Node<>(data);
		if (isEmpty()) {
			front = rear = newNode;     //1
		} else {
			rear.setNext(newNode);     //  1 -> 2 -> 3
			rear = newNode; //atras = 2
		}
		size++;
	}

	public T dequeue() {
		if (isEmpty()) {
			throw new RuntimeException("Queue is empty");
		}
		T data = front.getData(); //1
		front = front.getNext(); // 2
		if (front == null) {
			rear = null;
		}
		size--;
		return data;
	}

	public T peek() {
		if (isEmpty()) {
			throw new RuntimeException("Queue is empty");
		}
		return front.getData();
	}

	public boolean isEmpty() {
		return front == null;
	}

	public int size() {
		return size;
	}

	public void clear() {
		front = rear = null;
		size = 0;
	}

	public void print() {
		Node<T> current = front;
		while (current != null) {
			System.out.print(current.getData() + " ");
			current = current.getNext();
		}
		System.out.println();
	}

	public boolean equals(Queue<T> other) {
		if (size != other.size) {
			return false;
		}

		Node<T> current1 = front;
		Node<T> current2 = other.front;

		while (current1 != null) {
			if (!current1.getData().equals(current2.getData())) {
				return false;
			}
			current1 = current1.getNext();
			current2 = current2.getNext();
		}
		return true;
	}

	@Override
	public Queue<T> clone() {
		Queue<T> clonedQueue = new Queue<>();
		Node<T> current = front;
		while (current != null) {
			clonedQueue.enqueue(current.getData());
			current = current.getNext();
		}
		return clonedQueue;
	}

	public void reverse(){
		if (!isEmpty()){
			T data = dequeue();
			reverse();
			enqueue(data);
		}
	}
}