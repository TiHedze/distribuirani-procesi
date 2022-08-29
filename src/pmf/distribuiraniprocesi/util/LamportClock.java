package pmf.distribuiraniprocesi.util;

public class LamportClock {
    private int clock;

    public LamportClock() {
        this.clock = 0;
    }

    public int getValue()
    {
        return this.clock;
    }

    public void tick() {
        this.clock++;
    }

    public void sendAction() {
        this.clock++;
    }

    public void recieveAction(int sourceId, int sentValue) {
        this.clock = Util.max(this.clock, sentValue) + 1;
    }

}
