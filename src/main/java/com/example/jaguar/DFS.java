package com.example.jaguar;

import java.util.Arrays;

public class DFS {
    private boolean [] visited;
    private boolean result;
    private Graph graphToCheck;

    public DFS(Graph graphToCheck) {
        this.graphToCheck = graphToCheck;
        this.visited = new boolean[graphToCheck.getRow()* graphToCheck.getColumn()];
        for(int i = 0; i < graphToCheck.getColumn()* graphToCheck.getRow(); i++) {
            Arrays.fill(visited, false);
            DFSUsage(i);
            DFSCheck();
            if(!result) break;
        }
    }

    public void DFSUsage(int vertex) {
        visited[vertex] = true;
        for(int i =0; i <graphToCheck.graphStructure.get(vertex).getNeighbours().size();i++) {
            if(!visited[graphToCheck.graphStructure.get(vertex).getNeighbours().get(i).destination()]) {
                DFSUsage(graphToCheck.graphStructure.get(vertex).getNeighbours().get(i).destination());
            }
        }
    }

    public void DFSCheck() {
        for(int i =0;i<visited.length;i++) {
            if(!visited[i]) {
                result = false;
                break;
            }
            else result = true;
        }
    }

    public boolean getResult() {
        return result;
    }
}
