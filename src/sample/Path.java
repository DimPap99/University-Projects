package sample;

import jdk.jfr.StackTrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Path implements Cloneable {

    private int cost = 0;
    public String pathStr = "";


    Path(String path){
        this.pathStr = path;
    }
    Path(Path otherPath){
        this.cost = otherPath.cost;
        this.pathStr = otherPath.pathStr;

    }

    public int getCost() {
        return this.pathStr.split(" ").length;
    }

    public void addNode(Node n){


        this.pathStr = this.pathStr + n.getNodeId() + " ";
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(!Path.class.isAssignableFrom(obj.getClass())){
            return false;
        }

        final Path otherPath = (Path) obj;

        if((this.pathStr != null) ? (otherPath.pathStr != null) :this.pathStr.equals(otherPath.pathStr) ){
            return false;
        }
        if (this.cost != otherPath.cost){
            return false;
        }
        return true;

    }

    //https://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java/27609#27609
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.pathStr != null ? this.pathStr.hashCode() : 0);
        hash = 53 * hash + this.cost;
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return (Path)super.clone();
    }

    public String  getLastNode(){
        try{
        String[] strarray =  this.pathStr.split(" ");
        return strarray[strarray.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public String getPathStr() {
        return pathStr;
    }
}
