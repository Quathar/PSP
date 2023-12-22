package com.quathar.psp.threads;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * <h1>Worker</h1>
 *
 * @since 2022-XX-XX
 * @version 2.0
 * @author Q
 */
public class Worker extends Thread {

    // <<-FIELDS->>
    private final Semaphore _workerSemaphore;
    private final List<Sensor> _sensors;
    private final Map<String, Semaphore> _sensorsSemaphores;
    private final Map<String, Integer> _data;

    // <<-CONSTRUCTOR->>
    public Worker(List<String> sensorsTypes) {
        _workerSemaphore   = new Semaphore(0, true);
        _sensors           = new java.util.ArrayList<>();
        _sensorsSemaphores = new Hashtable<>();
        _data              = new Hashtable<>();

        sensorsTypes.forEach(type -> _sensorsSemaphores.put(type, new Semaphore(1, true)));
        for (var sensor : _sensorsSemaphores.entrySet())
            _sensors.add(new Sensor(sensor.getKey(), _workerSemaphore, _sensorsSemaphores, _data));
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        _sensors.forEach(Thread::start);
        while (true) {
            try {
                // Lock until all the sensors have called release() on the worker semaphore
                _workerSemaphore.acquire(_sensorsSemaphores.size());
                // Simulate work
                System.out.println("The Worker program is analyzing the obtained data");
                // Print sensor data:
                for (var data : _data.entrySet())
                    System.out.printf("%s: %d%n", data.getKey(), data.getValue());
                System.out.println("The Worker program has analyzed the data successfully!");
            } catch (InterruptedException e) {
                break;
            } finally {
                // Release all sensor semaphores (using a lambda so that you get used to it)
                _sensorsSemaphores.forEach((type, sensor) -> sensor.release());
            }
        }
        _sensors.forEach(Thread::interrupt);
        _sensors.forEach(it -> {
            try {
                it.join();
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        });
    }

}
