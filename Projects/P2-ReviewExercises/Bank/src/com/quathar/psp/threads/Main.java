package com.quathar.psp.threads;

/**
 * <p>
 *     Un banco necesita controlar el acceso a cuentas bancarias y para ello desea
 *     hacer un programa de prueba que permita lanzar procesos que ingresen y retiren
 *     dinero a la vez y comprobar así si el resultado final es el esperado.
 *     Se parte de una cuenta con 100 euros y se pueden tener procesos que ingresen
 *     100 euros, 50 o 20. También se pueden tener procesos que retiran 100, 50 o 20
 *     euros.
 *     <br/><br/>
 *     Se desean tener los siguientes procesos:
 * </p>
 * <ul>
 *     <li>40 procesos que ingresan 100</li>
 *     <li>20 procesos que ingresan 50</li>
 *     <li>60 procesos que ingresen 20</li>
 * </ul>
 * <p>De la misma manera se desean lo siguientes procesos que retiran cantidades.</p>
 * <ul>
 *     <li>40 procesos que retiran 100</li>
 *     <li>20 procesos que retiran 50</li>
 *     <li>60 procesos que retiran 20</li>
 * </ul>
 *
 * @since 2022-10-07
 * @author Q
 * @version 1.0
 */
public class Main {

    // <<-CONSTANTS->>
    private static final int N100C = 40;
    private static final int N50C = 20;
    private static final int N20C = 60;

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(100);
        java.util.List<Thread> threads = new java.util.ArrayList<>();

        for (int i = 0; i < N100C * 2; i++)
            if (i < N100C)
                 threads.add(new Client(account, true,  100));
            else threads.add(new Client(account, false, 100));
        for (int i = 0; i < N50C * 2; i++)
            if (i < N50C)
                 threads.add(new Client(account, true,  50));
            else threads.add(new Client(account, false, 50));
        for (int i = 0; i < N20C * 2; i++)
            if (i < N20C)
                 threads.add(new Client(account, true,  20));
            else threads.add(new Client(account, false, 20));

        threads.forEach(Thread::start);
        for (Thread thread : threads)
            thread.join();

        if (account.simulationSucceeded())
             System.out.println("The simulation was a success");
        else System.out.printf("Simulation failed, final balance is %d, check your synchronization", account.getBalance());
    }

}
