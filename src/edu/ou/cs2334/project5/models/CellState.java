package edu.ou.cs2334.project5.models;
//Worked with Keeton Fell, Jared Rubio and Guillermo Salvidar 
/**
 * This is an enumeration that represents the state of a cell
 * @author John Cervantes
 * 
 *
 */
public enum CellState {
	/**
	 * representation of empty cell state
	 */
	EMPTY,
	/**
	 * representation of filled
	 */
	FILLED, 
	/**
	 * representation of marked
	 */
	MARKED;
	 
	/**
	 * takes a cell state and turns it into a boolean
	 * @param state; given CellState to be converted
	 * @return a boolean representation of the cell state
	 */
	public static boolean toBoolean(CellState state) {
		return state.equals(FILLED);
	}
}

