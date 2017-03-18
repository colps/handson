package com.example.misc;


import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private Node<K, V> head = null;
    private Node<K, V> tail = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<K, Node<K, V>>(capacity);
    }

    private static class Node<K, V> {
        private Node<K, V> next;
        private Node<K, V> prev;
        private K key;
        private V value;


        private Node(K key, V value) {
            this.next = next;
            this.prev = prev;
            this.key = key;
            this.value = value;
        }
    }


    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            Node<K, V> old = cache.get(key);
            remove(old);
            old.value = value;
            setHead(old);
        } else {
            Node<K, V> node = new Node<>(key, value);
            if (cache.size() >= capacity) {
                cache.remove(tail.key);
                remove(tail);
            }
            setHead(node);
            cache.put(key, node);
        }
    }

    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        } else {
            remove(node);
            setHead(node);
            return node.value;
        }
    }

    private void remove(Node<K, V> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void setHead(Node<K, V> node) {
        node.next = head;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = head;
        }
    }


}
