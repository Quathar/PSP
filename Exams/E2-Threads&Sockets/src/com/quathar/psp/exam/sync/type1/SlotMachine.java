package com.quathar.psp.exam.sync.type1;

import java.util.concurrent.Semaphore;

public class SlotMachine extends Thread {

    // <<-FIELDS->>
    private final int _numDrums;
    private final int _maxTime;
    private final int _rounds;
    private final Semaphore _semaphore;
    private final java.util.List<Drum> _drums;

    // <<-CONSTRUCTOR->>
    public SlotMachine(int numDrums, int maxTime, int maxValue, int rounds) {
        _numDrums = numDrums;
        _maxTime = maxTime;
        _rounds = rounds;
        _semaphore = new Semaphore(0, true);
        _drums = new java.util.ArrayList<>();
        for (int i = 0; i < _numDrums; i++)
            _drums.add(new Drum(_maxTime, maxValue, _semaphore, new Semaphore(1, true)));
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        _drums.forEach(Thread::start);
        try {
            for (int i = 0; i < _rounds; i++) {
                // The slot machine waits the max time cause the drums
                // are spinning, then tries to acquire the permits
                Thread.sleep(_maxTime);
                _semaphore.acquire(_numDrums);
                // When it has the permits he takes the info and concatenates it
                StringBuilder sb = new StringBuilder();
                _drums.forEach(drum -> sb.append(drum.getValue()));
                System.out.printf("Slots value %s%n", sb);
                _drums.forEach(drum -> drum.getSemaphore().release());
            }
        } catch (InterruptedException e) {
            System.err.println("E R R O R: The slot machine semaphore couldn't be acquired");
        }
        _drums.forEach(Thread::interrupt);
        _drums.forEach(it -> {
            try { it.join(); }
            catch (InterruptedException e) {
                System.err.println("E R R O R: On join call");
            }
        });
        System.out.println("Slot machine done");
    }

}
