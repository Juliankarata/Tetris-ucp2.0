package com.tetris;
import java.util.Random;
import java.util.Deque;
import java.util.ArrayDeque;

public class FakeRandom extends Random {
    private final Deque<Integer> queue = new ArrayDeque<>();
    public FakeRandom enqueue(int... values) {
        for (int v : values) queue.addLast(v);
        return this;
    }
    @Override
    public int nextInt(int bound) {
        if (queue.isEmpty()) return 0;
        int v = queue.removeFirst();
        if (v < 0) v = 0;
        if (v >= bound) v = v % bound;
        return v;
    }
}
