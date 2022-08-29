package pmf.distribuiraniprocesi.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Topology {
    public static void readNeighbors(int myId, int N,
                                     LinkedList<Integer> neighbors) {
        Util.println("Reading topology");
        try {
            Util.createTopologyFiles();
            BufferedReader dIn = new BufferedReader(
                    new FileReader(System.getProperty("user.dir")+ "/topology" + myId));
            StringTokenizer st = new StringTokenizer(dIn.readLine());
            while (st.hasMoreTokens()) {
                int neighbor = Integer.parseInt(st.nextToken());
                neighbors.add(neighbor);
            }
        } catch (FileNotFoundException e) {
            for (int j = 0; j < N; j++)
                if (j != myId) neighbors.add(j);
        } catch (IOException e) {
            System.err.println(e);
        }
        Util.println(neighbors.toString());
    }
}
