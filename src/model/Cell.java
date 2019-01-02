package model;

import java.util.*;

public class Cell {
	
	static final int NOT_VISITED = -1;

	int row, col;  // where this cell is
	int value;     // its (final) value (if any) computed by the mex function
	int nOptions;  // number of options it has to move into (those define its value)
	List<Integer> options;
		
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		value = NOT_VISITED;
		nOptions = 0;
		options = new LinkedList<>();
	}
	
	public void computeMex() {
		Collections.sort(options);
		
		if (options.get(0) > 0)
			value = 0;
		else {
			int lastVal = options.get(0); 
			for(int i=1; i<options.size(); i++) {
				int current = options.get(i);
				if (current > lastVal+1) {
				   value = lastVal+1;
				   return;
				} 
				lastVal = current;
			}
			value = lastVal+1;
		    return;
		}
	}
}