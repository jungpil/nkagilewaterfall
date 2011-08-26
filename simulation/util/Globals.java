package util;

import obj.InfluenceMatrix;
import obj.Landscape;

import util.MersenneTwisterFast;

import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import app.Test;

import com.martiansoftware.jsap.*;

public class Globals {
	/**
	 * simulation parameters: default values
	 */
	public static int N = 16; // 16
	public static int periods = -1;
	private static String outfilename = "testing.out";
	private static String influenceMatrix = "inf/n16k15.txt";
	public static int numOrgs = 1;
	public static String methodology = "waterfall"; // waterfall || incremental
	public static int increment = 8;
	public static String search = "cognitive";  // cognitive || experiential
	public static boolean integrationNew = true; 
	public static boolean adaptNew = false; // all || new

	// dynamism and turbulence
	public static boolean changeOnce = false;
	public static boolean changeRecurring = false;
	public static int changeFrequency = 0;
	public static double stability = 1.0d;
	public static double bias = 1.0d;
	public static String biasType = "random";

	// scope plan and biases
	public static int numInterations;
	public static int[] scopes;
	public static double[] biases; 
	
	public static Landscape landscape;
	public static String reportLevel = "details"; // summary || details
	public static boolean debug = false; 
	public static boolean replicate = true; 
	public static double landscapeMax;
	public static int numRuns = 3;
	public static int startLandscapeID = 0;

	/**
	 * utils
	 */
	public static long runID = System.currentTimeMillis(); // need?
	public static PrintWriter summaryOut;
	public static PrintWriter detailsOut; 
	public static PrintWriter neighborsOut;
	public static MersenneTwisterFast rand = new MersenneTwisterFast(runID);
	
