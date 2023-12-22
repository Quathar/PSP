package com.quathar.psp.threads.introduction;

/**
 * Second way to create a Thread object. Implementing the Runnable interface.
 *
 * @since 2022-10-10
 * @version 1.0
 * @author Q
 */
public class HelloRunnable implements Runnable {

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
        Thread th1 = new Thread(new HelloRunnable());
        th1.start();
        Thread th2 = new Thread(new HelloRunnable());
        th2.start();
    }

}
