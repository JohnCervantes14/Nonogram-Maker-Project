package edu.ou.cs2334.project5.handlers;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * This class represents a general handler involving file selection.
 * @author John Cervantes
 * @version 1.0
 */
public abstract class AbstractBaseHandler {
	protected Window window;
	protected FileChooser fileChooser;
	
	protected AbstractBaseHandler(Window window, FileChooser fileChooser) {
		this.window = window;
		this.fileChooser = fileChooser;
	}
}
