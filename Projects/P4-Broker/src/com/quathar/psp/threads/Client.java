package com.quathar.psp.threads;

/**
 * <h1>Client</h1>
 * <br>
 * Clients who buy shares of a broker in packages.
 *
 * @since 2022-10-20
 * @version 1.0
 * @author Q
 */
public class Client extends Thread {

    // <<-CONSTANTS->>
    /**
     * Maximum time (milliseconds) the thread has to sleep.
     */
    private static final int MAX_MS = 2000;
    /**
     * Minimum time (milliseconds) the thread has to sleep.
     */
    private static final int MIN_MS = 1000;
    /**
     * Maximum shares the client can buy at one time.
     */
    private static final int MAX_SharesToBuy = 5;
    /**
     * Minimum shares the client can buy at one time.
     */
    private static final int MIN_SharesToBuy = 1;

    // <<-FIELDS->>
    /**
     * The client name.
     */
    private final String name;
    /**
     * The broker where the shares will be purchased.
     */
    private final Broker broker;

    // <<-CONSTRUCTOR->>
    public Client(String name, Broker broker) {
        this.name   = name;
        this.broker = broker;
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        int sharesToBuy;
        int counter = 0;
        while (true) {
            try {
                // Formula for random number with limits 'Math.random() * (max - min + 1) + min'
                Thread.sleep((int) ((Math.random() * (MAX_MS - MIN_MS + 1) + MIN_MS)));
                // Final thought to increase the probability of purchase
                sharesToBuy = this.broker.getSharesAvailable() > 5 ?
                                (int) ((Math.random() * (MAX_SharesToBuy - MIN_SharesToBuy + 1) + MIN_SharesToBuy)):
                        this.broker.getSharesAvailable();
//                sharesToBuy = (int) ((Math.random() * (MAX_SharesToBuy - MIN_SharesToBuy + 1) + MIN_SharesToBuy));
                System.out.printf("Client %s about to buy %d shares%n", this.name, sharesToBuy);
                if (this.broker.buy(sharesToBuy)) {
                    System.out.printf("Client %s bought %d shares%n", this.name, sharesToBuy);
                    counter += sharesToBuy;
                } else System.out.printf("Client %s couldn't buy %d shares%n", this.name, sharesToBuy);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.printf("Client %s bought %d shares in total%n", this.name, counter);
    }

}
