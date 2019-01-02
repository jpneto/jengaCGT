package model;

import java.util.*;

import model.SubtractionGame.Pair;

public class Matrix {
	
	Cell[][] matrix;
	
	public Matrix(int sizeX, int sizeY) {
		// note the coordinate swap, each row has a fixed y value
		matrix = new Cell[sizeY][sizeX]; 
	}
	
	public void computeGame(SubtractionGame game) {
		Queue<Cell> queue = new ArrayDeque<>();
		
		// compute how much options each cell has
		for(int r=0; r<matrix.length; r++) {          // for each row
			for(int c=0; c<matrix[0].length; c++) {   //   for each column
				matrix[r][c] = new Cell(r,c);
				
				for(Pair p : game.pairs)   // compute how much options this cell has
					if (valid(r+p.dy, c+p.dx))
						matrix[r][c].nOptions++;
				
				if (matrix[r][c].nOptions == 0) {  // found an eden cell
					matrix[r][c].value = 0;   // its value is zero (game with no options)
					queue.add(matrix[r][c]);
				}
			}
		}
		
		// start with Eden positions and account for their contribution
		// to their outcells. Keep doing that until the queue is empty
		while(!queue.isEmpty()) {
			Cell cell = queue.poll();
			
			for(Pair p : game.pairs) {
				// check to where this cell contributes
				int newX = cell.col - p.dx;
				int newY = cell.row - p.dy;
				
				// if a valid cell, add the cell contribution
				if (valid(newY, newX) && !outOfBoundaries(newY, newX)) {
					matrix[newY][newX].options.add(cell.value);
				
					// if this new cell has already all its contributions,
					// compute its mex value, and add it to queue
					if (matrix[newY][newX].options.size() == matrix[newY][newX].nOptions) {
						matrix[newY][newX].computeMex();
						queue.add(matrix[newY][newX]);					
					}
				}
			}
		}
	}

	private boolean valid(int row, int col) {
		return row>=0 && col>=0;
	}
	
	private boolean outOfBoundaries(int row, int col) {
		return row>=matrix.length || col>=matrix[0].length;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int r=0; r<matrix.length; r++) {          // yy
			for(int c=0; c<matrix[0].length; c++) {   // xx
				sb.append(String.format("%3d;",matrix[r][c].value));
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public double[][] asHeatMap() {
		int maxRowCol = Math.max(matrix.length, matrix[0].length);	
		int diagonal = 0;

		while (matrix[diagonal][diagonal].value != Cell.NOT_VISITED)
			if (diagonal < maxRowCol-1)
				diagonal++;
			else
				break;  // reached the end of the original matrix
		
		double[][] result = new double[diagonal][diagonal];
		
		for(int r=0; r<result.length; r++)
			for(int c=0; c<result[0].length; c++)
				result[r][c] = matrix[r][c].value;
		
		return result;
	}
	
//	private void printNOptions() {
//		
//		for(int r=0; r<matrix.length; r++) {          // yy
//			for(int c=0; c<matrix[0].length; c++) {   // xx
//				System.out.print(String.format("%3d",matrix[r][c].nOptions));
//			}
//			System.out.println();
//		}
//	}
//
//	private void printSizeList() {
//		
//		for(int r=0; r<matrix.length; r++) {          // yy
//			for(int c=0; c<matrix[0].length; c++) {   // xx
//				System.out.print(String.format("%3d",matrix[r][c].options.size()));
//			}
//			System.out.println();
//		}
//	}

}