	public static void loadGlobalsFromCLI(String[] args) throws Exception {
		Debug.println("loading parms from command line");
//		JSAP jsap = new JSAP();
		JSAP jsap = new JSAP(Test.class.getResource("config.xml"));

		JSAPResult config = jsap.parse(args);
		// check help first
		if (config.getBoolean("help")) {
            System.err.println("Usage: java " + Test.class.getName());
            System.err.println("                " + jsap.getUsage() + "\n");
            System.err.println(jsap.getHelp(160));
            System.exit(1);
        }

		if (!((new ArrayList<String>(Arrays.asList(new String[]{"waterfall", "agile", "NK"}))).contains(config.getString("methodology")))) { 
			config.addException("methodology", new Exception("Option -T (--org-type) must be either \"waterfall\" or \"agile\" or \"NK\"")); System.out.println("exception created for methodology");}
		
		if (!new ArrayList<String>(Arrays.asList(new String[]{"cognitive", "experiential"})).contains(config.getString("search"))) { 
			config.addException("search", new Exception("Option -S (--search) must be either \"cognitive\" or \"experiential\"")); }

		if (!new ArrayList<String>(Arrays.asList(new String[]{"random", "overestimate"})).contains(config.getString("biasType"))) { 
			config.addException("biasType", new Exception("Option -B (--bias-type) must be either \"random\" or \"overestimate\"")); }
		
		if (!new ArrayList<String>(Arrays.asList(new String[]{"none", "once", "recurring"})).contains(config.getString("changeType"))) { 
			config.addException("changeType", new Exception("Option -c (--change-type) must be either \"none\", \"once\" or \"recurring\"")); }

		if (!new ArrayList<String>(Arrays.asList(new String[]{"summary", "details"})).contains(config.getString("reportLevel"))) { 
			config.addException("reportLevel", new Exception("Option -L (--report-level) must be either \"summary\" or \"details\"")); }

		if (!config.success()) { configErrorMsg(config, jsap); }
		
		N = config.getInt("N");
		periods = config.getInt("periods");
		influenceMatrix = "inf/" + config.getString("influenceMatrix") + ".txt";
		numOrgs = config.getInt("numOrgs");
		numRuns = config.getInt("numRuns");
		increment = config.getInt("increment");
		startLandscapeID = config.getInt("startLandscapeID");
		methodology = config.getString("methodology");
		search = config.getString("search");
		integrationNew = config.getBoolean("integrationNew");
		adaptNew = config.getBoolean("adaptNew");
		bias = config.getDouble("bias");
		biasType = config.getString("biasType");
		changeFrequency = config.getInt("changeFrequency");
		stability = config.getDouble("stability");
		reportLevel = config.getString("reportLevel");
		debug = config.getBoolean("debug"); if (debug) { replicate = true; }
		String pathTo = config.getString("resultsPath"); if (!pathTo.equals("")) { pathTo += "/"; }

		if (config.getString("changeType").equals("recurring")) { changeRecurring = true; } 
		else if (config.getString("changeType").equals("once")) { changeOnce = true; } 
		else { changeFrequency = 0; stability = 1.0d; }

		if (changeRecurring || changeOnce) {
			if (changeFrequency <= 0) { config.addException("changeFrequency", new Exception("Option -f (--change-frequency) must be a positive integer when -c (--change-type) is either \"once\" or \"recurring\"")); }
			if (stability == 0.0d) { config.addException("stability", new Exception("Error: Option -s (--stability) must not be 1.0d when -c (--change-type) is either \"once\" or \"recurring\"")); }
		}

		// Derive numIteration steps
		if (methodology.equals("waterfall")) {
			numInterations = 3;
		} else if (methodology.equals("NK")) {
			// TODO CHECK LOGIC IF NEEDED
			numInterations = 2;
		} else if (methodology.equals("agile")) {
			numInterations = (N % increment == 0) ? (N / increment) : (N / increment + 1);
		}

		/* set scopes[] and biases[] */
		// set planned iteration sequence based on increment value
		scopes = new int[numInterations]; 
		scopes[0] = increment;
		for (int i = 1; i < numInterations - 1; i++) { scopes[i] += scopes[i-1] + increment; }
		scopes[numInterations - 1] = Globals.N;

		// set biases iterations
		biases = new double[numInterations];
		if (methodology.equals("waterfall")) { 
			biases[0] = bias; biases[1] = bias; biases[2] = 0.0d;
		} else if (methodology.equals("NK")) {
			biases[0] = bias; biases[1] = 0.0d;
		} else if (methodology.equals("agile")) {
			for (int i = 0; i < numInterations; i++) { biases[i] = bias * ((double)(N - scopes[i]) / (double)(N - increment)); }
		}
		
		// adjust for waterfall
		if (methodology.equals("waterfall")) {
			if (increment != N / 2) { System.err.println("Config: increment is not N/2; resetting to " + N / 2); increment = N / 2; }
			if (!integrationNew) { System.err.println("Config: integration is not \"new\"; resetting to new"); integrationNew = true; }
			if (!adaptNew) { System.err.println("Config: adaptation is not \"new\"; resetting to new"); adaptNew = true; }
		}
		
		// derive outfilename base
		outfilename = ""; //agile_cognitive_n16k15_4_random_0.2_new_all_none_0_1.0.out : methodology_search_infmatrix_increment_biastype_bias_integrate_adapt_changetype_changefrequency_stability
		outfilename += methodology + "_" + search + "_" + config.getString("influenceMatrix")  + "_" + increment + "_" + biasType + "_" + bias + "_";
		if (integrationNew) { outfilename += "new_"; } else { outfilename += "all_"; }
		if (adaptNew) { outfilename += "new_"; } else { outfilename += "all_"; }
		if (changeOnce) { outfilename += "once_"; }
		if (changeRecurring) { outfilename += "recurring_"; }
		if (!(changeOnce || changeRecurring)) { outfilename += "none_"; }
		outfilename += changeFrequency + "_" + stability;
		if (startLandscapeID != 0) {
			outfilename += "_" + startLandscapeID;
		}

		// create output printwriter
		try {
			if (reportLevel.equals("summary")) {
				summaryOut = new PrintWriter(new FileOutputStream(pathTo + outfilename + ".summary", true), true);
			} else { // "details"
				detailsOut = new PrintWriter(new FileOutputStream(pathTo + outfilename + ".details", true), true);
				neighborsOut = new PrintWriter(new FileOutputStream(pathTo + outfilename + ".neighbors", true), true);
			}
		} catch (IOException io) {
			System.err.println("IO error: " + io.getMessage());
			io.printStackTrace();
		}
		
		Debug.setDebug(debug); 
	}

	private static void configErrorMsg(JSAPResult conf, JSAP aJsap) {
		System.err.println();
        for (java.util.Iterator errs = conf.getErrorMessageIterator(); errs.hasNext();) {
            System.err.println("Error: " + errs.next());
            System.err.println("Use --help (-h) for full syntax");
        }
        System.exit(1);
	}
		
	public static void setRandomNumbers(int intRunID) {
		long runID;
		if (replicate) { runID = (long)intRunID;
		} else { runID = System.currentTimeMillis(); }
		rand = new MersenneTwisterFast(runID);
	} 
	
	public static void createLandscape(int id) {
		landscape  = new Landscape(id, new InfluenceMatrix(influenceMatrix));
		landscapeMax = landscape.getMaxFitness();
	}
	
