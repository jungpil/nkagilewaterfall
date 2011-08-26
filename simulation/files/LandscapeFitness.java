package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import obj.Location;
import util.Globals;

public class LandscapeFitness extends RandomAccessFile {
	private static int CHARS_PER_LINE = 13; // 12 (0.00000000) + 1 (\n; new line) 
	private static String DIRECTORY = "landscapes";
	private double maxFitness;
	private long fileLength;

	public LandscapeFitness(int n, long lndscpID, String infmatrix, String theBiasType, double theBias) throws FileNotFoundException {
		//n16k12_0_random_0.0.txt
		super(new File(DIRECTORY + "/" + infmatrix + "_" + lndscpID + "_" + theBiasType + "_" + theBias + ".txt"), "r");
		File file = new File(DIRECTORY + "/" + infmatrix + "_" + lndscpID + "_" + theBiasType + "_" + theBias + ".txt");
		fileLength = file.length(); file = null;
		setMaxFitness();
	}
	
	public double getMaxFitness() {
		return maxFitness;
	}

	public double getFitness(Location loc) {
		int missing = loc.numUndefined();
		double retVal = 0d;
		if (missing > 0) {
			ArrayList<Location> list = getGeneralizedLocationsList(loc); 
			for (int i = 0; i < list.size(); i++) { retVal += readFitness((Location)list.get(i)); }
			retVal = (retVal / list.size()) * ((double)(Globals.N - missing) / Globals.N);
		} else {
			retVal = readFitness(loc);
		}
		return retVal; 
	}
	
	private void setMaxFitness() {
		double retVal = 0d;
		try {
			seek(fileLength - CHARS_PER_LINE);
			retVal = Double.parseDouble("0." + readLine());
		} catch (IOException e) {
			System.out.println("IOError files.LandscapeFitness.setMaxFitness():");
			e.printStackTrace();
			System.exit(1);
		}
		maxFitness = retVal;
	}
	
	private double readFitness(Location loc) {
		double retVal = 0d;
		try {
			int lineNo = loc.locationToInt();
	        seek((long)(lineNo * CHARS_PER_LINE));
	        retVal = Double.parseDouble("0." + readLine());
		} catch (Exception e) {
			System.out.println("Exception files.LandscapeFitness.readFitness(lineNo):");
			e.printStackTrace();
			System.exit(1);
		}
		return retVal;
	}
	
	private ArrayList<Location> getGeneralizedLocationsList(Location loc) {
		// since locations may contain missing elements (" "), we need to fill those spaces with 1s and 0s
		// i.e., if space exists, remove entry and replace with combinations of 0/1 replacing the space
		ArrayList<Location> list = new ArrayList<Location>(); 
		list.add(loc);
		
		boolean done = false;
		while (!done) {
			boolean found = false;
			for (int i = 0; i < list.size(); i++) {
				Location listItem = list.get(i);
				int idx = listItem.indexOfUndefined();
				if (idx > -1) { // found ' '
					found = true;
					Location withUndefined = list.remove(i);
					withUndefined.setLocationAt(idx, "0");
					list.add(new Location(withUndefined));
					withUndefined.setLocationAt(idx, "1");
					list.add(new Location(withUndefined));
					break;
				} 
			} 
			if (!found) { done = true; }
		}
		// now list should contain all combinations of matching policy choices (e.g., "  1" -> {"001", "011", "101", "111"})
		return list;
	}
	
	/** TESTING ONLY */
	public static void main(String args[]) {
		System.out.println(System.currentTimeMillis());
		try {
			LandscapeFitness f = new LandscapeFitness(16, 0, "n16k12", "random", 0.0d);
			Location full0 = new Location(new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full1 = new Location(new String[]{" ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full2 = new Location(new String[]{" ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full3 = new Location(new String[]{" ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full4 = new Location(new String[]{" ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full5 = new Location(new String[]{" ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full6 = new Location(new String[]{" ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full7 = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full8 = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0", "0"});
			Location full9 = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0", "0"});
			Location fulla = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0", "0"});
			Location fullb = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0", "0"});
			Location fullc = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0", "0"});
			Location fulld = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0", "0"});
			Location fulle = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0", "0"});
			Location fullf = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "0"});
			Location fullg = new Location(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "});
			System.out.println(System.currentTimeMillis());
			System.out.println(System.currentTimeMillis() + "\t" + full0.toString() + "\t" + f.getFitness(full0));
			System.out.println(System.currentTimeMillis() + "\t" + full1.toString() + "\t" + f.getFitness(full1));
			System.out.println(System.currentTimeMillis() + "\t" + full2.toString() + "\t" + f.getFitness(full2));
			System.out.println(System.currentTimeMillis() + "\t" + full3.toString() + "\t" + f.getFitness(full3));
			System.out.println(System.currentTimeMillis() + "\t" + full4.toString() + "\t" + f.getFitness(full4));
			System.out.println(System.currentTimeMillis() + "\t" + full5.toString() + "\t" + f.getFitness(full5));
			System.out.println(System.currentTimeMillis() + "\t" + full6.toString() + "\t" + f.getFitness(full6));
			System.out.println(System.currentTimeMillis() + "\t" + full7.toString() + "\t" + f.getFitness(full7));
			System.out.println(System.currentTimeMillis() + "\t" + full8.toString() + "\t" + f.getFitness(full8));
			System.out.println(System.currentTimeMillis() + "\t" + full9.toString() + "\t" + f.getFitness(full9));
			System.out.println(System.currentTimeMillis() + "\t" + fulla.toString() + "\t" + f.getFitness(fulla));
			System.out.println(System.currentTimeMillis() + "\t" + fullb.toString() + "\t" + f.getFitness(fullb));
			System.out.println(System.currentTimeMillis() + "\t" + fullc.toString() + "\t" + f.getFitness(fullc));
			System.out.println(System.currentTimeMillis() + "\t" + fulld.toString() + "\t" + f.getFitness(fulld));
			System.out.println(System.currentTimeMillis() + "\t" + fulle.toString() + "\t" + f.getFitness(fulle));
			System.out.println(System.currentTimeMillis() + "\t" + fullf.toString() + "\t" + f.getFitness(fullf));
			System.out.println(System.currentTimeMillis() + "\t" + fullg.toString() + "\t" + f.getFitness(fullg));
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			System.out.println("Error in main():");
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
	}
}
