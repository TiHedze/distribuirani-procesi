package pmf.distribuiraniprocesi.util;


public class BinarySemaphore {
    private boolean value;

    public BinarySemaphore(boolean initialValue) {
        this.value = initialValue;
    }

    public synchronized void P() {
        while (!this.value)
            Util.myWait(this);
        this.value = false;
    }

    public synchronized void V() {
        this.value = true;
        notify();
    }
}
