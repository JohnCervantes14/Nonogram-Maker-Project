package edu.ou.cs2334.project5.views;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 

import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.views.clues.LeftCluesView;
import edu.ou.cs2334.project5.views.clues.TopCluesView;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
/**
 * this is view of the entire puzzle it extends border pane
 * @author John Cervantes
 *
 */
public class NonogramView extends BorderPane {
	private String STYLE_CLASS = "nonogram-view";
	private String SOLVED_STYLE = "nonogram-view-solved";
	private LeftCluesView leftCluesView;
	private TopCluesView topCluesView;
	private CellGridView cellGridView;
	private HBox bottomHBox;
	private Button loadBtn;
	private Button resetBtn;
	/**
	 * constructor of the nonogram view
	 */
	public NonogramView() {
		super();
		getStyleClass().add(STYLE_CLASS);
	}
	/**
	 * initializes the clue views and cell grid view
	 * @param rowClues; row clues
	 * @param colClues; column clues
	 * @param cellLength; cell size
	 */
	public void initialize(int[][] rowClues, int[][] colClues, int cellLength) {
		int rowWidth = getLength(rowClues);
		int colHeight = getLength(colClues);
		
		leftCluesView = new LeftCluesView(rowClues, cellLength, rowWidth);
		topCluesView = new TopCluesView(colClues, cellLength, colHeight);
		initBottomHBox();
		cellGridView = new CellGridView(rowClues.length, colClues.length, cellLength);
		
		setLeft(leftCluesView);
		setTop(topCluesView);
		setCenter(cellGridView);
		setBottom(bottomHBox);
		setAlignment(getTop(), Pos.CENTER_RIGHT);
	}
	/**
	 * helper method to get the longest length of clues for the row width and height
	 * @param clues; the puzzle clues
	 * @return the size
	 */
	public int getLength(int[][] clues) {
		int value = clues[0].length;
		for(int i = 0; i < clues.length; ++i) {
			if(value < clues[i].length) {
				value = clues[i].length;
			}
		}
		return value;
	}
	
	private void initBottomHBox() {
		bottomHBox = new HBox();
		bottomHBox.setAlignment(Pos.CENTER);
		loadBtn = new Button("Load");
		resetBtn = new Button("Reset");
		bottomHBox.getChildren().addAll(loadBtn, resetBtn);
	}
	/**
	 * gets the cell view a given row and column
	 * @param rowIdx; row index
	 * @param colIdx; column index
	 * @return the cell view
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellGridView.getCellView(rowIdx, colIdx);
	}
	/**
	 * sets the cell state at given row and column
	 * @param rowIdx; row index
	 * @param colIdx; column index
	 * @param state new state
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGridView.setCellState(rowIdx, colIdx, state);
	}
	/**
	 * sets the row clue state to solved if its solved
	 * @param rowIdx; row index
	 * @param solved boolean variable if the row is solved or not
	 */
	public void setRowClueState(int rowIdx, boolean solved) {
		leftCluesView.setState(rowIdx, solved);
	}
	/**
	 * sets the column clue state to solved if solved
	 * @param colIdx; column index
	 * @param solved; boolean variable if column is solved or not
	 */
	public void setColClueState(int colIdx, boolean solved) {
		topCluesView.setState(colIdx, solved);
	}
	/**
	 * sets the puzzle state to solved or unsolved
	 * @param solved; state of puzzle
	 */
	public void setPuzzleState(boolean solved) {
		if(solved) {
			getStyleClass().add(SOLVED_STYLE);
		}
		else {
			getStyleClass().remove(SOLVED_STYLE);
		}
	}
	/**
	 * returns the load button
	 * @return the load button
	 */
	public Button getLoadButton() {
		return loadBtn;
	}
	/**
	 * returns the reset button
	 * @return the reset button
	 */
	public Button getResetButton() {
		return resetBtn;
	}
	/**
	 * alerts the user that they won
	 */
	public void showVictoryAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Puzzle Solved");
		alert.setHeaderText("Congratulations!");
		ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
		alert.setContentText("You Win!");
		alert.getButtonTypes().setAll(type);
		alert.show();
	}
}
