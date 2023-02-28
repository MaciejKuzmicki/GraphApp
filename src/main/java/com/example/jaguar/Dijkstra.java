package com.example.jaguar;

import java.util.*;

import static java.lang.Long.MAX_VALUE;

public class Dijkstra {

    Graph graphPath;

    private int [] predecessor;

    private double [] minimumCostValue;

    private ArrayList<Vertex> listOfVertexes;

    private PriorityQueue<PairIntDouble> minimumCost = new PriorityQueue<>();

    private Stack<PairIntDouble> finalPath = new Stack<>();

    private Integer [] finalPathArray;

    private int start;

    private int end;

    private int removedVertexIndex;

    private boolean ifPathExists;

    private double finalCost;

    private String path;

    public Integer[] getFinalPathArray() {
        return finalPathArray;
    }

    public boolean getIfPathExists() {
        return ifPathExists;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public String getPath() {
        return path;
    }

    public Dijkstra(Graph graphPath, int start, int end) {
        if(graphPath == null) return;
        ifPathExists = true;
        this.graphPath = graphPath;
        predecessor = new int[graphPath.getColumn()*graphPath.getRow()];
        minimumCostValue = new double[graphPath.getRow()* graphPath.getColumn()];
        this.start = start;
        this.end = end;
        this.listOfVertexes = graphPath.graphStructure;
        prepareDijkstra();
        dijkstraUsage();
        makeThePath();
        cleanAfterDijkstraUsage();
        readThePath();
    }

    public void prepareDijkstra() {
        for(int i =0; i < graphPath.graphStructure.size(); i++) {
            if(i == start) {
                minimumCost.add(new PairIntDouble(i, 0.0));
                minimumCostValue[i] = 0;
            }
            else {
                minimumCost.add(new PairIntDouble(i, (double) MAX_VALUE));
                minimumCostValue[i] = MAX_VALUE;
            }
        }
        for(int i =0 ; i<predecessor.length; i++) {
            predecessor[i] = -1;
        }
        removedVertexIndex = start;
    }

    public void updateQueue() {
        PriorityQueue<PairIntDouble> queue = new PriorityQueue<>();
        for(int i = 0; i <graphPath.getColumn()* graphPath.getRow();i++) {
            if(listOfVertexes.get(i).getVertexToProcess()) {
                queue.add(new PairIntDouble(i, minimumCostValue[i]));
            }
        }
        this.minimumCost=queue;
    }

    public void dijkstraUsage() {
        for(int i = 0; i < listOfVertexes.size(); i++) {
            removedVertexIndex = minimumCost.peek().getKey();
            listOfVertexes.get(removedVertexIndex).setVertexProcessed();
            for(int j = 0; j<listOfVertexes.get(removedVertexIndex).getNeighbours().size(); j++) {
                if(listOfVertexes.get(listOfVertexes.get(removedVertexIndex).getNeighbours().get(j).destination()).getVertexToProcess()) {
                    if(minimumCostValue[listOfVertexes.get(removedVertexIndex).getNeighbours().get(j).destination()] > minimumCostValue[removedVertexIndex] + listOfVertexes.get(removedVertexIndex).getNeighbours().get(j).weight()) {
                        predecessor[listOfVertexes.get(removedVertexIndex).getNeighbours().get(j).destination()] = removedVertexIndex;
                        minimumCostValue[listOfVertexes.get(removedVertexIndex).getNeighbours().get(j).destination()] = minimumCostValue[removedVertexIndex] + listOfVertexes.get(removedVertexIndex).getNeighbours().get(j).weight();
                    }
                }
            }
            updateQueue();
        }
    }

    public void makeThePath() {
        int currentVertex = end;
        int temporaryVariable;
        int numberOfIterations=0;
        while(currentVertex != start) {
            try {
                if(numberOfIterations == 0) finalCost = minimumCostValue[currentVertex];
                finalPath.push(new PairIntDouble(currentVertex, minimumCostValue[currentVertex]));
                temporaryVariable = currentVertex;
                currentVertex = predecessor[temporaryVariable];
                numberOfIterations++;
            }
            catch(ArrayIndexOutOfBoundsException e) {
                ifPathExists = false;
                return;
            }
        }
        finalPathArray = new Integer[numberOfIterations + 1];
        currentVertex = end;
        numberOfIterations=0;
        while(currentVertex != start) {
            finalPathArray[numberOfIterations++] = currentVertex;
            temporaryVariable = currentVertex;
            currentVertex = predecessor[temporaryVariable];
        }
        finalPathArray[numberOfIterations] = start;
    }

    public void readThePath() {
        path = new String();
        path += start;
        while(finalPath.size() != 0) {
            path += finalPath.pop().toString();
        }
    }

    public void cleanAfterDijkstraUsage() {
        for(int i = 0;i < graphPath.numberOfVertexes();i++) {
            graphPath.graphStructure.get(i).makeVertexUnProcessed();
        }
    }
}
