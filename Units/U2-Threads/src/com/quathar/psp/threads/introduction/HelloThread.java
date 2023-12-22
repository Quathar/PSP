package com.quathar.psp.threads.introduction;

/**
 * First way to create a Thread object. Extending the Thread class.
 *
 * @since 2022-10-10
 * @version 1.0
 * @author Q
 */
public class HelloThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("Hello %d%n", i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        HelloThread ht1 = new HelloThread();
        ht1.start();
        HelloThread ht2 = new HelloThread();
        ht2.start();
    }

}
