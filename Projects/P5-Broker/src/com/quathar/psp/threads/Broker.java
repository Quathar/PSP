package com.quathar.psp.threads;

/**
 * <h1>Broker</h1>
 * <br>
 * Broker that sell shares to clients.
 *
 * @since 2022-10-20
 * @version 1.0
 * @author Q
 */
public class Broker {

    // <<-FIELD->>
    /**
     * Number of shares available in the broker.
     */
    private int amount;

    // <<-CONSTRUCTOR->>
    /**
     * Constructor.
     *
     * @param amount max number of shares available
     */
    public Broker(int amount) {
        this.amount = amount;
    }

    // <<-METHODS->>
    /**
     * Allow the clients to buy shares from the broker.
     *
     * @param amount the amount of shares the client wants to buy
     * @return true if the client was able to buy the shares, false otherwise
     */
    synchronized boolean buy(int amount) {
        System.out.printf("Broker: there's %d available shares%n", this.amount);
        if (amount > this.amount)
            return false;
        this.amount -= amount;
        System.out.printf("Broker: now there's %d available shares%n", this.amount);
        this.notifyAll();
        return true;
    }

    /**
     * Make the thread that invokes this Broker
     * wait in case there are any shares available to sell.
     */
    synchronized void waitUntilNoShareAvailable() throws InterruptedException {
        while (this.amount > 0) wait();
    }

    /**
     * @return the number of shares available
     */
    public int getSharesAvailable() {
        return this.amount;
    }

}
