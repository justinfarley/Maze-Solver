import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;
/**
 * @author Justin Farley<p>
 * Date: 9/1/2023<p>
 */
public class GridText {
    protected ArrayList<String> gridText = new ArrayList<>();
    /**
     * Constructor 1
     * @param source string to be split using the delimiter
     * @param delimiter the string to look for when splitting the source string
     */
    public GridText(String source, String delimiter){
        String[] linesOfText = source.split(delimiter);
        Collections.addAll(gridText, linesOfText);
    }
    /**
     * Constructor 2, if numLines is bigger than the source lines, it will take everything from the scanner<p>
     * if numLines is smaller than the source lines it will read up until that number is reached
     * @param source scanner to read from
     * @param numLines maximum number of lines to be read
     */
    public GridText(Scanner source, int numLines){
        int i = 0;
        while(i < numLines){
            if(source.hasNextLine()){
                gridText.add(source.nextLine());
                i++;
            }
            else{
                break;
            }
        }
    }
    public GridText(Scanner source){
        while(source.hasNextLine()){
            gridText.add(source.nextLine());
        }
    }
    /**
     * @return the number of rows also known as the size of the {@link #gridText}
     */
    public int numRows()
    {
        return gridText.size();
    }
    /**
     * Returns the length (# of chars) of specified row
     * @param row the row to get the length of
     * @return the length of the row given
     */
    public int length(int row){
        String line;
        if(row < gridText.size() && row >= 0){
            line = gridText.get(row);
            return line.length();
        }
        else{
            return 0;
        }
    }
    public char[][] toCharArray(){
        char[][] newArr = new char[gridText.size()][gridText.get(0).length()];
        for(int i = 0; i < gridText.size(); i++){
            for(int j = 0; j < gridText.get(i).length(); j++){
                newArr[i][j] = gridText.get(i).charAt(j);
            }
        }
        return newArr;
    }

    /**
     * gets the character at the given row and column and returns it
     * @param row the row given
     * @param col the column given
     * @return the character at the row and column given
     */
    public Character at(int row, int col){
        try{
            if(row > gridText.size()) return null;
            String line = gridText.get(row);
            Character character = line.charAt(col);
            return character;
        }catch(Exception e){
            return null;
        }
    }

}
