package compGeo;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Cookies {
	public static Scanner scanner = new Scanner(System.in);
	static double diameter = 5;
	static double radius = diameter/2;
	public static void main(String[] args) {

		int N = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		for(int i = 0; i < N; i++){
			solveOneProblem();
		}
	}
	static int numPoints;
	static int winner=0;
	public static void solveOneProblem(){
		Point2D.Double points[] = new Point2D.Double[200];
		numPoints = 0;
		// read a line
		String s = scanner.nextLine();
		while(s.length() > 0){
			String xy[] = s.split(" ");
			double x = Double.valueOf(xy[0]);
			double y = Double.valueOf(xy[1]);
			points[numPoints] = new Point2D.Double(x, y);
			numPoints += 1;
			s = scanner.nextLine();
		}
	
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for(int i = 0; i<numPoints;i++){
			for(int j = 0; j<numPoints; j++){
				double dist = points[i].distance(points[j]);
				if(dist<=diameter&&dist>0){
					nums.add(checkChips(points[i],points[j],dist,points));
				}
			}
		}
		System.out.println(nums);
		winner = Collections.max(nums);
		System.out.println(winner);
	}

	private static int checkChips(
			Point2D.Double p1, 
			Point2D.Double p2, 
			double distance, 
			Point2D.Double[] points) {
		int numChips = 2;
		for(int i = 0; i<numPoints;i++){
			Point2D.Double testPoint = points[i];
			if(testPoint!= p1&&testPoint!=p2){
				if(isContained(testPoint,p1,p2,distance)){
					numChips++;
				}
			}
		}
		return numChips;
	}

	private static boolean isContained(Point2D.Double testPoint,Point2D.Double p1,Point2D.Double p2, double distance) {
		double alpha;
		double theta;

		Point2D.Double v1 = new Point2D.Double(p1.x-testPoint.x,p1.y-testPoint.y);
		Point2D.Double v2 = new Point2D.Double(p2.x-testPoint.x,p2.y-testPoint.y);
		double dotP = (v1.x*v2.x+v2.y*v1.y);
		double denom = (Math.sqrt(v1.x*v1.x+v1.y*v1.y)*Math.sqrt(v2.x*v2.x+v2.y*v2.y));

		alpha = Math.asin(distance/diameter);
		theta = Math.acos(dotP/denom);

		boolean canContain = false;
		if(onRight(testPoint,p1,p2)){
			if(theta>= (Math.PI-Math.asin(distance/diameter))){
				canContain = true;
			}
		}else if(!onRight(testPoint,p1,p1)){
			if(theta >= (Math.asin(distance/diameter))){
				canContain = true;
			}
		}

		if(canContain&&theta>=alpha){//
			return true;
		}		
		return false;
	}

	private static boolean onRight(Point2D.Double test, Point2D.Double p1, Point2D.Double p2) {
		Line2D.Double line = new Line2D.Double(p1,p2);
		int val = -99;
		val = line.relativeCCW(test);
		boolean retVal = false;
		if(p1.y>p2.y){
			if(val==1){
				retVal = true;
			}else if(val==-1){
				retVal = false;
			}else if(val==0){
				if((test.x>p1.x&&test.x<p2.x)||(test.y>p1.y&&test.y<p2.y)||
						(test.x<p1.x&&test.x>p2.x)||(test.x<p1.x&&test.x>p2.x)){
					return true;
				}else{
					return false;
				}
			}
		}else{ 
			if(val==1){
				retVal = true;
			}else if(val<=-1){
				retVal = false;
			}else if(val==0){
				if((test.x>p1.x&&test.x<p2.x)||(test.y>p1.y&&test.y<p2.y)||
						(test.x<p1.x&&test.x>p2.x)||(test.x<p1.x&&test.x>p2.x)){
					return true;
				}else{
					return false;
				}
			}
		}			
		return retVal;
	}
}

