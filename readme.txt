Available command options
-h           : do not output heatmap (will run out of memory around 500x500)
-d nrow ncol : define matrix size (default 100x100)
-r n         : produces heatmaps for n random games (use square matrices)

Egs:
 java -jar make_matrix.jar -r 10 -d 300 300
 java -jar make_matrix.jar -h -d 500 1000 < game.txt > result.csv
    
where file, say, game.txt must have the following syntax:

 1 -1 
-2  0 
-2  1

The program will output the matrix into result.csv
and will also produce a heat map for easy visualization.
