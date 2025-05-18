// src/main/java/com/app_wallet/virtual_wallet/utils/MyHashMap.java
package com.app_wallet.virtual_wallet.utils;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K,V> {
    private class Entry { K key; V value; Entry next; }
    private Entry[] buckets;

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        buckets = (Entry[]) new Object[capacity];
    }
    private int idx(K key) { return (key.hashCode() & 0x7fffffff) % buckets.length; }

    public void put(K k, V v) {
        int i = idx(k);
        for (Entry e = buckets[i]; e != null; e = e.next) {
            if (e.key.equals(k)) { e.value = v; return; }
        }
        Entry ne = new Entry(); ne.key = k; ne.value = v; ne.next = buckets[i];
        buckets[i] = ne;
    }

    public V get(K k) {
        for (Entry e = buckets[idx(k)]; e != null; e = e.next)
            if (e.key.equals(k)) return e.value;
        return null;
    }

    /** Itera sobre todas las claves del mapa */
    public List<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (Entry bucket : buckets) {
            for (Entry e = bucket; e != null; e = e.next) {
                keys.add(e.key);
            }
        }
        return keys;
    }
}
