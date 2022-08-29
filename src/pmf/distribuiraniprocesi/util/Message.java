package pmf.distribuiraniprocesi.util;

import java.util.StringTokenizer;

public class Message {
    int srcId, destId;
    String tag;
    String msgBuf;
    public Message(int s, int t, String msgType, String buf) {
        this.srcId = s;
        destId = t;
        tag = msgType;
        msgBuf = buf;
    }
    public int getSrcId() {
        return srcId;
    }
    public int getDestId() {
        return destId;
    }
    public String getTag() {
        return tag;
    }
    public String getMessage() {
        return msgBuf;
    }
    public int getMessageInt() {
        StringTokenizer st = new StringTokenizer(msgBuf);
        return Integer.parseInt(st.nextToken());
    }
    public static Message parseMsg(StringTokenizer st){
        int srcId = Integer.parseInt(st.nextToken());
        int destId = Integer.parseInt(st.nextToken());
        String tag = st.nextToken();
        String buf = st.nextToken("#");
        return new Message(srcId, destId, tag, buf);
    }
    public String toString(){
        String s = srcId +" " +
                destId + " " +
                tag + " " +
                msgBuf + "#";
        return s;
    }
}
