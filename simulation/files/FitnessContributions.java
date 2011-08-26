package files;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileNotFoundException;

public class FitnessContributions extends RandomAccessFile {
	private static int CHARS_PER_LINE = 13; // 12 (0.00000000) + 1 (\n; new line) = FixedWidthFileWriter.LENGTH + 1
	private static String DIRECTORY = "fitnesscontribs";

	public FitnessContributions(int n, long lndscpID, String theBiasType, double theBias) throws FileNotFoundException {
			super(new File(DIRECTORY + "/" + n + "/" + lndscpID + "_" + theBiasType + "_" + theBias), "r");
	}
	
	public double getFitnessContribution(int lineNo) {
		double retVal = 0.0d;
		try {
	        seek((long)(lineNo * CHARS_PER_LINE));
	        retVal = Double.parseDouble("0." + readLine());
		} catch (IOException e) {
			System.out.println("IOError files.FitnessContributions.getFitnessContribution(lineNo):");
			e.printStackTrace();
			System.exit(1);
		}
		return retVal;
	}
	
	/** FOR TESTING ONLY */
	public static void main(String args[]) {
		try {
			FitnessContributions f = new FitnessContributions(16, 0, "random", 0.0d);
			
			System.out.println("52999:\t" +f.getFitnessContribution(52999));
			System.out.println("25459:\t" +f.getFitnessContribution(25459));
			System.out.println("49056:\t" +f.getFitnessContribution(49056));
			System.out.println("40544:\t" +f.getFitnessContribution(40544));
			System.out.println("34538:\t" +f.getFitnessContribution(34538));
			System.out.println("12039:\t" +f.getFitnessContribution(12039));
			System.out.println("39802:\t" +f.getFitnessContribution(39802));
			System.out.println("6317:\t" +f.getFitnessContribution(6317));
			System.out.println("35208:\t" +f.getFitnessContribution(35208));
			System.out.println("22408:\t" +f.getFitnessContribution(22408));
			System.out.println("47063:\t" +f.getFitnessContribution(47063));
			System.out.println("4801:\t" +f.getFitnessContribution(4801));
			
		} catch (Exception e) {
			System.out.println("Error in main():");
			e.printStackTrace();
		}
	}
}
