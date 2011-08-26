package obj;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Vector;
import java.util.Arrays;

import util.Globals;
import util.Debug;


public abstract class Organization {
	protected int index; 
	protected String methodology;
	protected Location location;
//	protected double bias; // updated by subclass (DmuAgile) during incrementKnowledge
	protected boolean scope[] = new boolean[Globals.N]; // scope before increment 
	protected boolean incrementalScope[] = new boolean[Globals.N]; // incremental scope
	protected Vector<Location> incrementedScopeNeighbors = new Vector<Location>();
	protected Vector<Location> currentScopeNeighbors = new Vector<Location>();
	protected int numConsideringNeighbors;
	protected boolean completed;
	protected boolean completedPhaseOne;
//	private boolean lastPrinted = false; // so as to not print details of organization that are completed; to get the final period (I think)
	
	protected int currentIteration;
	
	/**
	 * Constructor (Superclass)
	 * @param idx : index for organization
	 */
	public Organization(int idx) {
		index = idx;
		location = new Location();  // new empty location to start with
		completed = false;
		numConsideringNeighbors = Globals.increment; // initialize for t=-1
		currentIteration = -1; // will increment to 0 with incrementKnowledge()
//		bias = Globals.bias; // initialize bias
		// do dmu constructor stuff in the subclasses
	}
	
	// *--- end Constructor
	
	/**
	 * ACCESSORS
	 */
	
	/** protected int index;  */
	public int getIndex() { return index; }
	public void setIndex(int idx) { index = idx; }
	
	/** protected String methodology; */
	public String getMethodology() { return methodology; } 
	// setting methodology is done by subclass' Constructor
	
	/** protected Location location; */
	public Location getLocation() { return location; }
	public void setLocation(Location l) {  location.setLocation(l); }

	/** protected double bias; */
	public double getBias() { return Globals.biases[currentIteration]; }
//	public void setBias(double newBias) { bias = newBias; }
	
	/** protected boolean scope[]; */
	public int getScopeSize() { 
		int x = 0; 
		for (int i = 0; i < scope.length; i++) { if (scope[i] || incrementalScope[i]) { x++; } }
		return x;
	}
	public boolean canIncrementScope() { return getScopeSize() < Globals.N ? true : false; }
	// setting scope is done by subclass' incrementKnowledge()   
	public boolean inScope(int i) { return scope[i] || incrementalScope[i]; } 

	/** protected Vector<Location> currentScopeNeighbors; */
	/** protected Vector<Location> incrementedScopeNeighbors; */
	private String getNeighborsToString(String type) {
		String retStr = "";
		if (type.equals("current")) {
			retStr += "Current Scope Neighbors:\n";
			for (Location neighbor : currentScopeNeighbors) { retStr += neighbor.toString() + "\n"; }
		} else if (type.equals("incremental")) {
			retStr += "Incremented Scope Neighbors:\n";
			for (Location neighbor : incrementedScopeNeighbors) { retStr += neighbor.toString() + "\n"; }
		} else {} 
		return retStr; 
	}

	/** protected boolean incrementalScope[]; */
	// no need for accessors
	
	/** protected boolean completed; */
	public boolean isCompleted() { return completed; }
	public boolean isPhaseOneCompleted() { return completedPhaseOne; }

	// setting completed is done by subclass' run() 

//	protected boolean isLocalOptimum() { // called by subclass' run() 
//	}

	/** fitness */
	public double getOrgFitness() { return Globals.landscape.getPerceivedFitness(location, Globals.numInterations - 1); }
	public double getOrgPerceivedFitness() { return Globals.landscape.getPerceivedFitness(location, currentIteration); }

	// *--- end ACCESSORS

	protected void incrementKnowledge() {   // increases the scope of the local representation by Globals.increment
		currentIteration++;
		Debug.println("Organization.incrementKnowledge: incrementing knowledge to step " + currentIteration + " with bias " + Globals.biases[currentIteration]);
		// merge scope and incrementalScope before determining which elements to increment
		for (int i = 0; i < Globals.N; i++) { scope[i] = scope[i] || incrementalScope[i]; }
		// reset incrementalScope to false
		incrementalScope = new boolean[Globals.N]; // reset to all false

		// Globals.increment is the number of knowledge elements to increment
		int scopeSize = getScopeSize();
		
		if (scopeSize == Globals.N) {
			// do nothing; we cannot increment scope anymore
		} else {
			int[] scopeIndex = new int[scopeSize]; // index of currently known elements; scopeSize == 0 is not a problem
			int s = 0;
			for (int i = 0; i < Globals.N; i++) {
				if (scope[i]) { scopeIndex[s] = i; s++; }
			}

			int toIncrement = (Globals.N - scopeSize < Globals.increment) ? Globals.N - scopeSize : Globals.increment;
			int[] temp = Globals.rand.nextUniqueIntsBetweenNotIncluding(0, Globals.N - 1, toIncrement, scopeIndex); 
			for (int i = 0; i < temp.length; i++) { incrementalScope[temp[i]] = true; }
			
			String[] newLocation = new String[Globals.N];
			for (int i = 0; i < Globals.N; i++) {
				if (scope[i]) { // if current scope is true, then take it from the current knowledge
					newLocation[i] = location.getLocationAt(i);
				} else {
					if (incrementalScope[i]) { // if incremental scope is true, then generate random value for new scope
						newLocation[i] = Integer.toString(Globals.rand.nextInt(2));
					} else { // scope and incremental scope are both false so make it " "
						newLocation[i] = " ";
					}
				}
			}
			setLocation(new Location(newLocation));
			Debug.println("Organization.incrementKnowledge: setting representation to: " + location.toString()); 
			resetSearchHistory(); // probably need to reset local representation since the knowledge scope changed
		}
	}
	
	
//	private void incrementKnowledge() { } // implemented by subclasses
	public void run() { } // implemented by subclasses
	
