package ds1;

/*
 * Plays the Tic-Tac-Toe game
 */

import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class implements the game of Tic-Tac-Toe
 *
 * @author UD DS1, Spring 2014
 * @version 0.1
 * @see ds1.Nim
 *
 */
public class TicTacToe extends TwoPlayer{
	 /**
     * The TicTacToe board is a two dimensional array of chars ("X" "O" or " ")
     * that store which player moved on which space ("X" or "O") or
     * that the space is open (" ")
     * @see board this is the actual game board, telling java board will be a char[][]
     */
    /**
     * The TicTacToe game is encoded in an 2-dimensional integer array
     * Board[i][j] is a moveChar, which is either an 'X' or an 'O'.
     */
    char[][] board; // this tells Java what "board" will be
    Scanner scanner; // for reading input

    /**
     * constructor for a TicTacToeGame
     * creates a scanner to receives the user's input during play
     * prints the endstate of the game (winner or draw)
     *
     * @see board creates a blank board
     * @see DSGameNode from Game, creates the "root" (tree) for all possible moves from the blank board
     * @see evaluateNode from Game, determines the outcome of each node and assigns it a value
     * @see play() from TwoPlayer, calls the play method to start the TicTacToe game
     * @see numMoves
     */
    public TicTacToe(){
        scanner = new Scanner(System.in);  
        board = new char[3][3];  // here we actually build the board
        // fill the board with spaces
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = ' ';

        numNodes = 0;
        DSGameNode<Object> root = buildTree(board);    // Builds the game tree for the whole game!
        System.out.println("NumNodes = " + numNodes);
        int ww = evaluateNode(root);
        System.out.println(ww + " wins Tic-Tac-Toe");
        
        numMoves = 0;
        play();
        //computerPlaySelf();
        System.out.println("End state = " + endstate);
    }

     
    /**
     * Prints a representation of the board out in the terminal.
     * draws the TicTacToe Board
     *
     * @author UD DSI, Spring 2014
     * @see ds1.Game#drawBoard()
     * @version 0.1
     * @since 02/27/2014
     */
    void drawBoard(){
        System.out.println(" " + board[0][0] + " | " +
                board[0][1] + " | " + board[0][2] + "\t" +
                " " + charForPosition(0, 0) + " | " +
                charForPosition(0, 1) + " | " +
                charForPosition(0, 2));
        System.out.println("---+---+---\t---+---+---");
        System.out.println(" " + board[1][0] + " | " +
                board[1][1] + " | " + board[1][2] + "\t" +
                " " + charForPosition(1, 0) + " | " +
                charForPosition(1, 1) + " | " +
                charForPosition(1, 2));

        System.out.println("---+---+---\t---+---+---");
        System.out.println(" " + board[2][0] + " | " +
                board[2][1] + " | " + board[2][2] + "\t" +
                " " + charForPosition(2, 0) + " | " +
                charForPosition(2, 1) + " | " +
                charForPosition(2, 2));
        System.out.println("\n");
    }

    /**
     * This will give the character that we print in our
     * "input template" based on what we see in the board.
     * Prints a number if that space is available
     * a blank otherwise
     *
     * @param row The row of a particular space
     * @param col The column of a particular space
     * @return A char corresponding to a particular space.
     */
    char charForPosition(int row, int col){
        // This is the number that goes there if we print a number
        int candidateValue = 3*(2-row) + col + 1;
        if(board[row][col] == ' '){ // This position is available
            // "48" is the ASCII value for character '0'
            return (char)(candidateValue + 48);
        } else {
            return ' ';
        }
    }

    /**
     * This method is used when it is the human player's move
     * This method also keeps the player from making an illegal move
     * @author UD DSI, Spring 2014
     * @version 0.1
     * @param turn
     */
    void humanMove(int turn){ 
    	System.out.print("What is Player " + whoseTurn + "'s move? "); 
    	int move = scanner.nextInt(); 
    	int row = 2 - (move - 1)/3; 
    	int col = (move - 1) % 3; 
    	/* A do-while loop was used because it will loop the move while 
    	 * the player is making an invalid move. 
    	 * It will run at least once when the illegal move is made. 
    	 */
    	if (board[row][col] != ' ') { 
    		do { 
    			System.out.println("That space is already taken! Please try again."); 
    			move = scanner.nextInt(); 
    			row = 2 - (move - 1)/3; 
    			col = (move - 1) % 3;
    		} while (board[row][col] != ' '); 
    	}
    	char moveChar;
    	if(whoseTurn == 1)
    		moveChar = 'X';
    	else
    		moveChar = 'O';
    	board[row][col] = moveChar;
    	numMoves++;

    }
    /*
         if (board[row][col] != ' ') {
            System.out.println("CHEATER!!! You lose a turn :(");
            return;      
    	 }
     */



