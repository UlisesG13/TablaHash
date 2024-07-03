package models;

import java.util.LinkedList;

public class HashTable<K, V> {
    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int capacity;

    public HashTable(int capacity) {
        this.capacity = capacity;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Primera función hash
    private int hash1(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    // Segunda función hash
    private int hash2(K key) {
        return Math.abs((key.hashCode() / capacity) % capacity);
    }

    public void put(K key, V value) {
        int index = hash1(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
        size++;
    }

    public V search(K key) {
        int index = hash1(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }
}
