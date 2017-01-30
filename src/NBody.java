import java.io.File;
import java.io.FileNotFoundException;
// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class NBody {
	
	public static void main(String[] args){
		double T = 157788000.0;
		double dt = 25000.0;
		String pfile = "data/twin-binaries.txt";
		if (args.length > 2) {
			T = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			pfile = args[2];  // already as String
		}	
		Planet[] planets = readPlanets(pfile);  // initialize array of planets by calling static method
		double radius = readRadius(pfile);      // load in radius
	
		System.out.printf("%d\n", planets.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              planets[i].myXPos, planets[i].myYPos, 
		                      planets[i].myXVel, planets[i].myYVel, 
		                      planets[i].myMass, planets[i].myFileName);	
		}
		// figure out how to draw stuff
//		StdDraw.setScale(-radius, radius);
//		StdDraw.picture(0.0, 0.0, "images/starfield.jpg", radius*2, radius*2); // have to rescale to fit window
//		for (Planet p : planets) {
//			p.draw();
//		}
		StdAudio.play("audio/2001.mid");
		double time = 0.0;
		while (time <= T) {
			// empty xforces array
			double[] xForces = new double[planets.length];
			// empty yforces array
			double[] yForces = new double[planets.length];
			// store xforces and yforces in respective arrays
			for (int i=0; i<planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			// now call update on all the planets
			for (int j=0; j<planets.length; j++) {
				planets[j].update(dt, xForces[j], yForces[j]); // what is time interval?
			}
			// draw updated picture
			StdDraw.setScale(-radius, radius);
			StdDraw.picture(0.0, 0.0, "images/starfield.jpg", radius*2, radius*2); // have to rescale to fit window
			for (Planet p : planets) {
				p.draw();
			}
			
			StdDraw.show(10);
			time += dt;
		}
	}
	
	public static double readRadius(String fname) {
		// returns second number in file fname
		// need to create a new Scanner for File object 
		// call scan.nextDouble twice for our return value
		File theF = new File(fname); //?
		double radius = 0.0;
		try {
			Scanner scan = new Scanner(theF);
			double first = scan.nextDouble();
			double second = scan.nextDouble();
			radius += second;
			scan.close();
			// return second;
			// return second;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return first;
		return radius;
	}
	
	public static Planet[] readPlanets(String fname) {
		// returns an array of Planets found in file fname
		// initialize an array of size first?
		// Planet[] allPlanets = new Planet<ArrayList>();
		// Planet[] planetList = new List<Planet>();
		File file = new File(fname);
		ArrayList<Planet> planetAL = new ArrayList<Planet>(); 
		try {
			Scanner scan = new Scanner(file);
			int numPlanets = scan.nextInt();
			// Planet[] planetList = new Planet[numPlanets];
			// skip radius
			scan.next();
			int count = 0; // initialize count 
			while(count < numPlanets) {
				//String planData = scan.nextLine();
				// now we need to put this long string into a planet object
				double xP = scan.nextDouble();
				double yP = scan.nextDouble();
				double xV = scan.nextDouble();
				double yV = scan.nextDouble();
				double mass = scan.nextDouble();
				String myFileName = scan.next();
				Planet p = new Planet(xP, yP, xV, yV, mass, myFileName);
				// planetList[count] = p;
				planetAL.add(p);  // okay, now we've built an ArrayList. Convert to Array. 
				count += 1; 
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Planet[] planetList = planetAL.toArray(new Planet[planetAL.size()] );
		return planetList;
	}
	
}
