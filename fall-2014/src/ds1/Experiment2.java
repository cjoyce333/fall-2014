package ds1;

public class Experiment2 { public static void main(String args[]){ 
	int p1Wins = 0; 
	int p2Wins = 0; 
//	int draw = 0; 
	int p1 = 60;
	int p2 = 22;
	int p3 = 19;
	int playTimes = 1; 
	for(int gamesPlayed = 0; gamesPlayed < playTimes; gamesPlayed++){ 
		Nim mcExperiment = new Nim(p1, p2, p3); 
		if(mcExperiment.endstate == 1) p1Wins++; 
		if(mcExperiment.endstate == 2) p2Wins++; 
	//	if(mcExperiment.endstate == -1) draw++; 
		} 
	System.out.println("Player 1 won " + p1Wins + " out of " +playTimes+ " times."); 
	System.out.println("Player 2 won " + p2Wins + " out of " + playTimes+" times."); 
	//System.out.println("The game was a draw " + draw + " out of " +playTimes+ " times."); 
	if(p1Wins>p2Wins){
		System.out.println(p1 +", "+ p2 +", "+ p3 + " is a Player 1 Game!");
	}
	else
		System.out.println(p1 +", "+ p2 +", "+ p3 + " is a Player 2 Game!");
	
	} 
} 

// in order to get this to run, I had to use my computerPlaySelf method inside the TicTacToe constructor in the TicTacToe class instead of play method.

