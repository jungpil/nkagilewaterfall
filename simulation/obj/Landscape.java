package obj;

import java.util.ArrayList;

import files.LandscapeFitness;
import util.Globals;
import util.MersenneTwisterFast;

public class Landscape {
	private long landscapeID;
	private double maxFitness;
	private InfluenceMatrix infmat; 
	private int numCases;
	private int numLocations; 

	private double[][] fitnessContribs;
	private int numBiasedLandscapes; // number of biased landscapes to create = Globals.numSteps - 1 (1 for actual)

	private MersenneTwisterFast rnd;
//	private MersenneTwisterFast rnd = new MersenneTwisterFast(System.currentTimeMillis());
	
	/**
	 * Constructor 
	 */
	public Landscape(long lndscpID, InfluenceMatrix inf) {
		// set main members
		landscapeID = lndscpID;
		rnd = new MersenneTwisterFast(landscapeID); // this random number generator is for landscape only so that landscapes are comparable across experiments
		infmat = inf;
		numCases = inf.getNumCases();
		numLocations = (int)(Math.pow(2, Globals.N));
		setPerceivedFitnessContributions();
	}
	
	/**
	 * Accessors
	 */
	public long getLandscapeID() {
		return landscapeID;
	}

	/**
	 * returns the maximum fitness value for the landscape
	 */
	public double getMaxFitness() { return maxFitness; }
	
	private void setPerceivedFitnessContributions() {
		fitnessContribs = new double[Globals.numInterations][infmat.getNumCases()];
		// first set actual fitness contribution (for bias = 0.0d)
		for (int i = 0; i < numCases; i++) { fitnessContribs[Globals.numInterations - 1][i] = rnd.nextDouble(); }
		
		// set perceived fitness contributions for when there is bias
		for (int b = 0; b < Globals.numInterations - 1; b++) {
			for (int i = 0; i < numCases; i++) {
				fitnessContribs[b][i] = ((1 - Globals.biases[b]) * fitnessContribs[Globals.numInterations - 1][i]) + (Globals.biases[b] *  (rnd.nextDouble())); // random turbulence
				//fitnessContribs[i] = ((1 - Globals.biases[b]) * fitnessContribs[Globals.numSteps - 1][i]) + java.lang.Math.abs((Globals.biases[b] *  rnd.nextDouble())); // under estimated perceptions
				//fitnessContribs[i] = fitnessContribs[Globals.numSteps - 1][i] * (1 + Globals.biases[b] * rnd.nextDouble()); // over-estimated perceptions
			}
		}
		setLandscapeMax();
	}
	
	private void setLandscapeMax() {
		maxFitness = 0d; // initialize 
		for (int i = 0; i < numLocations; i++) {
			maxFitness = maxFitness < getPerceivedFitness(Location.getLocationFromInt(i), Globals.numInterations - 1) ? getPerceivedFitness(Location.getLocationFromInt(i), Globals.numInterations - 1) : maxFitness;
		}
	}
	
	// get perceived fitness for a location recognizing reduced scope given bias
	public double getPerceivedFitness(Location loc, int step) {
		double fitness = 0d;
		for (int i = 0; i < Globals.N; i++) {
			String s = loc.getLocationAt(i, infmat);
			if (!s.substring(0, 1).equals(" ")) {
				fitness += getPerceivedFitnessContribution(s, i, step);
			}
		}
		return (fitness / Globals.N);
	}

	/**
	 * create correlated landscape with stability parameter from Globals.stability
	 */
	public void shock() {
		// update all fitness contributions (Ci); adapted from Siggelkow and Rivkin (OS 2005)
		for (int b = 0; b < Globals.numInterations - 1; b++) {
			for (int i = 0; i < numCases; i++) { 
				fitnessContribs[b][i] = (Globals.stability * fitnessContribs[b][i]) + ((1 - Globals.stability) *  (rnd.nextDouble()));
			}
		}
		//setFitnessLandscape();
		setLandscapeMax();
	}
	
	/**
	 * create correlated landscape with stability parameter as input 
	 */
	public void shock(double tau) {
		// update all fitness contributions (Ci); adapted from Siggelkow and Rivkin (OS 2005)
		for (int b = 0; b < Globals.numInterations - 1; b++) {
			for (int i = 0; i < numCases; i++) { 
				fitnessContribs[b][i] = (tau * fitnessContribs[b][i]) + ((1 - tau) *  (rnd.nextDouble()));
			}
		}
		//setFitnessLandscape();
		setLandscapeMax();
	}
	
