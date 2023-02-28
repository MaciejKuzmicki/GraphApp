package com.example.jaguar;

public record Neighbour(int destination, double weight) implements Comparable<Neighbour> {

    @Override
    public int compareTo(Neighbour o) {
        if (weight > o.weight) return -1;
        else if (weight < o.weight) return 1;
        else return 0;
    }
}
