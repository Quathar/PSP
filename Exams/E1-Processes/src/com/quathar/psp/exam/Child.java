package com.quathar.psp.exam;

/**
 * <h1>Child</h1>
 *
 * @since 2022-10-06
 * @version 1.0
 * @author Q
 */
public class Child {

    public static void main(String[] args) {
        switch (args[0]) {
            case "-t1" -> System.out.println(new StringBuilder(args[1]).reverse());
            case "-t2" -> System.out.println(args[1].toUpperCase());
        }
    }

}
