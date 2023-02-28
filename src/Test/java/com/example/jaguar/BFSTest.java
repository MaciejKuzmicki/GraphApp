package com.example.jaguar;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BFSTest {
    private Graph graphForBFSTest;
    private BFS bfs;
    @Test
    public void getResultFromConnectedGraph() throws IOException {
        graphForBFSTest = new Graph();
        graphForBFSTest.readFromFile("graph_for_tests_connected.txt");
        bfs = new BFS(graphForBFSTest);
        assertEquals(true, bfs.getResult());
    }
    @Test
    public void getResultFromNotConnectedGraph() throws IOException {
        graphForBFSTest = new Graph();
        graphForBFSTest.readFromFile("graph_for_tests_not_connected.txt");
        bfs = new BFS(graphForBFSTest);
        assertEquals(false, bfs.getResult());
    }
    @Test
    public void getResultFromConnectedGraph2() throws IOException {
        graphForBFSTest = new Graph();
        graphForBFSTest.readFromFile("graph_for_tests_connected_2.txt");
        bfs = new BFS(graphForBFSTest);
        assertEquals(true, bfs.getResult());
    }
    @Test
    public void getResultFromConnectedGraph3() throws IOException {
        graphForBFSTest = new Graph();
        graphForBFSTest.readFromFile("graph_for_tests_connected_3.txt");
        bfs = new BFS(graphForBFSTest);
        assertEquals(true, bfs.getResult());
    }
}