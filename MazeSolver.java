package hw2;
import java.util.ArrayList;
public class MazeSolver {
    private final char OPEN = '.';
    private final char CLOSED = '#';
    private final char START = 'S';
    private final char GOAL = 'G';
    private final char MARKED = '+';
    private final char UNMARKED = 'x';
    private char[][] maze;
    private ArrayList<String> mazePath = new ArrayList<String>();
    private int numCellsVisited;
    private int mazesTried;
    private int mazesSolved;

    public boolean solveMaze(char[][] maze){
        mazePath.clear();
        this.maze = maze;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == START) { //make sure to start at the actual start square
                    if(findPath(i, j)) { //if a path to the GOAL exists...
                        mazesSolved++;
                        mazesTried++;
                        return true;
                    }
                }
            }
        }
        mazesTried++;
        return false;
    }
    /**
     * Finds a path to the GOAL from the y and x coordinates used respectively
     * @param y the y value (in index form - 0 to length)
     * @param x the x value (in index form - 0 to length)
     * @return true if a path to the goal was found (from specified coordinates y, x), false otherwise
     */
    public boolean findPath(int y, int x) {
        if (y < 0 || x < 0 || x >= maze[0].length || y >= maze.length) //assuming each maze is a square this will work
            return false;
        if (maze[y][x] == GOAL) {
            mazePath.add(x + "," + y);
            for(int i = 0; i < mazePath.size() - 1; i++){
                String element = mazePath.get(i);
                String nextElement = mazePath.get(i+1);
                int x1 = Integer.parseInt(element.substring(0, element.indexOf(",")));
                int y1 = Integer.parseInt(element.substring(element.indexOf(",") + 1));
                int x2 = Integer.parseInt(nextElement.substring(0, nextElement.indexOf(",")));
                int y2 = Integer.parseInt(nextElement.substring(nextElement.indexOf(",") + 1));

                if(x2 > x1){
                    mazePath.set(i, "East");
                }
                if(x2 < x1){
                    mazePath.set(i, "West");
                }
                if(y2 > y1){
                    mazePath.set(i, "South");
                }
                if(y2 < y1){
                    mazePath.set(i, "North");
                }
            }
            mazePath.remove(mazePath.size() - 1);
            return true;
        }
        if (maze[y][x] == CLOSED) {
            return false;
        }
        if (maze[y][x] == MARKED) {
            return false;
        }
        maze[y][x] = MARKED; // mark
        numCellsVisited++;
        String temp = x + "," + y;
        mazePath.add(temp);
        if (findPath(y - 1, x))
            return true;
        if (findPath(y, x + 1))
            return true;
        if (findPath(y + 1, x))
            return true;
        if (findPath(y, x - 1))
            return true;
        maze[y][x] = UNMARKED;
        mazePath.remove(mazePath.size() - 1);
        return false;
    }

    public String[] getMoves(){
        if (!mazePath.isEmpty()) { //get path if its not empty
            String[] res = new String[mazePath.size()];
            for (int i = 0; i < mazePath.size(); i++) {
                res[i] = mazePath.get(i);
            }
            return res;
        }
        return new String[0];
    }
    public int getNumCellsVisited(){
        return numCellsVisited;
    }
    public double getPerformance(){
        return (double) mazesSolved / mazesTried;
    }
    public void reset(){
        mazePath.clear();
        numCellsVisited = 0;
    }
}