	public static void shockLandscape() {
		landscape.shock();
		landscapeMax = landscape.getMaxFitness();
	}

	public static void loadGlobals_deprecated(String configFile) {
		if (!configFile.equals("")) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(configFile));
				
				// simulation parameters
				N = Integer.parseInt(p.getProperty("N"));
				periods = Integer.parseInt(p.getProperty("periods"));
				outfilename = p.getProperty("outfile");
				influenceMatrix = p.getProperty("influenceMatrix");
				numOrgs = Integer.parseInt(p.getProperty("numOrgs"));
//				orgType = p.getProperty("orgType");
				methodology = p.getProperty("methodology");
				increment = Integer.parseInt(p.getProperty("increment"));
				search = p.getProperty("search");
				if (!search.equals("cognitive") && !search.equals("experiential")) {
					System.err.println("search must be either \"cognitive\" or \"experiential\""); System.exit(1); 
				} 
				String integration = p.getProperty("integration");
				if (integration.equals("new")) { integrationNew = true;
					} else if (integration.equals("all")) { integrationNew = false;
					} else { System.err.println("integration must be either \"new\" or \"all\""); System.exit(1); }
				String adapt = p.getProperty("adapt");
				if (adapt.equals("new")) { adaptNew = true;
					} else if (adapt.equals("all")) { adaptNew = false;
					} else { System.err.println("adapt must be either \"new\" or \"all\""); System.exit(1); }
				if(p.getProperty("changeType").equals("once")) { changeOnce = true; 
				} else if (p.getProperty("changeType").equals("recurring")) { changeRecurring = true; 
				} else if (p.getProperty("changeType").equals("none")) { 
				} else { System.err.println("changeType must be either \"none\", \"once\" or \"recurring\""); System.exit(0); }
				
				changeFrequency = Integer.parseInt(p.getProperty("changeFrequency"));
				stability = Double.parseDouble(p.getProperty("stability"));
				bias = Double.parseDouble(p.getProperty("bias"));
				numRuns = Integer.parseInt(p.getProperty("runs"));

				reportLevel = p.getProperty("reportLevel");
				String startLandscapeIDStr = p.getProperty("startLandscapeID");
				if (startLandscapeIDStr == null) { startLandscapeID = 0;
				} else { startLandscapeID = Integer.parseInt(startLandscapeIDStr); }

				String debugString = p.getProperty("debug"); 
				if (debugString.equals("true") || debugString.equals("1")) { debug = true; } 

