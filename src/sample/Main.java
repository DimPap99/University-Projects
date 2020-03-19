package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Maze Solver");
        primaryStage.setScene(new Scene(root, 1141, 600));


        primaryStage.show();

    }

    public static String[] getTheStrPaths(List<Path> paths){
        String[] arr = new String[paths.size()];
        for(int i = 0; i < paths.size();i++){
            arr[i] = paths.get(i).getPathStr();
        }
        return arr;
    }


    public static void main(String[] args) throws CloneNotSupportedException {


        launch(args);




        }


}
