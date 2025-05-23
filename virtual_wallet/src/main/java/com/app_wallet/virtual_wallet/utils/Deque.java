package com.app_wallet.virtual_wallet.utils;

import java.util.ArrayList;
import java.util.List;

public class Deque<T> extends Queue<T> {

	public Deque() {
		super();
		front = null;
		rear = null;
		size = 0;
	}

	public void addLast(T data) {
		enqueue(data);
	}

	public void addFirst(T data) {
		Node<T> newNode = new Node<>(data);
		if (isEmpty()) {
			front = rear = newNode;
		} else {
			newNode.setNext(front);
			front = newNode;
		}
		size++;
	}

	public T removeFirst() {
		return dequeue(); // inherited from Queue
	}

	public T removeLast() throws Exception {
		T value;
		if (!isEmpty()) {
			if (front == rear) { // Only one element in deque
				value = dequeue();
			} else {
				Node<T> current = front;
				while (current.getNext() != rear) {
					current = current.getNext();
				}

				// Set next of previous node to null
				current.setNext(null);
				value = rear.getData();
				rear = current;
				size--;
			}
		} else {
			throw new Exception("Remove from empty deque");
		}
		return value;
	}

	public List<T> toJavaList() {
		List<T> list = new ArrayList<>();
		Node<T> current = front;
		while (current != null) {
			list.add(current.getData());
			current = current.getNext();
		}
		return list;
	}

	public static <T> Deque<T> fromJavaList(List<T> javaList) {
		Deque<T> deque = new Deque<>();
		for (T item : javaList) {
			deque.addLast(item);
		}
		return deque;
	}
}