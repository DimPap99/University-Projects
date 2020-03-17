package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Path {

    private int cost = 0;
    public String pathStr = "";
    public List<Node> nodesInPath = new ArrayList<Node>();



    public int getCost() {
        return this.cost;
    }

    public void addNode(Node n){
        this.nodesInPath.add(n);
        this.cost = this.nodesInPath.size();
        this.pathStr = this.pathStr + " " + n.getNodeId();
    }

    public String getPathStr() {
        return pathStr;
    }
}
