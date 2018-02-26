package poteto;

public class Player{
	
	private int chips;
	private String username;
	private float victory;
	private float loss;
	
	private int currentBet;
	
	public Player(String username, int chips){
		this.victory = 0;
		this.loss = 0;
		
		this.chips = chips;
		this.username = username;
	}
	
	public int getChips(){
		return chips;
	}
	
	public void updateChips(int amount){
		this.chips += amount;
	}
	
	public String getUserName(){
		return username;
	}
	
	public void updateVictory(){
		this.victory++;
	}
	
	public void updateLoss(){
		this.loss++;
	}
	
	public float getWL(){
		return (victory / (victory + loss)) * 100;
	}
	
	public void setCurrentBet(int betSize){
		this.currentBet = betSize;
	}
	
	public int getCurrentBet(){
		return currentBet;
	}
}