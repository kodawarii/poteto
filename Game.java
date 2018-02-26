package poteto;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game{
	
	private int bb;
	private int sb;
	private int rounds;
	private int roundsCounter = 0;
	private int pot;
	
	private Player blindStarter;
	private Player blindOther;
	private Player p1;
	private Player p2;

	public Game(int bb, int sb, int rounds, Player p1, Player p2){
		
		pot = 0;
		
		this.bb = bb;
		this.sb = sb;
		this.rounds = rounds;
		
		this.p1 = p1;
		this.p2 = p2;
		
		Random ran = new Random();
		if(ran.nextInt(10) + 1 > 5){
			blindStarter = p1;
			blindOther = p2;
		}
		else{
			blindStarter = p2;
			blindOther = p1;
		}
		
		printStart();
	}
	
	public void start(boolean first) throws InterruptedException{
		
		if(roundsCounter >= rounds){
			displayResults();
		}
		else{
			System.out.println("Round " + (roundsCounter + 1) + " of " + rounds);
		}
		
		roundsCounter++;
		
		if(!first){
			Player temp = blindOther;
			blindOther = blindStarter;
			blindStarter = temp;
			
			pot = 0;
		}
		
		// Posting Blinds
		System.out.println("|  Big Blind is: " + blindStarter.getUserName());
		System.out.println("|  Small Blind is: " + blindOther.getUserName());
		System.out.println("");
		System.out.println("|  " + blindStarter.getUserName() + " posts big blind ( " + bb + " )");
		System.out.println("|  " + blindOther.getUserName() + " posts small blind ( " + sb + " )");
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
			blindStarter.updateChips(-bb);
			blindStarter.setCurrentBet(bb);
			pot += bb;
			blindOther.updateChips(-sb);
			blindOther.setCurrentBet(sb);
			pot += sb;
		printChipData();
		System.out.println("");
		System.out.println("<Current Bet is " + blindStarter.getCurrentBet() + " >");
		System.out.println("<Your Bet was " + blindOther.getCurrentBet() + " >");
		System.out.println("");
		
		// Pre-Flop round
		System.out.println("// PRE-FLOP");
		System.out.println(blindOther.getUserName() + " can call ( " + (bb-sb) + " ) <c>");
		System.out.println(blindOther.getUserName() + " can raise <r>");
		System.out.println(blindOther.getUserName() + " can go ALL IN <a>");
		System.out.println(blindOther.getUserName() + " can fold <f>");
		System.out.println("");
		
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			System.out.println("");
			
			List<String> choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("c")){
				call(blindOther, blindStarter, bb-sb);
				
				System.out.println("");
				System.out.println(blindStarter.getUserName() + " posted the Big Blind " + bb + ", and " + blindOther.getUserName() + " (SB) has called you");
				System.out.println("");
				System.out.println(blindStarter.getUserName() + " can check <q>");
				System.out.println(blindStarter.getUserName() + " can raise <r>");
				System.out.println(blindStarter.getUserName() + " can go ALL IN <a>");
				System.out.println("");
				
					sc = new Scanner(System.in);
					choice = sc.nextLine();
					
					choiceList = Arrays.asList(choice.split("\\s+"));
					if(choiceList.get(0).equals("q")){
						// ... continue to Flop round 
						
						System.out.println("");
						System.out.println("");
						System.out.println("-------------------------------------------------------------------------");
						System.out.println("");
						System.out.println("                 " + blindStarter.getUserName() + " Checks " + bb);
						System.out.println("");
						System.out.println("");
						printChipData();
						System.out.println("");
						System.out.println("-------------------------------------------------------------------------");
						System.out.println("");
							Thread.sleep(900);
					}
					else if(choiceList.get(0).equals("r")){
						raise(blindStarter, blindOther, Integer.parseInt(choiceList.get(1)));
					}
					else if(choiceList.get(0).equals("a")){
						// ... all in 
					}
					
				// ... continue to Flop round
			}
			else if(choiceList.get(0).equals("r")){
				raise(blindOther, blindStarter, Integer.parseInt(choiceList.get(1)));
			}
			else if(choiceList.get(0).equals("f")){
				fold(blindStarter, blindOther);
			}
			else if(choiceList.get(0).equals("a")){
				
				allin();
				// ... all in
			}
			
		// Flop round
		System.out.println("// FLOP");
		System.out.println(blindStarter.getUserName() + " can Check <q>");
		System.out.println(blindStarter.getUserName() + " can Bet <b>");
		System.out.println(blindStarter.getUserName() + " can go ALL IN <a>");
		System.out.println("");
		
			sc = new Scanner(System.in);
			choice = sc.nextLine();
			System.out.println("");
			
			choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("q")){
					blindStarter.setCurrentBet(0);
				System.out.println("");
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
				System.out.println("                 " + blindStarter.getUserName() + " Checks");
				System.out.println("");
				System.out.println("");
				printChipData();
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
					Thread.sleep(900);
					passCheck(blindStarter, blindOther);
			}
			else if(choiceList.get(0).equals("b")){
					int currentBet = Integer.parseInt(choiceList.get(1));
					blindStarter.updateChips(-currentBet);
					blindStarter.setCurrentBet(currentBet);
					pot += currentBet;
					bet(blindStarter, blindOther, currentBet);
			}
			else if(choiceList.get(0).equals("a")){
				
				// ... all in
				blindStarter.setCurrentBet(blindStarter.getChips());
				allin();
			}
			
		// Turn round
		System.out.println("// TURN");
		System.out.println(blindOther.getUserName() + " can Check <q>");
		System.out.println(blindOther.getUserName() + " can Bet <b>");
		System.out.println(blindOther.getUserName() + " can go ALL IN <a>");
		System.out.println("");
		
			sc = new Scanner(System.in);
			choice = sc.nextLine();
			System.out.println("");
			
			choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("q")){
					blindOther.setCurrentBet(0);
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
				System.out.println("                 " + blindOther.getUserName() + " Checks");
				System.out.println("");
				System.out.println("");
				printChipData();
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
					Thread.sleep(900);
					passCheck(blindOther, blindStarter);
			}
			else if(choiceList.get(0).equals("b")){
					int currentBet = Integer.parseInt(choiceList.get(1));
				System.out.println(blindOther.getUserName() + " bets " + currentBet);
					blindOther.updateChips(-currentBet);
					blindOther.setCurrentBet(currentBet);
					pot += currentBet;
					bet(blindOther, blindStarter, currentBet);
			}
			else if(choiceList.get(0).equals("a")){
				
				// ... all in
				blindOther.setCurrentBet(blindOther.getChips());
				allin();
			}
		
		// River round
		System.out.println("// RIVER");
		System.out.println(blindStarter.getUserName() + " can Check <q>");
		System.out.println(blindStarter.getUserName() + " can Bet <b>");
		System.out.println(blindStarter.getUserName() + " can go ALL IN <a>");
		System.out.println("");
		
			sc = new Scanner(System.in);
			choice = sc.nextLine();
			System.out.println("");
			
			choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("q")){
					blindStarter.setCurrentBet(0);
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
				System.out.println("                 " + blindStarter.getUserName() + " Checks");
				System.out.println("");
				System.out.println("");
				printChipData();
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
					Thread.sleep(900);
					passCheck(blindStarter, blindOther);

					victory();
			}
			else if(choiceList.get(0).equals("b")){
					int currentBet = Integer.parseInt(choiceList.get(1));
				System.out.println(blindStarter.getUserName() + " bets " + currentBet);
					blindStarter.updateChips(-currentBet);
					blindStarter.setCurrentBet(currentBet);
					pot += currentBet;
					bet(blindStarter, blindOther, currentBet);
					
					victory();
			}
			else if(choiceList.get(0).equals("a")){
				// ... all in
				
				blindStarter.setCurrentBet(blindStarter.getChips());
				allin();
				
				victory();
			}
	}
	
	private void victory() throws InterruptedException{
		System.out.println("Enter userName of Winner: ");
		System.out.println("");
			
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			System.out.println("");
			
			List<String> choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals(blindStarter.getUserName())){
				fold(blindStarter, blindOther);
			}
			else{
				fold(blindOther, blindStarter);
			}
	}
	
	private void allin(){
		
	}
	
	private void bet(Player thisGuyBetted, Player thisGuyHasToCall, int betSize) throws InterruptedException{
		
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("                 " + thisGuyBetted.getUserName() + " bets " + betSize);
		System.out.println("");
		System.out.println("");
		printChipData();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
			Thread.sleep(900);
		
		System.out.println(thisGuyHasToCall.getUserName() + " can call ( " + betSize + " )");	
		System.out.println(thisGuyHasToCall.getUserName() + " can Raise");
		System.out.println(thisGuyHasToCall.getUserName() + " can go All in");
		System.out.println(thisGuyHasToCall.getUserName() + " can Fold");
		System.out.println("");
		
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			System.out.println("");
			
			List<String> choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("c")){
					thisGuyHasToCall.setCurrentBet(betSize);
				System.out.println(thisGuyHasToCall.getUserName() + " calls");
					call(thisGuyHasToCall, thisGuyBetted, betSize);
					// ... continue to turn, river round
			}
			else if(choiceList.get(0).equals("r")){
				int raiseValue = Integer.parseInt(choiceList.get(1));
				
				
				thisGuyHasToCall.setCurrentBet(raiseValue);
				raise(thisGuyHasToCall, thisGuyBetted, raiseValue);
			}
			else if(choiceList.get(0).equals("f")){
				fold(thisGuyBetted, thisGuyHasToCall);
			}
			else if(choiceList.get(0).equals("a")){
				
				// ... all in
				thisGuyHasToCall.setCurrentBet(thisGuyHasToCall.getChips());
				allin();
			}
		
	}
	
	private void reRaise(Player guyWhoReRaised, Player guyWhoNeedsToCall, int reRaiseSize) throws InterruptedException{
		
			guyWhoReRaised.updateChips(guyWhoReRaised.getCurrentBet());
			pot -= guyWhoReRaised.getCurrentBet();
			
			guyWhoReRaised.updateChips(-(reRaiseSize));
			guyWhoReRaised.setCurrentBet(reRaiseSize);
			pot += reRaiseSize;
			
			int chipsForCallingReRaise = reRaiseSize - guyWhoNeedsToCall.getCurrentBet();
			
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("                 " + guyWhoReRaised.getUserName() + " Re-raises to " + reRaiseSize);
		System.out.println("");
		printChipData();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		
		System.out.println(guyWhoNeedsToCall.getUserName() + " can fold");	
		System.out.println(guyWhoNeedsToCall.getUserName() + " can call ( " + chipsForCallingReRaise + " )");	
		System.out.println(guyWhoNeedsToCall.getUserName() + " can re-raise");
		System.out.println(guyWhoNeedsToCall.getUserName() + " can go ALL IN");
		System.out.println("");
		
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			System.out.println("");
			
			List<String> choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("f")){
				fold(guyWhoReRaised, guyWhoNeedsToCall);
			}
			if(choiceList.get(0).equals("c")){
				guyWhoNeedsToCall.setCurrentBet(reRaiseSize);
				call(guyWhoNeedsToCall, guyWhoReRaised, chipsForCallingReRaise);
			}
			if(choiceList.get(0).equals("r")){
				reRaise(guyWhoNeedsToCall, guyWhoReRaised, Integer.parseInt(choiceList.get(1)));
			}
			if(choiceList.get(0).equals("a")){
				
				// ... all in
				guyWhoNeedsToCall.setCurrentBet(guyWhoNeedsToCall.getChips());
				allin();
			}
	}
	
	private void raise(Player guyWhoRaised, Player guyWhoNeedsToCall, int raiseSize) throws InterruptedException{
		
		// ... update pot and process raising-Player's chips
			guyWhoRaised.setCurrentBet(raiseSize);
			pot += raiseSize;
			guyWhoRaised.updateChips(-(raiseSize));
		
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("                 " + guyWhoRaised.getUserName() + " raises to " + raiseSize);
		System.out.println("");
		printChipData();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		
		// ... respond - player can call, re-raise, fold, all in 
		System.out.println(guyWhoNeedsToCall.getUserName() + " can fold");	
		System.out.println(guyWhoNeedsToCall.getUserName() + " can call ( " + (raiseSize - guyWhoNeedsToCall.getCurrentBet()) + " )");	
		System.out.println(guyWhoNeedsToCall.getUserName() + " can re-raise");
		System.out.println(guyWhoNeedsToCall.getUserName() + " can go ALL IN");
		System.out.println("");
		
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			System.out.println("");
			
			List<String> choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("f")){
				fold(guyWhoRaised, guyWhoNeedsToCall);
			}
			if(choiceList.get(0).equals("c")){
				guyWhoNeedsToCall.setCurrentBet(raiseSize - guyWhoNeedsToCall.getCurrentBet());
				call(guyWhoNeedsToCall, guyWhoRaised, guyWhoNeedsToCall.getCurrentBet());
			}
			if(choiceList.get(0).equals("r")){
				reRaise(guyWhoNeedsToCall, guyWhoRaised, Integer.parseInt(choiceList.get(1)));
			}
			if(choiceList.get(0).equals("a")){
				
				// ... all in
				guyWhoNeedsToCall.setCurrentBet(guyWhoNeedsToCall.getChips());
				allin();
			}
	}
	
	private void passCheck(Player thisGuyChecked, Player checkedToThisPerson) throws InterruptedException{
		System.out.println(checkedToThisPerson.getUserName() + " can Check");	
		System.out.println(checkedToThisPerson.getUserName() + " can Bet");
		System.out.println(checkedToThisPerson.getUserName() + " can go ALL IN");
		System.out.println("");
		
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			System.out.println("");
			
			List<String> choiceList = Arrays.asList(choice.split("\\s+"));
			if(choiceList.get(0).equals("q")){
					checkedToThisPerson.setCurrentBet(0);
				System.out.println("");
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
				System.out.println("                 " + checkedToThisPerson.getUserName() + " also Checks");
				System.out.println("");
				System.out.println("");
				printChipData();
				System.out.println("");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
					Thread.sleep(900);
				// ... continue to turn round, River round
			}
			else if(choiceList.get(0).equals("b")){
					int currentBet = Integer.parseInt(choiceList.get(1));
				System.out.println(checkedToThisPerson.getUserName() + " bets " + currentBet);
					checkedToThisPerson.updateChips(-currentBet);
					checkedToThisPerson.setCurrentBet(currentBet);
					pot += currentBet;
					bet(checkedToThisPerson, thisGuyChecked, currentBet);
			}
			else if(choiceList.get(0).equals("a")){
				
				// ... all in
				checkedToThisPerson.setCurrentBet(checkedToThisPerson.getChips());
				allin();
			}
	}
	
	private void call(Player callingPlayer, Player guyWhoMadeBet, int betSize) throws InterruptedException{
		System.out.println("");System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("                 " + callingPlayer.getUserName() + " calls " + betSize);
		System.out.println("");
			callingPlayer.updateChips(-betSize);
			callingPlayer.setCurrentBet(betSize);
			pot += (betSize);
		printChipData();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
			Thread.sleep(900);
		System.out.println("");System.out.println("");
	}
	
	private void fold(Player victor, Player folder) throws InterruptedException{
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("                 " + folder.getUserName() + " folds / Mucks / Looses");
		System.out.println("                 " + victor.getUserName() + " wins Pot: " + pot);
		System.out.println("");
			victor.updateChips(pot);
			victor.updateVictory();
			folder.updateLoss();
			pot = 0;
		printChipData();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
			Thread.sleep(2500);
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("                 Round " + roundsCounter + " of " + rounds);
		System.out.println("");
		System.out.println("                 Win/Loss of Players:");
		System.out.println("");
		System.out.println("                                      " + folder.getUserName() + ": " + folder.getWL() + "%");
		System.out.println("                                      " + victor.getUserName() + ": " + victor.getWL() + "%");
		System.out.println("");
		System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
			start(false);
	}
	
	private void printChipData(){
		System.out.println(p1.getUserName() + ": " + p1.getChips() + "     |     " + p2.getUserName() + ": " + p2.getChips() + "     ||     " + "POT: " + pot);
	}
	
	private void printStart(){
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("                                 ----------------------");
		System.out.println("                                 |                    |");		
		System.out.println("                                 |  New Game Created  |");
		System.out.println("                                 |                    |");
		System.out.println("                                 ----------------------");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");
		
		System.out.println("|  BIG BLIND: " + bb + "    ");
		System.out.println("|  SMALL BLIND: " + sb + "  ");
		System.out.println("|  Best of: " + this.rounds + " games      ");
	}
	
	private void displayResults(){
		System.out.println("Game has Finished: " + rounds + " played");
		System.out.println(p1.getUserName() + " has W/L of: " + p1.getWL() + "%");
		System.out.println(p2.getUserName() + " has W/L of: " + p2.getWL() + "%");
		
		// end of program
		Scanner sc = new Scanner(System.in);
		String choice = sc.nextLine();
	}
}