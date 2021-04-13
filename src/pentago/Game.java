package pentago;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game 
{
	private static String MY_FILE = "./resources/Output2.txt";
	//the current gameboard
	private GameBoard currentBoard;
	//the ai player
	private AI_Player theAI;
	// Determines who is going first
	private int val;
	//Determines which piece the player is
	private int playerPiece;
	
	
	//constructor for the game
	public Game(int first,char piece)
	{
		int check;
		if(piece == 'w')
		{
			check = 1;
		}
		else 
		{
			check = -1;
		}
		int startDepth = 3;
		if(first == 2)
		{
			first = -1;
			startDepth = 1;
		}
		currentBoard = new GameBoard();
		playerPiece = check;
		theAI = new AI_Player(-playerPiece ,startDepth);
		val = first;
	}
	//getter for the current board
	public GameBoard getBoard()
	{
		return currentBoard;
	}
	//method to start the game
	public void start()
	{
		Scanner in = new Scanner(System.in);
		try(BufferedWriter out = new BufferedWriter(new FileWriter(MY_FILE, true))) 
		{
			if(val == 1)
			{
				out.write("Player 1: Timmy Roma.\nToken: ");
				if(playerPiece == 1)
				{
					out.write("w\n");
				}
				else
				{
					out.write("b\n");
				}
				out.write("Player 2: Computer.\nToken: ");
				if(playerPiece == 1)
				{
					out.write("b\n");
				}
				else
				{
					out.write("w\n");
				}
				System.out.print("Player 1: Timmy Roma.\nToken: ");
				if(playerPiece == 1)
				{
					System.out.print("w\n");
				}
				else
				{
					System.out.print("b\n");
				}
				System.out.print("Player 2: Computer.\nToken: ");
				if(playerPiece == 1)
				{
					System.out.print("b\n");
				}
				else
				{
					System.out.print("w\n");
				}
				
			}
			else
			{
				out.write("Player 1: Computer.\nToken: ");
				if(playerPiece == 1)
				{
					out.write("w\n");
				}
				else
				{
					out.write("b\n");
				}
				out.write("Player 2: Timmy Roma.\nToken: ");
				if(playerPiece == 1)
				{
					out.write("w\n");
				}
				else
				{
					out.write("b\n");
				}
				System.out.print("Player 1: Computer.\nToken: ");
				if(-playerPiece == 1)
				{
					System.out.print("w\n");
				}
				else
				{
					System.out.print("b\n");
				}
				System.out.print("Player 2: Timmy Roma.\nToken: ");
				if(playerPiece == 1)
				{
					System.out.print("w\n");
				}
				else
				{
					System.out.print("b\n");
				}
			}
			while (true) {
				boolean check2 = true;

				currentBoard.output(out);
				if (val == 1) {
					val = -val;
					while (check2) {
						System.out.println("Which Box > ");
						int x = in.nextInt();
						System.out.println("Which Space > ");
						int y = in.nextInt();
						if (currentBoard.legalMove(x - 1, y - 1)) {
							currentBoard = currentBoard.addTile(x - 1, y - 1, playerPiece);
							check2 = false;
						} else {
							System.out.println("Invalid selection");
						}
					}
					if (currentBoard.getWin() >= 0) {
						currentBoard.output(out);
						switch (currentBoard.getWin()) {
						case 0:
							System.out.println("Tie!");
							break;
						case 1:
							System.out.println("Min Win!");
							break;
						case 2:
							System.out.println("Max Win!");
							break;
						}
						break;
					}
					currentBoard.output(out);
					System.out.println("Which Box would you like to rotate > ");
					int r = in.nextInt();
					System.out.println("Which direction? (1-left , 2-right) > ");
					int d = in.nextInt();
					if (d == 1) {
						currentBoard = currentBoard.leftRotate(r);
					} else {
						currentBoard = currentBoard.rightRotate(r);
					}
					if (currentBoard.getWin() >= 0) {
						currentBoard.output(out);
						switch (currentBoard.getWin()) {
						case 0:
							System.out.println("Tie!");
							break;
						case 1:
							System.out.println("Min Win!");
							break;
						case 2:
							System.out.println("Max Win!");
							break;
						}
						break;
					}
				} else if (val == -1) {
					val = -val;
					long time = System.currentTimeMillis();
					theAI.makeMove(currentBoard);
					currentBoard = theAI.placeTile();
					if (currentBoard.getWin() >= 0) {
						currentBoard.output(out);
						switch (currentBoard.getWin()) {
						case 0:
							System.out.println("Tie!");
							break;
						case 1:
							System.out.println("Min Win!");
							break;
						case 2:
							System.out.println("Max Win!");
							break;
						}
						break;
					}
					currentBoard.output(out);
					currentBoard = theAI.rotateBox();
					long time2 = System.currentTimeMillis();
					System.out.println((time2 - time));
					if (currentBoard.getWin() >= 0) {
						currentBoard.output(out);
						switch (currentBoard.getWin()) {
						case 0:
							System.out.println("Tie!");
							break;
						case 1:
							System.out.println("Min Win!");
							break;
						case 2:
							System.out.println("Max Win!");
							break;
						}
						break;
					}
				}
			}
			in.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
