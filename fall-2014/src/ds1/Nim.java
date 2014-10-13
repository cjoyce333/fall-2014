package ds1;
import ds1.Game.DSGameNode;

import java.util.ArrayList;
import java.util.Scanner;

import ds1.Game.DSGameNode;

/**
 * This class implements the game of Nim on arbitrarily many piles.
 *
 * @author  UD DS1, Spring 2014
 * @version 0.1
 * @see     ds1.TicTacToe
 */
public class Nim extends TwoPlayer{
	/**
	 * The number of piles for this game. Set within the constructor
	 */
	int numPiles;
	/**
	 * The Nim game board is encoded by an integer array.
	 * board[0] is the player whose turn it is
	 * board[i] is the number of stones left in pile i, for i > 0
	 */
	int[] board;
	Scanner scanner;

	/**
	 * Nim's constructor
	 * User sets {@link #numPiles} and board {@link #board}. <br>
	 * The game board is created. <br>
	 * When the game is finished, the endState is returned. <br>
	 * 
	 * @see ds1.TicTacToe#TicTacToe()
	 * @param piles
	 */
	public Nim(int...piles){
		scanner = new Scanner(System.in);
		numPiles = piles.length;
		board = new int[numPiles + 1];

		for(int i = 1; i <= numPiles; i++)
			board[i] = piles[i-1];

		board[0] = 1;
		numNodes = 0;

		// analyze tree
		DSNode.sizeHash.clear();
		DSNode<Object> root = buildTree(board);
		System.out.println("Size of tree is " + root.sizeOfTree());
		DSNode.sizeHash.clear();
		System.out.println("The number of leaves is " + root.leavesInTree());

		play();
		//computerPlaySelf();


		//	System.out.println("End state = " + endstate);

	}

	/**
	 * human moves performs the users turn\
	 * turn is whose turn it is
	 * take user input for the pile number and the number of stones to take
	 * then it updates the board and whose turn it is
	 * this is the gameboard and it is updated after the users move.
	 * @see board  
	 * @see {@link ds1.Game#humanMove(int)}
	 */
	void humanMove(int turn) {
		System.out.println(" From which pile? ");
		int pile = scanner.nextInt();

		if(pile > numPiles || pile < 1 || board[pile]<=0){
			do {
				System.out.println("Please pick an existing pile.");
				System.out.println(" From which pile? ");
				pile = scanner.nextInt();}
			while (pile > numPiles || pile < 1 || board[pile]<=0);
		}
		System.out.println("How many? ");
		int howMany = scanner.nextInt();

		if (howMany > board[pile] || howMany <= 0){
			do{
				System.out.println("Please pick a valid number of stones.");
				System.out.println("How many? ");
				howMany = scanner.nextInt();}
			while (howMany > board[pile] || howMany <= 0);
		}
		board[pile] -= howMany;
		board[0] = 3 - board[0];
	}

	/**
	 * The method for the computer's move <br>
	 * The computer figures out what move is the best move, and makes it. <br>
	 * If the computer knows its going to lose, it implements {@link #computerRandomMove}.
	 * @param {int} turn - The player whose turn it is at this point in the game
	 */
	void computerMove(int turn) {

		for(int i = 1; i <= numPiles; i++){ // loop over piles
			for(int j = 0; j < board[i]; j++){ // loop over stones to leave

				// make a new board, with this move
				int[] newBoard = new int[numPiles + 1];
				for(int k = 1; k <= numPiles; k++){
					newBoard[k] = board[k];
				}
				newBoard[i] = j;// make the move
				newBoard[0] = 3 - board[0]; // set those turn on this child board
				DSGameNode<Object> root = buildTree(newBoard);
				int ww = evaluateNode(root);
				// First check to see if we find a winning move
				if((ww == TwoPlayer.PLAYER1WIN && turn == 1) ||
						(ww == TwoPlayer.PLAYER2WIN && turn == 2)){
					board[i] = j;// really make the move on the current game board
					board[0] = 3-board[0];
					numMoves++;
					return;
				}
			}    
		}		

		// And if there is no win move, make a random move
		computerRandomMove(turn);
	}
	/*
	void computerRandomMove(int turn){
		// take one stone from the largest pile 
		int largestPileSize = -1;
		int largestPileIndex = -1; 
		for(int i = 1; i <= numPiles; i++)
			if(board[i] > largestPileSize){
				largestPileSize = board[i];
				largestPileIndex = i;
			}
		board[largestPileIndex] -= 1;
		board[0] = 3 - board[0]; // update whose turn it is on this board
	}

	 */


