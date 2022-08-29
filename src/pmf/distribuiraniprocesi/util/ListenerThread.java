package pmf.distribuiraniprocesi.util;

import pmf.distribuiraniprocesi.contracts.MessageHandler;

import java.io.*;
public class ListenerThread extends Thread {
    int channel;
    MessageHandler process;
    public ListenerThread(int channel, MessageHandler process) {
        this.channel = channel;
        this.process = process;
    }
    public void run() {
        while (true) {
            try {
                Message m = process.receiveMessage(channel);
                process.handleMessage(m, m.getSrcId(), m.getTag());
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
