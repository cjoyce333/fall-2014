package ds1;

import java.util.HashMap;

/**
 * @author UD DS1, Spring 2014
 * @version 0.5
 * @see ds1.TwoPlayer
 * @see ds1.DSArrayList
 * @see ds1.DSNode
 * @see ds1.TicTacToe
 * @see ds1.Demo
 */
public abstract class GameCopy{
	/**
	 * The number of nodes in a tree
	 * @see #buildTree(Object)
	 * @see ds1.TicTacToe#TicTacToe() // constructor
	 */
	int numNodes;
	// The endstate takes a value at the end of
	// the game, showing win/lose/draw or points
	// earned
	int endstate;

	int numMoves;

	// We will declare an "object" called "board"
	// but we won't say anything about its
	// structure at this time.
	Object board;

	// Define the "move" method
	// turn = 1 or 2, naming which player should go
	// Simply calls the appropriate humanMove or computerMove method
	// the "final" keyword prohibits subclasses from over-riding this
	//     method.
	final void move(int turn){
		if(isHuman[turn])
			humanMove(turn);
		else
			computerMove(turn);
	}

	// Array of booleans: true = human, false = computer
	// isHuman[1] is for Player 1, isHuman[2] is for Player 2
	boolean[] isHuman = new boolean[3];

	abstract void humanMove(int turn);
	abstract void computerMove(int turn);

	// We will override this so each game can
	// draw itself
	/**
	 * @see TicTacToe.drawBoard
	 */
	abstract void drawBoard();

	// This method is called after each move
	// Returns 0 if game still going
	// returns -1 if game over, DRAW
	// returns P if player P has won
	abstract int endCheck();

	// Call this method to start a game
	public abstract void play();


	/*
	 * This inner class will contain game win/lose/draw information
	 * for each node in the game tree.
	 */
	/**
	 * @author UD DS1, Spring 2014
	 *
	 * @param <E> -any object which goes inside the game node
	 * @see ds1.DSNode
	 * 
	 */
	class DSGameNode<E> extends DSNode<E>{
		int whoWins;

		public DSGameNode(E theThing, DSGameNode<E> p){
			super(theThing, p);
		}
	}


	/*
	 * Builds a game tree for the game starting with board b
	 */

	/**
	 * @param b -the object from which a tree will be built
	 * @return DSGameNode<Object> - a tree of nodes of object b and it's children nodes
	 */
	DSGameNode<Object> buildTree(Object b){
		DSGameNode<Object> root =
				new DSGameNode<Object>(b, null);
		numNodes++;
		// Get the children of this board. In the base case, there
		// will be no children
		DSArrayList<Object> children =
				getChildren(b);

		for(int i = 0; i < children.size(); i++){
			DSGameNode<Object> n = buildTree(children.get(i));
			root.addChild(n);
		}

		return root;
	}

	/*
	 * The actual game needs to be able to produce a list of
	 * all "child" boards of a given board b.
	 */
	abstract DSArrayList<Object> getChildren(Object b);


	/*
	 * Given a DSGameNode, returns the winner of
	 * this game board.
	 *
	 * XXX Currently this is geared toward 2-player
	 * games
	 * 
	 */
	/**
	 * @see TwoPlayer.PLAYER1WIN
	 * @see TwoPlayer.PLAYER2WIN
	 * @see TwoPlayer.CONTINUE
	 * @see TwoPlayer.DRAW
	 * @see ds1.DSArrayList
	 * @see ds1.DSNode
	 * @see ds1.TwoPlayer
	 * @see Game.DSGameNode
	 * @param n -the node to be evaluated
	 * @return -an int representing the winner of the given game board 
	 */
	HashMap<String, Integer> boardValues = new HashMap<String, Integer>(); // Field can be outside method and then used in method later
	/**
	 * 
	 */
	abstract String boardHash(Object b);
	
	int evaluateNode(DSGameNode<Object> n){
		// First, just look at our own board
		Object localBoard = n.returnThing();
		int rv = -99; // -99 means uninitialized
		
		
		// hash the board,see if we've saved work for this board in our boardValues HashMap. If so, return the result
		String bh = boardHash(localBoard);
		if(boardValues.containsKey(bh))
			return boardValues.get(bh);
		
		int val = evaluateBoard(n.returnThing());
		if(val != TwoPlayer.CONTINUE)
			rv = val;

		else{
			DSArrayList<DSNode<Object>> children = n.returnChildren();

			boolean drawIsPossible = false;
			int whoseTurn = whoseTurn(n.returnThing());

			for(int i = 0; i < children.size(); i++){
				DSGameNode<Object> c = (DSGameNode<Object>)	children.get(i);
				int childVal = evaluateNode(c);
				if(childVal == TwoPlayer.PLAYER1WIN && whoseTurn  == 1){
					rv = TwoPlayer.PLAYER1WIN;
					break;
				}
				if(childVal == TwoPlayer.PLAYER2WIN && whoseTurn  == 2){
					rv = TwoPlayer.PLAYER2WIN;
					break;
				}
				if(childVal == TwoPlayer.DRAW){
					drawIsPossible = true; // flag!
				}
			}
			
			if(rv == -99){
			if(drawIsPossible)
				rv = TwoPlayer.DRAW;
			else if(whoseTurn == 1)
				rv = TwoPlayer.PLAYER2WIN;
			else
				rv = TwoPlayer.PLAYER1WIN;
			}
		}// end of else
	
		
		/*
		 System.out.printf("Board:([%d], %d, %d, %d)->%d\n", 
	                localBoard[0], localBoard[1], localBoard[2], localBoard[3], rv);
		 */
		
		
		 // before returning rv, save it
		 boardValues.put(bh, rv);
		 
		return rv;
	}

	abstract int evaluateBoard(Object b);
	abstract int whoseTurn(Object b);
}