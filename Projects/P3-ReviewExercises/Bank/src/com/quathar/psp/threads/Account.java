package com.quathar.psp.threads;

public class Account {

    // <<-FIELDS->>
    private final int _initialBalance;
    private int _balance;

    // <<-CONSTRUCTOR->>
    public Account(int initialBalance) {
        _initialBalance = initialBalance;
        _balance = initialBalance;
    }

    // <<-METHODS->>
    synchronized void deposit(int amount) {
        _balance += amount;
    }

    synchronized void withdraw(int amount) {
        _balance -= amount;
    }

    boolean simulationSucceeded() {
        return _balance == _initialBalance;
    }

    public int getBalance() {
        return _balance;
    }

}
