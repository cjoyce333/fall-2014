package ds1;

//Unit 2: Code from Class - Trominos


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.math.BigInteger;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Trominos extends JFrame{
    private boolean[][][] trom = 
        {{{true, true}, {true, false}}, 
            {{true, true}, {false, true}}, 
            {{true, false}, {true, true}}, 
            {{false, true}, {true, true}}};
    private int[] offset = {0, 0, 0, 1};
    private boolean[][] board;
    private int[][] tiled;
    private static BigInteger numSolutions;
    private static int numused;
    private static int WIDTH = 9;
    private static int HEIGHT = 20;
    private static int unitSize;
    private static int voffset = 50;
    private static DSHashMap memo = new DSHashMap();

    public static void main(String args[]){
        Trominos t = new Trominos();
        t.setPreferredSize(new Dimension(unitSize*WIDTH + 20, unitSize*HEIGHT + 55 + voffset));
        t.setLocation(500,  20);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.pack();
        t.setVisible(true);
        long startTime = System.currentTimeMillis();
        t.solve(1);
        System.out.println("Time:" + (System.currentTimeMillis() - startTime));
        System.out.println("There are " + numSolutions + " solutions!");
        memo.printStats();
    }


    public Trominos() {
        unitSize = 20;
        board = new boolean[HEIGHT + 2][WIDTH + 2];
        tiled = new int[HEIGHT + 2][WIDTH + 2];
        for (int i = 0; i <= HEIGHT + 1; i++)
            for (int j = 0; j <= WIDTH + 1; j++) {
                board[i][j] = false;
                tiled[i][j] = -1;
            }

        numused = 0;
        numSolutions = new BigInteger("0");
    }

    /*
     * The solve() function assumes that a board[][] of booleans is given,
     * showing which squares are empty (false) and occupied (true). Also, the
     * numused[] array correctly knows how many copies of each polyomino have
     * been used.
     * 
     * The solve() function will find the leftmost of the topmost unoccupied
     * squares and attempt to fill it with each orientation of each polyomino,
     * without exceeding the polyomino's numAllowed value. For each orientation
     * of each available polyomino, solve() will place that polyomino,
     * recursively call solve() to fill the rest of the board, and then remove
     * the polyomino. Whenever a solution is found, solve() will celebrate
     * appropriately by drawing the solution, announcing the solution and number
     * of solutions to System.out, etc...
     */
    public void solve(int siStart) {
        int si = 0, sj = 0; // Hold leftmost of topmost open square
        boolean canFit; // can the board take the current polyomino?
        // set si and sj to be the leftmost of the topmost open squares
        boolean emptyFlag = false;
        BigInteger numSolutionsAtStart;

        //drawBoard();
        findFirstGap:
            for (int i = siStart; i <= HEIGHT; i++)
                for (int j = 1; j <= WIDTH; j++)
                    if (!board[i][j]) {
                        emptyFlag = true;
                        si = i;
                        sj = j;
                        break findFirstGap;
                    }

        // If emptyFlag is false, there are no holes left!
        if (emptyFlag == false) { // Found a solution
            drawBoard();
            numSolutions = numSolutions.add(BigInteger.ONE);
            return;
        }

        // si now holds the index of the first non-filled row
        long hashVal = si - 1;
        for(int i = si; i <= si+1; i++)
            for(int j = 1; j <= WIDTH; j++)
                hashVal = (hashVal << 1) + (board[i][j] ? 1 : 0);

        if(memo.containsKey(String.valueOf(hashVal))){
            numSolutions = 
                    numSolutions.add(new BigInteger(memo.get(String.valueOf(hashVal))));
            return;
        } else {
            numSolutionsAtStart = numSolutions;
        }


        // Otherwise, see if any tile fits in row si, column sj
        for (int rot=0; rot < 4; rot++) {
            if (offset[rot] < sj && sj + 2 - offset[rot] < WIDTH + 2
                    && si + 2 < HEIGHT + 2) {
                canFit = true;
                for (int pj = 0; pj < 2; pj++)
                    for (int pi = 0; pi < 2; pi++)
                        if (trom[rot][pi][pj]
                                && board[si + pi][sj + pj - offset[rot]])
                            canFit = false;
                if (canFit) {
                    numused++;
                    for (int pj = 0; pj < 2; pj++)
                        for (int pi = 0; pi < 2; pi++)
                            if (trom[rot][pi][pj]) {
                                board[si + pi][sj + pj - offset[rot]] = true;
                                tiled[si + pi][sj + pj - offset[rot]] = 1 + numused;

                            }

                    // One did fit! Recursively fill the rest of the board
                    solve(si);

                    // Okay. Recursion is done. Take the tile off and continue
                    for (int pj = 0; pj < 2; pj++)
                        for (int pi = 0; pi < 2; pi++)
                            if (trom[rot][pi][pj]) {
                                board[si + pi][sj + pj - offset[rot]] = false;
                                tiled[si + pi][sj + pj - offset[rot]] = -1;
                            }
                }
            }
        }

        memo.put(String.valueOf(hashVal), numSolutions.subtract(numSolutionsAtStart).toString());
    }



    private void drawBoard() {
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, unitSize*WIDTH, voffset);
        g.setColor(Color.black);
        g.drawString(numSolutions.toString(), 20,  voffset - 10);
        for (int i = 1; i <= HEIGHT; i++)
            for (int j = 1; j <= WIDTH; j++) {
                int cc = tiled[i][j];
                if (tiled[i][j] == -1)
                    g.setColor(Color.gray);
                else
                    g.setColor(new Color(cc * 13 % 256, cc * 51 % 256,
                            cc * 77 % 256));

                g.fillRect(unitSize * (j - 1) + 5, unitSize * i + voffset, unitSize,
                        unitSize);

            }
    }
}