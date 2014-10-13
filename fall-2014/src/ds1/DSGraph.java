package ds1;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Claire
 *
 */
public class DSGraph<E> {

	private DSArrayList<DSGraphNode<E>> vertices;
	private HashMap<E, DSGraphNode<E>> nameMap;

	public DSGraph(){
		vertices = new DSArrayList<DSGraphNode<E>>();
		nameMap = new HashMap<E, DSGraphNode<E>>();
	}

	public void addVertex(E label){
		DSGraphNode<E> v = new DSGraphNode(label);
		vertices.add(v);
		nameMap.put(label, v);
	}

	public void connect(E label1, E label2){
		DSGraphNode<E> v1 = nameMap.get(label1);
		DSGraphNode<E> v2 = nameMap.get(label2);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}

	public DSArrayList<E> getVertices(){
		DSArrayList<E> rv = new DSArrayList<E>();
		for(int i = 0; i < vertices.size(); i++){
			E thing = vertices.get(i).thing;
			rv.add(thing);
		}
		return rv;
	}

	public void print(){

		for(int i = 0; i < vertices.size(); i++){
			DSGraphNode<E> v = vertices.get(i);
			System.out.print(v.thing + ": ");
			for(DSGraphNode<E> n : v.neighbors){
				System.out.print(n.thing + ", ");
			}
			System.out.println("");
		}
	}

	public void shortestPath(E s, E e){
		DSGraphNode<E> start = nameMap.get(s);
		DSGraphNode<E> end = nameMap.get(e);
		DSQueue<DSGraphNode<E>, Integer> q = new DSQueue<DSGraphNode<E>, Integer>();
		
		q.push(start,  0);
		while(!q.isEmpty()){
			DSGraphNode<E> v = q.pop();
		}
	}
	
	static class DSGraphNode<E>{
		E thing;
		HashSet<DSGraphNode<E>> neighbors;

		public DSGraphNode(E item){
			this.thing = item;
			neighbors = new HashSet<DSGraphNode<E>>();
		}

		public void addNeighbor(DSGraphNode<E> n){
			neighbors.add(n);
		}
	}

}
