package com.quathar.psp.threads.parking;

/**
 * <h1>Parking</h1>
 *
 * @since 2022-10-17
 * @version 1.0
 * @author Q
 */
public class Parking {

    // <<-CONSTANT->>
    private static final int MIN_CAPACITY = 1;

    // <<-FIELD->>
    private int capacity;

    // <<-CONSTRUCTOR->>
    public Parking(int capacity) {
        if (capacity <= 0)
             this.capacity = MIN_CAPACITY;
        else this.capacity = capacity;
    }

    // <<-METHODS->>
    /**
     * Allow a car to park in the parking reducing its capacity.
     *
     * @param car the car that wants to enter the parking
     */
    synchronized void enter(Car car) throws InterruptedException {
        System.out.printf("%s try to enter...%n", car);
        while (this.capacity == 0) wait();
        this.capacity--;
        System.out.printf("%s has entered the parking lot %-3s\uE111%n", car, "");
    }

    /**
     * Allow a car to leave the parking increasing its capacity.
     *
     * @param car the car that wants to leaves the parking
     */
    synchronized void leave(Car car) {
        System.out.printf("%s try to get out...%n", car);
        this.capacity++;
        System.out.printf("%s has left the parking lot %-5s\uE112%n", car, "");
        this.notifyAll();
    }

    // <<-GETTER->>
    public int getCapacity() {
        return this.capacity;
    }

}
