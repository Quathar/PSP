package com.quathar.psp.exam.sync;

import com.quathar.psp.exam.sync.type1.SlotMachine;
import com.quathar.psp.exam.sync.type2.HorseRace;

import java.io.BufferedReader;

/**
 * <h1>Main</h1>
 * <br>
 * See type1.pdf and type2.pdf [Ejercicio 2] to understand the requirements.
 *
 * @since 2022-11-22
 * @author Q
 * @version 1.0
 */
public class Main {

    // <<-CONSTANTS - TYPE 1->>
    private static final int NUM_DRUMS = 5;
    private static final int MAX_TIME = 1000;
    private static final int MAX_VALUE = 5;
    private static final int ROUNDS = 3;

    // <<-CONSTANTS - TYPE 2->>
    private static final int NUM_HORSES = 5;
    private static final int TIME_RUNNING = 3000;
    private static final int RACES = 3;

    // <<-METHODS->>
    private static void type1() throws InterruptedException {
        SlotMachine slotMachine = new SlotMachine(NUM_DRUMS, MAX_TIME, MAX_VALUE, ROUNDS);
        slotMachine.start();
        slotMachine.join();
    }

    private static void type2() throws InterruptedException {
        HorseRace horseRace = new HorseRace(NUM_HORSES, TIME_RUNNING, RACES);
        horseRace.start();
        horseRace.join();
    }

    // <<-MAIN METHOD->>
    public static void main(String[] args) throws java.io.IOException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))) {
            System.out.println("Which exercise do you want to execute? [ -t1 | SlotMachine ] [ -t2 | HorseRace ]");
            switch (br.readLine()) {
                case "-t1" -> type1();
                case "-t2" -> type2();
                default    -> System.out.println("E R R O R: Incorrect option");
            }
        }
    }

}
