package poteto;

import java.util.Scanner;

public class Main{
	
	public static Game game;
	public static Player player1;
	public static Player player2;
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println("");
		System.out.println("");
		System.out.println("Loading new Game ...");
		System.out.println("");
		System.out.println("");
		
			Scanner sc = new Scanner(System.in);
		System.out.println("Enter Username for Player 1:");
			String user1 = sc.nextLine();
		
		System.out.println("");
		
		System.out.println("Enter Username for Player 2:");
			String user2 = sc.nextLine();
		
		System.out.println("");
		
		System.out.println("Enter Big Blind Amount");
			int bb = Integer.parseInt(sc.next());
			int sb = bb / 2;
		
		System.out.println("");
		
		System.out.println("Enter Number of Rounds");
			int rounds = Integer.parseInt(sc.next());
		
		System.out.println("");
		
		System.out.println("Enter Player Starting Chips Amount");
			int chips = Integer.parseInt(sc.next());
		
		System.out.println("");
		System.out.println("");
			player1 = new Player(user1, chips);
			player2 = new Player(user2, chips);
			game = new Game(bb, sb, rounds, player1, player2);
		
		System.out.println("");
		System.out.println("|  " + player1.getUserName() + " VS "+ player2.getUserName());
		System.out.println("|  Everyone has " + chips + " chips");
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		
			game.start(true);
	}
}