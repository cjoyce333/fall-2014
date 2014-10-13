package compGeo;

/*
 * Partial solution to the Rope Crisis in Ropeland problem
 * Does not solve the case where the line through the two points 
 * intersects the pillar, but the segment does not.
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.Scanner;
import java.util.StringTokenizer;


class Ropeland2 {
	//public static Scanner scanner = new Scanner(System.in);
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;


	public static void main(String[] args) throws IOException {
		//int N = scanner.nextInt();

		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++){
			solveOneProblem();
		}
	}

	static void solveOneProblem() throws IOException{
		/*double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();
        double R  = scanner.nextDouble();*/

		st = new StringTokenizer(br.readLine());
		double x1 = Double.parseDouble(st.nextToken());
		double y1 = Double.parseDouble(st.nextToken());
		double x2 = Double.parseDouble(st.nextToken());
		double y2 = Double.parseDouble(st.nextToken());
		double R  = Double.parseDouble(st.nextToken());

		double m = (y2-y1)/(x2-x1);
		double a = m*m+1;
		double c = y1-m*x1;
		double cp = c*c-R*R;
		double b = 2*cp*m;

		if(m==0)
			if(((b*b)-(4*a*cp))/(2*a)<0){
				System.out.printf("%.3f\n",Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
				return;
			}else{
				double x01 = -b+Math.sqrt(b*b-4*a*cp)/2*a;
				double x02 = -b-Math.sqrt(b*b-4*a*cp)/2*a;
				double y01 = m*x01-m*x1+y1;
				double y02 = m*x02-m*x1+y1;
				double acdist = Math.sqrt((x1-x01)*(x1-x01) + (y1-y01)*(y1-y01));
				double abdist = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
				double bcdist = Math.sqrt((x2-x01)*(x2-x01) + (y2-y01)*(y2-y01));

				double addist = Math.sqrt((x1-x02)*(x1-x02) + (y1-y02)*(y1-y02));
				double bddist = Math.sqrt((x2-x02)*(x2-x02) + (y2-y02)*(y2-y02));
				if(acdist+bcdist!=abdist&&addist+bddist!=abdist){
					System.out.printf("%.3f\n",Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
					return;
				}
			}
		
		if(x1<0&&y1<0&&x2<0&&y2<0&&Math.abs(x1)>R&&Math.abs(y1)>R&&Math.abs(x2)>R&&Math.abs(y2)>R){
			System.out.printf("%.3f\n",Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
			return;
		}
		
		double area = Math.abs(x1*y2 - x2*y1) / 2;
		double base = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		double height = 2 * area / base;

		if(height >= R){
			System.out.printf("%.3f\n", base);
			return;
		}

		double d1 = Math.sqrt(x1*x1 + y1*y1);
		double d2 = Math.sqrt(x2*x2 + y2*y2);

		double s1 = Math.sqrt(d1*d1 - R*R);
		double s2 = Math.sqrt(d2*d2 - R*R);

		double bigAngle = Math.acos((x1*x2 + y1*y2) / (d1*d2));
		double p1 = Math.acos(R/d1);
		double p2 = Math.acos(R/d2);
		double angle = bigAngle - p1 - p2;
		double arc = R * angle;

		System.out.printf("%.3f\n", s1 + arc + s2);
	}
}
