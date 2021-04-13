package pentago;

import java.io.BufferedWriter;
import java.io.IOException;

public class GameBoard 
{
	// repersentation of the game board
	private int[][] board;
	// all possible win paths
	private int[][] wins;
	//the index that has the rotation that gave this board its value
	private int rotationIndex;
	//the value of the gameboard
	private int value;
	//if this boards is a min or max win
	private boolean maxWin,minWin;
	//bix placement from lasy board
	private int boxPlace;
	//space placement from last board
	private int spacePlace; 
	//rotation direction from last board
	private int rotVal;
	//box rotated from last board
	private int rotBoxVal;
	
	// default constructor for starting board
	public GameBoard()
	{
		rotationIndex = -1;
		maxWin = minWin = false;
		board = new int[4][9];
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				board[i][j] = 0;
			}
		}
		value = 0;
	}
	
	//constructor for a new board after a move
	public GameBoard(int[][] theBoard,int newBox, int newSpace,int rot,int rotBox)
	{
		rotVal = rot;
		rotBoxVal = rotBox;
		boxPlace = newBox;
		spacePlace = newSpace;
		maxWin = minWin = false;
		board = theBoard;
		wins = new int[32][6];
		fillWins();
		calculateValue();
	}
	// prints out the board
	public void output(BufferedWriter out)
	{
		char[][] temp = new char[4][9];
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				 if(board[i][j] == 0)
				 {
					 temp[i][j] = '-';
				 }
				 else if(board[i][j] == 1)
				 {
					 temp[i][j] = 'w';
				 }
				 else 
				 {
					 temp[i][j] = 'b'; 
				 }
			}
		}
		System.out.println("+-------+-------+");
		System.out.println("| " + temp[0][0] + " "+ temp[0][1] + " "+ temp[0][2] + " | "+ temp[1][0] + " "+ temp[1][1] + " "+ temp[1][2] + " |");
		System.out.println("| " + temp[0][3] + " "+ temp[0][4] + " "+ temp[0][5] + " | "+ temp[1][3] + " "+ temp[1][4] + " "+ temp[1][5] + " |");
		System.out.println("| " + temp[0][6] + " "+ temp[0][7] + " "+ temp[0][8] + " | "+ temp[1][6] + " "+ temp[1][7] + " "+ temp[1][8] + " |");
		System.out.println("+-------+-------+");
		System.out.println("| " + temp[2][0] + " "+ temp[2][1] + " "+ temp[2][2] + " | "+ temp[3][0] + " "+ temp[3][1] + " "+ temp[3][2] + " |");
		System.out.println("| " + temp[2][3] + " "+ temp[2][4] + " "+ temp[2][5] + " | "+ temp[3][3] + " "+ temp[3][4] + " "+ temp[3][5] + " |");
		System.out.println("| " + temp[2][6] + " "+ temp[2][7] + " "+ temp[2][8] + " | "+ temp[3][6] + " "+ temp[3][7] + " "+ temp[3][8] + " |");
		System.out.println("+-------+-------+");
		if(rotVal != 0)
		{			
			System.out.print(boxPlace + "/" + spacePlace + " " + rotBoxVal);
			if(rotVal == 1)
			{
				System.out.println('L');
			}
			else
			{
				System.out.println('R');
			}
		}
		try 
		{
			out.write("+-------+-------+\n");
			out.write("| " + temp[0][0] + " "+ temp[0][1] + " "+ temp[0][2] + " | "+ temp[1][0] + " "+ temp[1][1] + " "+ temp[1][2] + " |\n");
			out.write("| " + temp[0][3] + " "+ temp[0][4] + " "+ temp[0][5] + " | "+ temp[1][3] + " "+ temp[1][4] + " "+ temp[1][5] + " |\n");
			out.write("| " + temp[0][6] + " "+ temp[0][7] + " "+ temp[0][8] + " | "+ temp[1][6] + " "+ temp[1][7] + " "+ temp[1][8] + " |\n");
			out.write("+-------+-------+\n");
			out.write("| " + temp[2][0] + " "+ temp[2][1] + " "+ temp[2][2] + " | "+ temp[3][0] + " "+ temp[3][1] + " "+ temp[3][2] + " |\n");
			out.write("| " + temp[2][3] + " "+ temp[2][4] + " "+ temp[2][5] + " | "+ temp[3][3] + " "+ temp[3][4] + " "+ temp[3][5] + " |\n");
			out.write("| " + temp[2][6] + " "+ temp[2][7] + " "+ temp[2][8] + " | "+ temp[3][6] + " "+ temp[3][7] + " "+ temp[3][8] + " |\n");
			out.write("+-------+-------+\n");
			if(rotVal != 0)
			{			
				out.write(boxPlace + "/" + spacePlace + " " + rotBoxVal);
				if(rotVal == 1)
				{
					out.write("L\n");
				}
				else
				{
					out.write("R\n");
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	// getter for the board
	public int[][] getBoard()
	{
		return board;
	}
	// fills all the possible wins
	// this is filling based on the successive indexes that if all are the same will be a win
	private void fillWins()
	{
		int[] temp = {board[0][0],board[0][1],board[0][2],board[1][0],board[1][1],0};
		wins[0] = temp;
		int[] temp1 = {board[0][3],board[0][4],board[0][5],board[1][3],board[1][4],0};
		wins[1] = temp1;
		int[] temp2 = {board[0][6],board[0][7],board[0][8],board[1][6],board[1][7],0};
		wins[2] = temp2;
		int[] temp3 = {board[2][0],board[2][1],board[2][2],board[3][0],board[3][1],0};
		wins[3] = temp3;
		int[] temp4 = {board[2][3],board[2][4],board[2][5],board[3][3],board[3][4],0};
		wins[4] = temp4;
		int[] temp5 = {board[2][6],board[2][7],board[2][8],board[3][6],board[3][7],0};
		wins[5] = temp5;
		int[] temp6 = {board[0][1],board[0][2],board[1][0],board[1][1],board[1][2],0};
		wins[6] = temp6;
		int[] temp7 = {board[0][4],board[0][5],board[1][3],board[1][4],board[1][5],0};
		wins[7] = temp7;
		int[] temp8 = {board[0][7],board[0][8],board[1][6],board[1][7],board[1][8],0};
		wins[8] = temp8;
		int[] temp9 = {board[2][1],board[2][2],board[3][0],board[3][1],board[3][2],0};
		wins[9] = temp9;
		int[] temp10 = {board[2][4],board[2][5],board[3][3],board[3][4],board[3][5],0};
		wins[10] = temp10;
		int[] temp11 = {board[2][7],board[2][8],board[3][6],board[3][7],board[3][8],0};
		wins[11] = temp11;
		int[] temp12 = {board[0][0],board[0][3],board[0][6],board[2][0],board[2][3],0};
		wins[12] = temp12;
		int[] temp13 = {board[0][1],board[0][4],board[0][7],board[2][1],board[2][4],0};
		wins[13] = temp13;
		int[] temp14 = {board[0][2],board[0][5],board[0][8],board[2][2],board[2][5],0};
		wins[14] = temp14;
		int[] temp15 = {board[0][3],board[0][6],board[2][0],board[2][3],board[2][6],0};
		wins[15] = temp15;
		int[] temp16 = {board[0][4],board[0][7],board[2][1],board[2][4],board[2][7],0};
		wins[16] = temp16;
		int[] temp17 = {board[0][5],board[0][8],board[2][2],board[2][5],board[2][8],0};
		wins[17] = temp17;
		int[] temp18 = {board[1][0],board[1][3],board[1][6],board[3][0],board[3][3],0};
		wins[18] = temp18;
		int[] temp19 = {board[1][1],board[1][4],board[1][7],board[3][1],board[3][4],0};
		wins[19] = temp19;
		int[] temp20 = {board[1][2],board[1][5],board[1][8],board[3][2],board[3][5],0};
		wins[20] = temp20;
		int[] temp21 = {board[1][3],board[1][6],board[3][0],board[3][3],board[3][6],0};
		wins[21] = temp21;
		int[] temp22 = {board[1][4],board[1][7],board[3][1],board[3][4],board[3][7],0};
		wins[22] = temp22;
		int[] temp23 = {board[1][5],board[1][8],board[3][2],board[3][5],board[3][8],0};
		wins[23] = temp23;
		int[] temp24 = {board[0][0],board[0][4],board[0][8],board[3][0],board[3][4],0};
		wins[24] = temp24;
		int[] temp25 = {board[0][4],board[0][8],board[3][0],board[3][4],board[3][8],0};
		wins[25] = temp25;
		int[] temp26 = {board[2][6],board[2][4],board[2][2],board[1][6],board[1][4],0};
		wins[26] = temp26;
		int[] temp27 = {board[2][4],board[2][2],board[1][6],board[1][4],board[1][2],0};
		wins[27] = temp27;
		int[] temp28 = {board[0][1],board[0][5],board[1][6],board[3][1],board[3][5],0};
		wins[28] = temp28;
		int[] temp29 = {board[2][3],board[2][1],board[0][8],board[1][3],board[1][1],0};
		wins[29] = temp29;
		int[] temp30 = {board[1][5],board[1][7],board[3][0],board[2][5],board[2][7],0};
		wins[30] = temp30;
		int[] temp31 = {board[0][3],board[0][7],board[2][2],board[3][3],board[3][7],0};
		wins[31] = temp31;
		
	}
	// calculates the value of the board by checking all wins and seeing 
	// for which who could win in each and how close in each they are
	private void calculateValue()
	{
		for(int i = 0; i < 32; i++)
		{
			int temp = 0;
			if(wins[i][0] >= 0 && wins[i][1] >= 0 && wins[i][2] >= 0 && wins[i][3] >= 0 && wins[i][4] >= 0)
			{
				for(int j = 0; j < 5; j++)
				{
					temp += wins[i][j];
				}
			}
			else if(wins[i][0] <= 0 && wins[i][1] <= 0 && wins[i][2] <= 0 && wins[i][3] <= 0 && wins[i][4] <= 0)
			{
				for(int j = 0; j < 5; j++)
				{
					temp += wins[i][j];
				}
			}
			if(temp > 0)
			{
				wins[i][5] = (int) Math.pow(5,temp);
			}
			else if(temp < 0)
			{
				wins[i][5] = -((int) Math.pow(5,-temp));
			}
			if(wins[i][5] == 3125)
			{
				maxWin = true;
				wins[i][5] = 10000;
			}
			if(wins[i][5] == -3125)
			{
				minWin = true;
				wins[i][5] = -10000;
			}
			value += wins[i][5];
		}
	}
	//getter for the value of the board
	public int getValue()
	{
		return value;
	}
	// returns a value based on which player is a winner in this board
	public int getWin()
	{
		if(maxWin && minWin)
		{
			return 0;
		}
		if(minWin)
		{
			return 1;
		}
		if(maxWin)
		{
			return 2;
		}
		else
		{
			return -1;
		}
	}
	// checks if a placement is legal 
	public boolean legalMove(int x, int y)
	{
		if(x < 0 || x > 3 || y < 0 || y > 8)
		{
			return false;
		}
		if(board[x][y] == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	// adds a tile and returns the new board
	public GameBoard addTile(int box, int space, int player)
	{
		int[][] temp = copyBoard();
		temp[box][space] = player;
		return new GameBoard(temp,box+1,space+1,0,0);
	}
	//makes a left rotation then returns the new board
	public GameBoard leftRotate(int box)
	{
		int[] temp = new int[9];
		temp[0] = this.board[box-1][2];
		temp[1] = this.board[box-1][5];
		temp[2] = this.board[box-1][8];
		temp[3] = this.board[box-1][1];
		temp[4] = this.board[box-1][4];
		temp[5] = this.board[box-1][7];
		temp[6] = this.board[box-1][0];
		temp[7] = this.board[box-1][3];
		temp[8] = this.board[box-1][6];
		int [][] temp2 = copyBoard();
		temp2[box-1] = temp;
		return new GameBoard(temp2,boxPlace,spacePlace,1,box);
	}
	//makes a right rotation then returns the new board
	public GameBoard rightRotate(int box)
	{
		int[] temp = new int[9];
		temp[0] = this.board[box-1][6];
		temp[1] = this.board[box-1][3];
		temp[2] = this.board[box-1][0];
		temp[3] = this.board[box-1][7];
		temp[4] = this.board[box-1][4];
		temp[5] = this.board[box-1][1];
		temp[6] = this.board[box-1][8];
		temp[7] = this.board[box-1][5];
		temp[8] = this.board[box-1][2];
		int [][] temp2 = copyBoard();
		temp2[box-1] = temp;
		return new GameBoard(temp2,boxPlace,spacePlace,2,box);
	}
	//copies the board
	private int[][] copyBoard()
	{
		int[][] temp = new int[4][9];
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				temp[i][j] = this.board[i][j];
			}
		}
		return temp;
	}
	//setter for the rotation index
	public void setRotationIndex(int index)
	{
		rotationIndex = index;
	}
	//getter for the rotation index
	public int getRotationIndex()
	{
		return rotationIndex;
	}
}

