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
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static sample.Main.getTheStrPaths;

public class MainController implements Initializable {

    public String getLatestClosedSet(List<Path> closedSet){
        //System.out.println(closedSet.size());
        String latestSet = "";
        for (Path p: closedSet
             ) {
            latestSet = latestSet + p.getPathStr()+" ";

        }
        return latestSet;
    }

    //@FXML private TableView<Records> tableView;
    @FXML private TableColumn<Records, String> searchfrontCol;
    @FXML private TableColumn<Records, String> closedSetCol;
    @FXML private TableColumn<Records, String> microscopeCOl;
    @FXML private TableColumn<Records, Integer> boundCol;
    @FXML private TableColumn<Records, String> childrenStateCol;
    @FXML
    private TableView<Records> tableView;
   // @FXML
    //private TableColumn<Person, String> firstNameColumn;
    //@FXML
    //private TableColumn<Person, String> lastNameColumn;
    //@FXML
    //private TableColumn<Person, LocalDate> birthdayColumn;

    public void generateRandom(ActionEvent event) {
        ObservableList<Records> people = FXCollections.observableArrayList();
        ObservableList<Records> l = FXCollections.observableArrayList();

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
        boolean registeredBound = false;
        int counter = 1;
        while(MetwpoAnazhthshs.size() != 0){
            // if its not the first time that we get in the loop, add to the closedSet our the path that
            // we previously checked at the microscope, and add at the front of the MetwpoAnazitisis the previous
            // ChildrenStates

            if(counter != 1){
                closedSet.add(visitedPaths.get(visitedPaths.size() - 1));

            }
            //First we get the currentPath and add it to the microscope. If the counter is ven
            microscope = MetwpoAnazhthshs.pop();
            MetwpoAnazhthshs.push(microscope);
            if(graph.nodes.get(microscope.getLastNode()).is_end){
                //subtract 1 because the final element of the string array after .split is a blank string " "
                bound = microscope.getCost() ;
                registeredBound = true;}
            //System.out.println(Arrays.toString(getTheStrPaths(MetwpoAnazhthshs))+" "+microscope.getPathStr() +" ClosedSet: "+ getLatestClosedSet(closedSet) + " "+bound);
            //StringBuilder str = new StringBuilder(Arrays.toString(getTheStrPaths(MetwpoAnazhthshs)).replace("[","").replace("]",""));
            Records r = new Records(Arrays.toString(getTheStrPaths(MetwpoAnazhthshs)).replace("[","").replace("]",""),getLatestClosedSet(closedSet), microscope.getPathStr() ,bound,"[]" );
            l.add(r);
            MetwpoAnazhthshs.pop();
            visitedPaths.add(microscope);




                if(registeredBound){
                    registeredBound = false;
                continue;}

            if(microscope.getCost()  > bound) continue;
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
            //System.out.println(Arrays.toString(getTheStrPaths(childrenStates)));
            l.get(counter - 1).setChildrenStates(Arrays.toString(getTheStrPaths(childrenStates)));
            people.add(r);
            r = null;
            //After we get all the children we pop the stack until its empty and we assign its values in the MetwpoAnazitisis
            while (childrenStates.size()!=0){

                MetwpoAnazhthshs.push(childrenStates.pop());
            }
            counter++;





        }
        for (Records record:l) {
            System.out.println("SearchFront " + record.getSearchFront()+" Microscope "+record.getClosedSet()+" ClosedSet "+ record.getMicroscope() + " Bound "+record.getBound()+" Children " +record.getChildrenStates());
        }
        tableView.setItems(l);
       // System.out.println("        ");




    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        searchfrontCol.setCellValueFactory(new PropertyValueFactory<Records, String>("searchFront"));
        closedSetCol.setCellValueFactory(new PropertyValueFactory<Records, String>("closedSet"));
        microscopeCOl.setCellValueFactory(new PropertyValueFactory<Records, String>("microscope"));
        boundCol.setCellValueFactory(new PropertyValueFactory<Records, Integer>("bound"));
        childrenStateCol.setCellValueFactory(new PropertyValueFactory<Records, String>("childrenStates"));

        //load dummy data


        //Update the table to allow for the first and last name fields
        //to be editable
        tableView.setEditable(true);
        searchfrontCol.setCellFactory(TextFieldTableCell.forTableColumn());
        microscopeCOl.setCellFactory(TextFieldTableCell.forTableColumn());
        childrenStateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //closedSetCol.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    public ObservableList<Records> getPeople() {
        ObservableList<Records> people = FXCollections.observableArrayList();
        people.add(new Records("asdasd", "asdasd", "asdasd",1,"asdasd"));
        people.add(new Records("asdasd", "asdasd", "asdasd",1,"asdasd"));
        people.add(new Records("asdasd", "asdasd", "asdasd",1,"asdasd"));

        return people;
    }
}
