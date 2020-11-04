package com.jasato;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> stock = addStock();
        Almacen almacen = new Almacen(stock);
        Thread lector1 = new Thread(new Lector(almacen, 1));
        Thread lector2 = new Thread(new Lector(almacen, 2));
        Thread lector3 = new Thread(new Lector(almacen, 3));
        Thread proveedor = new Thread(new Proveedor(almacen));



        lector1.start();
        lector2.start();
        lector3.start();
        proveedor.start();
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            return;
        }

        lector1.interrupt();
        lector2.interrupt();
        lector3.interrupt();
        proveedor.interrupt();

        try {
            lector1.join();
            lector2.join();
            lector3.join();
        } catch (InterruptedException e) {
            return;
        }

    }


    private static ArrayList<Integer> addStock() {
        ArrayList<Integer> array = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            array.add(random.nextInt(3) +1);
        }
        return array;
    }
}
