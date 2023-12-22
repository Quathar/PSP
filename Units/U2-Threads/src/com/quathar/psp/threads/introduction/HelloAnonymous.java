package com.quathar.psp.threads.introduction;

/**
 * Third way to create a Thread object. Anonymous classes.
 *
 * @since 2022-10-10
 * @version 1.0
 * @author Q
 */
public class HelloAnonymous {

    public static void main(String[] args) {
        // 1º Thread
        Thread th1 = new Thread(new Runnable() {
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
        });
        th1.start();

        // 2º Thread
        Thread th2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("Hello %d%n", i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th2.start();
    }

}
