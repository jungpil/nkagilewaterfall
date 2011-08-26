package util;

import java.util.*;
import obj.Location;

public class PerceivedFitnessComparator implements Comparator<Location> { // order DESC; most fit to least fit

	private int scopeStep; 
	
	public PerceivedFitnessComparator(int step) { 
		scopeStep = step; 
	}

	public int compare(Location a, Location b) { 
		if (Globals.landscape.getPerceivedFitness(a, scopeStep) > Globals.landscape.getPerceivedFitness(b, scopeStep)) {
			return -1;
		} else if (Globals.landscape.getPerceivedFitness(a, scopeStep) < Globals.landscape.getPerceivedFitness(b, scopeStep)) {
			return 1;
		} else {
			if (Globals.rand.nextBoolean()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

}
