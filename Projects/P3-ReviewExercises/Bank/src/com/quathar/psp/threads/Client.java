package com.quathar.psp.threads;

public class Client extends Thread {

    // <<-FIELDS->>
    private final Account _account;
    private final boolean _deposit;
    private final int _money;

    // <<-CONSTRUCTOR->>
    public Client(Account account, boolean deposit, int money) {
        _account = account;
        _money = money;
        _deposit = deposit;
    }

    // <<-OVERRIDE->>
    @Override
    public void run() {
        if (_deposit)
            for (int i = 0; i < 100; i++) _account.deposit(_money);
        else
            for (int i = 0; i < 100; i++) _account.withdraw(_money);
    }

}
