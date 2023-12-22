package com.quathar.psp.threads;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Packager extends Thread {

    // <<-FIELDS->>
    private final int _type;
    private final int[] _chain;
    private final Semaphore _placerSemaphore;
    private final Semaphore _packagerSemaphore;
    private final PackagesCounter _packagesCounter;
    private final Random _random;

    // <<-CONSTRUCTOR->>
    public Packager(int type, int[] chain, Semaphore placerSemaphore, Semaphore packagerSemaphore, PackagesCounter packagesCounter) {
        _type = type;
        _chain = chain;
        _placerSemaphore = placerSemaphore;
        _packagerSemaphore = packagerSemaphore;
        _packagesCounter = packagesCounter;
        _random = new Random();
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        while (true)
            try {
                _packagerSemaphore.acquire();
                int position = 0;
                while (position < _chain.length && _chain[position] != _type) position++;
                if (position < _chain.length) {
                    System.out.printf("Type %s robot is packaging item at position %d%n", _type, position);
                    _chain[position] = 0;
                    _placerSemaphore.release();
                    Thread.sleep(_random.nextInt(500, 1000));
                    _packagesCounter.increase();
                }
            } catch (InterruptedException e) {
                return;
            }
    }

}
