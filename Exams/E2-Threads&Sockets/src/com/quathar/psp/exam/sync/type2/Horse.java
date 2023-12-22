package com.quathar.psp.exam.sync.type2;

import java.util.concurrent.Semaphore;

public class Horse extends Thread {

    // <<-FIELDS->>
    private final int _maxTime;
    private final Semaphore _horseRaceSemaphore;
    private final Semaphore _semaphore;
    private float _value;

    // <<-CONSTRUCTOR->>
    public Horse(int maxTime, Semaphore horseRaceSemaphore, Semaphore semaphore) {
        _maxTime = maxTime;
        _horseRaceSemaphore = horseRaceSemaphore;
        _semaphore = semaphore;
    }

    // <<-METHODS->>
    @Override
    public void run() {
        try {
            while (true) {
                _semaphore.acquire();
                long sleep = 1000 + (long) (Math.random() * _maxTime);
                Thread.sleep(sleep);
                // It is divided to show the value in seconds (no miliseconds)
                _value = sleep / (float) 1000;
                _horseRaceSemaphore.release();
            }
        } catch (InterruptedException e) {
            System.out.println("Horse done");
        }
    }

    public float getValue() {
        return _value;
    }

    public Semaphore getSemaphore() {
        return _semaphore;
    }

}
