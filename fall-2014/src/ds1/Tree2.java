package ds1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Tree2 {
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
		//root = null;
		root = new DSNode<Integer>(postOrd.get(postOrd.size()-1), null);
		int answer = findLeaf(buildTheTree(inOrd,postOrd,0,inOrd.size()-1,0,postOrd.size()-1,root));
		System.out.println(answer);
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

	static DSNode<Integer> left = new DSNode<Integer>(null,root);
	static DSNode<Integer> right = new DSNode<Integer>(null,root);
	static int rightchild = 0;
	static int leftchild = 0;
	static DSNode<Integer> buildTheTree(
			ArrayList<Integer> in, 
			ArrayList<Integer> post, 
			int s1, int e1, 
			int s2, int e2,
			DSNode<Integer> r){

				/*
		if(!left.equals(null)||!right.equals(null)){
			r.addChild(left);
			r.addChild(right);
		}
		 */

		int N = post.size()-1;
		int n = 0;
		n = findInOtherA(in,post,e2);
		int rootVal = post.get(n-1);
		//post.remove(post.lastIndexOf(r)+1);
		//in.remove(in.lastIndexOf(r)+1);


		if(s1>e1||s2>e2)
			return null;

		DSNode<Integer> newR = new DSNode<Integer>(rootVal,r);
		if(n>e1||rightchild+1==s1||s2==n){
			left.addChild(newR);
			buildTheTree(in,post,s1,n-1,0,n,newR); //s2+n-(s1+1)

			//r.addChild(newR);
			leftchild++;
			System.out.println("Left:"+leftchild);
		}else if(n<e1||leftchild+1==s1||n==e2){
			right.addChild(newR);
			
			buildTheTree(in,post,n+1,e1,s2+n-s1,e2-1,newR);//s2+n-s1,e2-1
			//r.addChild(newR);
			rightchild++;
			System.out.println("Right:"+rightchild);

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
	static int findLeaf(DSNode<Integer> t){

		if(t.returnChildren().equals(null)){
			return t.returnThing();
		}else{

			int numLeaves = leavesInTree2(t).intValue();
			int[][] leaves = new int[numLeaves][2];
			for(int j = 0;j<=numLeaves;j++){
				for(int i = 0;i<t.countChildren();i++){
					if(t.returnChildren().get(i)==null){
						leaves[j][0]=t.returnChildren().get(i).returnThing();
						leaves[j][1]+=t.returnChildren().get(i).returnThing();
					}	
				}

				findLeaf(t.returnChildren().get(j));
			}

			java.util.Arrays.sort(leaves, new java.util.Comparator<int[]>() {
				public int compare(int[] a, int[] b) {
					return Double.compare(a[1], b[1]);
				}
			});
			int bigLeaf = leaves[0][1];
			return bigLeaf;
		}
	}

	static DSArrayList<DSNode<Integer>> children = new DSArrayList<DSNode<Integer>>();
	public static BigInteger leavesInTree2(DSNode<Integer> t){
		children = t.returnChildren();
		if(children.size() == 0){
			return BigInteger.ONE;
		}

		BigInteger retVal = BigInteger.ZERO; // return value
		for(int i = 0; i < children.size(); i++){
			retVal = retVal.add(leavesInTree2(children.get(i)));
		}
		return retVal;
	}

	static int findInOtherA(ArrayList<Integer> in, ArrayList<Integer> post, int idx){
		int inIdx = 0;
		int num = 0;
		num = post.get(idx);
		inIdx = in.lastIndexOf(num);
		return inIdx;
	}
}