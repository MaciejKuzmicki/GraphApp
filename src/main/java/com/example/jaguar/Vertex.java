package com.example.jaguar;

import java.util.ArrayList;

public class Vertex {
    private ArrayList<Neighbour> neighbours = new ArrayList<>();
    private int index;

    private boolean vertexToProcess;

    public Vertex(int index) {
        this.index = index;
        this.vertexToProcess = true;
    }

    public void addNeighbour(int destination, double weight) {
        Neighbour neighbour = new Neighbour(destination, weight);
        neighbours.add(neighbour);
    }

    public ArrayList<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void setVertexProcessed() {
        vertexToProcess = false;
    }

    public void makeVertexUnProcessed() {
        vertexToProcess = true;
    }

    public boolean getVertexToProcess() {
        return vertexToProcess;
    }

    @Override
    public String toString() {
        String valueToReturn = "";
        for(int i = 0; i < neighbours.size(); i++) {
            valueToReturn += neighbours.get(i).destination() + ": " + neighbours.get(i).weight() + " ";
        }
        return valueToReturn + "\n";
    }
}
