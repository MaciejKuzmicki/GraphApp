package com.example.jaguar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GraphTest {
    private Graph graphForTests = new Graph();
    @Test
    public void readTheGraph() throws IOException {
        graphForTests.readFromFile("graph_for_tests.txt");
        String currentValue = "";
        for(int i = 0; i <graphForTests.getColumn()* graphForTests.getRow(); i++) {
            currentValue +=graphForTests.graphStructure.get(i).toString();
        }
        assertEquals(currentValue,"1: 3.399102 2: 4.824144 \n" +
                "0: 4.009802 3: 3.496961 \n" +
                "3: 4.366697 0: 3.37258 \n" +
                "1: 4.766935 2: 3.759853 \n" );
    }
}
