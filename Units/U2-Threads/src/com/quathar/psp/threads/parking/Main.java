package com.quathar.psp.threads.parking;

/**
 * <h1>Parking Exercise</h1>
 * <br>
 * <p>
 *     Model a synchronized Parking class in which cars have to wait for a parking space to become available.<br/>
 *     <ul>
 *         <li>Builder (= Constructor) with maximum capacity</li>
 *         <li>Synchronized methods enter() and leave() receive a Car</li>
 *     </ul>
 *     Car extends the Thread class with an infinite loop in which:
 *     <ul>
 *         <li>Random time expected up to 1 second</li>
 *         <li>Attempts to enter the parking lot</li>
 *         <li>Random time expected up to 1 second</li>
 *         <li>Leaves the parking lot</li>
 *    </ul>
 *    Creates as many cars as places + 1.<br/>
 *    Watch the cars drive in and out for a few seconds before interrupting them.
 * </p>
 *
 * @since 2022-10-17
 * @version 1.0
 * @author Q
 */
public class Main {

    private static final int CAPACITY = 3;

    public static void main(String[] args) throws InterruptedException {
        Parking parking = new Parking(CAPACITY);
        Car[] cars = new Car[parking.getCapacity() + 1];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(
                    String.format("C%02d", i),
                    parking
            );
            cars[i].start();
        }

        Thread.sleep(10000); // 10 seconds
        for (Car car : cars) {
            car.interrupt();
            car.join();
        }
    }

}
