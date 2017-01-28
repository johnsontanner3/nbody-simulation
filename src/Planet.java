
public class Planet {
	// Planet has six fields
	double myXPos;
	double myYPos;
	double myXVel;
	double myYVel;
	double myMass;
	String myFileName;
	
	// now we call two constructors
	public Planet(double xP, double yP, double xV,
			double yV, double m, String img) {
		myXPos = xP;
		myYPos = yP;
		myXVel = xV;
		myYVel = yV;
		myMass = m;
		myFileName = img;
	}
	
	public Planet(Planet p) {
		// copy instances from p 
		myXPos = p.myXPos;
		myYPos = p.myYPos;
		myXVel = p.myXVel;
		myYVel = p.myYVel;
		myMass = p.myMass;
		myFileName = p.myFileName;
	}
	
	public double calcForceExertedByX(Planet planny) {
		// need F1 
		// do we need to instantiate planny's attributes?
		// need to start with computing r 
		// r = Math.sqrt( dx^2 + dy^2 ) 
		// F = G m1 m2 / r*r
		// we want to return F1x in this method 
		// F1x = F1 * dx / r
		double dx = planny.myXPos - myXPos;
		double dy = planny.myYPos - myYPos;
		double rad = Math.sqrt((dx*dx) + (dy*dy));
		double forceVec = calcForceExertedBy(planny);
		double f1x = forceVec * dx / rad;
		return f1x;	
		
	}
	
	public double calcForceExertedByY(Planet planny2) {
		double dx = planny2.myXPos - myXPos;
		double dy = planny2.myYPos - myYPos;
		double rad = Math.sqrt((dx*dx) + (dy*dy));
		double forceVec = calcForceExertedBy(planny2);
		double f1y = forceVec * dy / rad;
		return f1y;
	}
	
	public double calcDistance(Planet p2) {
		// calculates distance between two Planet objects 
		double dx = p2.myXPos - myYPos;
		double dy = p2.myYPos - myYPos;
		double rad = Math.sqrt((dx*dx) + (dy*dy));
		return rad;
	}
	
	public double calcForceExertedBy(Planet p3) {
		double dx = p3.myXPos - myXPos;
		double dy = p3.myYPos - myYPos;
		double rad = Math.sqrt((dx*dx) + (dy*dy));
		double gConst = 6.67e-11;
		double forceVector = gConst * p3.myMass * myMass / (rad*rad);
		return forceVector;
	}
	
	public double calcNetForceExertedByX(Planet[] planets) {
		// @param is array of Planet objects 
		// do not add in force if planet is equal to itself 
		// pp.equals(pp)... if ! p.equals(this) 
		double sum = 0.0;
		for(Planet p : planets) {
			if(!p.equals(this)) {
				sum += calcForceExertedByX(p);
			}
		}
		return sum;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double sum = 0.0;
		for(Planet p : planets) {
			if(!p.equals(this)) {
				sum += calcForceExertedByY(p);
			}
		}
		return sum;
	}
	
	public void update(double seconds, double xforce, double yforce) {
		// for a given amount of time and forces in x and y, updates
		// the new position (x, y) and velocity (Vx, Vy) of the given Planet
		// first compute acceleration. Can do this with xforce and yforce.
		// separate a into components ax and ay
		double aX = xforce / this.myMass;	// uses the Planet's mass
		double aY = yforce / this.myMass;
		// now compute the components of velocity = v0 + at
		double vX = this.myXVel + (aX * seconds);
		double vY = this.myYVel + (aY * seconds);
		// now time to compute new position using old and new velocity ^
		// formula: newXPos = oldXPos + v*t
		double newXPos = this.myXPos + (vX * seconds);
		double newYPos = this.myYPos + (vY * seconds);
		this.myXPos = newXPos;
		this.myYPos = newYPos;
		this.myXVel = vX;
		this.myYVel = vY;
	}
	
	public void draw() {
		String extendedFile = "images/" + this.myFileName; // need to locate image directory. not included on input file
		StdDraw.picture(this.myXPos, this.myYPos, extendedFile);
	}
}
