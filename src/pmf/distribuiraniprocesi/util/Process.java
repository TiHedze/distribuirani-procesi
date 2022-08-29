package pmf.distribuiraniprocesi.util;

import pmf.distribuiraniprocesi.contracts.MessageHandler;

import java.io.IOException;

public class Process implements MessageHandler {
    protected int N, myId;
    Linker comm;

    public Process(Linker initComm) {
        comm = initComm;
        myId = comm.getMyId();
        N = comm.getNumProc();
    }

    public synchronized void handleMsg(Message m, int src, String tag) {
    }

    public void sendMessage(int destId, String tag, String msg) {
        Util.println("Sending msg to " + destId + ":" + tag + " " + msg);
        comm.sendMsg(destId, tag, msg);
    }

    public void sendMessage(int destId, String tag, int msg) {
        sendMessage(destId, tag, String.valueOf(msg) + " ");
    }

    public void sendMessage(int destId, String tag, int msg1, int msg2) {
        sendMessage(destId, tag, String.valueOf(msg1)
                + " " + String.valueOf(msg2) + " ");
    }

    public void sendMessage(int destId, String tag) {
        sendMessage(destId, tag, " 0 ");
    }

    public void broadcastMsg(String tag, int msg) {
        for (int i = 0; i < N; i++)
            if (i != myId) sendMessage(i, tag, msg);
    }

    public void sendToNeighbors(String tag, int msg) {
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) sendMessage(i, tag, msg);
    }

    public boolean isNeighbor(int i) {
        if (comm.neighbors.contains(i)) return true;
        else return false;
    }

    @Override
    public void handleMessage(Message message, int sourceId, String tag) {
        System.out.println(tag + " " + message.getMessage());
    }

    public Message receiveMessage(int fromId) {
        try {
            return comm.receiveMsg(fromId);
        } catch (IOException e) {
            System.out.println(e);
            comm.close();
            return null;
        }
    }

    public synchronized void myWait() {
        try {
            wait();
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
