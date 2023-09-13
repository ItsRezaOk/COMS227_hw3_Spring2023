package hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import api.Tile;

/**
 * Utility class with static methods for saving and loading game files.
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */
	public static void save(String filePath, ConnectGame game) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			// TODO: write to file, can use writer.write()
			long score = game.getScore();
			writer.write(game.getGrid().getWidth() + " ");
			writer.write(game.getGrid().getHeight() + " ");
			writer.write(game.getMinTileLevel() + " ");
			writer.write(game.getMaxTileLevel() + " " + score + '\n');
			for(int i = 0; i < game.getGrid().getHeight(); i++) {
						
					writer.write('\n');

				for(int j = 0; j < game.getGrid().getWidth(); j++) {
					 
					Tile tile = game.getGrid().getTile(j, i);
					
					if(tile != null) {
						if(j == game.getGrid().getHeight() - 1) {
							writer.write(tile.getLevel());
						}
						else {
							writer.write(tile.getLevel() + " ");
						}
					}
					else {
						writer.write("0 ");
					}
				}
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * <p>
	 * See the save() method for the specification of the file format.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 * @throws FileNotFoundException 
	 */
	public static void load(String filePath, ConnectGame game) throws FileNotFoundException {
		  try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
              // Read game data from file
              String[] dataTokens = reader.readLine().split(" ");
              /**
               * width is the width given, 
               */
              int width = Integer.parseInt(dataTokens[0]);
              int height = Integer.parseInt(dataTokens[1]);
              int minTileLevel = Integer.parseInt(dataTokens[2]);
              int maxTileLevel = Integer.parseInt(dataTokens[3]);
              int score = Integer.parseInt(dataTokens[4]);
              Grid grid = new Grid(width, height);
              game.setMinTileLevel(minTileLevel);
              game.setMaxTileLevel(maxTileLevel);
              game.setScore(score);
              
              // Read game grid from file
              grid = new Grid(width, height);
              for (int row = 0; row < height; row++) {
                  String[] levelTokens = reader.readLine().split(" ");
                  for (int col = 0; col < width; col++) {
                      int level = Integer.parseInt(levelTokens[col]);
                      Tile tile = new Tile(level);
                      grid.setTile(tile,col, row);
                  }
              }
              game.setGrid(grid);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  
	
}
