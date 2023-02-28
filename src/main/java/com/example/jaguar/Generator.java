package com.example.jaguar;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    public Graph tlw(int Rows, int Columns, double MinWeight , double MaxWeight, double probability ) {
        Graph graph = new Graph();
        graph.setRow(Rows);
        graph.setColumn(Columns);
        graph.graphStructure = new ArrayList<>(graph.getColumn()*graph.getRow());
        Random generator = new Random();
        Random variableUsedForProbability = new Random();
        int currentVertex =0;
        for(int i =0; i<Rows;i++){
            for(int j =0; j<Columns;j++){
                graph.graphStructure.add(graph.addVertexForGraf(currentVertex));
                if(i==0 && j==0){
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour(j+1, MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns, MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(i==0 && j==Columns-1){
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( j-1, MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns+j, MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(Rows*Columns-Columns == Columns*i+j){
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+1, MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i-Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(Rows*Columns-1 == Columns*i+j){
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i-1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j-1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(j==0){
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i-Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(i==0){
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( j-1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( j+1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns+j, MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(j==Columns-1) {
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i-1+j,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j-Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+Columns+j,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else if(i == Rows-1) {
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j-1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j+1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j-Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                else {
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j+1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j-1,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j-Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                    if(variableUsedForProbability.nextDouble() < probability) graph.graphStructure.get(currentVertex).addNeighbour( Columns*i+j+Columns,  MinWeight + (MaxWeight - MinWeight)*generator.nextDouble());
                }
                currentVertex++;

            }
        }
        return graph;
    }
}
