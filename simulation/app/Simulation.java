package app;

import java.util.Collections;
import java.util.Vector;
import java.io.PrintWriter;

import obj.Location;
import obj.Organization;
import obj.OrgAgile;
import obj.OrgWaterfall;
import obj.OrgNK;
import obj.Location;

import util.Globals;
import util.Debug;
import util.StatCalc;
import util.PerceivedFitnessComparator;


public class Simulation {
	private static Vector<Organization> organizations; //= new Vector<Organization>();

	public static void main(String args[]) {
		
		// load parameters
//		if (args.length == 1) {
//			String configFile = setConfigFile(args);
//			Globals.loadGlobals(configFile); 
//		} else {
			try {
				Globals.loadGlobalsFromCLI(args);
			} catch (Exception e) {
				// no need for anything
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
//		}
		
//		String configFile = setConfigFile(args);
//		Globals.loadGlobals(configFile);

		
		// output result file header
		if (Globals.reportLevel.equals("details")) { detailsHeader(); neighborsHeader(); } 
		else if (Globals.reportLevel.equals("summary")) { summaryHeader(); } 


		// iterate Runs
		for (int j = Globals.startLandscapeID; j < Globals.numRuns; j++) {
			// set random number seed
			Globals.setRandomNumbers(j);
			// create landscape
			Globals.createLandscape(j);
			
			Debug.println("Simulation.main: landscape created with landscapeID:  " + j); 
			
			organizations = new Vector<Organization>();
			// create numOrgs organizations
			
			for (int i = 0; i < Globals.numOrgs; i++) {
				if (Globals.methodology.equals("waterfall")) { organizations.add(new OrgWaterfall(i));
				} else if (Globals.methodology.equals("agile")) { organizations.add(new OrgAgile(i));
				} else if (Globals.methodology.equals("NK")) { organizations.add(new OrgNK(i)); 
				} else { // should not happen if config file is properly specified 
					System.err.println("Unknown orgType error:\t" + Globals.methodology);
					System.exit(0);
				}
			}
			
			Debug.println("Simulation.main: orgs created for landscape " + j); 
			
			report(-1);
			
			// run
			if (Globals.periods == -1) { runUntilEnd(); } else { run(); }

			// reset landscape after each run 
			Globals.landscape = null; 
		} // end iterate Runs
		
		if (Globals.reportLevel.equals("details")) { Globals.neighborsOut.close(); Globals.detailsOut.close(); } 
		else if (Globals.reportLevel.equals("summary")) { Globals.summaryOut.close(); }
	}


	
	
	private static String setConfigFile(String[] args) {
		String retString = "";
		if (args.length > 1) {
			System.err.println("Need at most one argument (config file).  Try again.");
			System.exit(0);
		} else if (args.length == 0) {
			retString = "";
			
		} else {
			retString = args[0];
		}
		return retString;
	}

	/**
	 * Methods to control simulation 
	 */

	private static void run() {
		for (int t = 0; t < Globals.periods + 1; t++) {
			Debug.println("Simulation.run:\tperiod:\t" + t); 
			
			if (Globals.changeOnce) {  if (Globals.changeFrequency == t) { Globals.shockLandscape(); } }
			if (Globals.changeRecurring) { if (t % Globals.changeFrequency == 0 && t != 0) { Globals.shockLandscape(); } }
			
			report(t);
			for (Organization org : organizations) { org.run(); }
			
//			report(t);
		}
	}

	private static void runUntilEnd() {
		int t = 0; 
		while (!allEnded()) {
			Debug.println("Simulation.runUntilEnd:\tperiod:\t" + t); 
			
			if (Globals.changeOnce) { if (Globals.changeFrequency == t) { Globals.shockLandscape(); } }
			if (Globals.changeRecurring) { if (t % Globals.changeFrequency == 0 && t != 0) { Globals.shockLandscape(); } }

			for (Organization org : organizations) { org.run(); }
			
			report(t);
			t++;
		}
	}

	private static boolean allEnded() {
		boolean retBool = true;
		for (Organization org : organizations) {
			if (!org.isCompleted()) {
				retBool = false; 
				break;
			}
		}
		return retBool;
	}

	/**
	 * PRINTERS
	 */
	
	private static void report(int t) {
		if (Globals.reportLevel.equals("details")) {  detailsReport(t); } 
		else if (Globals.reportLevel.equals("summary")) { summaryReport(t); }
	}
	
	private static void detailsHeader() {
		// match with report details data (Organization.printDetails)
		Globals.detailsOut.println("LandscapeID\tPeriod\tOrgID\tMethodology\tSearch\tScopeSize\tCompletedPhaseOne\tCompleted\tNumConsideringNeighbors\tLocation\tBias\tActualFitness\tPerceivedFitness");
	}
	
	private static void neighborsHeader() {
		// match with report details data (Organization.printDetails)
		Globals.neighborsOut.println("LandscapeID\tPeriod\tOrgID\tNeighborType\tLocation\tActualFitness\tPerceivedFitness");
	}
	
	private static void detailsReport(int period) {
		for (int i = 0; i < organizations.size(); i++) {
			Organization org = (Organization)organizations.get(i);
			Globals.detailsOut.println(Globals.landscape.getLandscapeID() + "\t" + period + "\t" + org.toString());
			Globals.neighborsOut.print(org.neighborsToString(period));
			if (org.isCompleted()) {
				organizations.remove(i--); // remove org and reset index to reflect deletion
			}
		}
	}

	private static void summaryHeader() {
		// match with report summary data
		Globals.summaryOut.println("LandscapeID\tPeriod\tNumCompleted\tNumCompletedPhaseOne\tAvgActual\tStdActual\tMinActual\tQ1Actual\tQ2Actual\tQ3Actual\tMaxActual\tAvgPerceived\tStdPerceived\tMinPerceived\tQ1Perceived\tMedianPerceived\tQ3Perceived\tMaxPerceived\tAvgScope\tStdScope\tMinScope\tQ1Scope\tMedianScope\tQ3Scope\tMaxScope");
	}
	
	private static void summaryReport(int period) { // need to report avg scope size
		// calc average and report average for landscape
		StatCalc actual = new StatCalc();
		StatCalc perceived = new StatCalc();
		StatCalc scope = new StatCalc();
		int completed = 0;
		int completedPhaseOne = 0;
		for(Organization org : organizations) {
			actual.enter(org.getOrgFitness());
			perceived.enter(org.getOrgPerceivedFitness());
			scope.enter(org.getScopeSize());
			if (org.isPhaseOneCompleted()) { completedPhaseOne++; }
			if (org.isCompleted()) { completed++; }
		}
		Globals.summaryOut.println(Globals.landscape.getLandscapeID() + "\t" + period + "\t" + completed + "\t" + completedPhaseOne + "\t"
			+ actual.getMean() + "\t" + actual.getStandardDeviation() + "\t" + actual.getMin() + "\t" + actual.getQuartile(1) + "\t" + actual.getQuartile(2)+ "\t"  + actual.getQuartile(3) + "\t" + actual.getMax() + "\t"    
			+ perceived.getMean() + "\t" + perceived.getStandardDeviation() + "\t" + perceived.getMin() + "\t" + perceived.getQuartile(1) + "\t" + perceived.getQuartile(2)+ "\t"  + perceived.getQuartile(3) + "\t" + perceived.getMax() + "\t"
			+ scope.getMean() + "\t" + scope.getStandardDeviation() + "\t" + scope.getMin() + "\t" + scope.getQuartile(1) + "\t" + scope.getQuartile(2)+ "\t"  + scope.getQuartile(3) + "\t" + scope.getMax()
			);
	}

}
