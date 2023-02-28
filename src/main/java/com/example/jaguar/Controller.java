package com.example.jaguar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;



public class Controller {
    @FXML
    public TextField NumberOfRows;
    @FXML
    public TextField NumberOfColumns;
    @FXML
    public TextField MinimumWeight;
    @FXML
    public TextField MaximumWeight;
    @FXML
    public Button GenerateButton;
    @FXML
    public Text MessagesAndErrorsText;
    @FXML
    public CheckBox AllConnections;
    @FXML
    public CheckBox MustBeConnected;
    @FXML
    public Canvas graphDrawing;
    @FXML
    public Canvas weightScale;
    @FXML
    public ScrollPane graphScrollPane;
    @FXML
    public Button DijkstraButton;
    @FXML
    public TextField StartVertex;
    @FXML
    public TextField EndVertex;
    @FXML
    public Text WSmin;
    @FXML
    public Text WSmax;

    private int Columns=2;

    private int Rows=2;

    private double MinWeight=0;

    private double MaxWeight=1;

    public static String MessagesAndErrors = "";

    private boolean MustBeConnectedValue = false;

    private boolean AllConnectionsValue = false;

    private boolean ErrorsInGraphParameters = false;

    private boolean ErrorsInDijkstraParameters = false;
    Graph graphToShow;

    private GraphicsContext graphicsContext;

    private GraphicsContext weightContext;

    private int StartVertexValue;

    private int EndVertexValue;

    private ArrayList<Integer[]> DijkstraPaths = new ArrayList<>();

    private Dijkstra dijkstra;

    @FXML
    public ListView<AnchorPane> pathsList;

    public ObservableList<AnchorPane> paneList = FXCollections.observableArrayList();

    public ArrayList<PathContainer> pathsPaneList = new ArrayList<>();

    private boolean ifCanvasClicked = false;

    private int startVertexByCanvas;

    public void addNewText(String newText) {
        MessagesAndErrors += newText + "\n";
        MessagesAndErrorsText.setText(MessagesAndErrors);
    }

    public void setMinimumWeight() {
        try{
            if(MinimumWeight.getText() == "");
            else if(Double.parseDouble(MinimumWeight.getText()) >= 0){
                MinWeight=Double.parseDouble(MinimumWeight.getText());
            }
            else {
                addNewText("The minimum weight must be higher than 0");
                ErrorsInGraphParameters =true;
            }
        }
        catch(Exception e){
            ErrorsInGraphParameters =true;
            addNewText("The minimum weight should be positive number higher than 0");
        }
    }
    public void setMaximumWeight() {
        try{
            if(MinimumWeight.getText() == "");
            else if(Double.parseDouble(MaximumWeight.getText()) > MinWeight) {
                MaxWeight=Double.parseDouble(MaximumWeight.getText());
            }
            else if(Double.parseDouble(MaximumWeight.getText()) <0) {
                addNewText("The maximum weight must be higher than 0");
                ErrorsInGraphParameters = true;
            }
            else {
                addNewText("The maximum weight must be higher than the minimum one");
                ErrorsInGraphParameters = true;
            }
        }
        catch(Exception e) {
            addNewText("The maximum weight should be positive number higher than 0");
            ErrorsInGraphParameters =true;
        }
    }

    public int setNumberOfRowsAndColumns() {
        try{
            if(Integer.parseInt(NumberOfRows.getText()) >= 2 && Integer.parseInt(NumberOfColumns.getText()) >=2){
                Columns = Integer.parseInt(NumberOfColumns.getText());
                Rows = Integer.parseInt(NumberOfRows.getText());
                return 1;
            }
            else if(Integer.parseInt(NumberOfRows.getText()) <2 || Integer.parseInt(NumberOfColumns.getText())<2) {
                ErrorsInGraphParameters =true;
                addNewText("The number of rows and columns must be higher or equal than/to 2");
                return -1;
            }
        }
        catch(Exception e) {
            ErrorsInGraphParameters =true;
            addNewText("The number of rows and columns must be an integer");
            return -1;
        }
        return 0;
    }

