package edu.ou.cs2334.project5.models;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * This class is the model for the cells in the grid
 * @author John Cervantes
 *
 */
public class NonogramModel {

	private static final String DELIMITER = " ";
	private static final int IDX_NUM_ROWS = 0;
	private static final int IDX_NUM_COLS = 1;

	private int[][] rowClues;
	private int[][] colClues;
	private CellState[][] cellStates;
	/**
	 * This is the constructor for a NonogramModel
	 * @param rowClues; a double array list for the row clues
	 * @param colClues; a double array list for the column clues
	 */
	public NonogramModel(int[][] rowClues, int[][] colClues) {
		// TODO: Implement deepCopy. 
		// This is simple, and you should not ask about this on Discord.
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);

		cellStates = initCellStates(getNumRows(), getNumCols());
	}
	
	/**
	 * Nonogram constructor using a file
	 * @param file; the file used to make the model
	 * @throws IOException if file does not exist
	 */
	public NonogramModel(File file) throws IOException {
		// Number of rows and columns
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String header = reader.readLine();
		String[] fields = header.split(DELIMITER);
		int numRows = Integer.parseInt(fields[IDX_NUM_ROWS]);
		int numCols = Integer.parseInt(fields[IDX_NUM_COLS]);
		cellStates = initCellStates(numRows, numCols);
		rowClues = readClueLines(reader, numRows);
		colClues = readClueLines(reader, numCols);
		reader.close();
	}
	/**
	 * Nonogram constructor using a file name
	 * @param filename; String representation of the file
	 * @throws IOException if file does not exist
	 */
	public NonogramModel(String filename) throws IOException {
		// TODO: Fix this constructor
		// This is simple, and you should not ask about this on Discord.
		NonogramModel temp = new NonogramModel(new File(filename));
		this.rowClues = deepCopy(temp.getRowClues());
		this.colClues = deepCopy(temp.getColClues());
		cellStates = initCellStates(getNumRows(), getNumCols());

	}
	
	// TODO: Add more TODOs
	
	
	
	// This is implemented for you
	private static CellState[][] initCellStates(int numRows, int numCols) {
		// Create a 2D array to store numRows * numCols elements
		CellState[][] cellStates = new CellState[numRows][numCols];
		
		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for (int colIdx = 0; colIdx < numCols; ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
		
		// Return the result
		return cellStates;
	}
	
	// TODO: Implement this method
	private static int[][] deepCopy(int[][] array) {
		if(array == null) {
			return null;
		}
		
		int[][] copy = new int[array.length][];
	    for (int i = 0; i < array.length; ++i) {
	        copy[i] = Arrays.copyOf(array[i], array[i].length);
	        
	    }
		return copy;
	}
	/**
	 * Get the cell state at desired position
	 * @param rowIdx; desired row index
	 * @param colIdx; desired column index
	 * @return the cell state
	 */
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}
	/**
	 * Gets cell state as a boolean
	 * @param rowIdx; desired row
	 * @param colIdx; desired column
	 * @return the cell state
	 */
	public boolean getCellStateAsBoolean(int rowIdx, int colIdx) {
		return CellState.toBoolean(getCellState(rowIdx, colIdx));
	}
	/**
	 * Sets the cell state given a new state
	 * @param rowIdx; desired row
	 * @param colIdx; desired column
	 * @param state; new cell state
	 * @return true if the state changed or false if not
	 */
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		
		if(state == null || isSolved()) {
			return false;
		}
		//keeton showed me that I needed this else if
		else if(cellStates[rowIdx][colIdx] == state) {
			return false;
		}
		else {
			cellStates[rowIdx][colIdx] = state;
			return true;
		}
	}
	/**
	 * returns a deep copy of the row clues
	 * @return the row clues
	 */
	public int[][] getRowClues(){
		return deepCopy(rowClues);
	}
	/**
	 * returns a deep copy of the column clues
	 * @return the column clues
	 */
	public int[][] getColClues(){
		return deepCopy(colClues);
	}
	/**
	 * Gets the row clue of given row
	 * @param rowIdx; desired row
	 * @return the row clue
	 */
	public int[] getRowClue(int rowIdx) {
		return Arrays.copyOf(rowClues[rowIdx], rowClues[rowIdx].length);
	}
	/**
	 * Gets the column clue of given column
	 * @param colIdx; desired column
	 * @return column clue
	 */
	public int[] getColClue(int colIdx) {
		return Arrays.copyOf(colClues[colIdx], colClues[colIdx].length);
	}
	/**
	 * Checks to see if the row is solved
	 * @param rowIdx; desired row
	 * @return true if the row is solved false if not
	 */
	//got help from keeton on these two; reminded me I had to check the length
	public boolean isRowSolved(int rowIdx) {
		int[] temp = Arrays.copyOf(rowClues[rowIdx], rowClues[rowIdx].length);
		
		if(temp.length != projectCellStatesRow(rowIdx).length)
			return false;
		
		for(int i = 0; i < temp.length; ++i) {
			if(temp[i] != projectCellStatesRow(rowIdx)[i]) {
				return false;
			}
		}
			return true;
	}
	/**
	 * Checks to see if the column is solved
	 * @param colIdx; desired column
	 * @return  true if the column is solved false if not
	 */
	public boolean isColSolved(int colIdx) {
		int[] temp = Arrays.copyOf(colClues[colIdx], colClues[colIdx].length);
		if(temp.length != projectCellStatesCol(colIdx).length) {
			return false;
		}
		for(int i = 0; i < temp.length; ++i) {
			if(temp[i] != projectCellStatesCol(colIdx)[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks to see if the entire puzzle is solved
	 * @return true if solved, false if not
	 */
	public boolean isSolved() {
		for(int row = 0; row < getNumRows(); ++row) {
			if(!isRowSolved(row)) 
				return false;
		}
		for(int col = 0; col < getNumCols(); ++col) {
			if(!isColSolved(col)) 
				return false;
		}
		
		return true;
		
	}
	/**
	 * resets the puzzle to all empty cells
	 */
	public void resetCells() {
		for (int rowIdx = 0; rowIdx < getNumRows(); ++rowIdx) {
			for (int colIdx = 0; colIdx < getNumCols(); ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
		
	}
	/**
	 * projects what the clues will be given the cells
	 * @param cells; a boolean array of cells
	 * @return a List of integers representing the clues
	 */
	public static List<Integer> project(boolean[] cells) {
		ArrayList<Integer> project = new ArrayList<Integer>();
		int trueCount = 0;
		for(int i = 0; i < cells.length; ++i) {
			if(cells[i]) {
				++trueCount;
			}
			else {
				if(trueCount > 0) {
					project.add(trueCount);
					trueCount = 0;
				}
			}
		}
		if(trueCount > 0) {
			project.add(trueCount);
		}
		if(project.size() == 0) {
			project.add(0);
		}
		return project;
	}
	
	/**
	 * projects a single row
	 * @param rowIdx; desired row
	 * @return int array of clues
	 */
	public int[] projectCellStatesRow(int rowIdx) {
		boolean[] temp = new boolean[getNumRows()];
		 
		 for(int i = 0; i < getNumCols(); ++i) {
			 temp[i] = getCellStateAsBoolean(rowIdx, i); 
		 }
		 
		 List<Integer> list = project(temp);
		 int[] row = new int[list.size()];
		 
		 for(int i = 0; i < list.size(); ++i) {
			 row[i] = list.get(i);
		 }
		 
		 return row;
	}
	/**
	 * projects a single column
	 * @param colIdx; desired column
	 * @return int array of column clues
	 */
	public int[] projectCellStatesCol(int colIdx) {
		boolean[] temp = new boolean[rowClues.length];
		 
		 for(int i = 0; i < temp.length; ++i) {
			 temp[i] = getCellStateAsBoolean(i, colIdx); 
		 }
		 
		 List<Integer> list = project(temp);
		 int[] col = new int[list.size()];
		 
		 for(int i = 0; i < col.length; ++i) {
			 col[i] = list.get(i);
		 }
		 
		 return col;
	
	}
	
	
	
	// This method is implemented for you. You need to figure out how it is useful.
	private static int[][] readClueLines(BufferedReader reader, int numLines)
			throws IOException {
		// Create a new 2D array to store the clues
		int[][] clueLines = new int[numLines][];
		
		// Read in clues line-by-line and add them to the array
		for (int lineNum = 0; lineNum < numLines; ++lineNum) {
			// Read in a line
			String line = reader.readLine();
			
			// Split the line according to the delimiter character
			String[] tokens = line.split(DELIMITER);
			
			// Create new int array to store the clues in
			int[] clues = new int[tokens.length];
			for (int idx = 0; idx < tokens.length; ++idx) {
				clues[idx] = Integer.parseInt(tokens[idx]);
			}
			
			// Store the processed clues in the resulting 2D array
			clueLines[lineNum] = clues;
		}
		
		// Return the result
		return clueLines;
	}
	/**
	 * return number of rows
	 * @return number of rows
	 */
	public int getNumRows() {
		
		return rowClues.length;
	}
	/**
	 * return number of columns
	 * @return number of columns
	 */
	public int getNumCols()  {
		
		return colClues.length;
	}
	
}
