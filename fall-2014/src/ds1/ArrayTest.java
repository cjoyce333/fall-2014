package ds1;

public class ArrayTest{

	public static void main(String[] args){
		DSArrayList<Integer> test =
				new DSArrayList<Integer>(9);

		for (int i = 0; i < 45; i++){
			test.add(i);
			for(int j = 0; j < test.size(); j++)
				System.out.print(test.get(j) + ", ");
			System.out.println("");
		}
	}
}