	protected void resetSearchHistory() { 
		incrementedScopeNeighbors.clear();
		currentScopeNeighbors.clear();
		setNeighbors();
	}

	private void setNeighbors() {
		Debug.println("Setting neighbors for location: " + location.toString());

		for (int i = 0; i < Globals.N; i++) {
			String[] neighborLocString = new String[Globals.N];
			boolean addToCurrentScopeNeighbors = false;
			boolean addToIncrementedScopeNeighbors = false;
			for (int j = 0; j < Globals.N; j++) {
				if (i == j) {
					if (location.getLocationAt(j).equals("1")) {
						neighborLocString[j] = "0"; 
					} else if (location.getLocationAt(j).equals("0")) {
						neighborLocString[j] = "1"; 
					} // else locationAt is blank so do nothing
					if (scope[i]) { addToCurrentScopeNeighbors = true; }
					if (incrementalScope[i]) { addToIncrementedScopeNeighbors = true; }
				} else { // all other i != j
					neighborLocString[j] = location.getLocationAt(j);
				}
			}
			
			if (addToCurrentScopeNeighbors) {
				Location neighborLoc = new Location(neighborLocString);
				Debug.println("adding to currentScopeNeighbor: " + neighborLoc.toString() + " (" + Globals.landscape.getPerceivedFitness(neighborLoc, Globals.numInterations - 1) + "\t" + Globals.landscape.getPerceivedFitness(neighborLoc, currentIteration) + ")"); 
				currentScopeNeighbors.add(neighborLoc);
			}
			if (addToIncrementedScopeNeighbors) { 
				Location neighborLoc = new Location(neighborLocString);
				Debug.println("adding to incrementedScopeNeighbor: " + neighborLoc.toString() + " (" + Globals.landscape.getPerceivedFitness(neighborLoc, Globals.numInterations - 1) + "\t" + Globals.landscape.getPerceivedFitness(neighborLoc, currentIteration) + ")"); 
				incrementedScopeNeighbors.add(neighborLoc);
			}
		}
		
		Debug.println("currentScopeNeighbor size: " + currentScopeNeighbors.size());
		Debug.println("IncrementedScopeNeighbor size: " + incrementedScopeNeighbors.size());

		if (Globals.search.equals("experiential")) {
			Collections.shuffle(currentScopeNeighbors);  // shuffle so that order of retrieval is randomized
			Collections.shuffle(incrementedScopeNeighbors);  
		}
	}
	
	/** 
	 * PRINTERS 
	 */
	public String toString() {
		int phaseone = (completedPhaseOne)? 1 : 0;
		int complete = (completed)? 1: 0;
		String retString = index + "\t" + methodology + "\t" + Globals.search + "\t" + getScopeSize() + "\t" + phaseone + "\t" + complete + "\t" + numConsideringNeighbors + "\t" + location.toString() + "\t" + Globals.biases[currentIteration] + "\t" +  (getOrgFitness() / Globals.landscapeMax) + "\t" + (getOrgPerceivedFitness() / Globals.landscapeMax);
//		for (int i = 0; i < currentScopeNeighbors.size(); i++) {
//			Location loc = (Location)currentScopeNeighbors.get(i);
//			retString += "\n\t\t\t\t\t\t\t\t\tc\t" + loc.toString() + "\t" + (Globals.landscape.getFitness(loc) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(loc) / Globals.landscapeMax);			
//		}
//		for (int i = 0; i < incrementedScopeNeighbors.size(); i++) {
//			Location loc = (Location)incrementedScopeNeighbors.get(i);
//			retString += "\n\t\t\t\t\t\t\t\t\ti\t" + loc.toString() + "\t" + (Globals.landscape.getFitness(loc) / Globals.landscapeMax)+ "\t" + (Globals.landscape.getPerceivedFitness(loc) / Globals.landscapeMax);			
//		}
// 
		return retString;
	}
	
	public String scopeToString() {
		return "scope: " + Arrays.toString(scope); 
	}
		
	/**
	 * FOR TESTING
	 */
	private void printNeighbors() { 
		System.out.println(getNeighborsToString("current"));
		System.out.println(getNeighborsToString("incremental"));
	}
	
	public void start() { // MOVED FROM DMU
		resetSearchHistory();
	}

	public static void main(String args[]) {
		Globals.createLandscape(0);
//		Location l = new Location();
//		System.out.println("initial location: " + l.toString());
//		try {
//			Organization o = new Organization(0, "incremental");
			Organization o = new OrgAgile(0);
			o.printNeighbors();
//			o.printDMUNeighbors(1);
//		} catch (UnknownOrgTypeException e) {
//			System.err.println("UnknownOrgTypeError: " + e.getMessage());
//		}
		
	}
	
	public abstract String neighborsToString(int period);

}
