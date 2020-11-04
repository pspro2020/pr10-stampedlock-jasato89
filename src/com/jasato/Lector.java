package com.jasato;

public class Lector  implements Runnable{


    private final Almacen almacen;
    private final int productID;

    public Lector(Almacen almacen, int productID){
        this.almacen = almacen;
        this.productID = productID;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                almacen.checkStock(productID);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
