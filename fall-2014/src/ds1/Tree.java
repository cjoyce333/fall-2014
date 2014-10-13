package ds1;

import java.util.ArrayList;
import java.util.Scanner;

public class Tree {
	static Scanner s;
	public static void main(String[] args) {
		s = new Scanner(System.in);

		while(s.hasNextInt()){
			doNextProb();
		}

	}
	static ArrayList<Integer> inOrd = new ArrayList<Integer>();
	static ArrayList<Integer> postOrd = new ArrayList<Integer>();

	static DSNode<Integer> root;

	private static void doNextProb() {
		String line1 = "";
		String line2 = "";

		String[] line10;
		String[] line20;

		line1 = s.nextLine();
		line2 = s.nextLine();

		line10 = line1.split("\\s");
		line20 = line2.split("\\s");

		for(int i = 0; i < line10.length; i++){
			int n = Integer.parseInt(line10[i]);
			inOrd.add(n);

		}
		for(int i = 0; i < line20.length; i++){
			int n = Integer.parseInt(line20[i]);
			postOrd.add(n);
		}
		//System.out.println(inOrd);
		//System.out.println(postOrd);
		//System.out.println(postOrd.size());

		root = new DSNode<Integer>(postOrd.get(postOrd.size()-1), null);
		buildTheTree(root);

	}
	/*
	 * get root from post order
	 * find root in in order
	 * split in order to right and left subtrees
	 * recurse using new subtrees, new root
	 */

	static DSNode<Integer> buildTheTree(DSNode<Integer> r){
		DSNode<Integer> newRoot = r; 
		int rootIdx = 0;
		rootIdx = findInOtherA(inOrd, postOrd, r);

		System.out.println(iidx);
		System.out.println("Building!");
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();


		for(int i = 0; i<rootIdx; i++){
			left.add(inOrd.get(i));
		}
		
		for(int i = rootIdx; i<inOrd.size()-1; i++){
			right.add(inOrd.get(i));
		}

		if(postOrd.size()==1){
			r=r.addChild(postOrd.get(0));
		}else{
			System.out.println("Left");
			for(int i = 0; i<left.size(); i++){
				//System.out.println(inOrd);
				//System.out.println(postOrd);
				//System.out.println(r.returnThing());

				int x = postOrd.get(rootIdx);
				System.out.println(inOrd);
				System.out.println(postOrd);
				System.out.println(r.returnThing());

				postOrd.remove(postOrd.get(iidx));
				System.out.println(inOrd);
				System.out.println(postOrd);
				System.out.println(r.returnThing());

				//DSNode<Integer> y = new DSNode<Integer>(x,r); 
				r=r.addChild(x);
				System.out.println(inOrd);
				System.out.println(postOrd);
				System.out.println(r.returnThing());



				rootIdx = findInOtherA(inOrd,postOrd,r);
				System.out.println(inOrd);
				System.out.println(postOrd);
				System.out.println(r.returnThing());

				inOrd.remove(iidx-1);

				buildTheTree(r);

			}

			//rootIdx = findInOtherA(inOrd,postOrd,r);
			System.out.println(iidx);
			System.out.println("Right");

			for(int i = rootIdx; i<postOrd.size(); i++){
				//right.add(postOrd.get(i));
				int x = postOrd.get(postOrd.size()-1);
				//DSNode<Integer> y = new DSNode<Integer>(x,r); 
				r=r.addChild(x);
				postOrd.remove(postOrd.size()-1);
				rootIdx = findInOtherA(inOrd,postOrd,r);

				buildTheTree(r);
			}
			inOrd.remove(iidx);

		}
		/*
		DSNode<Integer> n = new DSNode<Integer>(left.get(left.size()-1),root);
		newRoot = n;
		root.addChild(left.get(left.size()-1));
		buildTheDamnTree(newRoot);



		newRoot.addChild(right.get(right.size()-1));
		newRoot = (DSNode<Integer>) newRoot.returnChildren().get(newRoot.countChildren());
		 */
		return newRoot;


		//System.out.println(right);
		//System.out.println(left);

	}

	/*
	 * once tree is built:
	 * find leaves
	 * move back up tree, counting value
	 * find smallest value
	 * return value of leaf of least path
	 */
	/*
	static int findLeaf(DSNode<Integer> t){
		t.returnChildren();
		DSArrayList<Integer> L = new DSArrayList<Integer>(); 
		if(t.countChildren()==0){

		}
	}
	 */

	static int iidx = 0;
	static int findInOtherA(ArrayList<Integer> in, ArrayList<Integer> post, DSNode<Integer> newRoot){
		int inIdx = 0;
		int num = 0;
		num = newRoot.returnThing();
		inIdx = in.lastIndexOf(num);
		//inOrd.remove(inIdx);
		iidx = inIdx;
		return inIdx;
	}

}
