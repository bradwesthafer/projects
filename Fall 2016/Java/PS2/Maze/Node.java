package Maze;

/**
 * Created by Brad on 9/30/2016.
 */
public class Node {
    private char data;
    private Node north;
    private Node south;
    private Node east;
    private Node west;

    public Node(char data, Node north, Node south, Node east, Node west) {
        this.data = data;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public char getData() {
        return data;
    }

    /*public void setData(char data) {
        this.data = data;
    }*/

    public Node getNorth() {
        return north;
    }

    public void setNorth(Node north) {
        this.north = north;
    }

    public Node getSouth() {
        return south;
    }

    public void setSouth(Node south) {
        this.south = south;
    }

    public Node getEast() {
        return east;
    }

    public void setEast(Node east) {
        this.east = east;
    }

    public Node getWest() {
        return west;
    }

    public void setWest(Node west) {
        this.west = west;
    }
}
