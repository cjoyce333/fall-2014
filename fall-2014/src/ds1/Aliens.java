package ds1;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Aliens {
    static Scanner scanner;
    static HashMap<String, Integer> m = new HashMap<String, Integer>();
    static char[] chars;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        while(doNextProblem());
    }

    
    static boolean doNextProblem(){
        int mTimes = scanner.nextInt(); // # times longest string must appear
        if (mTimes == 0)  // End of problem input
            return false;

        String sThing = scanner.next();  // read the string
        chars = sThing.toCharArray(); // Convert to an array of char
		return false;

        // Solve the problem here...
   }
}