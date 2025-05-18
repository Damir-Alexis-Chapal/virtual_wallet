package com.app_wallet.virtual_wallet.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;


public class Graph<T> {
    private final Map<T, List<T>> adj = new HashMap<>();

    public void addVertex(T v) {
        adj.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(T v, T w) {
        addVertex(v);
        addVertex(w);
        adj.get(v).add(w);
    }

    public List<T> neighbors(T v) {
        return adj.getOrDefault(v, Collections.emptyList());
    }

    public MyHashMap<T,Integer> breadthFirstCounts(T start) {
        MyHashMap<T,Integer> counts = new MyHashMap<>(32);
        Queue<T> q = new Queue<>();           // tu propia cola
        q.enqueue(start);
        counts.put(start, 1);
        while (!q.isEmpty()) {
            T cur = q.dequeue();
            for (T nb : neighbors(cur)) {
                if (counts.get(nb) == null) {
                    counts.put(nb, 1);
                    q.enqueue(nb);
                } else {
                    counts.put(nb, counts.get(nb) + 1);
                }
            }
        }
        return counts;
    }
}
