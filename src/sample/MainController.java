package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;

import static sample.Main.getTheStrPaths;

public class MainController implements Initializable {

    public String getLatestClosedSet(List<Path> closedSet){
        String latestSet = "";
        for (Path p: closedSet
             ) {
            latestSet = latestSet + p.getPathStr()+" ";

        }
        return latestSet;
    }



        public void DrawPathOptimalPath(ObservableList<Records> States) throws InterruptedException {

            for (Records state:States) {
                //Draws the path that is an end state and has the lowest bound
                if(state.bound == BranchAndBound.bound && Objects.equals(state.childrenStates.get(),"TELIKO")){
                    for (String id:state.microscope.get().split(" ")) {
                        Rectangle rec = (Rectangle)GP.lookup("#"+id);
                        rec.setFill(javafx.scene.paint.Color.RED);
                    }
                }
            }

        }




    //@FXML private TableView<Records> tableView;
    @FXML private TableColumn<Records, String> searchfrontCol;
    @FXML private TableColumn<Records, String> closedSetCol;
    @FXML private TableColumn<Records, String> microscopeCOl;
    @FXML private TableColumn<Records, Integer> boundCol;
    @FXML private TableColumn<Records, String> childrenStateCol;
    @FXML private GridPane GP;
    @FXML
    private TableView<Records> tableView;
   // @FXML
    //private TableColumn<Person, String> firstNameColumn;
    //@FXML
    //private TableColumn<Person, String> lastNameColumn;
    //@FXML
    //private TableColumn<Person, LocalDate> birthdayColumn;

    public void generateRandom(ActionEvent event) throws InterruptedException {


        ObservableList<Records> States = BranchAndBound.BranchAndBound();
        tableView.setItems(States);
        int index = 0;
        DrawPathOptimalPath(States);


    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        searchfrontCol.setCellValueFactory(new PropertyValueFactory<Records, String>("searchFront"));
        closedSetCol.setCellValueFactory(new PropertyValueFactory<Records, String>("closedSet"));
        microscopeCOl.setCellValueFactory(new PropertyValueFactory<Records, String>("microscope"));
        boundCol.setCellValueFactory(new PropertyValueFactory<Records, Integer>("bound"));
        childrenStateCol.setCellValueFactory(new PropertyValueFactory<Records, String>("childrenStates"));

        tableView.setEditable(true);
        searchfrontCol.setCellFactory(TextFieldTableCell.forTableColumn());
        microscopeCOl.setCellFactory(TextFieldTableCell.forTableColumn());
        childrenStateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //closedSetCol.setCellFactory(TextFieldTableCell.forTableColumn());

    }


}
