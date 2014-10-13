package ds1;

public class DSHashMap {
	static int arraySize;
	static DSArrayList<DSArrayList<Pair>> theArray;
	static int maxChainSize;

	public DSHashMap(){
		arraySize = 7001;
		theArray = new DSArrayList<DSArrayList<Pair>>(arraySize);

		maxChainSize = 0; // For Testing
	}



	private int hashFunction(String s){
		int hv = 0;
		for(int i = 0; i < s.length(); i++)
			hv = (hv * 128 + (int)(s.charAt(i))) % arraySize;

		return hv;
	}

	void put(String key, String value){
		int hashVal = hashFunction(key);
		
		if((theArray.size()/2)+1>=arraySize/2){
			rehash(theArray);
		}
		
		if(theArray.get(hashVal) == null){
			DSArrayList<Pair> DSAL = new DSArrayList<Pair>();
			DSAL.add(new Pair(key, value));
			theArray.put(hashVal, DSAL);
		} else {
			theArray.get(hashVal).add(new Pair(key, value));
		}

		// For Testing
		if(theArray.get(hashVal).size() > maxChainSize){
			maxChainSize = theArray.get(hashVal).size();
			System.out.println("We have a chain of size " + maxChainSize);
		}
	}

	void rehash(DSArrayList<DSArrayList<Pair>> a){
		int newSize = getPrime(a.size()*2);
		DSArrayList<DSArrayList<Pair>> newArray = new DSArrayList<DSArrayList<Pair>>(newSize);
		for (int i = 0; i<a.size(); i++){
			newArray.add(a.get(i));
		}
		System.out.println("The array is now: "+newArray.size());
		a=newArray;
	}

	private int getPrime(int i) {
		for(int j = i+1;true;j++){
			if(isPrime(j)){
				return j;
			}
		}
	}

	private boolean isPrime(int n) {
		for(int j=2;(j*j <= n);j++){
			if(n%j==0){
				return false;
			}
		}
		return true;
	}

	//XXX
	void delete(String key){
		int hashVal = hashFunction(key);
		DSArrayList<Pair> DSAL = theArray.get(hashVal);
		for(Pair p : DSAL){
			if(p.key.equals(key)){
				theArray.quickDelete(hashVal);
			}
		}
	}

	Object get(String key){
		int hashVal = hashFunction(key);
		DSArrayList<Pair> DSAL = theArray.get(hashVal);
		for(Pair p : DSAL){
			if(p.key.equals(key)){
				return p.value;
			}
		}
		return null;
	}


	class Pair{
		public Pair(String key, String value){
			this.key = key;
			this.value = value;
		}

		String key;
		String value;
	}
}