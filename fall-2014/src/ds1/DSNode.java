package ds1;

import java.math.BigInteger;
import java.util.HashMap;

public class DSNode<E>{
	private DSNode<E> parent;
	private DSArrayList<DSNode<E>> children;
	private E thing;

	// Constructor
	// whenever you construct, make sure that you set up the fields the way you want
	// must use the three fields given above
	public DSNode(E theThing, DSNode<E> p){
		parent = p;
		children = new DSArrayList<DSNode<E>>(9);
		thing = theThing;
	}    

	/**
	 * Adds a child node "n" to this node
	 * 
	 * @param n
	 */
	public void addChild(DSNode<E> n){
		children.add(n);
	}

	/**
	 * This method creates a DSNode holding theThing,
	 * makes this new DSNode a child of DSNode on
	 * which the method was called, and then
	 * returns the newly created child DSNode
	 * @param <E> theThing held in the child DSNode
	 * @return    the new child node
	 */
	public DSNode<E> addChild(E theThing){
		DSNode<E> newNode = new DSNode<E>(theThing, this);
		children.add(newNode);
		return newNode;
	}

	public DSArrayList<DSNode<E>> returnChildren(){
		return children;
	}
	//instance method already uses DSNode
	public int countChildren(){
		return children.size();
	}

	/* 
	 * In creating this method, my goal was to create temporary list of children, childList,
	 * then add that to overall treeList. 
	 * First, the function will add "thing" which is the 
	 * starting node contents, usually the root. It will then enter the first for loop, create 
	 * childList, get child at the j index, and then recurse, by running the function linearize.
	 * This continues until it reaches a node with no children, when it will move to the second for loop,
	 * and add the childList to treeList.
	 */	
	/**
	 * @return - a DSArrayList of all the nodes of a tree in the order 
	 */
	public DSArrayList<E> linearize(){
		DSArrayList<E> treeList = new DSArrayList<E>();

		treeList.add(thing);
		for (int j = 0; j < children.size(); j++){
			DSArrayList<E> childList = children.get(j).linearize();
			for(int i = 0; i < childList.size(); i++){
				treeList.add(childList.get(i));
			}
		}
		return treeList;
	}

	/**
	 * @see #sizeOfTree()
	 * @see #leavesInTree()
	 * If used by multiple methods, must be cleared in between uses
	 */
	public static HashMap<DSNode<Object>, BigInteger> sizeHash = new HashMap<DSNode<Object>, BigInteger>(); // now this one instance shared by all.
	
	/**
	 * @return
	 */
	public BigInteger sizeOfTree(){
		// If I have already been computed, just look up and return my size
		if(sizeHash.containsKey(this))
			return sizeHash.get(this);

		if(children.size() == 0){
			return BigInteger.ONE;
		}

		BigInteger retVal = BigInteger.ONE; // return value. Starts at 1 for myself
		for(int i = 0; i < children.size(); i++){
			retVal = retVal.add(children.get(i).sizeOfTree());
		}
		sizeHash.put((DSNode<Object>) this, retVal); // save this answer so we don't re-compute
		return retVal;
	}
	// returns the number of nodes in this node tree, counting itself and its descendants

	/**
	 * @return a BigInteger of the number of leaves in the tree, or ways to play the game, stored as retVal
	 * @see #sizeHash
	 * @see #sizeOfTree()
	 * @see Nim 
	 */
	public BigInteger leavesInTree(){
		// If already computed, look up and return size
		if(sizeHash.containsKey(this))
			return sizeHash.get(this);

		if(children.size() == 0){
			return BigInteger.ONE;
		}

		BigInteger retVal = BigInteger.ZERO; // return value
		for(int i = 0; i < children.size(); i++){
			retVal = retVal.add(children.get(i).leavesInTree());
		}
		sizeHash.put((DSNode<Object>) this, retVal); // save this answer to find later
		return retVal;
	}

	public DSNode<E> returnParent(){
		return parent;
	}

	public E returnThing(){
		return thing;
	}

	public void setThing(E newThing){
		this.thing = newThing;
	}
}



