package com.example.jaguar;

import java.util.ArrayDeque;

public class BFS {
    private boolean result;

    public boolean getResult() {
        return result;
    }

    public BFS(Graph graph) {
        boolean [] visited = new boolean[graph.getRow()*graph.getColumn()];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for(int i =0; i< visited.length;i++) visited[i] = false;
        queue.add(0);
        visited[0] =true;
        while(queue.size() != 0) {
            int w = queue.getFirst();
            for(int i=0;i<graph.graphStructure.get(w).getNeighbours().size();i++) {
                if(!visited[graph.graphStructure.get(w).getNeighbours().get(i).destination()]) {
                    queue.add(graph.graphStructure.get(w).getNeighbours().get(i).destination());
                    visited[graph.graphStructure.get(w).getNeighbours().get(i).destination()] =true;
                }
            }
            queue.removeFirst();
        }
        for(int i=0;i< visited.length;i++ ) {
            if(!visited[i]) {
                result = false;
                break;
            }
            else result = true;
        }
    }
}
