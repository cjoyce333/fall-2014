package ds1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Tree8 {
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
	static int min;
	static DSNode<Integer> root;

	private static void doNextProb() {
		inOrd = new ArrayList<Integer>();
		postOrd = new ArrayList<Integer>();
		childArr = new int[3][2];
		min = 10000;
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
		int answer = 0;
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
			return;
		}

		if(t.returnChildren().get(0)==null&&t.returnChildren().get(1)==null){
			if(min>t.returnThing()+num){
				min = t.returnThing()+num;
				childArr[0][0] = t.returnThing()+num;
				childArr[0][1] = t.returnThing();
			}else if(min == t.returnThing()+num){
				if(childArr[0][1]==0||childArr[0][1]>t.returnThing()){
					childArr[0][1]=t.returnThing();
				}
			}

			return;
		}

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

		findLeaf(t.returnChildren().get(0),t.returnThing()+num);
		findLeaf(t.returnChildren().get(1),t.returnThing()+num);

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