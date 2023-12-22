package com.quathar.psp.exam.sync.type1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Drum extends Thread {

    // <<-FIELDS->>
    private final int _maxTime;
    private final int _maxValue;
    private final Semaphore _slotMachineSemaphore;
    private final Semaphore _semaphore;
    private int _value;
    private final Random _random;

    // <<-CONSTRUCTOR->>
    public Drum(int max_time, int max_value, Semaphore slotMachineSemaphore, Semaphore semaphore) {
        _maxTime = max_time;
        _maxValue = max_value;
        _slotMachineSemaphore = slotMachineSemaphore;
        _semaphore = semaphore;
        _random = new Random();
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        try {
            while (true) {
                _semaphore.acquire();
                // Drum is spinning
                Thread.sleep(_random.nextInt(_maxTime + 1));
                _value = _random.nextInt(_maxValue + 1);
                _slotMachineSemaphore.release();
            }
        } catch (Exception e) {
            System.out.println("Drum done");
        }
    }

    // <<-GETTERS->>
    public int getValue() {
        return _value;
    }

    public Semaphore getSemaphore() {
        return _semaphore;
    }

}
