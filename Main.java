package com.company;

import java.util.*;

public class Main {

    static int time2 = 0;

    public static void main(String[] args) {
        Graphwh kar = new Graphwh();
        HashMap<String, Integer> test = new HashMap<>();
        test.put("A", 2);
        test.put("H", 3);
        test.put("C", 4);
        test.put("D", 5);
        HashMap<String, Boolean> processed = new HashMap<>();
        kar.addVertex("A");
        kar.addVertex("B");
        kar.addVertex("C");
        kar.addVertex("D");
        kar.addVertex("E");
        kar.addVertex("F");
        kar.addEdge("A", "B", 1);
        kar.addEdge("A", "C", 1);
        kar.addEdge("B", "D", 1);
        kar.addEdge("B", "E", 1);
        kar.addEdge("B", "F", 1);
        kar.display();


        ArrayList<String> brr = new ArrayList<>(test.keySet());
        if (test.get("A") != null) {
            System.out.println("Lol worked");
        }
        System.out.println(processed);
        for (String key : brr) {
            System.out.println(key);
        }

        kar.DFTScycledetect("A", processed);
        System.out.println(kar.time);

        int n = 6;
        int[][] connections = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}};

        System.out.println(makeConnected(n, connections));


        // write your code here
    }

    public static int makeConnected(int n, int[][] connections) {
        Graphwh kar = new Graphwh();
        for (int i = 0; i < n; i++) {
            kar.addVertex(Integer.toString(i));
        }
        for (int i = 0; i < connections.length; i++) {
            kar.addEdge(Integer.toString(connections[i][0]), Integer.toString(connections[i][1]), 1);
        }
        int count = 0;
        if (connections.length < n - 1) {
            return -1;
        } else {
            HashMap<String, Boolean> processed = new HashMap<>();
            count = kar.DFTScountdiscon(processed);
            return count - 1;

        }

    }



}
