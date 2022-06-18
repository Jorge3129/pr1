package org.example;

public class Worker extends Thread {

    private int id;
    private final Data data;

    public Worker(int id, Data d) {
        this.id = id;
        data = d;
        this.start();
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 5; i++) {
            synchronized (data) {
                try {
                    while (id != data.getState()) data.wait();
                    if (id == 1) data.Tic();
                    else data.Tak();
                    data.notify();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
