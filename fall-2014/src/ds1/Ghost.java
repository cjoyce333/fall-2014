/**
 * 
 */
package ds1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import ds1.Game.DSGameNode;

/**
 * @author Claire
 *
 *XXX add all contained substrings beginning with letters to hashmap
 */
public class Ghost extends TwoPlayer {
	String word;
	Scanner scanner;
	DSGameNode<Object> root;
	private static final String filePath = "C:/Users/James/My Documents/Claire/Workspace/DS1/src/ds1/ghostwords.txt";
	String existingWord;
	ArrayList<String> wordList = new ArrayList<String>(); 
	
	/**
	 * 
	 */
	public Ghost() {
		word = "";
		scanner = new Scanner(System.in);
		try{
			FileReader f = new FileReader(filePath);
			BufferedReader input = new BufferedReader(f);
			String line = null;
			for(int i = 0; (line = input.readLine()) != null; i++) {
				line = input.readLine();
				wordList.add(line);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		//System.out.println(existingWord);
		//root = buildTree(word);
		play();

	}	

	/**
	 * @see ds1.Game#humanMove(int)
	 */
	void humanMove(int turn) {
		String letter;

		//	while(word.length() < 3 || !(word.contentEquals(existingWord))) {
		System.out.println("Pick a letter, Player " + whoseTurn);
		letter = scanner.nextLine().toLowerCase();
		if(word == ""){
			word = letter;
		}else{
			word = word.concat(letter);
			//	}
		}
		System.out.println(word);
	}

	/**
	 * @see ds1.Game#computerMove(int)
	 */
	@Override
	void computerMove(int turn) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see ds1.Game#drawBoard()
	 */
	void drawBoard() {
		System.out.println("Build from: " + word);
if(endCheck() != 0){
	System.out.println("Player " + whoseTurn + " Spelled a Word!");
}
	}

	/**
	 * @see ds1.Game#endCheck()
	 */
	int endCheck() {
		if(word.length() == 0 || word.length() <= 2){
			return TwoPlayer.CONTINUE;
		}else{
			if(wordList.contains(word)){
				if (word.length()%2 == 0){
					return TwoPlayer.PLAYER1WIN;
				}
				else{ 
					return TwoPlayer.PLAYER2WIN;
				}
			}else{
				return TwoPlayer.CONTINUE;
			}
		}
	}


	/**
	 * @see ds1.Game#whoseTurn(java.lang.Object)
	 */
	@Override
	int whoseTurn(Object b) {
		String localBoard = (String)b;
		int m = localBoard.length();
		if(m%2 == 0)
			return 1;
		else
			return 2;
	}

	/**
	 * @see ds1.Game#getChildren(java.lang.Object)
	 */
	@Override
	DSArrayList<Object> getChildren(Object b) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see ds1.Game#boardHash(java.lang.Object)
	 */
	@Override
	String boardHash(Object b) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see ds1.Game#evaluateBoard(java.lang.Object)
	 */
	@Override
	int evaluateBoard(Object b) {
		// TODO Auto-generated method stub
		return 0;
	}

}
