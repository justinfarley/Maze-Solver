import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class SolveMazes {
    public static void main(String[] args) {
        GridText gridText;
        MazeSolver solver = new MazeSolver();
        Scanner scan = new Scanner(System.in);
        String fileName = "";
        File file = null;
        char[][] maze = null;
        while(true){ //only break when stop is entered
            do{ //keep asking for a file until the file input actually exists
            System.out.print("Input file name: ");
            fileName = scan.nextLine();
            file = new File(fileName);
            }while(!file.exists() && !fileName.equals("stop"));
            if(fileName.equals("stop")){
                break;
            }
            Scanner fileScanner = null;
            try{
            fileScanner = new Scanner(file, StandardCharsets.UTF_8);
            }catch(Exception e){
                System.out.println("error!");
                System.exit(0);
            }
            gridText = new GridText(fileScanner);
            maze = gridText.toCharArray();
            for(int i = 0; i < maze.length; i++){
                for(int j = 0; j < maze[i].length; j++){
                    System.out.print(maze[i][j]);
                }
                System.out.println();
            }
            if(solver.solveMaze(maze)){
                System.out.println("Here is the solution to the maze:\n");
                for(String s : solver.getMoves()){
                    System.out.print(s + ",");
                }
            }
            else{
                System.out.println("No Solution to that maze!");
            }
            System.out.println("\nThere were " + solver.getNumCellsVisited() + " cells visited!");
            solver.reset(); //reset counting values for the next maze
        }
        System.out.println("Percent Correct: " + (int)(solver.getPerformance() * 100) + "%"); //make normalized value a percentage
        scan.close();
    }
}
