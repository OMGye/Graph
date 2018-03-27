package com.upupgogogo;

/**
 * Created by upupgogogo on 2018/3/26.上午11:40
 */
public class Edge implements Comparable<Edge> {

    private int start,dest,weight;

    public int getStart() {
        return start;
    }

    public int getDest() {
        return dest;
    }

    public int getWeight() {
        return weight;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Edge(int start, int dest, int weight){
        this.start = start;
        this.dest = dest;
        this.weight = weight;
    }

    public String toString() {
        return "("+start+","+dest+","+weight+")";
    }

    public int compareTo(Edge e) {
        if (this.start != e.start)
            return this.start - e.start;
        return this.dest - e.dest;
    }
}
