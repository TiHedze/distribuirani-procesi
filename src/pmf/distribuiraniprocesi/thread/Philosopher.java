package pmf.distribuiraniprocesi.thread;

import pmf.distribuiraniprocesi.contracts.Resource;

public class Philosopher implements Runnable {
    private int id;
    Resource resource;

    public Philosopher(int id, Resource resource) {
        this.id = id;
        this.resource = resource;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Philosopher " + this.id + " is thinking.");
                Thread.sleep(30);

                System.out.println("Philosopher " + this.id + " is hungry.");
                this.resource.acquire(this.id);

                System.out.println("Philosopher " + this.id + " is eating.");
                Thread.sleep(40);

                resource.release(this.id);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
