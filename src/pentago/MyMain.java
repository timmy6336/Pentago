package pentago;

import java.util.Scanner;

public class MyMain 
{
	public static void main(String[] args)
	{
		System.out.println("Would you like to go first or second?(1-first/2-second): ");
		Scanner in = new Scanner(System.in);
		int playerMove = in.nextInt();
		System.out.println("Which color token would you like? W/B: ");
		String token = in.next();
		token = token.toLowerCase();
		Game theGame = new Game(playerMove,token.charAt(0));
		theGame.start();
		in.close();
	}
}
