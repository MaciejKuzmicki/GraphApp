package com.example.jaguar;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraTest {
    private Dijkstra dijkstraForTests;
    private Graph graphForTests;

    @Test
    public void getFinalCost() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_connected.txt");
        dijkstraForTests = new Dijkstra(graphForTests, 0, 9999);
        assertEquals("642.0788", String.format( Locale.US,"%.4f" , dijkstraForTests.getFinalCost()));
    }

    @Test
    public void getFinalCostWhereIsNoPath() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_not_connected_2.txt");
        dijkstraForTests = new Dijkstra(graphForTests, 47, 195);
        assertEquals(9.223372036854776E18, dijkstraForTests.getFinalCost());
    }

    @Test
    public void getFinalCostWhereIsNoPath2() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_not_connected.txt");
        dijkstraForTests = new Dijkstra(graphForTests, 0, 2);
        assertEquals(9.223372036854776E18, dijkstraForTests.getFinalCost());
    }
    @Test
    public void getFinalCost2() throws IOException {
        graphForTests = new Graph();
        graphForTests.readFromFile("graph_for_tests_connected_2.txt");
        dijkstraForTests = new Dijkstra(graphForTests, 0, 999);
        assertEquals("172.9896", String.format(Locale.US, "%.4f", dijkstraForTests.getFinalCost()));
        dijkstraForTests = new Dijkstra(graphForTests, 441, 337);
        assertEquals("56.0801", String.format(Locale.US, "%.4f", dijkstraForTests.getFinalCost()));
        dijkstraForTests = new Dijkstra(graphForTests, 80, 816);
        assertEquals("90.5774", String.format(Locale.US, "%.4f", dijkstraForTests.getFinalCost()));
        dijkstraForTests = new Dijkstra(graphForTests, 13, 960);
        assertEquals("101.6985", String.format(Locale.US, "%.4f", dijkstraForTests.getFinalCost()));
    }
}