				String replicateString = p.getProperty("replicate"); 
				if (replicateString.equals("true") || replicateString.equals("1")) { replicate = true; } 
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		} // else nothing -- defaults are used
		Debug.setDebug(debug); 
		try {
			// create output printwriter
			if (reportLevel.equals("summary")) {
				summaryOut = new PrintWriter(new FileOutputStream(outfilename + ".summary", true), true);
			} else { // "details"
				detailsOut = new PrintWriter(new FileOutputStream(outfilename + ".details", true), true);
				neighborsOut = new PrintWriter(new FileOutputStream(outfilename + ".neighbors", true), true);
			}
		} catch (IOException io) {
			System.err.println("IO error: " + io.getMessage());
			io.printStackTrace();
		}
	}
	

	private void junk() { // from loadGlobalsFromCLI
		/** N : 16 */
//		jsap.registerParameter(new FlaggedOption("N")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setDefault("16") 
//			.setRequired(true) 
//			.setShortFlag('N') 
//			.setLongFlag(JSAP.NO_LONGFLAG)
//			.setHelp("The number of elements (N) in the NK model."));
		
		/** periods : -1; */
//		jsap.registerParameter(new FlaggedOption("periods")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setDefault("-1")
//			.setRequired(true)
//			.setShortFlag('t')
//			.setLongFlag("periods")
//			.setHelp("The number of periods to run.  Default is -1 (= until convergence)."));

		/** outfilename : NO DEFAULT; MUST SPECIFY */
//		jsap.registerParameter(new FlaggedOption("outfilename")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setRequired(true)
//			.setShortFlag('F')
//			.setLongFlag("out")
//			.setHelp("The output filename to store the results."));

		/** influenceMatrixFile : inf/n16k12.txt */
//		jsap.registerParameter(new FlaggedOption("influenceMatrixFile")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setDefault("n16k12")
//			.setRequired(true)
//			.setShortFlag('I')
//			.setLongFlag("infmatrix")
//			.setHelp("The influence matrix file to use to create the landscapes.  The infmatrix file (with .txt extension must be in the inf directory."));
		
		/** numRuns */
//		jsap.registerParameter(new FlaggedOption("numRuns")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setDefault("200")
//			.setRequired(true)
//			.setShortFlag('r')
//			.setLongFlag("num-runs")
//			.setHelp("The number of landscapes (runs) to simulate."));
		
		/** numOrgs */
//		jsap.registerParameter(new FlaggedOption("numOrgs")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setDefault("100")
//			.setRequired(true)
//			.setShortFlag('n')
//			.setLongFlag("numorgs")
//			.setHelp("The number of organizations to simulate per landscape."));

		/** methodology */
//		jsap.registerParameter(new FlaggedOption("methodology")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setDefault("agile")
//			.setRequired(true)
//			.setShortFlag('T')
//			.setLongFlag("org-type")
//			.setHelp("The organization type (\"waterfall\" vs. \"agile\")."));
		
		/** increment */
//		jsap.registerParameter(new FlaggedOption("increment")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setRequired(true)
//			.setShortFlag('x')
//			.setLongFlag("increment")
//			.setHelp("The size of incremental scope. (Irrelevant for waterfall types = 8)"));
		
		/** search */
//		jsap.registerParameter(new FlaggedOption("search")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setDefault("cognitive")
//			.setRequired(true)
//			.setShortFlag('S')
//			.setLongFlag("search")
//			.setHelp("The search approach (\"cognitive\" vs. \"experiential\")."));
		
		/** integrationNew */
//		jsap.registerParameter(new Switch("integrationNew")
//	        .setShortFlag(JSAP.NO_SHORTFLAG)
//	        .setLongFlag("integration-new"));
		
		/** adaptNew // all || new */
//		jsap.registerParameter(new Switch("adaptNew")
//			.setShortFlag(JSAP.NO_SHORTFLAG)
//			.setLongFlag("adapt-new"));

		/** biasType : _random_ || overestimate */
//		jsap.registerParameter(new FlaggedOption("biasType")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setDefault("random")
//			.setRequired(true)
//			.setShortFlag('B')
//			.setLongFlag("bias-type")
//			.setHelp("The bias generating function (\"random\" or \"overestimate\")."));

		/** bias */
//		jsap.registerParameter(new FlaggedOption("bias")
//			.setStringParser(JSAP.DOUBLE_PARSER)
//			.setDefault("0.0")
//			.setRequired(true)
//			.setShortFlag('b')
//			.setLongFlag("bias")
//			.setHelp("The amount of initial bias in fitness misperception. "));
		
		/** changeType : _none_ || once || recurring */
//		jsap.registerParameter(new FlaggedOption("changeType")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setDefault("none")
//			.setRequired(true)
//			.setShortFlag('c')
//			.setLongFlag("change-type")
//			.setHelp("The nature of environmental turbulence (\"none\", \"once\" or \"recurring\").  If \"recurring\" is used -f (--change-frequency) and -s (--stability) must also be set"));
		
		/** changeFrequency : 0 */
//		jsap.registerParameter(new FlaggedOption("changeFrequency")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setDefault("0")
//			.setRequired(true)
//			.setShortFlag('f')
//			.setLongFlag("change-frequency")
//			.setHelp("The frequency (in number of periods) of environmental turbulence. "));
		
		/** stability : 1.0 */
//		jsap.registerParameter(new FlaggedOption("stability")
//			.setStringParser(JSAP.DOUBLE_PARSER)
//			.setDefault("1.0")
//			.setRequired(true)
//			.setShortFlag('s')
//			.setLongFlag("stability")
//			.setHelp("The (inverse) amount of environmental turbulence (1.0 is completely stable; 0.0 is completely random)"));
		
		/** reportLevel : _summary_ || details */
//		jsap.registerParameter(new FlaggedOption("reportLevel")
//			.setStringParser(JSAP.STRING_PARSER)
//			.setDefault("summary")
//			.setRequired(true)
//			.setShortFlag('L')
//			.setLongFlag("report-level")
//			.setHelp("Level of analysis for reporting (\"summary\" or \"details\")."));

		/** startLandscapeID : 0 */
//		jsap.registerParameter(new FlaggedOption("startLandscapeID")
//			.setStringParser(JSAP.INTEGER_PARSER)
//			.setDefault("0")
//			.setRequired(false)
//			.setShortFlag('l')
//			.setLongFlag("start-landscape-id")
//			.setHelp("The frequency (in number of periods) of environmental turbulence. "));

		/** debug : false */
//		jsap.registerParameter(new Switch("debug")
//			.setShortFlag('v')
//			.setLongFlag("verbose"));

		
	}
}
