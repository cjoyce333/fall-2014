package compGeo;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Scanner;
/*
 * draw bounding box
 * pick point one greater than bounding box in some direction
 * connect to point
 * find intersection
 * recurse, counting intersections
 * decide even/odd (mod)
 * 
 */

public class Polygon634 {
	static Scanner s;

	public static void main(String[] args) {
		s = new Scanner(System.in);

		// 0 indicates end of input

		while (true){
			int firstInt = s.nextInt();
			if( firstInt == 0){
				break;
			}else{
				numVertices = firstInt;
				doNextProb();
			}

		}
	}

	// first input number is the number of vertices
	static int numVertices;

	static int getIntersections(Line2D.Double l, Polygon p){
		int trueVal= 0;
		int coordinate1 = 0;
		int coordinate2 = 1;
		p.addPoint(p.xpoints[0], p.ypoints[0]);
		for (int i = 0; i<numVertices; i++){
			if(l.intersectsLine(p.xpoints[coordinate1], p.ypoints[coordinate1], p.xpoints[coordinate2], p.ypoints[coordinate2])){
				trueVal++;
				coordinate1++;
				coordinate2++;
			}else{
				coordinate1++;
				coordinate2++;
			}

		}
		return trueVal;
	}

	static boolean insideOutside(int v){
		boolean tf = false;
		if (v%2==0){
			tf = false;
		}else{
			tf = true;
		}
		return tf;
	}


	/* this method should
	 *  1. get the number of vertices of this shape
	 *  2. create polygon
	 *  3. create bounding box
	 *  4. draw line, find intersections
	 *  5. determine if inside/outside polygon
	 */
	static void doNextProb(){

		Polygon polygon = new Polygon();
		for(int i=0; i<numVertices; i++){
			int x = s.nextInt();
			int y = s.nextInt();
			polygon.addPoint(x, y);
		}


		Rectangle boundRec = polygon.getBounds();

		//int leftX = boundRec.x+1;
		//int topY = boundRec.y+1;
		//int bottomY = boundRec.y + boundRec.height+1;
		int rightX = boundRec.x + boundRec.width+1;


		int px = s.nextInt();
		int py = s.nextInt();

		// x is one outside of the bounding rectangle
		double testX = rightX+1;
		// y is same as point, because of given info: all edges vertical/horizontal, lengths of all edges even, point has even coordinates
		double testY = px;

		Line2D.Double testLine = new Line2D.Double((double)px, (double)py, (double)testX, (double)testY);

		if(insideOutside(getIntersections(testLine, polygon))==true){
			System.out.println("T");
		}else{
			System.out.println("F");
		}
	}
}