    public void graphGenerate(ActionEvent actionEvent) {
        ErrorsInGraphParameters = false;
        setMinimumWeight();
        setMaximumWeight();
        if(setNumberOfRowsAndColumns() == -1) return;
        if(!ErrorsInGraphParameters) {
            Generator generator = new Generator();
            if (AllConnectionsValue && MustBeConnectedValue) {
                graphToShow = generator.tlw(Rows, Columns, MinWeight, MaxWeight, 1);
                addNewText("Graph was generated with the following parameters:" + "\n" + "Number of Rows: " + Rows + " Number of Columns: " + Columns +
                        "\n" + "Weight range from " + MinWeight + " to " + MaxWeight + "\n" + "This graph is connected and includes all connections between neighbors");
            }
            else if (!AllConnectionsValue && MustBeConnectedValue) {
                BFS bfsUsedForGeneration;
                DFS dfsUsedForGeneration;
                do {
                    graphToShow = generator.tlw(Rows, Columns, MinWeight, MaxWeight, 0.8);
                    bfsUsedForGeneration = new BFS(graphToShow);
                    dfsUsedForGeneration = new DFS(graphToShow);

                } while(!(bfsUsedForGeneration.getResult() && dfsUsedForGeneration.getResult()));
                addNewText("Graph was generated with the following parameters:" + "\n" + "Number of Rows: " + Rows + " Number of Columns: " + Columns +
                        "\n" + "Weight range from " + MinWeight + " to " + MaxWeight + "\n" + "This graph is connected and does not include all connections between neighbors");

            }
            else if(AllConnectionsValue) {
                addNewText("It is impossible to create not connected graph with all connections");
            }
            else {
                graphToShow = generator.tlw(Rows, Columns, MinWeight, MaxWeight, 0.6);
                addNewText("Graph was generated with the following parameters:" + "\n" + "Number of Rows: " + Rows + " Number of Columns: " + Columns +
                        "\n" + "Weight range from " + MinWeight + " to " + MaxWeight + "\n" + "This graph is not need to be connected and does not include all connections between neighbors");

            }
            paneList.clear();
            DijkstraPaths.clear();
            drawGraph();
            drawTheWageScale();
        }
    }


    public void changeAllConnectionStatus(ActionEvent actionEvent) {
        AllConnectionsValue = !AllConnectionsValue;
    }

    public void changeConnectedStatus(ActionEvent actionEvent) {
        MustBeConnectedValue = !MustBeConnectedValue;
    }

    public void deleteMessagesAndErrorsText(ActionEvent actionEvent) {
        MessagesAndErrors = "";
        MessagesAndErrorsText.setText(MessagesAndErrors);
    }
    public void loadGraphFromFile(ActionEvent actionEvent)  {
        try {
            FileChooser OpenFile = new FileChooser();
            OpenFile.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt Files", "*.txt"));
            graphToShow = new Graph();
            graphToShow.readFromFile(String.valueOf(OpenFile.showOpenDialog(null)));
            addNewText("Graph was loaded successfully");
            MinWeight = graphToShow.getMinimumCost();
            MaxWeight = BigDecimal.valueOf(graphToShow.getMaximumCost()).setScale(3, RoundingMode.HALF_UP).doubleValue();
            MinWeight = BigDecimal.valueOf(graphToShow.getMinimumCost()).setScale(3, RoundingMode.HALF_UP).doubleValue();
            DijkstraPaths.clear();
            drawGraph();
            paneList.clear();
            drawTheWageScale();
        }
        catch(RuntimeException e){
            addNewText("Could not load the file");
        }
        catch(IOException f){
            addNewText("Weights cannot be negative numbers");
        }
    }

