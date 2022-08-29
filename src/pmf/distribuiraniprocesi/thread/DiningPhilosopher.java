package pmf.distribuiraniprocesi.thread;

import pmf.distribuiraniprocesi.contracts.Resource;
import pmf.distribuiraniprocesi.util.BinarySemaphore;

public class DiningPhilosopher implements Resource {
    int numberOfPhilosophers;
    BinarySemaphore[] semaphores;

    public DiningPhilosopher(int numberOfPhilosophers) {
        this.numberOfPhilosophers = numberOfPhilosophers;
        this.semaphores = new BinarySemaphore[numberOfPhilosophers];

        for(int i = 0; i < numberOfPhilosophers; i++) {
            this.semaphores[i] = new BinarySemaphore(true);
        }
    }

    @Override
    public void acquire(int resourceId) {
        this.semaphores[resourceId].P();
        this.semaphores[(resourceId + 1) % this.numberOfPhilosophers].P();
    }

    @Override
    public void release(int resourceId) {
        this.semaphores[resourceId].V();
        this.semaphores[(resourceId + 1) % this.numberOfPhilosophers].V();
    }

    public static void main(String[] args) {
        int numberOfPhilosophers = 5;
        if(args.length > 0) {
            numberOfPhilosophers = Integer.parseInt(args[0]);
        }

        var diningPhilosopher = new DiningPhilosopher(numberOfPhilosophers);
        for (int i = 0; i < numberOfPhilosophers; i++) {
            new Philosopher(i, diningPhilosopher);
        }
    }
}
