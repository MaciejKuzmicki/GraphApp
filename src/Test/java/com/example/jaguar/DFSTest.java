package com.example.jaguar;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DFSTest {
    private Graph graphForTests;
    private DFS dfs;

    @Test
    public void getResultFromConnectedGraph() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_connected_2.txt");
        dfs = new DFS(graphForTests);
        assertEquals(true, dfs.getResult());
    }
    @Test
    public void getResultFromNotConnectedGraph() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_not_connected_2.txt");
        dfs = new DFS(graphForTests);
        assertEquals(false, dfs.getResult());
    }
    @Test
    public void getResultFromNotConnectedGraph2() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_not_connected.txt");
        dfs = new DFS(graphForTests);
        assertEquals(false, dfs.getResult());
    }

}
