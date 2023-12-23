package com.quathar.psp.threads;

/**
 * <h1>Customer</h1>
 *
 * @since 2022-10-07
 * @version 1.0
 * @author Q
 */
public class Customer extends Thread {

    // <<-FIELDS->>
    private final Gate _gate;
    private final Store _store;
    private final String _name;
    private final int _tries;

    // <<-CONSTRUCTOR->>
    public Customer(Gate gate, Store store, String name, int maxTries) {
        _gate  = gate;
        _store = store;
        _name  = name;
        _tries = maxTries;
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        for (int i = _tries; i > 0; i--)
            // Be careful: Check when the customers leave the lock !!
            if (_gate.tryEnter()) {
                // The client is in the store.
                System.out.printf("Customer %s has entered in the store%n", this);
                if (_store.takeProduct())
                    System.out.printf("Customer %s has taken a product%n", this);
                else
                    System.out.printf("Customer %s didn't take a product%n", this);
                return;
            } else try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                return;
            }
    }

    @Override
    public String toString() {
        return _name;
    }

}
