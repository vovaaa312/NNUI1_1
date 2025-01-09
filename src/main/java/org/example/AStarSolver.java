package org.example;

import java.util.*;

public class AStarSolver {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // north
            {1, 0},  // south
            {0, -1}, // west
            {0, 1}   // east
    };

    private int[][] maze;
    private int[] start;
    private int[] goal;

    private static final int TURN_LEFT_COST = 1;
    private static final int TURN_RIGHT_COST = 1;
    private static final int TURN_180_COST = 2;
    private static final int STEP_COST = 3;

    public AStarSolver(int[][] maze, int[] start, int[] goal) {
        this.maze = maze;
        this.start = start;
        this.goal = goal;
    }

    // Heuristic function - Manhattan distance
    private int heuristic(int x, int y) {
        return Math.abs(x - goal[0]) + Math.abs(y - goal[1]);
    }

    // Pathfinding function
    public void solve() {
        PriorityQueue<State> openList = new PriorityQueue<>(Comparator.comparingInt(State::getFCost));
        Set<String> closedList = new HashSet<>();

        // Initial state
        openList.add(new State(start[0], start[1], 0, 0, heuristic(start[0], start[1]), null));

        while (!openList.isEmpty()) {
            State current = openList.poll();

            // If we reached the goal
            if (current.x == goal[0] && current.y == goal[1]) {
                printPath(current);
                return;
            }

            // If the state has already been visited
            String stateKey = current.x + "," + current.y + "," + current.direction;
            if (closedList.contains(stateKey)) {
                continue;
            }
            closedList.add(stateKey);

            // Turns (left, right, 180°)
            for (int i = -1; i <= 1; i += 2) {
                int newDirection = (current.direction + i + 4) % 4;
                int newGCost = current.gCost + (i == 1 ? TURN_RIGHT_COST : TURN_LEFT_COST);
                int newHCost = heuristic(current.x, current.y);
                if (isValid(current.x, current.y)) {
                    openList.add(new State(current.x, current.y, newDirection, newGCost, newHCost, current));
                }
            }

            // 180° turn
            int newDirection = (current.direction + 2) % 4;
            int newGCost = current.gCost + TURN_180_COST;
            if (isValid(current.x, current.y)) {
                openList.add(new State(current.x, current.y, newDirection, newGCost, heuristic(current.x, current.y), current));
            }

            // Move one step forward
            int newX = current.x + DIRECTIONS[current.direction][0];
            int newY = current.y + DIRECTIONS[current.direction][1];
            if (isValid(newX, newY)) {
                openList.add(new State(newX, newY, current.direction, current.gCost + STEP_COST, heuristic(newX, newY), current));
            }
        }
    }

    // Check if the cell is valid
    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 1; // 1 - white cell
    }

    // Print the path
    // Print the path
    private void printPath(State goalState) {
        List<State> path = new ArrayList<>();
        State current = goalState;
        int totalCost = 0;

        while (current != null) {
            path.add(current);
            totalCost += current.gCost; // Add the cost of this step to the total
            current = current.parent;
        }
        Collections.reverse(path);

        for (State state : path) {
            System.out.println("X: " + state.x + ", Y: " + state.y + ", Direction: " + state.direction);
        }

        // Print the total cost of the path
        System.out.println("Total path cost: " + totalCost);

        // Save the solution to a new BMP file
        BMPToBinaryArray.saveSolution("MAZE1.bmp", "MAZEsolved.bmp", path);
    }



//    private void printPath(State goalState) {
//        List<State> path = new ArrayList<>();
//        State current = goalState;
//        int totalCost = 0;
//
//        while (current != null) {
//            path.add(current);
//            totalCost += current.gCost; // Add the cost of this step to the total
//            current = current.parent;
//        }
//        Collections.reverse(path);
//
//        for (State state : path) {
//            System.out.println("X: " + state.x + ", Y: " + state.y + ", Direction: " + state.direction);
//        }
//
//        // Print the total cost of the path
//        System.out.println("Total path cost: " + totalCost);
//    }
}
