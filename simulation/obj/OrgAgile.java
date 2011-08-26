package obj;

import java.util.Collections;

import util.Globals;
import util.Debug;
import util.PerceivedFitnessComparator;

public class OrgAgile extends Organization {

	public OrgAgile(int idx) {
		super(idx);
		methodology = "agile";
		incrementKnowledge(); // increment knowledge 0->Globals.increment
	}

	private boolean isLocalOptimum() { // called by subclass' run() 
		boolean retBool = true;
		double currentFitness = Globals.landscape.getPerceivedFitness(location, currentIteration);

		if (Globals.integrationNew && Globals.adaptNew) {
			for (Location neighbor: incrementedScopeNeighbors) {
				double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
				if (neighborFitness > currentFitness) { retBool = false; }
			}
		} else {
			for (Location neighbor: incrementedScopeNeighbors) {
				double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
				if (neighborFitness > currentFitness) { retBool = false; }
			}
			for (Location neighbor: currentScopeNeighbors) {
				double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
				if (neighborFitness > currentFitness) { retBool = false; }
			}
		}
		
		return retBool;
	}

	public void run() {  
		Debug.println("OrgAgile.run: location:\t" + location.toString() + " (" + Globals.landscape.getPerceivedFitness(location, currentIteration) + ")"); 

		if (!completed) { // only do this if org has not reached final equilibrium state
			Location moveTo = search(); // moveTo is just a localLocation so I need to figure out the full location to update the org's location
			if (moveTo == null) { // search was unsuccessful
				if(isLocalOptimum()) { // sticking point?
					if (canIncrementScope()) {
						Debug.println("OrgAgile.run: NOT MOVING: Org is at local optimum for current scope"); 
						incrementKnowledge(); // + alter perceivedFitnessContributions of landscape
					} else {
						Debug.println("OrgAgile.run: NOT MOVING: Org is at local optimum for full scope / search complete"); 
						completed = true; 
						completedPhaseOne = true; // not really needed but to maintain consistency with data structure of OrgWaterfall
					}
				} else {
					Debug.println("OrgAgile.run: NOT MOVING: Org is NOT at local optimum / search for higher ground unsuccessful");  
					// do nothing; search just failed but can continue
				}
			} else { // search was successful
				// update location and reset search history
				Debug.println("OrgAgile.run: MOVING TO " + moveTo.toString()); 
				setLocation(moveTo);
				resetSearchHistory();
			}
		}
	}

