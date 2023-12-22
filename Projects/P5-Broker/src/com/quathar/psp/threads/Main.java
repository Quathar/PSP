package com.quathar.psp.threads;

/**
 * <h1>Main</h1>
 * <br>
 * Read 'broker.pdf' to see more about this exercise
 *
 * @since 2022-10-20
 * @version 1.0
 * @author Q
 */
public class Main {

    // <<-CONSTANT->>
    /**
     * Names of the buyers
     */
    private static final String[] NAMES = {
            "María",
            "Vanesa",
            "Luis",
            "Alberto"
    };
    /**
     * Maximum number of shares in the broker.
     */
    private static final int MAX_SHARES = 20;

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws InterruptedException {
        Broker broker = new Broker(MAX_SHARES);
        java.util.List<Client> clients = new java.util.ArrayList<>();

        for (String name : NAMES)
            clients.add(new Client(name, broker));
        clients.forEach(Thread::start);

        broker.waitUntilNoShareAvailable();
        clients.forEach(Thread::interrupt);
        for (Client client : clients)       // It is set like this in order to throw the 'InterruptedException'
            client.join();                  // (junto con 'waitUntilNoShareAvailable()')
                                            // otherwise you would have to use try catch
        clients.clear();
    }

}
