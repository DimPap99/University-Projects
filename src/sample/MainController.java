package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.awt.*;
import javafx.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainController {

    public void generateRandom(ActionEvent event){
        Graph graph = new Graph(6,5);
        final  ObservableList<String> test = FXCollections.observableArrayList();

        graph.createGraph();
        graph.printGraph();
        List<Path> MetwpoAnazhthshs = new ArrayList<Path>();
        Path p = new Path();
        p.addNode(graph.nodes.get("0,1"));
        System.out.println(p.getPathStr() +" "+ p.getCost());
    }


}
