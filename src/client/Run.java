package client;

import java.io.*;
import java.util.*;

import org.tc33.jheatchart.HeatChart;

import model.Matrix;
import model.SubtractionGame;

public class Run {
	
	public static void main(String[] args) {
		
//      if (!new Object(){}.getClass().getName().contains("Main"))    
//          try {   // redirect System.in and System.out to in/out text files
//             System.setIn (new FileInputStream("data/game.txt" ));
//             System.setOut(new     PrintStream("data/game_out.csv") );
//          } catch (Exception e) {}      
      
	    ///////////////////////////////
      
        boolean showHeatMap = true;
        boolean showRandomGames = false;
        boolean readFromIn = true;
        int nRandomGames = 0;
        int nRow = 100;
        int nCol = 100;
      
    	// we need to process command line
    	
    	Queue<String> q = new ArrayDeque<>();
    	
    	for(String arg : args)
    		q.add(arg);
    	
    	try {
        	while(!q.isEmpty()) {
        		String command = q.poll();
        		switch (command) {
        		  case "-?" : help();
        		              return;
        		  case "-h" : showHeatMap = false;
        		              break;
        		  case "-r" : showRandomGames = true;
        		  	          readFromIn = false;
        		  			  nRandomGames = Integer.parseInt(q.poll());
        		              break;
        		  case "-d" : nCol = Integer.parseInt(q.poll());
        		              nRow = Integer.parseInt(q.poll());
        		              break;     
        		  default   : throw new Exception();
        		}
        	}
    	} catch (Exception e) {
    		System.err.println("\nBad command arguments. Use -? for help");
    		return;
    	}
    	
    	/////////////////////////
    	
    	if (showRandomGames)
    		makeRandomGames(nRandomGames, nRow, nCol);
    	
    	if (readFromIn) {
	        Scanner sc = new Scanner(System.in);
			List<Integer> moves = new LinkedList<>();
			
			while(sc.hasNext())
				moves.add(sc.nextInt());
			
			SubtractionGame g = new SubtractionGame(moves);
			
			Matrix m = new Matrix(nRow, nCol);
			m.computeGame(g);
			System.out.println(m);	
			
			sc.close();
			
			if (showHeatMap)
			  makeHeatMap(m,g);
    	}

	}
	
	private static int makeHeatMap(Matrix m, SubtractionGame g) {
		// Creating a Heat Map, more details at http://www.javaheatmap.com/
		
		// Step 1: Create our heat map chart using our data.
		double[][] hm = m.asHeatMap();
		
		if (hm.length < 4)
			return -1;  // too small, should try again
		
		HeatChart map = new HeatChart(hm);

//		for(double[] row : m.asHeatMap())
//		   System.out.println(Arrays.toString(row));	

		// Step 2: Customise the chart.
		map.setTitle("Game: " + g.toString());
		map.setLowValueColour(java.awt.Color.WHITE);
		map.setLowValueColour(java.awt.Color.CYAN);

		// Step 3: Output the chart to a file.
		try {
			map.saveToFile(new File(g.toString() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private static void makeRandomGames(int nSamples, int nRow, int nCol) {
    	
    	while(nSamples > 0) {
    		int max = Math.max(nRow, nCol);  // random games should have square matrices
    		Matrix m = new Matrix(max, max);
    		SubtractionGame g = SubtractionGame.randomGame();
    		m.computeGame(g);
    		if (makeHeatMap(m,g)==0)
    			nSamples--;
    	}		
	}
	
	private static void help() {
		String help = "\nAvailable command options"
				+ "\n-h           : do not output heatmap (will run out of memory around 500x500)"
				+ "\n-d nrow ncol : define matrix size (default 100x100)"
				+ "\n-r n         : produces heatmaps for n random games (use square matrices)"
				+ "\n\nUse cases:"
				+ "\n java -jar make_matrix.jar -h -d 500 1000 < game.txt > result.csv"  
				+ "\n java -jar make_matrix.jar -r 10 -d 300 300"
				+ "\n\nFile game.txt must include the game's moves, eg:\n-1 -2\n-2 -3\n 0 -1"
				;
		
		System.out.println(help);
	}
}
