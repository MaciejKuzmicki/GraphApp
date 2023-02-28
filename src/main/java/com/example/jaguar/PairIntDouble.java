package com.example.jaguar;

public class PairIntDouble implements Comparable<PairIntDouble> {
    private int key;
    private Double value;

    public int getKey(){
        return key;
    }

    public PairIntDouble(int key, Double value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(PairIntDouble o) {
        if(value > o.value) return 1;
        else if(value < o.value) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return " --" + String.format("%.4f" ,value) + "-> " + key;
    }
}
