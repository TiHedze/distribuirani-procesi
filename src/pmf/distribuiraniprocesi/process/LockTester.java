package pmf.distribuiraniprocesi.process;

import pmf.distribuiraniprocesi.contracts.Lock;
import pmf.distribuiraniprocesi.contracts.MessageHandler;
import pmf.distribuiraniprocesi.util.Linker;
import pmf.distribuiraniprocesi.util.ListenerThread;
import pmf.distribuiraniprocesi.util.Util;

public class LockTester {
    public static void main(String[] args) throws Exception {
        Linker comm = null;
        try {
            String baseName = args[0];
            int myId = Integer.parseInt(args[1]);
            int numProc = Integer.parseInt(args[2]);
            comm = new Linker(baseName, myId, numProc);
            Lock lock = null;
            lock = new DinMutex(comm);
            for (int i = 0; i < numProc; i++)
                if (i != myId)
                    (new ListenerThread(i, (MessageHandler) lock)).start();
            while (true) {
                System.out.println(myId + " is not in CS");
                Util.mySleep(2000);
                lock.requestCS();
                Util.mySleep(2000);
                System.out.println(myId + " is in CS *****");
                lock.releaseCS();
            }
        } catch (InterruptedException e) {
            if (comm != null) comm.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}