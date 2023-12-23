package com.quathar.psp.threads;

import java.util.concurrent.Semaphore;

/**
 * <h1>Assembly Line Exercise</h1>
 * <br>
 * Read .pdf to see more about this exercise
 *
 * @since 2022-10-07
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANTS->>
    private static final int CHAIN_SIZE = 10;
    private static final int NUM_PACKAGERS = 3;

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws InterruptedException {
        // Creating objects to fill the Placer and Packagers robots
        int[] chain = new int[CHAIN_SIZE];
        Semaphore slots = new Semaphore(CHAIN_SIZE, true);
        Semaphore[] packagerSemaphore = {
                new Semaphore(0, true),
                new Semaphore(0, true),
                new Semaphore(0, true)
        };
        PackagesCounter counter = new PackagesCounter();

        // Creating Placer and Packagers robots (Threads)
        Placer placer = new Placer(chain, slots, packagerSemaphore);
        java.util.List<Packager> packagers = new java.util.ArrayList<>();
        for (int i = 0; i < NUM_PACKAGERS; i++)
            packagers.add(new Packager(i + 1, chain, slots, packagerSemaphore[i], counter));

        // Start threads
        System.out.printf("Chain's size is: %d%n", chain.length);

        placer.start();
        packagers.forEach(Thread::start);

        Thread.sleep(10000);

        // Interrupt threads
        placer.interrupt();
        packagers.forEach(Thread::interrupt);

        placer.join();
        packagers.forEach(it -> {
            try {
                it.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        packagers.clear();
    }

}
