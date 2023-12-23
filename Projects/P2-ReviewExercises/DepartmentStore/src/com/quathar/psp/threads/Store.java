package com.quathar.psp.threads;

/**
 * <h1>Store</h1>
 *
 * @since 2022-10-07
 * @version 1.0
 * @author Q
 */
public class Store {

    // <<-FIELD->>
    private int _product;

    // <<-CONSTRUCTOR->>
    public Store(int maxProducts) {
        _product = maxProducts;
    }

    // <<-METHODS->>
    synchronized boolean takeProduct() {
        if (_product > 0) {
            _product--;
            return true;
        }
        return false;
    }

    // <<-OVERRIDE->>
    @Override
    public String toString() {
        return String.format("The store has %d products", _product);
    }

}
