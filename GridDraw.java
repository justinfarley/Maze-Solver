package hw2;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

class GridDraw extends Canvas {
    private final Color OPEN = Color.white;
    private final Color CLOSED = Color.black;
    private final Color START = Color.green;
    private final Color GOAL = Color.red;
    private final Color MARKED = Color.yellow;
    private final Color UNMARKED = Color.DARK_GRAY;
    private char[][] maze;
    public static int step = 10;
    public static String pathName = "./mazes/maze-d.txt";

    public GridDraw() {
        File file = new File(pathName);
        Scanner nextMaze = null;
        try {
            nextMaze = new Scanner(file);
        } catch (Exception e) {
            System.exit(0);
        }
        GridText gridText = new GridText(nextMaze);
        this.maze = gridText.toCharArray();
    }

    // override for Canvas.paint
    public void paint(Graphics g) {
        drawGrid(g);
    }// paint()

    private void drawGrid(Graphics g) {
        // paint background white first
        g.setColor(Color.white);
        g.fillRect(0, 0, 1920, 1080);
        int xDivisions = maze[0].length;
        int yDivisions = maze.length;
        int offset = 25;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // check the chars and if its blocked use the blocked color etc
                // figure out way to change width and height based on how big the maze is
                switch (maze[i][j]) {
                    case 'S':
                        g.setColor(START);
                        break;
                    case 'G':
                        g.setColor(GOAL);
                        break;
                    case '#':
                        g.setColor(CLOSED);
                        break;
                    case '.':
                        g.setColor(OPEN);
                        break;
                    case '+':
                        g.setColor(MARKED);
                        break;
                    case 'x':
                        g.setColor(UNMARKED);
                        break;
                    default:
                        g.setColor(CLOSED);
                        break;
                }
                g.fillRect((j * step) + offset, (i * step) + offset, getMaze()[0].length * step / xDivisions,
                        getMaze().length * step / yDivisions);
            }
        }
    }

    public char[][] getMaze() {
        return maze;
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }
}

class Main {
    private static boolean pressedSolvedButton = false;
    static char[][] unsolvedMaze = null;
    static char[][] solvedMaze = null;
    public static void main(String[] args) {
        int step = GridDraw.step;

        MazeSolver solver = new MazeSolver();
        GridText gridText = new GridText(getMaze(GridDraw.pathName));
        unsolvedMaze = gridText.toCharArray();
        solvedMaze = gridText.toCharArray();
        solver.solveMaze(solvedMaze);
        GridDraw gridDraw = new GridDraw();
        JFrame f = new JFrame();
        JButton solveMazeButton = new JButton("Solve Maze!");
        JSlider mazeSizeSlider = new JSlider(1, 100);
        mazeSizeSlider.setValue(step);
        JLabel mazeSizeSliderText = new JLabel("Maze Size: " + step);
        JTextArea filePathArea = new JTextArea(GridDraw.pathName);
        JLabel filePathAreaLabel = new JLabel("Enter Path to Maze here: ");
        filePathAreaLabel.setBounds(1400, 360, 250, 50);
        filePathArea.setBounds(1545, 378, 250, 20);
        filePathArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                if (getMaze(filePathArea.getText()) == null) {
                    System.out.println("returning");
                    return;
                }
                GridText temp = new GridText(getMaze(filePathArea.getText()));
                char[][] tempMazeUnsolved = temp.toCharArray();
                char[][] tempMazeSolved = temp.toCharArray();
                solver.solveMaze(tempMazeSolved);
                unsolvedMaze = tempMazeUnsolved;
                gridDraw.setMaze(tempMazeUnsolved);
                gridDraw.paint(gridDraw.getGraphics());
                solvedMaze = tempMazeSolved;
                pressedSolvedButton = false;
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });
        mazeSizeSlider.addChangeListener(e -> {
            mazeSizeSliderText.setText("Maze Size: " + mazeSizeSlider.getValue());
            GridDraw.step = mazeSizeSlider.getValue();
            System.out.println(pressedSolvedButton);
            if (pressedSolvedButton)
                gridDraw.setMaze(solvedMaze);
            else {
                gridDraw.setMaze(unsolvedMaze);
            }
            gridDraw.paint(gridDraw.getGraphics());
        });
        mazeSizeSliderText.setBounds(1460, 560, 100, 50);
        mazeSizeSlider.setBounds(1400, 600, 200, 50);
        solveMazeButton.addActionListener(e -> {
            gridDraw.setMaze(solvedMaze);
            gridDraw.paint(gridDraw.getGraphics());
            pressedSolvedButton = true;
        });
        solveMazeButton.setBounds(1400, 500, 200, 50);
        f.add(filePathArea);
        f.add(filePathAreaLabel);
        f.add(mazeSizeSlider);
        f.add(mazeSizeSliderText);
        f.add(solveMazeButton);
        f.add(gridDraw);
        f.setSize(1920, 1080);
        f.setVisible(true);
    } // main

    private static Scanner getMaze(String pathName) {
        File file = new File(pathName);
        Scanner nextMaze = null;
        try {
            nextMaze = new Scanner(file);
        } catch (Exception e) {
            System.out.print("no file with name " + pathName);
        }
        return nextMaze;
    }
}