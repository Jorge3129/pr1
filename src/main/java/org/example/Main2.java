package org.example;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        Data2 d = new Data2();

        Worker2 w2 = new Worker2(2, d);
        Worker2 w3 = new Worker2(3, d);
        Worker2 w1 = new Worker2(1, d);

        w2.join();
        System.out.println("end of main...");
    }
}
