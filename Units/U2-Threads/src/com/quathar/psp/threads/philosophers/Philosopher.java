package com.quathar.psp.threads.philosophers;

public class Philosopher extends Thread {

    // <<-FIELDS->>
    private final DiningPhilosophers _diningPhilosophers;
    private final int _num;
    private final Fork _leftFork;
    private final Fork _rightFork;

    // <<-CONSTRUCTOR->>
    public Philosopher(DiningPhilosophers monitor, int num, Fork rightFork, Fork leftFork) {
        _diningPhilosophers = monitor;
        _num = num;
        _leftFork = leftFork;
        _rightFork = rightFork;
    }

    // <<-METHODS->>
    private void randomPause() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 10));
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        while (true) {
            try {
                System.out.printf("Philosopher %d contemplating the universe, working up an appetite%n", _num);
                randomPause();
                // ORIGINAL CODE in the program: Deadlock 1 - De esta manera el programa se bloquea
//                System.out.printf("Philosopher %d hungry; going for left fork%n", _num);
//                _diningPhilosophers.acquire(_leftFork);
//                System.out.printf("Philosopher %d hungry; now going for right fork%n", _num);
//                _diningPhilosophers.acquire(_rightFork);
                // Solution to Deadlock 1 - Coger los 2 tenedores a la vez
                System.out.printf("Philosopher %d hungry; going for both forks%n", _num);
                _diningPhilosophers.acquire(_leftFork, _rightFork);
                System.out.printf("Philosopher %d got both forks; chowing down%n", _num);
                randomPause();
                // ORIGINAL CODE in the program: Deadlock 2 - De esta manera el programa se bloquea
//                System.out.printf("Philosopher %d finished eating; dropping left fork%n", _num);
//                _diningPhilosophers.release(_leftFork);
//                System.out.printf("Philosopher %d finished eating; now dropping right fork%n", _num);
//                _diningPhilosophers.release(_rightFork);
                // Solution to Deadlock 2 - Soltar los 2 tenedores a la vez
                System.out.printf("Philosopher %d finished eating; dropping both forks%n", _num);
                _diningPhilosophers.release(_leftFork, _rightFork);
            } catch (InterruptedException e) {
                System.out.printf("Philosopher %d all done%n", _num);
                break;
            }
        }
    }

}
