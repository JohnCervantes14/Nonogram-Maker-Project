package edu.ou.cs2334.project5.presenters;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 

import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.handlers.OpenHandler;
import edu.ou.cs2334.project5.interfaces.Openable;
import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.models.NonogramModel;
import edu.ou.cs2334.project5.views.NonogramView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
/**
 * This is the class the presents the model as a GUI
 * @author John Cervantes
 *
 */
public class NonogramPresenter implements Openable {
	private NonogramView view;
	private NonogramModel model;
	private int cellLength;
	private static final String DEFAULT_PUZZLE = "puzzles/space-invaders.txt";
	/**
	 * This is the constructor of the presenter
	 * @param cellLength; desired size of cells
	 * @throws IOException if file does not exist
	 */
	public NonogramPresenter(int cellLength) throws IOException {
		this.cellLength = cellLength;
		model = new NonogramModel(DEFAULT_PUZZLE);
		view = new NonogramView();
		initializePresenter();
	}
	
	private void initializePresenter() {
		initializeView();
		bindCellViews();
		synchronise();
		configureButtons();
	}
	/**
	 * initializes the graphics view
	 */
	public void initializeView() {
		view.initialize(model.getRowClues(), model.getColClues(), cellLength);
		if(getWindow() != null) {
			getWindow().sizeToScene();
		}
	}
	/**
	 * Adds event handlers to handle mouse clicks on the cells
	 */
	public void bindCellViews() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				final var rowFinal = row;
				final var colFinal = col;
				view.getCellView(row, col).setOnMouseClicked((MouseEvent event)->{
					if(event.getButton() == MouseButton.PRIMARY) {
						handleLeftClick(rowFinal, colFinal);
					}
					else if(event.getButton() == MouseButton.SECONDARY) {
						handleRightClick(rowFinal, colFinal);
					}
				});
			}
		}
	}
	/**
	 * Handles a left click on a cell at given row and column
	 * @param rowIdx; desired row
	 * @param colIdx; desired column
	 */
	public void handleLeftClick(int rowIdx, int colIdx) {
		if(model.getCellStateAsBoolean(rowIdx, colIdx)) {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		}
		else {
			updateCellState(rowIdx, colIdx, CellState.FILLED);
		}
	}
	/**
	 * Handles a right click on cell at given row and column
	 * @param rowIdx; given row
	 * @param colIdx; given column
	 */
	public void handleRightClick(int rowIdx, int colIdx) {
		if(model.getCellStateAsBoolean(rowIdx, colIdx) || model.getCellState(rowIdx, colIdx) == CellState.EMPTY) {
			updateCellState(rowIdx, colIdx, CellState.MARKED);
		}
		else {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		}
	}
	/**
	 * This updates a cell state to a new state and checks if the row, column and puzzle are solved
	 * @param rowIdx; cell row
	 * @param colIdx; cell column
	 * @param state; new cell state
	 */
	public void updateCellState(int rowIdx, int colIdx, CellState state) {
		if(model.setCellState(rowIdx, colIdx, state)) {
			view.setCellState(rowIdx, colIdx, state);
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
			view.setColClueState(colIdx, model.isColSolved(colIdx));
		}
		
		if(model.isSolved()) {
			processVictory();
		}
	}
	/**
	 * synchronizes the view cells states with the model cell states 
	 */
	public void synchronise() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				view.setCellState(row, col, model.getCellState(row, col));
				view.setRowClueState(row, model.isRowSolved(row));
				view.setColClueState(col, model.isColSolved(col));
				view.setPuzzleState(model.isSolved());
				if(model.isSolved()) {
					processVictory();
				}
			}
		}
	}
	/**
	 * Signals the user of victory
	 */
	public void processVictory() {
		removeCellViewMarks();
		view.showVictoryAlert();
	}
	/**
	 * removes all marked cells
	 */
	public void removeCellViewMarks() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				if(model.getCellState(row, col) == CellState.MARKED) {
					view.setCellState(row, col, CellState.EMPTY);
				}
			}
		}
	}
	/**
	 * sets up load and reset buttons
	 */
	public void configureButtons() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File("."));
		view.getLoadButton().setOnAction(new OpenHandler(getWindow(), fileChooser, this));
		view.getResetButton().setOnAction((ActionEvent event) -> {
			resetPuzzle();
		});
	}
	/**
	 * resets the puzzle to be empty
	 */
	public void resetPuzzle() {
		model.resetCells();
		synchronise();
	}
	 /**
	  * returns the pane which is represented by the nonogram view
	  * @return returns nonogram view, view
	  */
	public Pane getPane() {
		return view;
	}
	/**
	 * gets the window from the nonogram view
	 * @return the window
	 */
	public Window getWindow() {
		try {
			return view.getScene().getWindow();
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * opens a given file
	 * @param file; the file to be opened
	 * @throws IOException if file does not exist
	 */
	public void open(File file) throws IOException {
		model = new NonogramModel(file);
		initializePresenter();
	}
	

}
