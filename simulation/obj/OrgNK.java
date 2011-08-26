package obj;

import java.util.Collections;

import util.Debug;
import util.Globals;
import util.PerceivedFitnessComparator;

public class OrgNK extends Organization {
	public OrgNK(int idx) {
		super(idx);
		methodology = "nk";
		incrementKnowledge(); // increment knowledge 0->16
	}

	private boolean isLocalOptimum() { // called by subclass' run()
		boolean retBool = true;
		double currentFitness = Globals.landscape.getPerceivedFitness(location, currentIteration);

		if (!completedPhaseOne) {
			for (Location neighbor: incrementedScopeNeighbors) {
				double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
				if (neighborFitness > currentFitness) { retBool = false; }
			}
		} else {
			for (Location neighbor: currentScopeNeighbors) {
				double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
				if (neighborFitness > currentFitness) { retBool = false; }
			}
		}
		return retBool;
	}
	
	public void run() {  
		Debug.println("OrgNK.run(): location:\t" + location.toString() + " (" + Globals.landscape.getPerceivedFitness(location, currentIteration) + ")"); 
		
		if (!completed) { // only do this if org has not reached final equilibrium state
			Location moveTo = search(); // moveTo is just a localLocation so I need to figure out the full location to update the org's location
			if (moveTo == null) { // search was unsuccessful
				if(isLocalOptimum()) { // sticking point?
						if (!completedPhaseOne) {
							Debug.println("OrgNK.run: NOT MOVING: Org is at local optimum for full scope / search complete for phase 1"); 
							// set completion flag to true; update (remove) bias; set perceived fitness (= actual); reset search history
							completedPhaseOne = true;
							incrementKnowledge(); // this merely sets scope[] for all elements to true (so we'll only have currentScopeNeighbors to worry about)
//							resetSearchHistory(); 
//							setBias(0d);
							Debug.println("OrgNK.run: Setting bias to: " + Globals.biases[currentIteration] + " after phase 1 completion"); 
//							Globals.landscape.setPerceivedFitnessContributions(bias);
//							Debug.println("OrgNK.run: Resetting PerceivedFitnessContributions with bias = " + bias);
						} else {
							Debug.println("OrgNK.run: NOT MOVING: Org is at local optimum for full scope / search complete for phase 2 also");
							completed = true;
						}
				} else {
					Debug.println("OrgNK.run: NOT MOVING: Org is NOT at local optimum / search for higher ground unsuccessful");  
					// do nothing; search just failed but can continue
				}
			} else { // search was successful
				// update location and reset search history
				Debug.println("OrgNK.run: MOVING TO " + moveTo.toString()); 
				setLocation(moveTo);
				resetSearchHistory();
			}
		}
	}

	
	public Location search() {  // cognitive search
		Location moveTo = null;
		PerceivedFitnessComparator c = new PerceivedFitnessComparator(currentIteration);
		double currentFitness = Globals.landscape.getPerceivedFitness(location, currentIteration);
		
		if (!completedPhaseOne) {
			// Phase 1 - Analysis / Design phases; search with integration = new; adaptation = new
			// waterfall only does integration = new and adaptation = new for initial development 
			if (!incrementedScopeNeighbors.isEmpty()) { // incrementedScopeNeighbors still has some neighbors
				if (Globals.search.equals("cognitive")) {
					numConsideringNeighbors = incrementedScopeNeighbors.size();
					Collections.sort(incrementedScopeNeighbors, c);
					Location neighbor = (Location)incrementedScopeNeighbors.remove(0); // max fitness neighbor
					double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
					Debug.println("OrgWaterfall.search: Trying neighbor #" + 0 + " from incrementedScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
					if (neighborFitness > currentFitness) { moveTo = new Location(neighbor); }
				} else { // experiential 
					int numRemainingIncrementedScopeNeighbors = incrementedScopeNeighbors.size();
					numConsideringNeighbors = 1;
					int r = Globals.rand.nextInt(numRemainingIncrementedScopeNeighbors);
					Location neighbor = (Location)incrementedScopeNeighbors.remove(r);
					double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
					Debug.println("OrgWaterfall.search: Trying neighbor #" + r + " from incrementedScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
					if (neighborFitness > currentFitness) { moveTo = new Location(neighbor); }
				}
			} 
		} else {
			// Phase 2 - Post-Delivery search; search with integration = all; adaptation = all
			Debug.println("OrgWaterfall.search: search w/ integration = all and adapt = all -- phase 2"); 
			if (!currentScopeNeighbors.isEmpty()) {
				if (Globals.search.equals("cognitive")) {
					numConsideringNeighbors = currentScopeNeighbors.size();
					Collections.sort(currentScopeNeighbors, c);
					Location neighbor = (Location)currentScopeNeighbors.remove(0); // max fitness neighbor
					double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
					Debug.println("OrgWaterfall.search: Trying neighbor #" + 0 + " from currentScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
					if (neighborFitness > currentFitness) { moveTo = new Location(neighbor); } 
				} else {// experiential
					int numRemainingCurrentScopeNeighbors = currentScopeNeighbors.size();
					numConsideringNeighbors = 1;
					int r = Globals.rand.nextInt(numRemainingCurrentScopeNeighbors);
					Location neighbor = (Location)currentScopeNeighbors.remove(r);
					double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
					Debug.println("OrgWaterfall.search: Trying neighbor #" + r + " from currentScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
					if (neighborFitness > currentFitness) { moveTo = new Location(neighbor); } 
				}
			}
		}
		return moveTo; 
	}
	
	@Override
	public String neighborsToString(int period) {
		String retString = "";
		if (!completedPhaseOne) { // only incrementedScopeNeighbors
			//OrgID\tLocation\tActualFitness\tPerceivedFitness
			for (Location neighbor : incrementedScopeNeighbors) {
				retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\ti\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
			}
		} else {
			for (Location neighbor : currentScopeNeighbors) { 
				retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\tc\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1 ) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
			}
		}
		return retString;
	}

}
