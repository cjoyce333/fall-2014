package ds1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Demo{

	public static void main(String args[]){
		// TicTacToe ttt = new TicTacToe();
		//	ttt.play();
		//	Nim n = new Nim(2, 3);

		//Ghost g = new Ghost();

		//*/
		DSQueue<String, Integer> q = new DSQueue<String, Integer>();
		q.print();
		q.push("a", 0);
		q.print();
		q.push("b", 0);
		//*/

		//*
		DSGraph<String> G = new DSGraph<String>();
		try{
			FileReader f = new FileReader("/C:/Users/James/Documents/Claire/DS1/src/ds1/words5.txt");
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			while ((line = reader.readLine()) != null) {
				G.addVertex(line);
				DSArrayList<String> v = G.getVertices();
				for(int i = 0; i < v.size(); i++){
					String otherString = v.get(i);
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s\n", x);
		}
		
		G.print();

		//*/
	}
	/*

	DSNode<Integer> root = new DSNode<Integer>(1, null);

	DSNode<Integer> b;
	b = root.addChild(2);
		DSNode<Integer> e;
		e = b.addChild(3);
			DSNode<Integer> f;
			f = e.addChild(4);
				DSNode<Integer> g;
				g = f.addChild(5);


	DSNode<Integer> c;
	c = root.addChild(6);
		DSNode<Integer> h;
		h = c.addChild(7);
			DSNode<Integer> i;
			i = h.addChild(8);
			DSNode<Integer> j;
			j = h.addChild(9);
				DSNode<Integer> k;
				k = j.addChild(10);
					DSNode<Integer> l;
					l = k.addChild(11);
					DSNode<Integer> m;
					m = k.addChild(12);


	DSNode<Integer> d;
	d = root.addChild(13);
		DSNode<Integer> n;
		n = d.addChild(14);
		DSNode<Integer> o;
		o = d.addChild(15);
		DSNode<Integer> p;
		p = d.addChild(16);
			DSNode<Integer> q;
			q = p.addChild(17);
			DSNode<Integer> r;
			r = p.addChild(18);
			DSNode<Integer> s;
			s = p.addChild(19);
			DSNode<Integer> y;
			y = p.addChild(20);
	 */

	//System.out.println("A has a tree of size " + root.sizeOfTree());
	//System.out.println (root.linearize());	


}