package com.quathar.psp.threads;

public class PackagesCounter {

    // <<-FIELD->>
    int _count = 0;

    // <<-METHOD->>
    synchronized void increase() {
        _count++;
        System.out.printf("Total packages: %d%n", _count);
    }

}
