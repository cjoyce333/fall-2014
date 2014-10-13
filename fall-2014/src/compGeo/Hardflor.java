package compGeo;

import java.util.Scanner;

public class Hardflor {
	static Scanner s;
	static int numSides;

	public static void main(String[] args) {
		s = new Scanner(System.in);

		numSides = 0;

		while (true){
			numSides = s.nextInt();

			if( numSides == 0){
				break;
			}else{
				doNextProb();
			}

		}
	}

	private static void doNextProb() {

		int[][] coordinates = new int[numSides][2];
		int changeY = 0;
		int changeX = 0;
		for (int i = 0; i<numSides;i++){

			char dir = s.next(".").charAt(0);
			int length = s.nextInt();
			if(dir == 'N' || dir == 'n'){
				changeY = length;
				changeX = 0;
			}else if(dir == 'S' || dir == 's'){
				changeY = -length;
				changeX = 0;
			}else if(dir == 'W' || dir == 'w'){
				changeX = -length;
				changeY = 0;
			}else if(dir == 'E' || dir == 'e'){
				changeX = length;
				changeY = 0;
			}
			coordinates[i][0] = changeX;			
			coordinates[i][1] = changeY;
			if(i!=0){
				coordinates[i][0] += coordinates[i-1][0];
				coordinates[i][1] += coordinates[i-1][1];
			}

			System.out.println((coordinates[i][0]) + (coordinates[i][1]));


		}
		double area = area(coordinates);
		System.out.println("THE AREA IS "+ area);
	}

	// Shoelace part

	public static double area(int[][] a)
	{
		int n = a.length;

		// It literally took me 3 hours to figure out that this was the problem, so DON'T TOUCH IT
		a[n - 1][0] = 0;//a[0][0];
		a[n - 1][1] = 0;//a[0][1];

		double det = 0.0;

		for (int i = 0; i < n; i++)
			det += (double)(a[i][0] * a[(i + 1)%n][1]);

		for (int i = 0; i < n; i++)
			det -= (double)(a[i][1] * a[(i + 1)%n][0]);

		// find absolute value and divide by 2
		det = Math.abs(det);    
		det /= 2;
		return det;        
	}
}
