package com.example.jaguar;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Double.MAX_VALUE;

public class Graph {
    private int row;
    private int column;
    private double minimumCost = MAX_VALUE;
    private double maximumCost = 0;
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    ArrayList<Vertex> graphStructure;
    public int numberOfVertexes() {
        return graphStructure.size();
    }
    public double getMinimumCost () {
        return minimumCost;
    }
    public double getMaximumCost() {
        return maximumCost;
    }
    public void readFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String [] lines = reader.readLine().split(" ");
        setRow(Integer.parseInt(lines[0]));
        setColumn(Integer.parseInt(lines[1]));
        graphStructure = new ArrayList<>(column*row);
        String line;
        Scanner scanner;
        int numberOfVertex =0;
        while((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(" :|\\s* ");
            scanner.useLocale(Locale.US);
            graphStructure.add(addVertexForGraf(numberOfVertex));
            while(scanner.hasNext()){
                int dest = scanner.nextInt();
                double weight = scanner.nextDouble();
                if(weight > maximumCost) maximumCost = weight;
                if(weight<0) throw new IOException();
                if(weight < minimumCost) minimumCost = weight;
                graphStructure.get(numberOfVertex).addNeighbour(dest, weight);
            }
            numberOfVertex++;
        }
        reader.close();
    }

    public Vertex addVertexForGraf(int index) {
        Vertex vertex = new Vertex(index);
        return vertex;
    }


    public void writeToFile(String filename) throws IOException {
        File file = new File(filename);
        if(!file.exists()) file.createNewFile();
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("%d %d", getRow(), getColumn());
        printWriter.printf("%n");
        for(int i =0;i< graphStructure.size();i++) {
            printWriter.printf("     ");
            for(int j=0;j< graphStructure.get(i).getNeighbours().size();j++) {
                printWriter.printf(Locale.US,"%d :%f  ", graphStructure.get(i).getNeighbours().get(j).destination(), graphStructure.get(i).getNeighbours().get(j).weight());
            }
            printWriter.printf("%n");
        }
        printWriter.close();
        fileWriter.close();
    }
}
