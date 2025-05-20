package com.app_wallet.virtual_wallet.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;
        int height;

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public void insert(T data) {
        root = insertRec(root, data);
        size++;
    }

    private Node<T> insertRec(Node<T> node, T data) {
        if (node == null) {
            return new Node<>(data);
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertRec(node.left, data);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, data);
        } else {
            // Duplicado, reemplazar
            node.data = data;
            size--; // Compensar el incremento en insert()
        }

        // Actualizar altura
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Balancear el árbol
        int balance = getBalance(node);

        // Casos de rotación
        // Izquierda-Izquierda
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // Derecha-Derecha
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // Izquierda-Derecha
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Derecha-Izquierda
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate