package com.quathar.psp.threads;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * <h1>Sensor</h1>
 *
 * @since 2022-XX-XX
 * @version 1.0
 * @author Q
 */
public class Sensor extends Thread {

    // <<-FIELDS->>
    private final String _type;
    private final Semaphore _workerSemaphore;
    private final Map<String, Semaphore> _sensorsSemaphores;
    private final Map<String, Integer> _data;
    private final Random _random;

    // <<-CONSTRUCTOR->>
    public Sensor(
            String type,
            Semaphore workerSemaphore,
            Map<String, Semaphore> sensorsSemaphores,
            Map<String, Integer>   data
    ) {
        _type              = type;
        _workerSemaphore   = workerSemaphore;
        _sensorsSemaphores = sensorsSemaphores;
        _data              = data;
        _random            = new Random();
    }

    // <<-METHODS->>
    @Override
    public void run() {
        while (true)
            try {
                // Lock until the worker releases my semaphore
                _sensorsSemaphores.get(_type)
                                  .acquire();
                // Simulate work
                System.out.printf("Performing '%s' measurement%n", this);
                Thread.sleep(_random.nextInt(500));
                _data.put(_type, _random.nextInt(100));
                System.out.printf("'%s' measurement performed%n", this);
            } catch (InterruptedException e) {
                System.out.printf("'%s' sensor is done%n", this);
                return;
            } finally {
                // Release the worker semaphore. The worker is trying to acquire as many permits as there are
                _workerSemaphore.release();
            }
    }

    @Override
    public String toString() {
        return _type;
    }

}
