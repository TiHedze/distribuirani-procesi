package pmf.distribuiraniprocesi.process;

import pmf.distribuiraniprocesi.contracts.Lock;
import pmf.distribuiraniprocesi.util.Linker;
import pmf.distribuiraniprocesi.util.Message;
import pmf.distribuiraniprocesi.util.Process;



public class DinMutex extends Process implements Lock {

    private static final int thinking = 0, hungry = 1, eating = 2;
    private boolean fork[], drity[], request[];
    private int myState;

    public DinMutex(Linker initComm) {
        super(initComm);
        this.fork = new boolean[super.N];
        this.drity = new boolean[super.N];
        this.request = new boolean[super.N];

        for (int i = 0; i < this.N; i++) {

            if ((this.myId > i) && isNeighbor(i)) {
                this.fork[i] = false;
                this.request[i] = true;
            } else {
                this.fork[i] = true;
                this.request[i] = false;
            }

            this.drity[i] = true;
        }
    }

    private boolean haveForks() {
        for (int i = 0; i < this.N; i++) {
            if (!this.fork[i]) return false;
        }
        return true;
    }

    @Override
    public synchronized void requestCS() {
        this.myState = hungry;
        if (this.haveForks()) {
            this.myState = eating;
        } else {
            for (int i = 0; i < this.N; i++) {
                if (this.request[i] && !this.fork[i]) {
                    this.sendMessage(i, "Request");
                    this.request[i] = false;
                }
            }

        }
        while (this.myState != eating) {
            this.myWait();
        }
    }

    @Override
    public void releaseCS() {
        this.myState = thinking;
        for (int i = 0; i < this.N; i++) {
            this.drity[i] = true;
            if (this.request[i]) {
                this.sendMessage(i, "Fork");
                this.fork[i] = false;
            }
        }
    }

    @Override
    public synchronized void handleMessage(Message message, int sourceId, String tag) {
        if (tag.equals("Request")) {
            this.request[sourceId] = true;
            if (this.myState == eating && this.fork[sourceId] && this.drity[sourceId]) {
                this.sendMessage(sourceId, "Fork");
                this.fork[sourceId] = false;
                if (this.myState == hungry) {
                    this.sendMessage(sourceId, "Request");
                    this.request[sourceId] = false;
                }
            }
        } else if (tag.equals("Fork")) {
            this.fork[sourceId] = true;
            this.drity[sourceId] = false;
            if (this.haveForks()) {
                this.myState = eating;
                notify();
            }
        }
    }

    @Override
    public Message receiveMessage(int sourceId) {
        var message =  super.receiveMessage(sourceId);
        System.out.println(message.getMessage());
        return message;
    }
}