	/**
	 * returns array list of compatible location Strings for location strings with blanks
	 * e.g., "00 0" returns {"0000", "0010"}
	 * - used by getPerceivedFitnessContribution and getFitnessContribution
	 */
	private ArrayList<String> getGeneralizedLocationsList(String locationString) {
		// since locationString may contain a " ", we need to fill those spaces with 1s and 0s
		// i.e., if space exists, remove entry and replace with combinations of 0/1 replacing the space
		ArrayList<String> list = new ArrayList<String>(); 
		list.add(locationString);
		
		boolean done = false;
		while (!done) {
			boolean found = false;
			for (int i = 0; i < list.size(); i++) {
				String listItem = list.get(i);
				int idx = listItem.indexOf(' '); // position of ' ' in string listItem
				if (idx > -1) { // found ' '
					found = true;
					list.remove(i);
					char[] stringArray = listItem.toCharArray();
					String s0 = "";  String s1 = "";
					for (int j = 0; j < stringArray.length; j++) {
						if (idx == j) {
							s0 += "0";  s1 += "1";
						} else {
							s0 += stringArray[j]; s1 += stringArray[j];
						}
					}
					list.add(s0); list.add(s1);
					break;
				} 
			} 
			if (!found) { done = true; }
		}
		// now list should contain all combinations of matching policy choices (e.g., "  1" -> {"001", "011", "101", "111"})
		return list;
	}

	private double getPerceivedFitnessContribution(String locationString, int place, int step) {
		double retVal = 0d;
		ArrayList<String> list = getGeneralizedLocationsList(locationString);
		int cnt = 0; 
		for (int i = 0; i < list.size(); i++) {
			//String listItem = list.get(i);
			//int fitnessContribIndex = im.getStartPosition(place) + Integer.parseInt(listItem, 2);
			//retVal += perceivedFitnessContribs[fitnessContribIndex];
			retVal += fitnessContribs[step][infmat.getStartPosition(place) + Integer.parseInt(list.get(i), 2)];
			cnt++;
		}
		return retVal /= cnt; // return average
	}
	
	/**
     * Utility for printing all possible fitness values for given landscape   
     */
	public void printLandscapeFitness(int step) { // step is scope plan steps (Globals.numSteps - 1 => no bias; 0 is full bias)
		for (int i = 0; i < numLocations; i++) {
			Location l = Location.getLocationFromInt(i);
			System.out.println(l.toString() + "\t" + getPerceivedFitness(l, step)); 
		}
	}
	
	public void printFitnessContributions() {
		printPerceivedFitnessContributions(Globals.numInterations - 1);
	}
	
	public void printPerceivedFitnessContributions(int step) {
		for (int i = 0; i < fitnessContribs[step].length; i++) {
			System.out.println(i + "\t" + fitnessContribs[step][i]);
		}
	}

