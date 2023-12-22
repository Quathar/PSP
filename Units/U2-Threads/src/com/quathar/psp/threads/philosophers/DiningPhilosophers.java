package com.quathar.psp.threads.philosophers;

import java.util.ArrayList;
import java.util.List;

public class DiningPhilosophers {

    // <<-CONSTANT->>
    public static final int NUM_FORKS = 3;

    // <<-FIELD->>
    private final List<Philosopher> _philosophers;

    // <<-CONSTRUCTOR->>
    public DiningPhilosophers() {
        List<Fork> forks = new ArrayList<>();
        for (int fork = 0; fork < NUM_FORKS; fork++)
            forks.add(new Fork());

        _philosophers = new ArrayList<>();
        for (int num = 0; num < NUM_FORKS; num++)
            _philosophers.add(new Philosopher(this, num, forks.get(num), forks.get((num + 1) % NUM_FORKS)));
    }

    // <<-METHODS->>
    public void dine() {
        for (Philosopher phil : _philosophers)
            phil.start();
    }

    // This method is not used because threads will lock if they use it
    public synchronized void acquire(Fork fork) throws InterruptedException {
        while (!fork.available) wait();
        fork.available = false;
    }

    public synchronized void acquire(Fork leftFork, Fork rightFork) throws InterruptedException {
        while (!leftFork.available && !rightFork.available) wait();
        leftFork.available  = false;
        rightFork.available = false;
    }

    // This method is not used because threads will lock if they use it
    public synchronized void release(Fork fork) {
        fork.available = true;
        notifyAll();
    }

    public synchronized void release(Fork leftFork, Fork rightFork) {
        leftFork.available  = true;
        rightFork.available = true;
        notifyAll();
    }

}
