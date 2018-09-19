package com.sasha.adorufu.misc;

public class Pair<K, V> {
    private final K first;
    private final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public static <K, V> Pair of(K a, V b) {
        return new Pair(a, b);
    }

    public K getKey() {
        return this.first;
    }

    public V getValue() {
        return this.second;
    }

    public String mkString(String separator) {
        return String.format("%s%s%s", this.first, separator, this.second);
    }
}