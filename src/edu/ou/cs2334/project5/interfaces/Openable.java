package edu.ou.cs2334.project5.interfaces;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface to specify that a class has a 
 * special method to handle opening a file.
 * @author John Cervantes
 *
 */
public interface Openable {
	/**
	 * method to open a file
	 * @param file; desired file
	 * @throws FileNotFoundException if file not found
	 * @throws IOException if file not found
	 */
	void open(File file) throws FileNotFoundException, IOException;
}
