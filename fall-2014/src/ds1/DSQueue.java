package ds1;


/**
 * DSQueue is really acting like a "priority queue" where we always return the element with the least value
 * 
 * @author Claire
 *
 * @param <K> the items that the queue will store
 * @param <V> the values we will sort on
 */
public class DSQueue<K, V> {
	QueueNode first;
	QueueNode last;


	public DSQueue(){
		first = null;
		last = null;
	}

	/**
	 * @usage DSQueue q
	 * @usage q.push("My name")
	 * @param item to add to the queue
	 * @param value initial value
	 */
	public void push(K item, V value){
		QueueNode newNode = new QueueNode(item, value, null);
		if(last == null){ // if nothing is on the queue
			first = newNode;
			last = newNode;
		}else{
			last.next = newNode;
			last = newNode;
		}

	}

	/**
	 * Returns and removes the first item from the queue
	 * @return
	 */
	public K pop(){
		if(first.next == null){ // only one item on queue
			K rv = first.item;
			first = null;
			last = null;
			return rv;
		}else{ // multiple items on queue
			K rv = first.item;
			first = first.next;
			return rv;
		}
	}


	public void sort(){

	}

	/**
	 * @param item the item to update
	 * @param value the new value to give it, if it's less
	 * @return true if the item's value was updated
	 */
	public boolean update(K item, V value){
		return true;

	}

	/**
	 * @return true if queue is empty
	 */
	public boolean isEmpty(){
		return (first == null);
	}

	public void print(){
		QueueNode n = first;
		if (n == null){ System.out.println("Queue is empty.");
		return;
		}

		do{
			System.out.println(n.item + ", ");
			n = n.next;
		} while(n != null);
		System.out.println("");
	}

	/**
	 * Inner class to hold the items and references to next
	 * 
	 * @author Claire
	 *
	 */
	public class QueueNode{
		K item;
		V value;
		QueueNode next;

		public QueueNode(K i, V v, QueueNode qn){
			item = i;
			value = v;
			next = qn;
		}
	}

}
