package ds1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Tree7 {
	static Scanner s;
	public static void main(String[] args) {
		s = new Scanner(System.in);

		while(s.hasNextInt()){ 
			doNextProb();
		}
	}
	static ArrayList<Integer> inOrd;
	static ArrayList<Integer> postOrd;
	static int[][] childArr;// [length][leaf#]
	static int c1;
	static int n1;
	static int c2;
	static int n2;
	static DSNode<Integer> root;

	private static void doNextProb() {
		c1 = 0;
		n1 = 0;
		c2 = 0;
		n2 = 0;

		inOrd = new ArrayList<Integer>();
		postOrd = new ArrayList<Integer>();
		childArr = new int[3][2];
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
		//root = null;
		root = new DSNode<Integer>(postOrd.get(postOrd.size()-1), null);
		postOrd.remove(postOrd.size()-1);
		int answer = -99;
		findLeaf(buildTheTree(inOrd,postOrd,root),0);
		answer = childArr[0][1];
		System.out.println("Answer: " +answer);
	}
	/*
	 * get root from post order
	 * find root in in order
	 * split in order to right and left subtrees
	 * recurse using new subtrees, new root
	 * if(in==null||in.size()==0||post==null||post.size()==0){
			return null;
		}
	 */

	//static DSNode<Integer> left = new DSNode<Integer>(null,root);
	//	static DSNode<Integer> right = new DSNode<Integer>(null,root);
	static int rightchild = 0;
	static int leftchild = 0;

	static DSNode<Integer> buildTheTree(
			ArrayList<Integer> in, 
			ArrayList<Integer> post, 
			DSNode<Integer> r){

		ArrayList<Integer> newRI = new ArrayList<Integer>();
		ArrayList<Integer> newRP = new ArrayList<Integer>();

		ArrayList<Integer> newLI = new ArrayList<Integer>();
		ArrayList<Integer> newLP = new ArrayList<Integer>();

		int rootIdx = in.indexOf(r.returnThing());

		DSNode<Integer> right;
		DSNode<Integer> left;

		if(rootIdx!=0){

			for(int i = 0; i < rootIdx;i++){
				newLI.add(in.get(i));
			}
			newLP = getPost(newLI, post);
			left = new DSNode<Integer>(newLP.get(newLP.size()-1),null);
			r.addChild(buildTheTree(newLI,newLP,left));
		}

		if(rootIdx!=in.size()-1){
			for(int i = rootIdx; i<in.size()-1;i++){
				newRI.add(in.get(i+1));
			}
			newRP = getPost(newRI, post);
			right = new DSNode<Integer>(newRP.get(newRP.size()-1),null);
			r.addChild(buildTheTree(newRI,newRP,right));
		}       
		return r;
	}


	/*
	 * once tree is built:
	 * find leaves
	 * move back up tree, counting value
	 * find smallest value
	 * return value of leaf of least path
	 */
	
	static void findLeaf(DSNode<Integer> t,int num){

		if(t==null){
			System.out.println("Done");
			return;
		}

		if(t.returnChildren()==null){
			childArr[0][0] = t.returnThing();
			childArr[0][1] = t.returnThing();

			System.out.println("Add leaf");
			return;
		}
		else{
			// find smaller child
			// add that to curNode
			/*
			 * 
			 * 		4
			 * 	2		7
			 * 1 3		5 6
			 * 
			 * 
			 * 	  7
			 * 		6
			 * 	  4	  3
			 * 	 2	 1	5
			 * 
			 */

			if(t.returnChildren().get(0)!=null){
				childArr[1][0] = t.returnChildren().get(0).returnThing()+c1;
				childArr[1][1] = t.returnChildren().get(0).returnThing();
			}
			if(t.returnChildren().get(1)!=null){
				childArr[2][0] = t.returnChildren().get(1).returnThing()+c2;
				childArr[2][1] = t.returnChildren().get(1).returnThing();
			}

			if(childArr[1][0]<childArr[2][0]){
				c1 = childArr[1][0];
				n1 = childArr[1][1];
				System.out.println("Case1: " + c1+" "+n1);

			}else if(childArr[1][0]>childArr[2][0]){
				c2 = childArr[2][0];
				n2 = childArr[2][1];
				System.out.println("Case2: " +c2+" "+n1);

			}else if(childArr[1][0]==childArr[2][0] && childArr[1][1]<childArr[2][1]){
				c1 = childArr[1][0];
				n1 = childArr[1][1];
				System.out.println("Case3: " +c1+" "+n1);

			}else if(childArr[1][0]==childArr[2][0] && childArr[1][1]>childArr[2][1]){
				c2 = childArr[2][0];
				n2 = childArr[2][1];
				System.out.println("Case4: " +c2+" "+n2);

			}

			if(c1<childArr[0][0]){
				childArr[0][0] = c1;
				childArr[0][1] = n1;
			}else if(c2<childArr[0][0]){
				childArr[0][0] = c2;
				childArr[0][1] = n2;
			}else if(c1==childArr[0][0]){
				if(n1<childArr[0][1]){
					childArr[0][0] = c1;
					childArr[0][1] = n1;
				}
			}else if(c2==childArr[0][0]){
				if(n2<childArr[0][1]){
					childArr[0][0] = c2;
					childArr[0][1] = n2;
				}
			}else if(c1==c2&&c1<childArr[0][0]){
				if(n2<childArr[0][1]){
					childArr[0][0] = c2;
					childArr[0][1] = n2;
				}else if(n1<childArr[0][1]){
					childArr[0][0] = c1;
					childArr[0][1] = n1;
				}
			}else if(c1==c2&&c1==childArr[0][0]){
				if(n2<childArr[0][1]){
					childArr[0][0] = c2;
					childArr[0][1] = n2;
				}else if(n1<childArr[0][1]){
					childArr[0][0] = c1;
					childArr[0][1] = n1;
				}else if(n1<n2){
					childArr[0][0] = c1;
					childArr[0][1] = n1;
				}else if(n2<n1){
					childArr[0][0] = c2;
					childArr[0][1] = n2;
				}
			}else{
				childArr[0][0] = childArr[0][0];
				childArr[0][1] = childArr[0][1];
			}

			System.out.println();
			System.out.println(childArr[0][0]+" "+childArr[0][1]);
			//System.out.println();

			findLeaf(t.returnChildren().get(0),c1);
			findLeaf(t.returnChildren().get(1),c2);

			return;
		}


		//winnerLeaf = childArr[0][1];

	}

	static DSArrayList<DSNode<Integer>> children = new DSArrayList<DSNode<Integer>>();
	public static BigInteger leavesInTree2(DSNode<Integer> t){
		children = t.returnChildren();
		if(children.size() == 0){
			return BigInteger.ONE;
		}else{

			BigInteger retVal = BigInteger.ZERO; // return value
			for(int i = 0; i < children.size(); i++){
				retVal = retVal.add(leavesInTree2(children.get(i)));
			}
			return retVal;
		}
	}

	static ArrayList<Integer> getPost(ArrayList<Integer> tempin, ArrayList<Integer> post) {
		HashMap<Integer,Integer> vals = new HashMap<Integer, Integer>();
		int[] indices = new int[tempin.size()];
		for(int i =0; i < tempin.size(); i++){
			int key = post.indexOf(tempin.get(i));
			vals.put(key, tempin.get(i));
			indices[i] = key;
		}
		ArrayList<Integer> rvals = new ArrayList<Integer>();
		Arrays.sort(indices);
		for(int i = 0; i < vals.size(); i++){
			rvals.add(vals.get(indices[i]));
		}
		return rvals;
	}

	static int findInOtherA(ArrayList<Integer> in, ArrayList<Integer> post, int idx){
		int inIdx = 0;
		int num = 0;
		num = post.get(idx);
		inIdx = in.lastIndexOf(num);
		return inIdx;
	}
}