    public void saveGraphToFile(ActionEvent actionEvent) {
        if(graphToShow==null){
            addNewText("There is nothing to save");
            return;
        }
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt Files", "*.txt"));
            graphToShow.writeToFile(String.valueOf(fileChooser.showSaveDialog(null)));
            addNewText("Graph was saved successfully");
        } catch (IOException e) {
            addNewText("Could not save the file");
        }
    }

    private double sizeOfElementInGraph;

    public void drawGraph() {
        if(graphToShow == null) return;
        graphicsContext = graphDrawing.getGraphicsContext2D();
        graphicsContext.clearRect(0,0,graphDrawing.getWidth(), graphDrawing.getHeight());
        graphDrawing.setWidth(751.00);
        graphDrawing.setHeight(611.00);
        double sizeOfGraphElement;
        if(graphToShow.getColumn() > graphToShow.getRow()) {
            sizeOfGraphElement = graphDrawing.getWidth()/(2*graphToShow.getColumn());
        }
        else sizeOfGraphElement = graphDrawing.getHeight()/(2*graphToShow.getRow());
        if(sizeOfGraphElement<20) {
            sizeOfGraphElement = 20;
            graphDrawing.setHeight(sizeOfGraphElement*graphToShow.getRow()*2);
            graphDrawing.setWidth(sizeOfGraphElement*graphToShow.getColumn()*2);
        }
        sizeOfElementInGraph = sizeOfGraphElement;
        double height = graphDrawing.getHeight()/(2*graphToShow.getRow()-1);
        double width = graphDrawing.getWidth()/(2*graphToShow.getColumn()-1);
        double startX, startY;
        int destination;
        int currentVertex;
        double weight;
        graphicsContext.setFill(Color.GRAY);
        for(int i = 0;i<graphToShow.getColumn();i++) {
            for(int j = 0; j<graphToShow.getRow();j++) {
                startX = 2*i*width;
                startY = 2*j*height;
                currentVertex = j*graphToShow.getColumn() + i;
                for(int h =0; h<graphToShow.graphStructure.get(currentVertex).getNeighbours().size();h++) {
                    graphicsContext.setLineWidth(sizeOfGraphElement/8);
                    weight = graphToShow.graphStructure.get(currentVertex).getNeighbours().get(h).weight();
                    graphicsContext.setStroke(assignColor(MinWeight,MaxWeight,weight));
                    destination = graphToShow.graphStructure.get(currentVertex).getNeighbours().get(h).destination();
                    if (currentVertex - destination == 1) {
                        graphicsContext.strokeLine(startX-(2*width-sizeOfGraphElement)/2 + sizeOfGraphElement/16, startY + sizeOfGraphElement/2, startX + sizeOfGraphElement/2 , startY + sizeOfGraphElement/2 );
                    }
                    if(currentVertex - destination == -1) {
                        graphicsContext.strokeLine(startX+sizeOfGraphElement/2, startY+sizeOfGraphElement/2, startX + sizeOfGraphElement +(2*width-sizeOfGraphElement)/2 - sizeOfGraphElement/16, startY + sizeOfGraphElement/2);
                    }
                    if(currentVertex-destination > 1) {
                        graphicsContext.strokeLine(startX + sizeOfGraphElement/2, startY - (2*height-sizeOfGraphElement)/2 + sizeOfGraphElement/16 , startX+sizeOfGraphElement/2, startY + sizeOfGraphElement/2 );
                    }
                    if(currentVertex-destination < -1) {
                        graphicsContext.strokeLine(startX+sizeOfGraphElement/2, startY + sizeOfGraphElement/2 , startX + sizeOfGraphElement/2, startY + sizeOfGraphElement + (2*height-sizeOfGraphElement)/2 -sizeOfGraphElement/16 );
                    }
                }
                graphicsContext.fillOval(startX, startY, sizeOfGraphElement, sizeOfGraphElement);
            }
        }
    }
    public Color assignColor(double min, double max, double v){
        return Color.hsb(Color.RED.getHue() + (Color.PURPLE.getHue() - Color.RED.getHue()) * (v-min)/(max-min), 1.0, 1.0);
    }

    public void drawThePaths() {
        double startX, startY;
        double height = graphDrawing.getHeight()/(2*graphToShow.getRow()-1);
        double width = graphDrawing.getWidth()/(2*graphToShow.getColumn()-1);
        int currentVertex;
        int nextVertex, i, j;
        for(int w = 0; w < DijkstraPaths.size();w++) {
            for(int z = 0; z <DijkstraPaths.get(w).length;z++) {
                i = DijkstraPaths.get(w)[z] % graphToShow.getColumn();
                j = (DijkstraPaths.get(w)[z] - i )/graphToShow.getColumn();
                startX = 2*i*width;
                startY = 2*j*height;
                currentVertex = j*graphToShow.getColumn() + i;
                graphicsContext.setLineWidth(sizeOfElementInGraph/7);
                if(w==DijkstraPaths.size()-1) graphicsContext.setStroke(Color.SANDYBROWN);
                else graphicsContext.setStroke(Color.BLACK);
                if(w==DijkstraPaths.size()-1) graphicsContext.setFill(Color.SANDYBROWN);
                else graphicsContext.setFill(Color.BLACK);
                try {
                    nextVertex = DijkstraPaths.get(w)[z+1];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    graphicsContext.fillOval(startX, startY, sizeOfElementInGraph, sizeOfElementInGraph);
                    break;
                }
                if (currentVertex - nextVertex == 1) {
                    graphicsContext.strokeLine(startX + sizeOfElementInGraph/2 - 2*width, startY + sizeOfElementInGraph/2, startX + sizeOfElementInGraph/2 , startY + sizeOfElementInGraph/2 );
                }
                else if(currentVertex - nextVertex == -1) {
                    graphicsContext.strokeLine(startX+sizeOfElementInGraph/2, startY+sizeOfElementInGraph/2, startX + sizeOfElementInGraph/2 + 2*width, startY + sizeOfElementInGraph/2);
                }
                else if(currentVertex-nextVertex > 1) {
                    graphicsContext.strokeLine(startX + sizeOfElementInGraph/2, startY + sizeOfElementInGraph/2 - 2*height  , startX+sizeOfElementInGraph/2, startY + sizeOfElementInGraph/2 );
                }
                else if(currentVertex-nextVertex < -1) {
                    graphicsContext.strokeLine(startX+sizeOfElementInGraph/2, startY + sizeOfElementInGraph/2 , startX + sizeOfElementInGraph/2, startY + sizeOfElementInGraph/2 + 2*height );
                }
                graphicsContext.fillOval(startX, startY, sizeOfElementInGraph, sizeOfElementInGraph);
            }
        }
    }

    public void findTheShortestPath() {
        if(graphToShow == null) {
            addNewText("There is no graph loaded");
            return;
        }
        ErrorsInDijkstraParameters = false;
        try{
            if(Integer.parseInt(StartVertex.getText()) >= 0 && Integer.parseInt(EndVertex.getText()) >=0 && Integer.parseInt(StartVertex.getText()) < graphToShow.numberOfVertexes() && Integer.parseInt(EndVertex.getText()) <graphToShow.numberOfVertexes()){
                StartVertexValue = Integer.parseInt(StartVertex.getText());
                EndVertexValue = Integer.parseInt(EndVertex.getText());
            }
            else if(Integer.parseInt(StartVertex.getText()) <0 || Integer.parseInt(EndVertex.getText())<0) {
                ErrorsInDijkstraParameters =true;
                addNewText("Start and end vertex must be positive numbers");
            }
            else if(Integer.parseInt(StartVertex.getText()) >= graphToShow.numberOfVertexes() || Integer.parseInt(EndVertex.getText()) >= graphToShow.numberOfVertexes()) {
                addNewText("Start and end vertex cannot be higher than number of vertexes in current graph");
                ErrorsInDijkstraParameters = true;
            }
        }
        catch(Exception e) {
            ErrorsInDijkstraParameters =true;
            addNewText("Start and end vertex must be an integer number");
        }
        if(!ErrorsInDijkstraParameters) {
         dijkstra = new Dijkstra(graphToShow, StartVertexValue, EndVertexValue);
         if(!dijkstra.getIfPathExists()) {
             addNewText("The path from " + StartVertexValue + " to " + EndVertexValue + " does not exist" );
             return;
         }
         DijkstraPaths.add(dijkstra.getFinalPathArray());
         drawThePaths();
         drawTheWageScale();
         PathContainer pathContainer = new PathContainer(dijkstra,dijkstra.getFinalPathArray() , StartVertexValue, EndVertexValue, dijkstra.getFinalCost(), dijkstra.getPath());
         pathsList.setItems(paneList);
        }
    }

    public void drawTheWageScale() {
        WSmin.setText(Double.toString(MinWeight));
        WSmax.setText(Double.toString(MaxWeight));
        weightContext = weightScale.getGraphicsContext2D();
        weightContext.clearRect(0,0,weightScale.getWidth(), weightScale.getHeight());
        for(int i = 1; i<=200;i++) {
            weightContext.setStroke(assignColor(0, 1, (double) i / 200));
            weightContext.strokeLine(i, 0,i +1 , weightScale.getHeight());
        }
    }

    public void chooseVertexes(MouseEvent mouseEvent) {
        if(graphToShow == null) return;
        double positionX = mouseEvent.getX();
        double positionY = mouseEvent.getY();
        double startX, startY;
        double height = graphDrawing.getHeight()/(2*graphToShow.getRow()-1);
        double width = graphDrawing.getWidth()/(2*graphToShow.getColumn()-1);
        for(int i = 0; i < graphToShow.getColumn(); i++) {
            for(int j =0; j < graphToShow.getRow(); j++) {
                startX = 2*i*width + sizeOfElementInGraph/2;
                startY = 2*j*height + sizeOfElementInGraph/2;
                if(positionX >= startX - sizeOfElementInGraph/2 && positionX <= startX + sizeOfElementInGraph/2 && positionY >= startY - sizeOfElementInGraph/2 && positionY <= startY + sizeOfElementInGraph/2) {
                    if(!ifCanvasClicked) {
                        startVertexByCanvas = j*graphToShow.getColumn() + i;
                        ifCanvasClicked = true;
                        graphicsContext.setFill(Color.WHITE);
                        graphicsContext.fillOval(startX-sizeOfElementInGraph/2, startY-sizeOfElementInGraph/2, sizeOfElementInGraph, sizeOfElementInGraph);
                    }
                    else {
                        if(startVertexByCanvas == j*graphToShow.getColumn() + i) {
                            ifCanvasClicked=false;
                            graphicsContext.setFill(Color.GRAY);
                            graphicsContext.fillOval(startX-sizeOfElementInGraph/2, startY-sizeOfElementInGraph/2, sizeOfElementInGraph, sizeOfElementInGraph);
                            break;
                        }
                        StartVertexValue = startVertexByCanvas;
                        EndVertexValue = j*graphToShow.getColumn()+i;
                        dijkstra = new Dijkstra(graphToShow, startVertexByCanvas, j*graphToShow.getColumn() + i);
                        ifCanvasClicked = false;
                        if(!dijkstra.getIfPathExists()) {
                            addNewText("The path from " + StartVertexValue + " to " + EndVertexValue + " does not exist" );
                            ifCanvasClicked = true;
                            return;
                        }
                        DijkstraPaths.add(dijkstra.getFinalPathArray());
                        drawThePaths();
                        drawTheWageScale();
                        PathContainer pathContainer = new PathContainer(dijkstra,dijkstra.getFinalPathArray() , StartVertexValue, EndVertexValue, dijkstra.getFinalCost(), dijkstra.getPath());
                        pathsList.setItems(paneList);
                    }
                }
            }
        }
    }

    public void useBFS() {
        BFS bfs = new BFS(graphToShow);
        if(bfs.getResult()) addNewText("This graph is connected");
        else addNewText("This graph is not connected");
    }

    public void useDFS() {
        DFS dfs = new DFS(graphToShow);
        if(dfs.getResult()) addNewText("This graph is connected");
        else addNewText("This graph is not connected");
    }

    public class PathContainer {
        private Dijkstra dijkstra;
        private int start;
        private int end;
        private double cost;

        private String path;
        private AnchorPane pathPane;

        private Integer [] pathIndexes;

        public PathContainer(Dijkstra dijkstra, Integer [] pathIndexes, int start, int end, double cost, String path) {
            this.dijkstra = dijkstra;
            this.start = start;
            this.end = end;
            this.cost = cost;
            this.path = path;
            this.pathIndexes = pathIndexes;
            addPathPane();
        }

        public void addPathPane() {
            pathPane = new AnchorPane();
            Rectangle r = new Rectangle(130,170);
            r.setFill(Color.web("#808080"));
            Label startL = new Label("From: " + start);
            Label endL = new Label("To: " + end);
            Label costL = new Label("Cost: " + String.format("%.4f" ,cost));
            Button deleteThisPath = new Button("Delete");
            deleteThisPath.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    remove();
                    drawGraph();
                    drawThePaths();
                }
            });
            Button showPathInfo = new Button("Show Info");
            showPathInfo.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    addNewText(path);
                }
            });
            Button selectThisPath = new Button("Select This Path");
            selectThisPath.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    remove();
                    addAgain();
                    drawGraph();
                    drawThePaths();
                }
            });
            pathPane.getChildren().add(r);
            r.setLayoutX(2);
            r.setLayoutY(6);
            pathPane.setPadding(new Insets(5,5,5,5));
            pathPane.getChildren().addAll(startL, endL, costL, deleteThisPath, showPathInfo, selectThisPath);
            startL.setLayoutX(13);
            startL.setLayoutY(20);
            endL.setLayoutX(13);
            endL.setLayoutY(37);
            costL.setLayoutX(13);
            costL.setLayoutY(54);
            deleteThisPath.setLayoutX(40);
            deleteThisPath.setLayoutY(71);
            showPathInfo.setLayoutX(30);
            showPathInfo.setLayoutY(105);
            selectThisPath.setLayoutX(20);
            selectThisPath.setLayoutY(139);
            pathsPaneList.add(this);
            paneList.add(pathPane);
        }
        public void addAgain() {
            pathsPaneList.add(this);
            paneList.add(pathPane);
            DijkstraPaths.add(pathIndexes);
        }
        public void remove() {
            DijkstraPaths.remove(this.pathIndexes);
            pathsPaneList.remove(this);
            paneList.remove(this.pathPane);
        }
    }
}

