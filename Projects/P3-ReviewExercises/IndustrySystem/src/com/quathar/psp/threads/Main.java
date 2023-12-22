package com.quathar.psp.threads;

import java.util.List;

/**
 * <h1>Industry System Exercise</h1>
 * <br>
 * Read .pdf to see more about this exercise
 *
 * @since 2022-10-07
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANTS->>
    private static final List<String> SENSOR_TYPE = List.of("Temperature", "Moist", "Light");

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker(SENSOR_TYPE);

        worker.start();

        // Wait 10 seconds
        Thread.sleep(10000);

        worker.interrupt();
        worker.join();
    }

}
