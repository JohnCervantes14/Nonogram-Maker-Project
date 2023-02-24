package edu.ou.cs2334.project5;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 
import edu.ou.cs2334.project5.presenters.NonogramPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * The main class that runs the project
 * @author John Cervantes
 * @version 1.0
 */
public class Main extends Application{
	private static final int IDX_CELL_SIZE = 0;
	private static final int DEFAULT_CELL_SIZE = 30;
	
	/**
	 * This is the main method to launch the application
	 * @param args arguments to run the program
	 */
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * sets up the graphics to run
	 * @param primaryStage; Stage variable that represents the graphics
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		var cellSize = getParameters().getUnnamed();
		NonogramPresenter presenter;
		if(cellSize != null) {
			int size = Integer.parseInt(cellSize.get(IDX_CELL_SIZE));
			presenter = new NonogramPresenter(size);
		}
		else {	
			presenter = new NonogramPresenter(DEFAULT_CELL_SIZE);
		}

			

		Scene scene = new Scene(presenter.getPane());
		scene.getStylesheets().add("/style.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Nonogram");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}