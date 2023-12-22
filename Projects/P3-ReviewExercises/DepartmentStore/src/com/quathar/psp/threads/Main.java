package com.quathar.psp.threads;

/**
 * <h1>Main</h1>
 * <br>
 * Read .pdf to see more about this exercise
 *
 * @since 2022-10-07
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANTS->>
    private static final int NUM_CUSTOMERS = 300; // original value = 300
    private static final int NUM_PRODUCTS = 100;  // original value = 100
    private static final int MAX_TRIES = 100;

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws InterruptedException {
        Customer[] customers = new Customer[NUM_CUSTOMERS];
        Store store = new Store(NUM_PRODUCTS);
        Gate  gate  = new Gate();

        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            String name = String.format("Customer%03d", i);
            customers[i] = new Customer(gate, store, name, MAX_TRIES);
            customers[i].start();
        }

        for (Customer customer : customers)
            customer.join();
        System.out.println(store);
    }

}
