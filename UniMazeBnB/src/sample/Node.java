package sample;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public int column;
    public int row;
    public List<String> neighbours = new ArrayList<String>();
    public boolean is_Wall;
    public boolean is_start;
    public boolean is_end;


    Node(int col,int row, boolean wall){
        this.column = col;
        this.row = row;
        this.is_Wall = wall;
    }

    public String getNodeId(){

        return String.valueOf(this.row) +','+ this.column;
    }


}
