package pentago;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class AI_Player 
{
	public static int DEFAULT_CHECK = Integer.MAX_VALUE;
	//which color tile the ai is
	private int turn;
	//value for if the ai is the min or max player 
	private int minOrMax;
	//for counting the number of nodes expanded
	private int nodes;
	//the board that the ai is making a move from
	private GameBoard currentBoard;
	//the board after the ai places a tile
	private GameBoard returnPlace;
	//stack used in the random move
	private Stack<GameBoard> myStack;
	//list used in the random move
	private List<GameBoard> possibleMoves;
	//the list of possible rotations that could be made after the tile is placed
	private List<GameBoard> possibleRot;
	// the max depth the game tree will go
	private int maxDepth;
	
	
	//constructor for the ai player
	public AI_Player(int newTurn,int newMinOrMax)
	{
		nodes = 0;
		turn = newTurn;
		minOrMax = newMinOrMax;
		maxDepth = 6;
	}
	//the method that gets the boards that will be returned from the given board.
	public void makeMove(GameBoard theBoard)
	{
		returnPlace = null;
		currentBoard = theBoard;
		possibleRot = new ArrayList<>();
		//no A/B pruning
		//miniMax(0,minOrMax,currentBoard);
		//With A/B pruning
		miniMaxWAB(0,minOrMax,currentBoard,Integer.MIN_VALUE,Integer.MAX_VALUE);
		System.out.println("Nodes Expanded: " + nodes);
	}
	// method for selecting the move at random, made for fun
	public GameBoard random(GameBoard theBoard)
	{
		possibleMoves = new ArrayList<>();
		myStack = new Stack<>();
		currentBoard = theBoard;
		generateMoves(currentBoard);
		Random rn = new Random();
		int randomNum = rn.nextInt((possibleMoves.size()));
		GameBoard temp = possibleMoves.get(randomNum);
		return temp;
	}
	//generates moves for the random selection
	private void generateMoves(GameBoard startBoard)
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(startBoard.legalMove(i, j))
				{
					myStack.add(startBoard.addTile(i,j,turn));
				}
			}
		}
		while(!myStack.isEmpty())
		{
			GameBoard tempBoard = myStack.pop();
			for(int i = 0; i < 4; i++)
			{
				possibleMoves.add(tempBoard.leftRotate(i+1));
				possibleMoves.add(tempBoard.rightRotate(i+1));
				
			}
		}
	}
	// miniMax algorithm with no A/B pruning.
	private int miniMax(int depth, int minOrMax,GameBoard current)
	{
		nodes++;
		if(depth == maxDepth)
		{
			return current.getValue();
		}
		else
		{
			if(minOrMax == 1)
			{
				int max = Integer.MIN_VALUE;
				List<GameBoard> placements = getPossiblePlacements(current);
				for(int i = 0; i < placements.size(); i++)
				{
					int check;
					if(placements.get(i).getWin() >= 0)
					{
						check = placements.get(i).getValue()  * 100;
					}
					else
					{
						check = miniMax(depth+1,2,placements.get(i));
					}
					if(check > max)
					{
						max = check;
						if(depth == 0)
						{
							returnPlace = placements.get(i);
						}
					}
				}
				return max;
			}
			else if(minOrMax == 2)
			{
				GameBoard best = null;
				int max = Integer.MIN_VALUE;
				List<GameBoard> rotations = rotations(current);
				for(int i = 0; i < rotations.size(); i++)
				{
					int check; 
					if(rotations.get(i).getWin() >= 0)
					{
						check = rotations.get(i).getValue() * 100;
					}
					else
					{
						check = miniMax(depth+1,3,rotations.get(i));
					}
					if(check > max)
					{
						max = check;
						best = rotations.get(i);
					}
				}
				if(depth == 1)
				{
					possibleRot.add(best);
					current.setRotationIndex(possibleRot.size()-1);
				}
				return max;
			}
			if(minOrMax == 3)
			{
				int min = Integer.MAX_VALUE;
				List<GameBoard> placements = getPossiblePlacements(current);
				for(int i = 0; i < placements.size(); i++)
				{
					int check;
					if(placements.get(i).getWin() >= 0)
					{
						check = placements.get(i).getValue() * 100;
					}
					else
					{
						check = miniMax(depth+1,4,placements.get(i));
					}
					if(check < min)
					{
						min = check;
						if(depth == 0)
						{
							returnPlace = placements.get(i);
						}
					}
				}
				return min;
			}
			else if(minOrMax == 4)
			{
				GameBoard best = null;
				int min = Integer.MAX_VALUE;
				List<GameBoard> rotations = rotations(current);
				for(int i = 0; i < rotations.size(); i++)
				{
					int check;
					if(rotations.get(i).getWin() >= 0)
					{
						check = rotations.get(i).getValue() * 100;
					}
					else
					{
						check = miniMax(depth+1,1,rotations.get(i));
					}
					if(check < min)
					{
						min = check;
						best = rotations.get(i);
					}
				}
				if(depth == 1)
				{
					possibleRot.add(best);
					current.setRotationIndex(possibleRot.size()-1);
				}
				return min;
			}
		}
			
		return 0;
	}
	private List<GameBoard> getPossiblePlacements(GameBoard current)
	{
		List<GameBoard> tempList = new ArrayList<>();
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(current.legalMove(i, j))
				{
					tempList.add(current.addTile(i,j,turn));
				}
			}
		}
		return tempList;
	}
	private List<GameBoard> rotations(GameBoard current)
	{
		List<GameBoard> tempList = new ArrayList<>();
		for(int i = 0; i < 4; i++)
		{
			tempList.add(current.leftRotate(i+1));
			tempList.add(current.rightRotate(i+1));
			
		}
		return tempList;
	}
	// gets the board with the tile placed
	public GameBoard placeTile()
	{
		return returnPlace;
	}
	// gets the rotation that the place tile came from 
	public GameBoard rotateBox()
	{
		return possibleRot.get(returnPlace.getRotationIndex());
	}
	// miniMax algorithm with A/B pruning.
	private int miniMaxWAB(int depth, int minOrMax,GameBoard current,int newAlpha,int newBeta)
	{
		nodes++;
		int alpha = newAlpha;
		int beta = newBeta;
		if(depth == maxDepth)
		{
			return current.getValue();
		}
		else
		{
			if(minOrMax == 1)
			{
				int max = Integer.MIN_VALUE;
				List<GameBoard> placements = getPossiblePlacements(current);
				for(int i = 0; i < placements.size(); i++)
				{
					int check = DEFAULT_CHECK;
					if(placements.get(i).getWin() >= 0)
					{
						check = placements.get(i).getValue() * 100;
					}
					else if (beta > alpha)
					{
						check = miniMaxWAB(depth+1,2,placements.get(i),alpha,beta);
					}
					if(check > alpha && check != DEFAULT_CHECK)
					{
						alpha = check;
					}
					if(check > max && check != DEFAULT_CHECK)
					{
						max = check;
						if(depth == 0)
						{
							returnPlace = placements.get(i);
						}
					}
				}
				return max;
			}
			else if(minOrMax == 2)
			{
				GameBoard best = null;
				int max = Integer.MIN_VALUE;
				List<GameBoard> rotations = rotations(current);
				for(int i = 0; i < rotations.size(); i++)
				{
					int check = DEFAULT_CHECK; 
					if(rotations.get(i).getWin() >= 0)
					{
						check = rotations.get(i).getValue() * 100 ;
					}
					else if(beta > alpha)
					{
						check = miniMaxWAB(depth+1,3,rotations.get(i),alpha,beta);
					}
					if(check > alpha && check != DEFAULT_CHECK)
					{
						alpha = check;
					}
					if(check > max && check != DEFAULT_CHECK)
					{
						max = check;
						best = rotations.get(i);
					}
				}
				if(depth == 1)
				{
					possibleRot.add(best);
					current.setRotationIndex(possibleRot.size()-1);
				}
				return max;
			}
			if(minOrMax == 3)
			{
				int min = Integer.MAX_VALUE;
				List<GameBoard> placements = getPossiblePlacements(current);
				for(int i = 0; i < placements.size(); i++)
				{
					int check = DEFAULT_CHECK;
					if(placements.get(i).getWin() >= 0)
					{
						check = placements.get(i).getValue() * 100;
					}
					else if(beta > alpha)
					{
						check = miniMaxWAB(depth+1,4,placements.get(i),alpha, beta);
					}
					if(check < beta && check != DEFAULT_CHECK)
					{
						beta = check;
					}
					if(check < min && check != DEFAULT_CHECK)
					{
						min = check;
						if(depth == 0)
						{
							returnPlace = placements.get(i);
						}
					}
				}
				return min;
			}
			else if(minOrMax == 4)
			{
				GameBoard best = null;
				int min = Integer.MAX_VALUE;
				List<GameBoard> rotations = rotations(current);
				for(int i = 0; i < rotations.size(); i++)
				{
					int check = DEFAULT_CHECK;
					if(rotations.get(i).getWin() >= 0)
					{
						check = rotations.get(i).getValue() * 100;
					}
					else if(beta > alpha)
					{
						check = miniMaxWAB(depth+1,1,rotations.get(i),alpha,beta);
					}
					if (check < beta && check != DEFAULT_CHECK)
					{
						beta = check;
					}
					if(check < min && check != DEFAULT_CHECK)
					{
						min = check;
						best = rotations.get(i);
					}
				}
				if(depth == 1)
				{
					possibleRot.add(best);
					current.setRotationIndex(possibleRot.size()-1);
				}
				return min;
			}
		}
			
		return 0;
	}
}
