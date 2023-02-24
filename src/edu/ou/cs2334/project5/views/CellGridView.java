package edu.ou.cs2334.project5.views;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 

import edu.ou.cs2334.project5.models.CellState;
import javafx.scene.layout.GridPane;
/**
 * This class extends Grid Pane is the visual for the puzzle
 * @author John Cervantes
 *
 */
public class CellGridView extends GridPane {
	
	private String STYLE_CLASS = "cell-grid-view";
	CellView[][] cellViews;
	/**
	 * constructor of the CellGridView with given sizes
	 * @param numRows; number of rows
	 * @param numCols; number of column
	 * @param cellLength; size of cells
	 */
	public CellGridView(int numRows, int numCols, int cellLength) {
		super();
		initCells(numRows, numCols, cellLength);
		getStyleClass().add(STYLE_CLASS);
	}
	/**
	 * initializes the double array list of Cell Views
	 * @param numRows; number of rows
	 * @param numCols; number of columns
	 * @param cellLength; cell size
	 */
	public void initCells(int numRows, int numCols, int cellLength) {
		getChildren().clear();
		cellViews = new CellView[numRows][numCols];
		for(int i = 0; i < numRows; ++i) {
			for(int j = 0; j < numCols; ++j) {
				CellView cell = new CellView(cellLength);
				cellViews[i][j] = cell;
				cell.setSize(cellLength);
			}
			addRow(i, cellViews[i]);
		}
	}
	/**
	 * returns the cell view a given row and column
	 * @param rowIdx; desired row
	 * @param colIdx; desired column
	 * @return the cell view
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		CellView view = cellViews[rowIdx][colIdx];
		return view;
	}
	/**
	 * sets a cell state to given state
	 * @param rowIdx; row index
	 * @param colIdx; column index
	 * @param state; new Cell state
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellViews[rowIdx][colIdx].setState(state);
	}
}
