package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Records {
    SimpleStringProperty searchFront;
    SimpleStringProperty closedSet;
    public SimpleStringProperty microscope;
    int bound;
    SimpleStringProperty childrenStates;
    Records(){};
    Records(String searchFront , String closedSet, String microscope, int bound, String childrenStates){
        this.searchFront = new SimpleStringProperty(searchFront);
        this.closedSet = new SimpleStringProperty(closedSet);
        this.microscope = new SimpleStringProperty(microscope);
        this.bound = bound;
        this.childrenStates = new SimpleStringProperty(childrenStates);
    }

    public String getSearchFront() {
        return searchFront.get();
    }

    public SimpleStringProperty searchFrontProperty() {
        return searchFront;
    }


    public void setSearchFront(String searchFront) {
        this.searchFront.set(searchFront);
    }

    public String getClosedSet() {
        return closedSet.get();
    }

    public SimpleStringProperty closedSetProperty() {
        return closedSet;
    }

    public void setClosedSet(String closedSet) {
        this.closedSet.set(closedSet);
    }

    public String getMicroscope() {
        return microscope.get();
    }

    public SimpleStringProperty microscopeProperty() {
        return microscope;
    }

    public void setMicroscope(String microscope) {
        this.microscope.set(microscope);
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public String getChildrenStates() {
        return childrenStates.get();
    }

    public SimpleStringProperty childrenStatesProperty() {
        return childrenStates;
    }

    public void setChildrenStates(String childrenStates) {
        this.childrenStates.set(childrenStates);
    }


    public void printRecord(){
        System.out.println("SearchFront:" + this.searchFront.get() + " ClosedSet " + this.closedSet.get() + " Microscope " + this.microscope.get() + " Bound " + this.bound
        +" ChildrenState " + this.childrenStates.get());
    }
}
