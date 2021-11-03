package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.print.DocFlavor;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BranchAndBound {
    static int bound = Integer.MAX_VALUE;
    public static String getLatestClosedSet(List<Path> closedSet){

        String latestSet = "";
        for (Path p: closedSet
        ) {
            latestSet = latestSet + p.getPathStr()+" ";

        }
        return latestSet;
    }

    public static String[] getTheStrPaths(Stack<Path> paths){
        String[] arr = new String[paths.size()];
        for(int i = 0; i < paths.size();i++){
            arr[i] = paths.get(i).getPathStr();
        }
        return arr;
    }

    public static String[] getReversedArray(String[] array){
        String[] temp = new String[array.length];
        int j = 0;
        for(int i = array.length - 1;i>-1;i--){
            temp[j] = array[i];
            j++;
        }
        return temp;
    }
    // It uses DFS search with priority at moving down.
    public static  ObservableList<Records> BranchAndBound(){




        //Initialize our initial values
        Graph graph = new Graph(6,5);
        graph.createGraph();
        int counter = 1;
        Stack<Path> searchFront = new Stack<Path>();
        List<Path> closedSet = new ArrayList<Path>();
        Path microscope = new Path("0,1 ");
        searchFront.push(microscope);

        Stack<Path> childrenStates = new Stack<Path>();
        List<Path> visitedPaths = new ArrayList<Path>();

        ObservableList<Records> rs= FXCollections.observableArrayList();

        microscope = null;
        boolean gotLastNode = false;
        //Keep running until the searchFront stack is empty
        while(searchFront.size() != 0){
            // assign to the closed set after one loop.
            if(counter != 1){
                closedSet.add(visitedPaths.get(visitedPaths.size() - 1));
            }
            Records r = new Records(Arrays.toString(getReversedArray(getTheStrPaths(searchFront))).replace("[","").replace("]",""),"EMPTY SET",
                    "", bound,"[]");
            microscope = searchFront.pop();
            if(counter !=1)r.setClosedSet(getLatestClosedSet(closedSet));
            if(microscope.getCost()<bound && graph.nodes.get(microscope.getLastNode()).is_end )r.setBound(microscope.getCost());

            r.setMicroscope(microscope.getPathStr());
            r.setChildrenStates("[]");

            visitedPaths.add(microscope);
            // if we find an end node we update the bound to the cost of the path that it belongs and we continue without
            // finding its children
            if(graph.nodes.get(microscope.getLastNode()).is_end){

                visitedPaths.add(microscope);
                r.setChildrenStates("[FINAL]".replace("[","").replace("]",""));
                r.setBound(microscope.getCost());

                gotLastNode = true;
                bound = microscope.getCost();

                rs.add(r);
                continue;
            }


            // if the path at the microscope has a bigger bound than the current best global bound
            // we prune it since we know it wont lead to a better solution
            if(microscope.getCost()  > bound){
                r.setChildrenStates("[Prunned current cost "+microscope.getCost()+" > bound "+bound+"]");
                rs.add(r);
                continue;}
            // Find the children of the last node of our path, combine them and create the new paths
            // check that we dont loopback to already checked paths that exists in the closed set.
            // Avoid checking nodes that are walls
            for (String neighbour :graph.nodes.get(microscope.getLastNode()).neighbours) {
                Node currentNeighbour = graph.nodes.get(neighbour);
                Path childPath = new Path(microscope);

                if(childPath.getPathStr().contains(currentNeighbour.getNodeId())){
                    continue;
                }
                childPath.addNode(currentNeighbour);
                if (!closedSet.contains(childPath) && !graph.nodes.get(childPath.getLastNode()).is_Wall ){

                    childrenStates.push(childPath);
                }
            }

            r.setChildrenStates(Arrays.toString(getTheStrPaths(childrenStates)));

            while (childrenStates.size()!=0){

                searchFront.push(childrenStates.pop());
            }
            rs.add(r);
            counter++;
        }

        rs.add(new Records("EMPTY SET",getLatestClosedSet(closedSet),
                "EMPTY SET ", bound,"[EMPTY SET]"));
        for(int i = 0; i<rs.size();i++) {
            rs.get(i).printRecord();
        }


        return rs;
    }
}
