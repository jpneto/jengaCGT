package model;

import java.util.*;

public class SubtractionGame {
	
	static Random random = new Random();

	List<Pair> pairs;

	public class Pair {
		int dx, dy;
		public Pair(int x, int y) { dx=x; dy=y; }
		public String toString() { return "("+dx+","+dy+")"; } 
	}
	
	/**
	 * Receives a subtraction game in an array format
	 * Eg: S={(1,-1),(-2,0),(-2,1)} is [1,-1,-2,0,-2,1] 
	 */
	public SubtractionGame(List<Integer> moves) {
		
		pairs = new LinkedList<>();
		
		for(int i=0; i<moves.size()/2; i++)
			pairs.add(new Pair(moves.get(2*i), moves.get(2*i+1)));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append('{');
		for(Pair p : pairs)
			sb.append(p + ",");
		sb.deleteCharAt(sb.length()-1);
		sb.append('}');
		return sb.toString();
	}
	
	public static SubtractionGame randomGame() {
		int size = 3 + random.nextInt(8);
		List<Integer> moves = new LinkedList<>();
		
		while(size-- > 0) {
			int d1 = random.nextInt(7)-3;
			int d2 = random.nextInt(7)-3;
			if (d1>=0 && d2>=0)   // preventing loopy games
				d1 = -d1;
			moves.add(d1);
			moves.add(d2);
		}
		
		return new SubtractionGame(moves);
	}
}