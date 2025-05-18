// CategoryGraph.java
package com.app_wallet.virtual_wallet.utils;

import com.app_wallet.virtual_wallet.model.Category;

public class CategoryGraph extends Graph<Category> {

    @Override
    public MyHashMap<Category,Integer> breadthFirstCounts(Category start) {
        MyHashMap<Category,Integer> counts = new MyHashMap<>(16);  // capacidad inicial
        Queue<Category> q = new Queue<>();
        q.enqueue(start);
        counts.put(start, 1);

        while (!q.isEmpty()) {
            Category cur = q.dequeue();
            for (Category nb : neighbors(cur)) {
                Integer prev = counts.get(nb);
                if (prev == null) {
                    counts.put(nb, 1);
                    q.enqueue(nb);
                } else {
                    counts.put(nb, prev + 1);
                }
            }
        }
        return counts;
    }

}
