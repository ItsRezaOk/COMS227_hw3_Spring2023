package hw3;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 */

/**
 * 
 * @author Reza C
 *
 */
public class ConnectGame {
	/**
	 *  a print variable to print the stuff on the game grid 
	 */
	private ShowDialogListener dialogListener;
	/**
	 * updates the score for me
	 */
	private ScoreUpdateListener scoreListener;
	/**
	 * the width of the grid
	 */
	private int width;
	/**
	 * the height of the grid
	 */
	private int height;
	/**
	 * the int min tile level
	 */
	private int min;
	/**
	 * the int max tile level
	 */
	private int max;
	/**
	 * the game grid made by the height and width variables
	 */
	private Grid gameGrid;
	/**
	 * a varible used to contruct rand
	 */
	private Random rand;
	/**
	 * a variable to keep track of the score
	 */
	private long score;
	/**
	 * says if the firstpick is happening 
	 */
	private boolean firstPick = true;
    /**
     * ArrayList of all the selcted tiles to used
     */
	private ArrayList<Tile> pickedTile = new ArrayList<>();
    /**
     *  a tracker of the current tile
     */
	private Tile currentTile;
	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		this.width = width;
		this.height = height;
		this.min = min;
		this.max = max;
		gameGrid = new Grid(width, height);
        this.rand = rand;
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
	
		int random = rand.nextInt(min, max); // random variable 
		
		Tile randomTile = new Tile(random);
		
		return randomTile;
	}

	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
					gameGrid.setTile(getRandomTile(), x, y); //randomize every tile in the grid
			}
		}
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
		double x1 = t1.getX();
		double x2 = t2.getX();
		double y1 = t1.getY();
		double y2 = t2.getY();
		if(Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 1){ // using a distance formula to find out the adjacent
			return true;
		} 
		else if(Math.abs(x2 - x1) == 1 && y1 == y2){
			return true;
		}
		else if(x2 == x1 && Math.abs(y2 - y1) == 1) {
			return true;
		}
		else {
			return false;
		}
	}
		
		
		

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		
		boolean a = false;
		
		if(firstPick == true) {
			
			 a = true;
		     firstPick = false;
		     gameGrid.getTile(x, y).setSelect(true);
		     pickedTile.add(gameGrid.getTile(x, y));
		}
		
		return a;
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		Tile currentTile = gameGrid.getTile(x, y); // current tile clicked on
		int startedSize = pickedTile.size(); // size of the array list of tiles
		if(isAdjacent(currentTile, pickedTile.get(pickedTile.size() - 1)) && !pickedTile.isEmpty()) { //has to be adjacent and with tiles to get into
			if(pickedTile.size() == 0) return;
			if(startedSize == 1) {
			
				if(pickedTile.get(0).getLevel() == currentTile.getLevel()) {
					pickedTile.add(currentTile);
					currentTile.setSelect(true);
				}
			} else if(pickedTile.get(startedSize - 2) == currentTile){
				pickedTile.remove(startedSize - 1).setSelect(false);
			} else {
				if(currentTile.getLevel() - pickedTile.get(startedSize - 1).getLevel() == 1 || currentTile.getLevel() - pickedTile.get(startedSize - 1).getLevel() == 0) {
					//checking to see if the levels match to what they should be
					pickedTile.add(currentTile);
					currentTile.setSelect(true);
				}
			}
		}	
	}
		
	

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		int tilesSize = pickedTile.size(); //the amount of tiles selected on already
		Tile currentTiles = gameGrid.getTile(x, y); // the tile given from the method call
		
		if(tilesSize == 1) { // only one selection so everything should be false
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					gameGrid.getTile(i, j).setSelect(false);
				}
			}
		} else if(pickedTile.get(tilesSize - 1) != currentTiles) {
			
			return false;
		} else if(tilesSize > 1) {
			
			score = 0;
			
			for(int i = 0; i < tilesSize; i++) {
			
				score += pickedTile.get(i).getValue();
			}
			upgradeLastSelectedTile();
			dropSelected();
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
				
					gameGrid.getTile(i, j).setSelect(false);
				}
			}
			
			pickedTile.clear();
			setScore(score);
			scoreListener.updateScore(score);
		}
		return true;
	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		
		Tile lastTile = pickedTile.get(pickedTile.size() - 1); // last tile picked
		
		lastTile.setLevel(lastTile.getLevel() + 1);
		unselect(lastTile.getX(), lastTile.getY());
		
		if(lastTile.getLevel() > max) {
			max++;
			int high = (int) Math.pow(2, max);
			int low = (int) Math.pow(2, min);
			dialogListener.showDialog("New block " + high + ", removing blocks " + low);
			min++;
			
		}
		
		
		// TODO

	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		return pickedTile.toArray(Tile[]::new);
	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				currentTile = gameGrid.getTile(i, j);
				if(currentTile.getLevel() == level) {
					currentTile.setLevel(level - 1);
				}
			}
		}
	}

	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(pickedTile.contains(gameGrid.getTile(i, j))){
					gameGrid.setTile(getRandomTile(), i, j);
						
					if(j == 0) {
						gameGrid.setTile(getRandomTile(), i, j);
					} 
					else if(j >= 1) {
						gameGrid.getTile(i, j - 1);
							
						gameGrid.setTile(gameGrid.getTile(i, j -1), i, j);
					}
				}		
			}
		}
	}

	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
		
	       gameGrid.getTile(x,y).setSelect(false);
	       pickedTile.remove(gameGrid.getTile(x,y));
	
	}

	/**
	 * Gets the player's score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		// TODO
		return score;
	}

	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		// TODO
		return gameGrid;
	}

	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		// TODO
		return min;
	}

	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		// TODO
		return max;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		this.score = score;
	}

	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		gameGrid = grid;
	}

	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		min = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		max = maxTileLevel;
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 * @throws FileNotFoundException 
	 */
	public void load(String filePath) throws FileNotFoundException {
		GameFileUtil.load(filePath, this);
	}
}