	public Location search() { 
		Location moveTo = null;
		double currentFitness = Globals.landscape.getPerceivedFitness(location, currentIteration);
		PerceivedFitnessComparator c = new PerceivedFitnessComparator(currentIteration);

		if (Globals.integrationNew) { // 
			if (Globals.adaptNew) { // integration = new && adapt = new => only consider incrementedScopeNeighbors
				Debug.println("OrgAgile.search: search w/ integration = new and adapt = new");
				if (Globals.search.equals("cognitive")) {
					numConsideringNeighbors = incrementedScopeNeighbors.size();
					Collections.sort(incrementedScopeNeighbors, c);
					Location neighbor = (Location)incrementedScopeNeighbors.remove(0); // max fitness neighbor
					double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
					Debug.println("OrgAgile.searchCognitive: Trying neighbor #" + 0 + " from incrementedScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
					if (neighborFitness > currentFitness) { moveTo = new Location(neighbor); }
				} else { // experiential
					if (!incrementedScopeNeighbors.isEmpty()) { // incrementedScopeNeighbors still has some neighbors
						numConsideringNeighbors = 1;
						int numRemainingIncrementedScopeNeighbors = incrementedScopeNeighbors.size();
						int r = Globals.rand.nextInt(numRemainingIncrementedScopeNeighbors);
						Location neighbor = (Location)incrementedScopeNeighbors.remove(r);
//						double currentFitness = Globals.landscape.getPerceivedFitness(location);
						double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
						Debug.println("OrgAgile.searchExperiential: Trying neighbor #" + r + " from incrementedScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
						if (neighborFitness > currentFitness) {
							moveTo = new Location(neighbor);
						} else { } // do nothing since neighbor tried from incrementedScopeNeighbors is not a good move and leave moveTo as NULL
					} else { } // do nothing since we're only considering incrementedScopeNeighbors
				}
			} else { // integration = new && adapt = all => consider incrementedScopeNeighbors first and then currentScopeNeighbors
				Debug.println("OrgAgile.search: search w/ integration = new and adapt = all");
				if (Globals.search.equals("cognitive")) {
					if (!incrementedScopeNeighbors.isEmpty()) {
						numConsideringNeighbors = incrementedScopeNeighbors.size();
						Collections.sort(incrementedScopeNeighbors, c);
						Location ineighbor = (Location)incrementedScopeNeighbors.remove(0); // max fitness neighbor
						double ineighborFitness = Globals.landscape.getPerceivedFitness(ineighbor, currentIteration);
						Debug.println("OrgAgile.searchCognitive: Trying neighbor #" + 0 + " from incrementedScopeNeighbors at location " + ineighbor.toString() + " with fitness " + ineighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
						if (ineighborFitness > currentFitness) { 
							moveTo = new Location(ineighbor); 
						} else { // no better move within incrementedScopeNeighbors
							if (!currentScopeNeighbors.isEmpty()) {
								numConsideringNeighbors += currentScopeNeighbors.size();
								Collections.sort(currentScopeNeighbors, c);
								Location cneighbor = (Location)currentScopeNeighbors.remove(0); // max fitness neighbor
								double cneighborFitness = Globals.landscape.getPerceivedFitness(cneighbor, currentIteration);
								Debug.println("OrgAgile.searchCognitive: Trying neighbor #" + 0 + " from currentScopeNeighbors at location " + cneighbor.toString() + " with fitness " + cneighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")");
								if (cneighborFitness > currentFitness) { moveTo = new Location(cneighbor); }
							}
						}
					} else {
						// this should never happen
						System.err.println("ERROR: OrgAgile.searchCognitive: incNeighbors is empty"); System.exit(0);
					}
				} else { // experiential
					if (!incrementedScopeNeighbors.isEmpty()) { // incrementedScopeNeighbors still has some neighbors
						numConsideringNeighbors = 1;
						int numRemainingIncrementedScopeNeighbors = incrementedScopeNeighbors.size();
						int r = Globals.rand.nextInt(numRemainingIncrementedScopeNeighbors);
						Location neighbor = (Location)incrementedScopeNeighbors.remove(r);
						double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
						Debug.println("OrgAgile.search: Trying neighbor #" + r + " from incrementedScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
						if (neighborFitness > currentFitness) {
							moveTo = new Location(neighbor);
						} else { }  // do nothing since neighbor tried from incrementedScopeNeighbors is not a good move and leave moveTo as NULL
					} else { // incrementedScopeNeighbors depleted, move onto currentScopeNeighbors
						numConsideringNeighbors += 1;
						int numRemainingCurrentScopeNeighbors = currentScopeNeighbors.size();
						int r = Globals.rand.nextInt(numRemainingCurrentScopeNeighbors);
						Location neighbor = (Location)currentScopeNeighbors.remove(r);
						double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
						Debug.println("OrgAgile.search: Trying neighbor #" + r + " from currentScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
						if (neighborFitness > currentFitness) {
							moveTo = new Location(neighbor);
						} else { } // do nothing since neighbor tried from currentScopeNeighbors is not a good move and leave moveTo as NULL
					}
				}
			}
		} else { // integration == all  
			if (Globals.adaptNew) { // integration == all && adapt = new => THIS SHOULD NEVER HAPPEN
				Debug.println("OrgAgile.search: search w/ integration = all and adapt = new\nDo nothing.  Invalid conf"); 
			} else { // integration == all && adapt = all => consider all neighbors from incrementedScopeNeighbors and currentScopeNeighbors concurrently
				if (Globals.search.equals("cognitive")) {
					Debug.println("OrgAgile.searchCognitive: search w/ integration = all and adapt = all");
					// find best neighbor within incrementedScopeNeighbors and currentScopeNeighbors
					Collections.sort(incrementedScopeNeighbors, c);
					Collections.sort(currentScopeNeighbors, c);
					numConsideringNeighbors = incrementedScopeNeighbors.size() + currentScopeNeighbors.size();
					Location ineighbor = (Location)incrementedScopeNeighbors.remove(0); // max fitness neighbor
					double ineighborFitness = Globals.landscape.getPerceivedFitness(ineighbor, currentIteration);

					double cneighborFitness = 0.0d;
					try {
						// for first iteration where current scope neighbors don't exist
						Location cneighbor = (Location)currentScopeNeighbors.remove(0); // max fitness neighbor
						cneighborFitness = Globals.landscape.getPerceivedFitness(cneighbor, currentIteration);
						
						if (ineighborFitness > currentFitness) {
							if (ineighborFitness > cneighborFitness) {
								Debug.println("OrgAgile.search: Trying neighbor #" + 0 + " from incrementedScopeNeighbors at location " + ineighbor.toString() + " with fitness " + ineighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")");
								moveTo = new Location(ineighbor); 
							} else {
								Debug.println("OrgAgile.search: Trying neighbor #" + 0 + " from currentScopeNeighbors at location " + cneighbor.toString() + " with fitness " + cneighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")");
								moveTo = new Location(cneighbor);
							}
						} else if (cneighborFitness > currentFitness) {
							Debug.println("OrgAgile.search: Trying neighbor #" + 0 + " from currentScopeNeighbors at location " + cneighbor.toString() + " with fitness " + cneighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
							moveTo = new Location(cneighbor);
						} else { // current location is better than max of incrementedScopeNeighbors and currentScopeNeighbors
						}

					} catch (ArrayIndexOutOfBoundsException e) {
						if (ineighborFitness > currentFitness) {
							if (ineighborFitness > cneighborFitness) {
								Debug.println("OrgAgile.search: Trying neighbor #" + 0 + " from incrementedScopeNeighbors at location " + ineighbor.toString() + " with fitness " + ineighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")");
								moveTo = new Location(ineighbor); 
							} 
						} else { // current location is better than max of incrementedScopeNeighbors and currentScopeNeighbors
						}
						
					}
//					Location cneighbor = (Location)currentScopeNeighbors.remove(0); // max fitness neighbor
//					double cneighborFitness = Globals.landscape.getPerceivedFitness(cneighbor);
//					if (ineighborFitness > currentFitness) {
//						if (ineighborFitness > cneighborFitness) {
//							Debug.println("OrgWaterfall.search: Trying neighbor #" + 0 + " from incrementedScopeNeighbors at location " + ineighbor.toString() + " with fitness " + ineighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")");
//							moveTo = new Location(ineighbor); 
//						} else {
//							Debug.println("OrgWaterfall.search: Trying neighbor #" + 0 + " from currentScopeNeighbors at location " + cneighbor.toString() + " with fitness " + cneighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")");
//							moveTo = new Location(cneighbor);
//						}
//					} else if (cneighborFitness > currentFitness) {
//						Debug.println("OrgWaterfall.search: Trying neighbor #" + 0 + " from currentScopeNeighbors at location " + cneighbor.toString() + " with fitness " + cneighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
//						moveTo = new Location(cneighbor);
//					} else { // current location is better than max of incrementedScopeNeighbors and currentScopeNeighbors
//					}
				} else { // experiential
					Debug.println("OrgAgile.searchExperiential: search w/ integration = all and adapt = all");
					numConsideringNeighbors = 1;
					int numRemainingIncrementedScopeNeighbors = incrementedScopeNeighbors.size();
					int numRemainingCurrentScopeNeighbors = currentScopeNeighbors.size();
					int totalNeighbors = numRemainingIncrementedScopeNeighbors + numRemainingCurrentScopeNeighbors;
					Debug.println("totalNeighbors: " + totalNeighbors); 
					int r = Globals.rand.nextInt(totalNeighbors);
					if (r < numRemainingIncrementedScopeNeighbors) { // get from incrementalScopeNeighbors
						Location neighbor = (Location)incrementedScopeNeighbors.remove(r);
						double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
						Debug.println("OrgAgile.search: Trying neighbor #" + r + " from incrementedScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
						if (neighborFitness > currentFitness) {
							moveTo = new Location(neighbor);
						} else { } // do nothing since neighbor tried from incrementedScopeNeighbors is not a good move and leave moveTo as NULL
					} else { // get from currentScopeNeighbors
						Location neighbor = (Location)currentScopeNeighbors.remove(r - numRemainingIncrementedScopeNeighbors);
						double neighborFitness = Globals.landscape.getPerceivedFitness(neighbor, currentIteration);
						Debug.println("OrgAgile.search: Trying neighbor #" + (r - numRemainingIncrementedScopeNeighbors) + " from currentScopeNeighbors at location " + neighbor.toString() + " with fitness " + neighborFitness + " (current fitness: " + currentFitness + " at location " + location.toString() + ")"); 
						if (neighborFitness > currentFitness) {
							moveTo = new Location(neighbor);
						} else { } // do nothing since neighbor tried from incrementedScopeNeighbors is not a good move and leave moveTo as NULL
					}
				}
			}
		}
		return moveTo; 
	}

//	public boolean isPhaseOneCompleted() { return completed; }

	public String neighborsToString(int period) {
		String retString = "";
		
		if (Globals.integrationNew) {
			if (Globals.adaptNew) { // integration = new && adapt = new => only consider incrementedScopeNeighbors
				for (Location neighbor : incrementedScopeNeighbors) {
					retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\ti\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
				}
			} else { // integration = new && adapt = all => consider incrementedScopeNeighbors first and then currentScopeNeighbors
				for (Location neighbor : incrementedScopeNeighbors) {
					retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\ti\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
				}
				for (Location neighbor : currentScopeNeighbors) {
					retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\tc\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
				}
			}
		} else {
			if (Globals.adaptNew) { // integration == all && adapt = new => THIS SHOULD NEVER HAPPEN
			} else { // integration == all && adapt = all => consider all neighbors from incrementedScopeNeighbors and currentScopeNeighbors concurrently
				for (Location neighbor : incrementedScopeNeighbors) {
					retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\ti\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
				}
				for (Location neighbor : currentScopeNeighbors) {
					retString += Globals.landscape.getLandscapeID() + "\t" + period + "\t" + getIndex() + "\tc\t" + neighbor.toString() + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, Globals.numInterations - 1) / Globals.landscapeMax) + "\t" + (Globals.landscape.getPerceivedFitness(neighbor, currentIteration) / Globals.landscapeMax) + "\n";
				}
			}
		}
		return retString;
	}

}
