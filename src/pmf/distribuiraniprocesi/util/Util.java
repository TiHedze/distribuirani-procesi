package pmf.distribuiraniprocesi.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Util {
    public static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }
    public static void mySleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    public static void myWait(Object obj) {
        println("waiting");
        try {
            obj.wait();
        } catch (InterruptedException e) {
        }
    }
    public static boolean lessThan(int A[], int B[]) {
        for (int j = 0; j < A.length; j++)
            if (A[j] > B[j]) return false;
        for (int j = 0; j < A.length; j++)
            if (A[j] < B[j]) return true;
        return false;
    }
    public static int maxArray(int A[]) {
        int v = A[0];
        for (int i=0; i<A.length; i++)
            if (A[i] > v) v = A[i];
        return v;
    }
    public static String writeArray(int A[]){
        StringBuffer s = new StringBuffer();
        for (int j = 0; j < A.length; j++)
            s.append(String.valueOf(A[j]) + " ");
        return new String(s.toString());
    }
    public static void readArray(String s, int A[]) {
        StringTokenizer st = new StringTokenizer(s);
        for (int j = 0; j < A.length; j++)
            A[j] = Integer.parseInt(st.nextToken());
    }
    public static int searchArray(int A[], int x) {
        for (int i = 0; i < A.length; i++)
            if (A[i] == x) return i;
        return -1;
    }
    public static void println(String s){
        if (Symbols.debugFlag) {
            System.out.println(s);
            System.out.flush();
        }
    }

    public static void createTopologyFiles() throws IOException {
        ArrayList<ArrayList<Integer>> topology = new ArrayList(List.of(
                new ArrayList<>(List.of(1,2)),
                new ArrayList<>(List.of(0,2,3,4)),
                new ArrayList<>(List.of(0,1,4,5)),
                new ArrayList<>(List.of(1,4)),
                new ArrayList<>(List.of(1,2,3,5)),
                new ArrayList<>(List.of(2,4))
        ));

        FileWriter writer;

        for(int i = 0; i < topology.size(); i++) {
            writer = new FileWriter(new File(System.getProperty("user.dir") + "/topology" + i));
            for (var number : topology.get(i) ) {
                writer.write(String.valueOf(number)+ " ");
            }
            writer.close();
        }
    }
}
