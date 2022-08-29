package pmf.distribuiraniprocesi.contracts;

import pmf.distribuiraniprocesi.util.Message;

import java.io.IOException;

public interface MessageHandler {
    void handleMessage(Message message, int sourceId, String tag);
    Message receiveMessage(int sourceId) throws IOException;
}