	/**
	 * @param turn
	 * @see numPiles
	 * @see Math.random
	 */
	void computerRandomMove(int turn){
		// take a random number of stones from a random pile
		int randPile = 1;
		int randStones = 1;
		for(int i = 1; i < numPiles; i++){
			for(int j = 0; j < board[i]; j++){
				randStones = (int)(Math.random() * j+1);

			}
			randPile = (int)(Math.random() * i+1);

		}
		board[randPile] -= randStones;
		numMoves++;
		board[0] = 3 - board[0]; // update whose turn it is on this board

	}



	void drawBoard() {
		for(int i = 1; i<=numPiles; i++){
			System.out.println("Pile " + i + " has " + board[i] + " stones.");
			System.out.println("");
		}
	}

	/** This method overrides the abstract method in the Game class.
	 * Checks to see if there are any stones left, and returns
	 * TwoPlayer.CONTINUE if there are stones remaining.
	 * If there are no more stones, the player whose turn it was
	 * on the preceding turn, must have taken the last stone, so
	 * it returns that player's win value as defined in the class
	 * TwoPlayer.java
	 */
	int endCheck() {
		int sumOfSizes = 0;
		// add up total number stones
		for(int i = 1; i <= numPiles; i++)
			sumOfSizes += board[i];
		if(sumOfSizes != 0)
			return TwoPlayer.CONTINUE;

		if (board[0] == 2)
			return TwoPlayer.PLAYER1WIN;
		else
			return TwoPlayer.PLAYER2WIN;
	}


	DSArrayList<Object> getChildren(Object b) {
		// Find the available moves
		// recompute "turn" right now
		int[] localBoard = (int[])b;

		int turn = localBoard[0];
		DSArrayList<Object> returnArray = new DSArrayList<Object>();

		// for each pile, consider what new size it could be

		for(int pile = 1; pile <= numPiles; pile++){
			for (int newSize = 0; newSize <localBoard[pile]; newSize++){

				// make a new board with the old board
				// plus our new move
				int[] newBoard = new int[numPiles + 1];
				for(int k = 1; k <= numPiles; k++){
					newBoard[k] = localBoard[k];
				}
				newBoard[pile] = newSize;
				newBoard[0] = 3- turn;

				returnArray.add(newBoard);
			}
		}
		return returnArray;
	}



	int evaluateBoard(Object b) {
		int[] localBoard = (int[])b;
		int rv = -1;

		int sumOfSizes = 0;
		// add up total number stones
		for(int i = 1; i <= numPiles; i++)
			sumOfSizes += localBoard[i];
		/*
		if(sumOfSizes != 0)
			return TwoPlayer.CONTINUE;
		// entry '0' represents whose turn it is
		if (localBoard[0] == 2)
			return TwoPlayer.PLAYER1WIN;
		else
			return TwoPlayer.PLAYER2WIN;
		 */
		if(sumOfSizes != 0)
			rv = TwoPlayer.CONTINUE;
		else{
			if(localBoard[0] == 1) // entry '0' represents whose turn it is
				rv = TwoPlayer.PLAYER2WIN;
			else
				rv = TwoPlayer.PLAYER1WIN;
		}
		//System.out.printf("Board:([%d], %d, %d, %d)->%d\n", 
		//        localBoard[0], localBoard[1], localBoard[2], localBoard[3], rv);
		return rv;
	}

	/**
	 * This method determines who turn it is, given a board
	 * 
	 * @see DS1.TicTacToe#whoseTurn(Object)
	 * @return returns and integer of 1 or 2.
	 */
	int whoseTurn(Object b) {
		int[] brd = (int[])b;
		return brd[0];
	}


	String boardHash(Object b) {
		int[] localBoard = (int[])b;
		String bh = "";
		for(int i = 0; i <= numPiles; i++)
			bh = bh + localBoard[i] + "+";
		return bh;
	}
}
