package com.quathar.psp.threads;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Placer extends Thread {

    // <<-FIELDS->>
    private final int[] _chain;
    private final Semaphore _slots;
    private final Semaphore[] _packagerSemaphore;
    private final int _packagersCount;
    private final Random _random;

    // <<-CONSTRUCTOR->>
    public Placer(int[] chain, Semaphore slots, Semaphore[] packagerSemaphore) {
        _chain = chain;
        _slots = slots;
        _packagerSemaphore = packagerSemaphore;
        _packagersCount = _packagerSemaphore.length;
        _random = new Random();
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        while (true)
            try {
                int type = _random.nextInt(1, _packagersCount + 1);
                Thread.sleep(_random.nextInt(500));
                _slots.acquire();
                int position = 0;
                while (position < _chain.length && _chain[position] != 0) position++;
                if (position < _chain.length) {
                    _chain[position] = type;
                    System.out.printf("Placing type %d on position %d. %s%n", type, position, java.util.Arrays.toString(_chain));
                    _packagerSemaphore[type - 1].release();
                }
            } catch (InterruptedException e) {
                return;
            }
    }

}
