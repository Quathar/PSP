package com.quathar.psp.threads;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <h1>Gate</h1>
 *
 * @since 2022-10-07
 * @version 1.0
 * @author Q
 */
public class Gate {

    // <<-FIELD->>
    private final ReentrantLock _lock;

    // <<-CONSTRUCTOR->>
    public Gate() {
        _lock = new ReentrantLock();
    }

    // <<-METHOD->>
    boolean tryEnter() {
        try {
            if (_lock.tryLock())
                return true;
        } finally {
            _lock.unlock();
        }
        return false;
    }

}
