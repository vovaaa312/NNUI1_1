package org.example;

public class State {
    int x, y, direction, gCost, hCost;
    State parent;

    public State(int x, int y, int direction, int gCost, int hCost, State parent) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }

    // Path cost: gCost + hCost (F-cost)
    public int getFCost() {
        return gCost + hCost;
    }
}
