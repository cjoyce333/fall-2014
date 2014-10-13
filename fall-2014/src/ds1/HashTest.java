package ds1;

public class HashTest {

	static DSHashMap m = new DSHashMap();
	static DSArrayList<String> l = new DSArrayList<String>();
	public static void main(String[] args) {
		l.add("a");
		l.add("b");
		l.add("c");
		l.add("d");
		l.add("e");
		l.add("f");
		l.add("g");

		for(int i = 0;i<l.size();i++){
			m.put(l.get(i), l.get(i));
		}

		for(int j = 0; j<l.size();j++){
			System.out.println(m.get(l.get(j)));
		}
		System.out.println();


		m.delete("c");

		//for(int i = 0; i<=l.size()-1;i++){
			//m.delete(l.get(i));
		/*
			for(int j = 0; j<l.size();j++){
				System.out.println((m.get(l.get(j))));
			}				
			System.out.println();
*/
		//}
	}

}
