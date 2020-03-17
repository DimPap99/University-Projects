package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Maze Solver");
        primaryStage.setScene(new Scene(root, 1060, 600));


        primaryStage.show();

    }


    public static void main(String[] args) throws CloneNotSupportedException {

        Graph graph = new Graph(6,5);
        graph.createGraph();

        Stack<Path> MetwpoAnazhthshs = new Stack<Path>();
        List<Path> closedSet = new ArrayList<Path>();
        Path microscope = new Path();
        List<Path> visitedPaths = new ArrayList<Path>();
        Stack<Path> childrenStates = new Stack<Path>();

        Path I = new Path();
        I.addNode(graph.nodes.get("0,1"));
        MetwpoAnazhthshs.push(I);
        int bound = Integer.MAX_VALUE;

        int counter = 1;
        while(MetwpoAnazhthshs.size() != 0){
            // if its not the first time that we get in the loop, add to the closedSet our the path that
            // we previously checked at the microscope, and add at the front of the MetwpoAnazitisis the previous
            // ChildrenStates
            System.out.println(microscope.getPathStr());
            if(counter != 1){
                closedSet.add(visitedPaths.get(visitedPaths.size() - 1));

            }
            //First we get the currentPath and add it to the microscope. If the counter is ven
            microscope = MetwpoAnazhthshs.pop();
            visitedPaths.add(microscope);
            if(graph.nodes.get(microscope.getLastNode()).is_end){
                bound = microscope.getCost();
            }
            //For each last node in our path check its neighbours
            for (String neighbour :graph.nodes.get(microscope.getLastNode()).neighbours) {
                Node currentNeighbour = graph.nodes.get(neighbour);
                Path childPath = new Path(microscope);

                if(childPath.getPathStr().contains(currentNeighbour.getNodeId())){
                    continue;

                }

                childPath.addNode(currentNeighbour);

                // if the last node of the child Path is not a wall and the child path is not already inside the closedSet
                // and we havent backtracked to a previous node Eg 0-1 1-1 0-1.
                // the we add it at the current states ChildrenStates stack.
                if (!closedSet.contains(childPath) && !graph.nodes.get(childPath.getLastNode()).is_Wall ){
                    //System.out.println(childPath.getPathStr());
                    childrenStates.push(childPath);
                }
            }
            //After we get all the children we pop the stack until its empty and we assign its values in the MetwpoAnazitisis
            while (childrenStates.size()!=0){

                MetwpoAnazhthshs.push(childrenStates.pop());
            }
            counter++;





        }

    }
}
