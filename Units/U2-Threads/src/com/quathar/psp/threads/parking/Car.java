package com.quathar.psp.threads.parking;

/**
 * <h1>Car</h1>
 * <br>
 * Class that return the car object which try to enter and leave the parking constantly<br>
 * <br>
 * All the execution is done within a determined thread.
 *
 * @since 2022-10-17
 * @version 1.0
 * @author Q
 */
public class Car extends Thread {

    // <<-FIELDS->>
    private final String name;
    private final Parking parking;

    // <<-CONSTRUCTOR->>
    public Car(String name, Parking parking) {
        this.name    = name;
        this.parking = parking;
    }

    // <<-METHODS->>
    @Override
    public void run() {
        System.out.println(getName());
        while (true) {
            try {
                // Random time up to 1 second
                Thread.sleep((int) (Math.random() * 1000));
                this.parking.enter(this);

                // Random time up to 1 second
                Thread.sleep((int) (Math.random() * 1000));
                this.parking.leave(this);
                if (isInterrupted()) break;
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

}
