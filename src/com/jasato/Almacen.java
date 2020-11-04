package com.jasato;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class Almacen {

    private ArrayList<Integer> products = new ArrayList<>();
    private final StampedLock stampedLock = new StampedLock();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");


    public Almacen(ArrayList<Integer> products) {
        this.products = products;
    }

    public void addStock(int productID) {
        long stamp = stampedLock.writeLock();
        try {
            products.add(productID);
            System.out.printf("%s - Producto %d añadido\n", LocalTime.now().format(dateFormat), productID);
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    public void checkStock (int productID) {
        long stamp = stampedLock.tryOptimisticRead();
        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                System.out.printf("%s - Producto %d - Stock: %d\n", LocalTime.now().format(dateFormat), productID, totalStock(productID));

            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        else {
            System.out.printf("%s - Producto %d - Stock: %d\n", LocalTime.now().format(dateFormat), productID, totalStock(productID));
        }

    }

    private int totalStock(int productID) {
        int totalStockOf = 0;
        for (int product : products) {
            if (product == productID) {
                totalStockOf++;
            }
        }
            return totalStockOf;

        }
}