    /**
     * main method for testing purposes only.  
     */
	public static void main(String args[]) {
		System.out.println(System.currentTimeMillis());
		long landscapeID = 0;
		String infmatfile = "n16k12.txt";
		
		if (args.length > 0) {
			landscapeID = Long.parseLong(args[0]);
			infmatfile = args[1];
		}
		InfluenceMatrix infmat = new InfluenceMatrix("inf/" + infmatfile);
//		infmat.print();
		Landscape l = new Landscape(landscapeID, infmat);
//		System.out.println("landscapeID: " + landscapeID);
//		System.out.println("LandscapeID: " + l.getLandscapeID());

//		l.printLandscapeFitness();

		//		System.out.println("cases:\t" + l.getNumCases());
//		System.out.println("max fitnes:\t" + l.getMaxFitness());
//		System.out.println("fitness contributions");
//		l.printFitnessContributions();
//		l.printLandscapeFitness();
//		System.out.println(l.getMaxFitness());
//		double bias = 3.2d;
//		System.out.println("CHECKING LOC");
//		String[] l1s = {" ", "1", "0", "1"};
//		Location loc1 = new Location(l1s);
//		System.out.println(loc1.toString() + "\t" + l.getFitness(loc1));

//		Location full0 = new Location(new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full1 = new Location(new String[]{" ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full2 = new Location(new String[]{" ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full3 = new Location(new String[]{" ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full4 = new Location(new String[]{" ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full5 = new Location(new String[]{" ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full6 = new Location(new String[]{" ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full7 = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full8 = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0"});
//		Location full9 = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0"});
//		Location fulla = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0"});
//		Location fullb = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0"});
//		Location fullc = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0"});
//		Location fulld = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0"});
//		Location fulle = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0"});
//		Location fullf = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0"});
//		Location fullg = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
//		System.out.println(System.currentTimeMillis());
//		System.out.println(System.currentTimeMillis() + "\t" + full0.toString() + "\t" + l.getFitnessOld(full0));
//		System.out.println(System.currentTimeMillis() + "\t" + full1.toString() + "\t" + l.getFitnessOld(full1));
//		System.out.println(System.currentTimeMillis() + "\t" + full2.toString() + "\t" + l.getFitnessOld(full2));
//		System.out.println(System.currentTimeMillis() + "\t" + full3.toString() + "\t" + l.getFitnessOld(full3));
//		System.out.println(System.currentTimeMillis() + "\t" + full4.toString() + "\t" + l.getFitnessOld(full4));
//		System.out.println(System.currentTimeMillis() + "\t" + full5.toString() + "\t" + l.getFitnessOld(full5));
//		System.out.println(System.currentTimeMillis() + "\t" + full6.toString() + "\t" + l.getFitnessOld(full6));
//		System.out.println(System.currentTimeMillis() + "\t" + full7.toString() + "\t" + l.getFitnessOld(full7));
//		System.out.println(System.currentTimeMillis() + "\t" + full8.toString() + "\t" + l.getFitnessOld(full8));
//		System.out.println(System.currentTimeMillis() + "\t" + full9.toString() + "\t" + l.getFitnessOld(full9));
//		System.out.println(System.currentTimeMillis() + "\t" + fulla.toString() + "\t" + l.getFitnessOld(fulla));
//		System.out.println(System.currentTimeMillis() + "\t" + fullb.toString() + "\t" + l.getFitnessOld(fullb));
//		System.out.println(System.currentTimeMillis() + "\t" + fullc.toString() + "\t" + l.getFitnessOld(fullc));
//		System.out.println(System.currentTimeMillis() + "\t" + fulld.toString() + "\t" + l.getFitnessOld(fulld));
//		System.out.println(System.currentTimeMillis() + "\t" + fulle.toString() + "\t" + l.getFitnessOld(fulle));
//		System.out.println(System.currentTimeMillis() + "\t" + fullf.toString() + "\t" + l.getFitnessOld(fullf));
//		System.out.println(System.currentTimeMillis() + "\t" + fullg.toString() + "\t" + l.getFitnessOld(fullg));
//		System.out.println(System.currentTimeMillis());
		
//		Location full = new Location(new String[]{"0", "0", "0", "0", 
//				"0", "0", "0", "0", 
//				"0", "0", "0", "0", 
//				"0", "0", "0", "0", });
//		Location loc = new Location(new String[]{" ", " ", " ", "1", 
//				" ", " ", "0", "1", 
//				" ", "1", " ", "1", 
//				"0", " ", " ", "1"});
//		Location loc2 = new Location(new String[]{" ", " ", " ", " ", 
//				" ", " ", " ", " ", 
//				" ", " ", " ", " ", 
//				" ", " ", " ", " "});
//		System.out.println("maxFitness:\t" + l.getMaxFitness());
//		System.out.println(full.toString() + "\t" + l.getFitness(full));
//		System.out.println(loc.toString() + "\t" + l.getFitnessOld(loc));
//		System.out.println(loc2.toString() + "\t" + l.getFitnessOld(loc2));

//		System.out.println(System.currentTimeMillis());
		
		/********
		
		String[] val = {" ", "0", "1"};
		
		for (int i0 = 0; i0 < val.length; i0++) { for (int i1 = 0; i1 < val.length; i1++) { for (int i2 = 0; i2 < val.length; i2++) {
		for (int i3 = 0; i3 < val.length; i3++) { 
		for (int i4 = 0; i4 < val.length; i4++) { for (int i5 = 0; i5 < val.length; i5++) {
		for (int i6 = 0; i6 < val.length; i6++) { for (int i7 = 0; i7 < val.length; i7++) { for (int i8 = 0; i8 < val.length; i8++) {
		for (int i9 = 0; i9 < val.length; i9++) { for (int i10 = 0; i10 < val.length; i10++) { for (int i11 = 0; i11 < val.length; i11++) {
		for (int i12 = 0; i12 < val.length; i12++) { for (int i13 = 0; i13 < val.length; i13++) { for (int i14 = 0; i14 < val.length; i14++) {
			for (int i15 = 0; i15 < val.length; i15++) { 
				String[] aLocString = new String[Globals.N];
				aLocString[0] = val[i0];
				aLocString[1] = val[i1];
				aLocString[2] = val[i2];
				aLocString[3] = val[i3];
				aLocString[4] = val[i4];
				aLocString[5] = val[i5];
				aLocString[6] = val[i6];
				aLocString[7] = val[i7];
				aLocString[8] = val[i8];
				aLocString[9] = val[i9];
				aLocString[10] = val[i10];
				aLocString[11] = val[i11];
				aLocString[12] = val[i12];
				aLocString[13] = val[i13];
				aLocString[14] = val[i14];
				aLocString[15] = val[i15];
				Location aLocation = new Location(aLocString); 
				System.out.println(aLocation.toString() + "\t" + l.getFitness(aLocation) + "\t" + l.getFitnessOld(aLocation));
			}
		}}}}}}}}}}}}}}}
		
		********/
		
		
		
//		String[] l0s = {" ", " ", " ", " "}; Location loc0 = new Location(l0s); System.out.println(loc0.toString() + "\t" + l.getFitness(loc0));
//		String[] l1s = {" ", " ", " ", "0"}; Location loc1 = new Location(l1s); System.out.println(loc1.toString() + "\t" + l.getFitness(loc1));
//		String[] l2s = {" ", " ", " ", "1"}; Location loc2 = new Location(l2s); System.out.println(loc2.toString() + "\t" + l.getFitness(loc2));
//		String[] l3s = {" ", " ", "0", " "}; Location loc3 = new Location(l3s); System.out.println(loc3.toString() + "\t" + l.getFitness(loc3));
//		String[] l4s = {" ", " ", "0", "0"}; Location loc4 = new Location(l4s); System.out.println(loc4.toString() + "\t" + l.getFitness(loc4));
//		String[] l5s = {" ", " ", "0", "1"}; Location loc5 = new Location(l5s); System.out.println(loc5.toString() + "\t" + l.getFitness(loc5));
//		String[] l6s = {" ", " ", "1", " "}; Location loc6 = new Location(l6s); System.out.println(loc6.toString() + "\t" + l.getFitness(loc6));
//		String[] l7s = {" ", " ", "1", "0"}; Location loc7 = new Location(l7s); System.out.println(loc7.toString() + "\t" + l.getFitness(loc7));
//		String[] l8s = {" ", " ", "1", "1"}; Location loc8 = new Location(l8s); System.out.println(loc8.toString() + "\t" + l.getFitness(loc8));
//		String[] l9s = {" ", "0", " ", " "}; Location loc9 = new Location(l9s); System.out.println(loc9.toString() + "\t" + l.getFitness(loc9));
//		String[] l10s = {" ", "0", " ", "0"}; Location loc10 = new Location(l10s); System.out.println(loc10.toString() + "\t" + l.getFitness(loc10));
//		String[] l11s = {" ", "0", " ", "1"}; Location loc11 = new Location(l11s); System.out.println(loc11.toString() + "\t" + l.getFitness(loc11));
//		String[] l12s = {" ", "0", "0", " "}; Location loc12 = new Location(l12s); System.out.println(loc12.toString() + "\t" + l.getFitness(loc12));
//		String[] l13s = {" ", "0", "0", "0"}; Location loc13 = new Location(l13s); System.out.println(loc13.toString() + "\t" + l.getFitness(loc13));
//		String[] l14s = {" ", "0", "0", "1"}; Location loc14 = new Location(l14s); System.out.println(loc14.toString() + "\t" + l.getFitness(loc14));
//		String[] l15s = {" ", "0", "1", " "}; Location loc15 = new Location(l15s); System.out.println(loc15.toString() + "\t" + l.getFitness(loc15));
//		String[] l16s = {" ", "0", "1", "0"}; Location loc16 = new Location(l16s); System.out.println(loc16.toString() + "\t" + l.getFitness(loc16));
//		String[] l17s = {" ", "0", "1", "1"}; Location loc17 = new Location(l17s); System.out.println(loc17.toString() + "\t" + l.getFitness(loc17));
//		String[] l18s = {" ", "1", " ", " "}; Location loc18 = new Location(l18s); System.out.println(loc18.toString() + "\t" + l.getFitness(loc18));
//		String[] l19s = {" ", "1", " ", "0"}; Location loc19 = new Location(l19s); System.out.println(loc19.toString() + "\t" + l.getFitness(loc19));
//		String[] l20s = {" ", "1", " ", "1"}; Location loc20 = new Location(l20s); System.out.println(loc20.toString() + "\t" + l.getFitness(loc20));
//		String[] l21s = {" ", "1", "0", " "}; Location loc21 = new Location(l21s); System.out.println(loc21.toString() + "\t" + l.getFitness(loc21));
//		String[] l22s = {" ", "1", "0", "0"}; Location loc22 = new Location(l22s); System.out.println(loc22.toString() + "\t" + l.getFitness(loc22));
//		String[] l23s = {" ", "1", "0", "1"}; Location loc23 = new Location(l23s); System.out.println(loc23.toString() + "\t" + l.getFitness(loc23));
//		String[] l24s = {" ", "1", "1", " "}; Location loc24 = new Location(l24s); System.out.println(loc24.toString() + "\t" + l.getFitness(loc24));
//		String[] l25s = {" ", "1", "1", "0"}; Location loc25 = new Location(l25s); System.out.println(loc25.toString() + "\t" + l.getFitness(loc25));
//		String[] l26s = {" ", "1", "1", "1"}; Location loc26 = new Location(l26s); System.out.println(loc26.toString() + "\t" + l.getFitness(loc26));
//		String[] l27s = {"0", " ", " ", " "}; Location loc27 = new Location(l27s); System.out.println(loc27.toString() + "\t" + l.getFitness(loc27));
//		String[] l28s = {"0", " ", " ", "0"}; Location loc28 = new Location(l28s); System.out.println(loc28.toString() + "\t" + l.getFitness(loc28));
//		String[] l29s = {"0", " ", " ", "1"}; Location loc29 = new Location(l29s); System.out.println(loc29.toString() + "\t" + l.getFitness(loc29));
//		String[] l30s = {"0", " ", "0", " "}; Location loc30 = new Location(l30s); System.out.println(loc30.toString() + "\t" + l.getFitness(loc30));
//		String[] l31s = {"0", " ", "0", "0"}; Location loc31 = new Location(l31s); System.out.println(loc31.toString() + "\t" + l.getFitness(loc31));
//		String[] l32s = {"0", " ", "0", "1"}; Location loc32 = new Location(l32s); System.out.println(loc32.toString() + "\t" + l.getFitness(loc32));
//		String[] l33s = {"0", " ", "1", " "}; Location loc33 = new Location(l33s); System.out.println(loc33.toString() + "\t" + l.getFitness(loc33));
//		String[] l34s = {"0", " ", "1", "0"}; Location loc34 = new Location(l34s); System.out.println(loc34.toString() + "\t" + l.getFitness(loc34));
//		String[] l35s = {"0", " ", "1", "1"}; Location loc35 = new Location(l35s); System.out.println(loc35.toString() + "\t" + l.getFitness(loc35));
//		String[] l36s = {"0", "0", " ", " "}; Location loc36 = new Location(l36s); System.out.println(loc36.toString() + "\t" + l.getFitness(loc36));
//		String[] l37s = {"0", "0", " ", "0"}; Location loc37 = new Location(l37s); System.out.println(loc37.toString() + "\t" + l.getFitness(loc37));
//		String[] l38s = {"0", "0", " ", "1"}; Location loc38 = new Location(l38s); System.out.println(loc38.toString() + "\t" + l.getFitness(loc38));
//		String[] l39s = {"0", "0", "0", " "}; Location loc39 = new Location(l39s); System.out.println(loc39.toString() + "\t" + l.getFitness(loc39));
//		String[] l40s = {"0", "0", "0", "0"}; Location loc40 = new Location(l40s); System.out.println(loc40.toString() + "\t" + l.getFitness(loc40));
//		String[] l41s = {"0", "0", "0", "1"}; Location loc41 = new Location(l41s); System.out.println(loc41.toString() + "\t" + l.getFitness(loc41));
//		String[] l42s = {"0", "0", "1", " "}; Location loc42 = new Location(l42s); System.out.println(loc42.toString() + "\t" + l.getFitness(loc42));
//		String[] l43s = {"0", "0", "1", "0"}; Location loc43 = new Location(l43s); System.out.println(loc43.toString() + "\t" + l.getFitness(loc43));
//		String[] l44s = {"0", "0", "1", "1"}; Location loc44 = new Location(l44s); System.out.println(loc44.toString() + "\t" + l.getFitness(loc44));
//		String[] l45s = {"0", "1", " ", " "}; Location loc45 = new Location(l45s); System.out.println(loc45.toString() + "\t" + l.getFitness(loc45));
//		String[] l46s = {"0", "1", " ", "0"}; Location loc46 = new Location(l46s); System.out.println(loc46.toString() + "\t" + l.getFitness(loc46));
//		String[] l47s = {"0", "1", " ", "1"}; Location loc47 = new Location(l47s); System.out.println(loc47.toString() + "\t" + l.getFitness(loc47));
//		String[] l48s = {"0", "1", "0", " "}; Location loc48 = new Location(l48s); System.out.println(loc48.toString() + "\t" + l.getFitness(loc48));
//		String[] l49s = {"0", "1", "0", "0"}; Location loc49 = new Location(l49s); System.out.println(loc49.toString() + "\t" + l.getFitness(loc49));
//		String[] l50s = {"0", "1", "0", "1"}; Location loc50 = new Location(l50s); System.out.println(loc50.toString() + "\t" + l.getFitness(loc50));
//		String[] l51s = {"0", "1", "1", " "}; Location loc51 = new Location(l51s); System.out.println(loc51.toString() + "\t" + l.getFitness(loc51));
//		String[] l52s = {"0", "1", "1", "0"}; Location loc52 = new Location(l52s); System.out.println(loc52.toString() + "\t" + l.getFitness(loc52));
//		String[] l53s = {"0", "1", "1", "1"}; Location loc53 = new Location(l53s); System.out.println(loc53.toString() + "\t" + l.getFitness(loc53));
//		String[] l54s = {"1", " ", " ", " "}; Location loc54 = new Location(l54s); System.out.println(loc54.toString() + "\t" + l.getFitness(loc54));
//		String[] l55s = {"1", " ", " ", "0"}; Location loc55 = new Location(l55s); System.out.println(loc55.toString() + "\t" + l.getFitness(loc55));
//		String[] l56s = {"1", " ", " ", "1"}; Location loc56 = new Location(l56s); System.out.println(loc56.toString() + "\t" + l.getFitness(loc56));
//		String[] l57s = {"1", " ", "0", " "}; Location loc57 = new Location(l57s); System.out.println(loc57.toString() + "\t" + l.getFitness(loc57));
//		String[] l58s = {"1", " ", "0", "0"}; Location loc58 = new Location(l58s); System.out.println(loc58.toString() + "\t" + l.getFitness(loc58));
//		String[] l59s = {"1", " ", "0", "1"}; Location loc59 = new Location(l59s); System.out.println(loc59.toString() + "\t" + l.getFitness(loc59));
//		String[] l60s = {"1", " ", "1", " "}; Location loc60 = new Location(l60s); System.out.println(loc60.toString() + "\t" + l.getFitness(loc60));
//		String[] l61s = {"1", " ", "1", "0"}; Location loc61 = new Location(l61s); System.out.println(loc61.toString() + "\t" + l.getFitness(loc61));
//		String[] l62s = {"1", " ", "1", "1"}; Location loc62 = new Location(l62s); System.out.println(loc62.toString() + "\t" + l.getFitness(loc62));
//		String[] l63s = {"1", "0", " ", " "}; Location loc63 = new Location(l63s); System.out.println(loc63.toString() + "\t" + l.getFitness(loc63));
//		String[] l64s = {"1", "0", " ", "0"}; Location loc64 = new Location(l64s); System.out.println(loc64.toString() + "\t" + l.getFitness(loc64));
//		String[] l65s = {"1", "0", " ", "1"}; Location loc65 = new Location(l65s); System.out.println(loc65.toString() + "\t" + l.getFitness(loc65));
//		String[] l66s = {"1", "0", "0", " "}; Location loc66 = new Location(l66s); System.out.println(loc66.toString() + "\t" + l.getFitness(loc66));
//		String[] l67s = {"1", "0", "0", "0"}; Location loc67 = new Location(l67s); System.out.println(loc67.toString() + "\t" + l.getFitness(loc67));
//		String[] l68s = {"1", "0", "0", "1"}; Location loc68 = new Location(l68s); System.out.println(loc68.toString() + "\t" + l.getFitness(loc68));
//		String[] l69s = {"1", "0", "1", " "}; Location loc69 = new Location(l69s); System.out.println(loc69.toString() + "\t" + l.getFitness(loc69));
//		String[] l70s = {"1", "0", "1", "0"}; Location loc70 = new Location(l70s); System.out.println(loc70.toString() + "\t" + l.getFitness(loc70));
//		String[] l71s = {"1", "0", "1", "1"}; Location loc71 = new Location(l71s); System.out.println(loc71.toString() + "\t" + l.getFitness(loc71));
//		String[] l72s = {"1", "1", " ", " "}; Location loc72 = new Location(l72s); System.out.println(loc72.toString() + "\t" + l.getFitness(loc72));
//		String[] l73s = {"1", "1", " ", "0"}; Location loc73 = new Location(l73s); System.out.println(loc73.toString() + "\t" + l.getFitness(loc73));
//		String[] l74s = {"1", "1", " ", "1"}; Location loc74 = new Location(l74s); System.out.println(loc74.toString() + "\t" + l.getFitness(loc74));
//		String[] l75s = {"1", "1", "0", " "}; Location loc75 = new Location(l75s); System.out.println(loc75.toString() + "\t" + l.getFitness(loc75));
//		String[] l76s = {"1", "1", "0", "0"}; Location loc76 = new Location(l76s); System.out.println(loc76.toString() + "\t" + l.getFitness(loc76));
//		String[] l77s = {"1", "1", "0", "1"}; Location loc77 = new Location(l77s); System.out.println(loc77.toString() + "\t" + l.getFitness(loc77));
//		String[] l78s = {"1", "1", "1", " "}; Location loc78 = new Location(l78s); System.out.println(loc78.toString() + "\t" + l.getFitness(loc78));
//		String[] l79s = {"1", "1", "1", "0"}; Location loc79 = new Location(l79s); System.out.println(loc79.toString() + "\t" + l.getFitness(loc79));
//		String[] l80s = {"1", "1", "1", "1"}; Location loc80 = new Location(l80s); System.out.println(loc80.toString() + "\t" + l.getFitness(loc80));
		
		
		
		/*********
//		double[] biases_random = {0d, 0.2d, 0.4d, 0.6d, 0.8d, 1.0d};
//		for (double bias : biases_random) {
//			l.setPerceivedFitnessContributions_random(bias);
//			l.printLandscapePerceivedFitness();
//		}
		*/
		
		
//		double[] biases_overestimate = {0d, 0.2d, 0.4d, 0.8d, 1.6d, 3.2d};
//		for (double bias : biases_overestimate) {
//			l.setPerceivedFitnessContributions_overestimate(bias);
//			l.printLandscapePerceivedFitness();
//		}
		
//		l.setPerceivedFitnessContributions(0d);
//		l.setPerceivedFitnessContributions(bias);

		//		System.out.println("perceived fitness contributions");
//		l.printPerceivedFitnessContributions();
//		l.printLandscapeFitness();

//		l.printLandscapePerceivedFitness();

		//		System.out.println("n1"); 
//		System.out.println(l.getFitness(loc));
//		l.shock(0.2d);
//		l.printLandscapeFitness();
//		System.out.println("cases:\t" + l.getNumCases());
//		System.out.println("max fitnes:\t" + l.getMaxFitness());
//		l.printFitnessContributions();
	}

}
