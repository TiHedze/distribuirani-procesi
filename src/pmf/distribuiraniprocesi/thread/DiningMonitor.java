package pmf.distribuiraniprocesi.thread;

import pmf.distribuiraniprocesi.contracts.Resource;
import pmf.distribuiraniprocesi.util.Util;

public class DiningMonitor implements Resource {
    int numberOfPhilosophers;
    int[] state;
    static final int thinking = 0, hungry = 1, eating = 2;

    public DiningMonitor(int numberOfPhilosophers) {
        this.numberOfPhilosophers = numberOfPhilosophers;
        this.state = new int[this.numberOfPhilosophers];

        for (int i = 0; i < numberOfPhilosophers; i++) {
            this.state[i] = thinking;
        }
    }

    private int left(int resourceId) {
        return (numberOfPhilosophers + resourceId - 1) % numberOfPhilosophers;
    }


    private int right(int resourceId) {
        return (resourceId + 1) % numberOfPhilosophers;
    }

    @Override
    public synchronized void acquire(int resourceId) {
        this.state[resourceId] = hungry;
        test(resourceId);
        while (this.state[resourceId] != eating) {
            Util.myWait(this);
        }
    }

    @Override
    public synchronized void release(int resourceId) {
        this.state[resourceId] = thinking;
        test(left(resourceId));
        test(right(resourceId));
    }

    void test(int resourceId) {
        if ((state[left(resourceId)] != eating) &&
                (state[resourceId] == hungry && (state[right(resourceId)] != eating))) {
            state[resourceId] = eating;
            notifyAll();
        }
    }

    public static void main(String[] args) {
        int numberOfPhilosophers = 5;
        if (args.length > 0) {
            numberOfPhilosophers = Integer.parseInt(args[0]);
        }

        var diningPhilosopher = new DiningMonitor(numberOfPhilosophers);
        for (int i = 0; i < numberOfPhilosophers; i++) {
            new Philosopher(i, diningPhilosopher);
        }
    }
}