    void computerRandomMove(int turn){
        // Make a random move among available moves
        // Make the list
        ArrayList<Integer> moves =
                new ArrayList<Integer>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ' ')
                    moves.add(3*i + j);
            }
        }
        int numMovesAvailable = moves.size();
        int myMoveIndex = (int)(Math.floor(Math.random() *
                numMovesAvailable));
        int myMove = moves.get(myMoveIndex);
        int row = myMove / 3;
        int col = myMove % 3;

        char moveChar;
        if(turn == 1)
            moveChar = 'X';
        else
            moveChar = 'O';

        board[row][col] = moveChar;
        numMoves++;
    }

    
    // Smart version. It wins.
    void computerMove(int turn){
        char moveChar;
        if(turn == 1)
            moveChar = 'X';
        else
            moveChar = 'O';
        // Make a random move among available moves
        // Make the list
        int moveRow = -1;
        int moveCol = -1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ' '){
                    // make a new board, with this move
                    char[][] newBoard = new char[3][3];
                    for(int k = 0; k < 3; k++){
                        for(int l = 0; l < 3; l++){
                            newBoard[k][l] = board[k][l];
                        }
                    }
                    newBoard[i][j] = moveChar;
                    DSGameNode<Object> root = buildTree(newBoard);
                    int ww = evaluateNode(root);
                    // First check to see if we find a winning move
                    if((ww == TwoPlayer.PLAYER1WIN && turn == 1) ||
                            (ww == TwoPlayer.PLAYER2WIN && turn == 2)){
                        board[i][j] = moveChar;
                        numMoves++;
                        return;
                    }
                    // If not, see if we have a draw to make later
                    // XXX This is the only part we neglected to account for.
                    if(ww == TwoPlayer.DRAW){
                        moveRow = i;
                        moveCol = j;
                    }
                }    
            }
        }
        // If we make it to here, we didn't find a winning move
        // First let's see if we have a draw move
        if(moveRow != -1){
            board[moveRow][moveCol] = moveChar;
            numMoves++;
            return;
        }
        
        // And if there is no draw move, make a random move
        computerRandomMove(turn);
    }

    /**
     * This method overrides the abstract method in the Game class.
     * Checks to see if the game has ended. If player 1 has won it
     * returns TwoPlayer.PLAYER1WIN, if player 2 has won it returns
     * TwoPlayer.PLAYER2Win, if a draw has occured it returns TwoPlayer.DRAW,
     * and if the game is not over it returns TwoPlayer.CONTINUE.
     *
     * Overrides the abstract method in the Game class
     * @see DS1.TicTacToe#endCheck()
     * @return Returns an integer which is either -2, -1, 0, or 1.
     */
    int endCheck(){
        int winner;
        char winChar = ' ';
        // Check the three rows
        for(int i = 0; i < 3; i++)
            if(board[i][0] == board[i][1] &&
            board[i][1] == board[i][2])
                winChar = board[i][0];
        // Check the three columns
        for(int i = 0; i < 3; i++)
            if(board[0][i] == board[1][i] &&
            board[1][i] == board[2][i])
                winChar = board[0][i];
        // Check the \ diagonal
        if(board[0][0] == board[1][1] &&
                board[1][1] == board[2][2])
            winChar = board[0][0];
        // Check the / diagonal
        if(board[2][0] == board[1][1] &&
                board[1][1] == board[0][2])
            winChar = board[2][0];

        if(winChar == 'X')
            return PLAYER1WIN;
        if(winChar == 'O')
            return PLAYER2WIN;

        if(numMoves == 9)
            return DRAW;  // Draw

        return CONTINUE;        // Keep going
    }


    /*
     * (non-Javadoc)
     * @see ds1.Game#getChildren(java.lang.Object)
     */
    DSArrayList<Object> getChildren(Object b){
        // Find the available moves
        // recompute "turn" right now
        char[][] localBoard = (char[][])b;
        //System.out.println("" + localBoard[0][0] + localBoard[0][1] + localBoard[0][2]);
        //System.out.println("" + localBoard[1][0] + localBoard[1][1] + localBoard[1][2]);
        //System.out.println("" + localBoard[2][0] + localBoard[2][1] + localBoard[2][2]);
        //System.out.println();
        DSArrayList<Integer> moves =
                new DSArrayList<Integer>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(localBoard[i][j] == ' ')
                    moves.add(3*i + j);
            }
        }

        int whoseTurn = (moves.size() % 2 == 0) ? 2 : 1;

        // For each move, create a board with that new
        // move and add it to the returnArray
        DSArrayList<Object> returnArray =
                new DSArrayList<Object>(moves.size());

        for(int i = 0; i < moves.size(); i++){
            int myMove = moves.get(i);
            int row = myMove / 3;
            int col = myMove % 3;

            char moveChar;
            if(whoseTurn == 1)
                moveChar = 'X';
            else
                moveChar = 'O';

            // make a new board with the old board
            // plus our new move
            char[][] newBoard = new char[3][3];
            for(int k = 0; k < 3; k++){
                for(int j = 0; j < 3; j++){
                    newBoard[k][j] = localBoard[k][j];
                }
            }
            newBoard[row][col] = moveChar;
            //System.out.println("  " + newBoard[0][0] + newBoard[0][1] + newBoard[0][2]);
            //System.out.println("  " + newBoard[1][0] + newBoard[1][1] + newBoard[1][2]);
            //System.out.println("  " + newBoard[2][0] + newBoard[2][1] + newBoard[2][2]);
            //System.out.println();
            returnArray.add(newBoard);
        }

        return returnArray;
    }

    /**
     * evaluates a board and determines if there is a winner
     * if there is a winner then it returns the winner
     * if the number of turns takes is nine and there is no winner then it returns draw
     * if there is no winner or draw then it returns continue
     * @see localBoard     this is the temporary board created that will be evaluates so
     * that the actual game board is not changed.
     * @see winChar    this is the char of the last move of the game, and it is used to determine the winner of the game
     * @see DS1.TicTacToe#evaluateBoard(Object)
     * @see ds1.Game#evaluateBoard(java.lang.Object)
     * @return Returns and integer which is either -2, -1, 0, or 1.
     */
    int evaluateBoard(Object b){
        char[][] localBoard = (char[][])b;
        char winChar = ' ';
        // Check the three rows
        for(int i = 0; i < 3; i++)
            if(localBoard[i][0] == localBoard[i][1] &&
            localBoard[i][1] == localBoard[i][2])
                winChar = localBoard[i][0];
        // Check the three columns
        for(int i = 0; i < 3; i++)
            if(localBoard[0][i] == localBoard[1][i] &&
            localBoard[1][i] == localBoard[2][i])
                winChar = localBoard[0][i];
        // Check the \ diagonal
        if(localBoard[0][0] == localBoard[1][1] &&
                localBoard[1][1] == localBoard[2][2])
            winChar = localBoard[0][0];
        // Check the / diagonal
        if(localBoard[2][0] == localBoard[1][1] &&
                localBoard[1][1] == localBoard[0][2])
            winChar = localBoard[2][0];

        if(winChar == 'X')
            return PLAYER1WIN;
        if(winChar == 'O')
            return PLAYER2WIN;

        // Count the number of moves
        if(numMoves(localBoard) == 9)
            return DRAW;  // Draw

        return CONTINUE;        // Keep going
    }
    
    // Counts the number of moves that have
    // been made in the game board b
    int numMoves(char[][] b){
        int m = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(b[i][j] != ' ')
                    m++;
            }
        }
        return m;
    }
    
    
    // Returns the player whose turn it is
    int whoseTurn(Object b){
        char[][] localBoard = (char[][])b;
        int m = numMoves(localBoard);
        if(m%2 == 0)
            return 1;
        else
            return 2;
    }

	
	/**
	 * @see ds1.Game#boardHash(java.lang.Object)
	 */
    
	String boardHash(Object b) {
		char[][] localBoard = (char[][])b;
		String bh = "";
		for(int i = 0; i <= 3; i++){
			for (int j = 0; j <= 3; j++){
			bh = bh + localBoard[i][j] + "+";
			}
		}
		return bh;
	}

}