package ds1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Hangman extends JFrame implements ActionListener {

	static int radius;
	static JTextField guessField;
	static JPanel hangmanDrawing;
	/**
	 * @param args
	 * *Note: When you put sub-componenets on a parent component, you have to set the parent components Layout Manager
	 */
	
	public static void main(String[] args){

		Hangman hangman = new Hangman();
		hangman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hangman.setBackground(Color.blue);
		hangman.setLayout(new BorderLayout());
		
		guessField = new JTextField("40");
		guessField.setBackground(Color.yellow);
		hangman.add(guessField, BorderLayout.SOUTH);
		guessField.addActionListener(hangman);
		
		JTextArea usedLettersArea = new JTextArea("Text Area");
		usedLettersArea.setBackground(Color.red);
		usedLettersArea.setPreferredSize(new Dimension(250, 250));
		hangman.add(usedLettersArea, BorderLayout.WEST);
		
		hangmanDrawing = new JPanel(){
			public void paint(Graphics gg){
			// using graphics 2D class
				Graphics2D g = (Graphics2D)gg;
				g.setColor(Color.black);
				g.drawArc(100,  70,  radius,  radius,  0,  360);
			}
		};
		hangmanDrawing.setBackground(Color.green);
		hangmanDrawing.setPreferredSize(new Dimension(250, 250));
		hangman.add(hangmanDrawing, BorderLayout.EAST);
		
		hangman.setPreferredSize(new Dimension(500, 400));
		hangman.pack();
		hangman.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		radius = Integer.valueOf(guessField.getText());	
		hangmanDrawing.repaint();
	}
}
