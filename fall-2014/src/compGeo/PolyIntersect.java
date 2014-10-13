package compGeo;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PolyIntersect {
	static Scanner s;
	public static void main(String[] args) {

		s = new Scanner(System.in);

		// 0 indicates end of input

		while (true){
			int firstInt = s.nextInt();
			if( firstInt < 3){
				break;
			}else{
				numVertices = firstInt;
				doNextProb();
			}

		}
	}

	// first input number is the number of vertices
	static int numVertices;
	static int numVertices2;
	static int numIntersections;

	static List<Double> xIntersections = new ArrayList<Double>();
	static List<Double> yIntersections = new ArrayList<Double>();
	static List<Point2D> intersectionPoints = new ArrayList<Point2D>();
	static List<Double> newX0 = new ArrayList<Double>();
	static List<Double> newY0 = new ArrayList<Double>();
	static void doNextProb(){
		numIntersections = 0;
		Polygon polygon1 = new Polygon();
		for(int i=0; i<numVertices; i++){
			int x = s.nextInt();
			int y = s.nextInt();
			polygon1.addPoint(x, y);
		}
		numVertices2 = s.nextInt();
		Polygon polygon2 = new Polygon();
		for(int i=0; i<numVertices2; i++){
			int x = s.nextInt();
			int y = s.nextInt();
			polygon2.addPoint(x, y);
		}

		Rectangle bounds2 = polygon2.getBounds();

		
		for (int i = 0; i<numVertices; i++){
			for (int j = 0; j<numVertices2; j++){
				if((double)polygon1.xpoints[i]==(double)polygon2.xpoints[j]&&
						(double)polygon1.ypoints[i]==(double)polygon2.ypoints[j]){
					numIntersections++;
					xIntersections.add((double) polygon1.xpoints[i]);
					yIntersections.add((double) polygon1.ypoints[i]);
				}
			}
		}
		

		if(polygon1.intersects(bounds2)){
			getIntersectionPoints(polygon1, polygon2);

			for(int i = 0; i<xIntersections.size();i++){
				newX0.add(i, round(xIntersections.get(i)));
				newY0.add(i, round(yIntersections.get(i)));
			}

			checkDoubles(newX0, newY0);
			numIntersections = newX0.size();
			System.out.println(numIntersections);

		
			for (int i = 0; i< newX0.size(); i++){
				System.out.println(newX0.get(i)+ " "+newY0.get(i));
			}
			
			
		}else if(numIntersections!=0){
			System.out.println(numIntersections);
			for (int i = 0; i< xIntersections.size(); i++){
				System.out.println(xIntersections.get(i)+" "+ 
								yIntersections.get(i));
			}
			 
		}else{
			System.out.println(0);
		}


	}

	static void checkDoubles(List<Double> x, List<Double> y){

		for(int i = 0; i<x.size(); i++){
			for(int j = i+1; j<x.size();j++){

				if(x.get(i)==x.get(j) && y.get(i)==y.get(j)){
					x.remove(j);
					y.remove(j);

				}
			}
		}
	}

	static double round(double n){
		double val = 0;
		val = n*100.00;
		val = Math.round(val);
		val/=100;
		return val;
	}

	/*
	 * get polygons
	 * pick line from polygon 1
	 * compare to all lines from polygon 2
	 * repeat for all lines of polygon 1
	 */

	private static void getIntersectionPoints(Polygon polygon1,
			Polygon polygon2) {
		int coordinate1 = 0;
		int coordinate2 = 1;
		int coordinate11 = 0;
		int coordinate22 = 1;
		polygon1.addPoint(polygon1.xpoints[0], polygon1.ypoints[0]);
		polygon2.addPoint(polygon2.xpoints[0], polygon2.ypoints[0]);


		for (int i = 0; i<numVertices; i++){
			for (int j = 0; j<numVertices2; j++){
				if(Line2D.linesIntersect(
						(double)polygon1.xpoints[coordinate1], (double)polygon1.ypoints[coordinate1], 
						(double)polygon1.xpoints[coordinate2]-1, (double)polygon1.ypoints[coordinate2]-1,

						(double)polygon2.xpoints[coordinate11], (double)polygon2.ypoints[coordinate11], 
						(double)polygon2.xpoints[coordinate22]-1, (double)polygon2.ypoints[coordinate22]-1) == true
						){

					solveSystem(
							(double)polygon1.xpoints[coordinate1], (double)polygon1.ypoints[coordinate1], 
							(double)polygon1.xpoints[coordinate2], (double)polygon1.ypoints[coordinate2],

							(double)polygon2.xpoints[coordinate11], (double)polygon2.ypoints[coordinate11], 
							(double)polygon2.xpoints[coordinate22], (double)polygon2.ypoints[coordinate22]);

					numIntersections++;
					coordinate11++;
					coordinate22++;


				}else{
					coordinate11++;
					coordinate22++;
				}
			}
			coordinate1++;
			coordinate2++;
			coordinate11=0;
			coordinate22=1;
		}

	}
	private static void solveSystem(
			double xpoints, double ypoints, 
			double xpoints2, double ypoints2, 
			double xpoints3, double ypoints3,
			double xpoints4, double ypoints4) {
		double m1,b1,m2,b2;
		double x0, y0;

		if(xpoints==xpoints2){
			m2 = (ypoints4-ypoints3)/(xpoints4-xpoints3);
			b2 = ypoints3 - m2*xpoints3;
			x0 = xpoints;
			y0 = m2*x0 +b2;
		}else if(xpoints3==xpoints4){
			m1 = (ypoints2-ypoints)/(xpoints2-xpoints);
			b1 = ypoints - m1*xpoints;
			x0 = xpoints3;
			y0 = m1*x0 +b1;
		}else{
			m1 = (ypoints2-ypoints)/(xpoints2-xpoints);
			b1 = ypoints - m1*xpoints;
			m2 = (ypoints4-ypoints3)/(xpoints4-xpoints3);
			b2 = ypoints3 - m2*xpoints3;
			x0 = -(b1-b2)/(m1-m2);
			y0 = m1*x0 + b1;
		}
		xIntersections.add(x0);
		yIntersections.add(y0);
		//intersectionPoints.add(new Point2D.Double(x0, y0));
	}

}

