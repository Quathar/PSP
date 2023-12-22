package com.quathar.psp.exam.sync.type2;

import java.util.concurrent.Semaphore;

public class HorseRace extends Thread {

    // <<-FIELDS->>
    private final int _numHorses;
    private final int _maxTime;
    private final int _races;
    private final Semaphore _semaphore;
    private final java.util.List<Horse> _horses;

    // <<-CONSTRUCTOR->>
    public HorseRace(int numHorses, int maxTime, int races) {
        _numHorses = numHorses;
        _maxTime = maxTime;
        _races = races;
        _semaphore = new Semaphore(0, true);
        _horses = new java.util.ArrayList<>();
        for (int i = 0; i < _numHorses; i++)
            _horses.add(new Horse(maxTime, _semaphore, new Semaphore(1, true)));
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        _horses.forEach(Thread::start);
        try {
            for (int i = 0; i < _races; i++) {
                Thread.sleep(_maxTime);
                _semaphore.acquire(_numHorses);
                StringBuilder sb = new StringBuilder(String.format("Race %d results%n", i));
                for (int j = 0; j < _numHorses; j++)
                    sb.append(String.format("Horse %d took %.2f seconds%n", j, _horses.get(j).getValue()));
                System.out.print(sb);
                _horses.forEach(horse -> horse.getSemaphore().release());
            }
        } catch (InterruptedException e) {
            System.err.println("E R R O R: The horse race semaphore couldn't be acquired");
        }
        _horses.forEach(Thread::interrupt);
        _horses.forEach(it -> {
            try { it.join(); }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("HorseRace done");
    }

}
