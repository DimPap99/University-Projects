package sample;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.*;


public class MainController implements Initializable {





        public void DrawPathOptimalPath(ObservableList<Records> States) throws InterruptedException {

            for (Records state:States) {
                //Draws the path that is an end state and has the lowest bound
                //This is the optimal path.
                if(state.bound == BranchAndBound.bound && Objects.equals(state.childrenStates.get(),"FINAL")){
                    for (String id:state.microscope.get().split(" ")) {
                        Rectangle rec = (Rectangle)GP.lookup("#"+id);
                        rec.setFill(javafx.scene.paint.Color.RED);
                    }
                }
            }

        }





    @FXML private TableColumn<Records, String> searchfrontCol;
    @FXML private TableColumn<Records, String> closedSetCol;
    @FXML private TableColumn<Records, String> microscopeCOl;
    @FXML private TableColumn<Records, Integer> boundCol;
    @FXML private TableColumn<Records, String> childrenStateCol;
    @FXML private GridPane GP;
    @FXML
    private TableView<Records> tableView;


    public void generateOptimalPath(ActionEvent event) throws InterruptedException {

        //populate our table and visualize the path
        ObservableList<Records> States = BranchAndBound.BranchAndBound();
        tableView.setItems(States);
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
        closedSetCol.setCellFactory(TextFieldTableCell.forTableColumn());

    }


}
