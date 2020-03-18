package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private int rows;
    private int columns;
    public List<Path> graphPaths = new ArrayList<Path>();
    public HashMap<String, Node> nodes = new HashMap<String, Node>();;
    Graph(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
    }

    //Creates the nodes of the graph and their respective neighbours
    public void createGraph(){

        for(int row = 0; row < this.rows; row++){
            for (int col = 0;col < this.columns; col++){
                boolean wall = false;
                if((col == 0 || col == 4) || ((row == 0 || row == 5 ) && col != 1) || ((row == 2 || row == 3) && col == 2)){
                    wall = true;
                }
                Node node = new Node(col,row,wall);
                if(col == 1 && row == 0) node.is_start = true;
                if(col == 1 && row == 5) node.is_end = true;
                this.nodes.put(node.getNodeId(),node);
            }
        }

        for(int row = 0; row < this.rows; row++){
            for (int col = 0;col < this.columns; col++) {
                String key = String.valueOf(row) +','+ col;

                Node current_Node = this.nodes.get(key);

                String[] possible_neighbors =
                        new String[]{(row - 1) + "," + col, row + "," + (col - 1),
                                (row + 1) + "," + col,row + "," + (col + 1)};
                for (String possNeighbor:possible_neighbors) {

                    if(this.nodes.containsKey(possNeighbor)){

                        current_Node.neighbours.add(possNeighbor);
                    }

                }
            }
        }


    }

    public void printGraph(){
        this.nodes.entrySet().forEach(stringNodeEntry -> {
            System.out.println(stringNodeEntry.getKey() + " " + stringNodeEntry.getValue().neighbours.toString());
        });
    }


}
