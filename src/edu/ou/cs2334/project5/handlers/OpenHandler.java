package edu.ou.cs2334.project5.handlers;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 
import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.interfaces.Openable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * This class extends the AbstractBaseHandler class 
 * and implements the EventHandler interface. 
 * It allows a grid to be opened.
 * @author John Cervantes
 *
 */
public class OpenHandler extends AbstractBaseHandler implements EventHandler<ActionEvent>{
	private Openable opener;
	
	/**
	 * constructs the save handler given a window, fileChooser, and Openable interface
	 * @param window; desired window
	 * @param fileChooser; desired fileChooser
	 * @param opener; Openable interface
	 */
	public OpenHandler(Window window, FileChooser fileChooser, Openable opener) {
		super(window, fileChooser);
		this.opener = opener;
	}

	//jared helped me fix this method
	/**
	 * This method is an overriden method generated from the eventHandler interface 
	 * @param event this represents the action event that is used
	 */
	@Override
	public void handle(ActionEvent event) {
		File file = super.fileChooser.showOpenDialog(super.window);
		if(file != null) {
			try {
				opener.open(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}