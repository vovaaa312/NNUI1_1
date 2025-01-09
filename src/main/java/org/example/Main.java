package org.example;

public class Main {
    public static void main(String[] args) {
        int[][] maze = BMPToBinaryArray.getArray("MAZE1.bmp");

        int[] start = {3, 15};
        int[] goal = {29, 5};

        AStarSolver solver = new AStarSolver(maze, start, goal);
        solver.solve();
    }
}