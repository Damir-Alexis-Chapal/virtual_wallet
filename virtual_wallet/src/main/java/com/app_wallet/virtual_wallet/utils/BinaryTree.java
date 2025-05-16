package com.app_wallet.virtual_wallet.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree<T extends Comparable<T>> {
    public TreeNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean exist(T data) {
        return existRecursive(root, data);
    }

    private boolean existRecursive(TreeNode<T> current, T data) {
        if (current == null) {
            return false;
        }
        if (data.compareTo(current.data) == 0) {
            return true;
        }
        return data.compareTo(current.data) < 0
                ? existRecursive(current.left, data)
                : existRecursive(current.right, data);
    }

    public void addRoot(T data) {
        root = addRecursive(root, data);
    }

    private TreeNode<T> addRecursive(TreeNode<T> current, T data) {
        if (current == null) {
            return new TreeNode<>(data);
        }
        int cmp = data.compareTo(current.data);
        if (cmp == 0) {
            return current;
        }
        if (cmp < 0) {
            current.left = addRecursive(current.left, data);
        } else {
            current.right = addRecursive(current.right, data);
        }

        return current;
    }

    public List<T> inOrderRout() {
        List<T> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            inOrderRecursive(node.left, result);
            result.add(node.data);
            inOrderRecursive(node.right, result);
        }
    }

    public List<T> preOrderRout() {
        List<T> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            result.add(node.data);
            preOrderRecursive(node.left, result);
            preOrderRecursive(node.right, result);
        }
    }

    public List<T> postOrderRout() {
        List<T> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            postOrderRecursive(node.left, result);
            postOrderRecursive(node.right, result);
            result.add(node.data);
        }
    }

    public int getWeight() {
        return countNodes(root);
    }

    private int countNodes(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public int getHeigth() {
        return calculateHeigth(root);
    }

    private int calculateHeigth(TreeNode<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(calculateHeigth(node.left), calculateHeigth(node.right));
    }

    public int getLevel(T data) {
        return findLevel(root, data, 0);
    }

    private int findLevel(TreeNode<T> node, T data, int level) {
        if (node == null) return -1;
        if (node.data.compareTo(data) == 0) return level;

        int levelLeft = findLevel(node.left, data, level + 1);
        if (levelLeft != -1) return levelLeft;

        return findLevel(node.right, data, level + 1);
    }

    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(TreeNode<T> node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeavesRecursive(node.left) + countLeavesRecursive(node.right);
    }

    public T getLower() {
        if (root == null) return null;
        TreeNode<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }

    public T getHigher() {
        if (root == null) return null;
        TreeNode<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    public List<T> printAmplitude() {
        List<T> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            result.add(current.data);

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return result;
    }

    public void eliminate(T data) {
        root = eliminateRecursive(root, data);
    }

    private TreeNode<T> eliminateRecursive(TreeNode<T> node, T data) {
        if (node == null) return null;

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = eliminateRecursive(node.left, data);
        } else if (cmp > 0) {
            node.right = eliminateRecursive(node.right, data);
        } else {
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;

            TreeNode<T> lower = findLower(node.right);
            node.data = lower.data;
            node.right = eliminateRecursive(node.right, lower.data);
        }

        return node;
    }

    private TreeNode<T> findLower(TreeNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void eliminateTree() {
        root = null;
    }

    public List<T> toJavaList() {
        List<T> list = new ArrayList<>();
        inOrderRecursive(root, list);
        return list;
    }

    public static <T extends Comparable<T>> BinaryTree<T> fromJavaList(List<T> javaList) {
        BinaryTree<T> tree = new BinaryTree<>();
        for (T item : javaList) {
            tree.addRoot(item);
        }
        return tree;
    }
}
