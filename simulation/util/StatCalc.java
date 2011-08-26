package util;

import java.util.Arrays;

/* 
An object of class StatCalc can be used to compute several simple statistics
for a set of numbers.  Numbers are entered into the dataset using
the enter(double) method.  Methods are provided to return the following
statistics for the set of numbers that have been entered: The number
of items, the sum of the items, the average, the standard deviation,
the maximum, and the minimum.
*/

public class StatCalc {

	private int count;   // Number of numbers that have been entered.
	private double sum;  // The sum of all the items that have been entered.
	private double squareSum;  // The sum of the squares of all the items.
	private double max = Double.NEGATIVE_INFINITY;  // Largest item seen.
	private double min = Double.POSITIVE_INFINITY;  // Smallest item seen.
	private double[] data = new double[Globals.numOrgs];
	
	public void enter(double num) { // Add the number to the dataset.
		data[count] = num;
		sum += num;
		squareSum += num*num;
		if (num > max) { max = num; }
		if (num < min) { min = num; }
		count++;
	}
	
	public int getCount() { // Return number of items that have been entered.
		return count;
	}
	
	public double getSum() { // Return the sum of all the items that have been entered.
		return sum;
	}
	
	public double getMean() { // Return average of all the items that have been entered; Value is Double.NaN if count == 0.
		return sum / count;  
	}
	
	public double getStandardDeviation() { // Return standard deviation of all the items that have been entered; Value will be Double.NaN if count == 0.
		double mean = getMean();
	   return Math.sqrt(squareSum/count - mean*mean);
	}
	
	public double getMin() { // Return the smallest item that has been entered; Value will be infinity if no items have been entered.
		return min;
	}
	
	public double getMax() { // Return the largest item that has been entered; Value will be -infinity if no items have been entered.
	   return max;
	}
	
	public double getQuartile(int quart) {
		Arrays.sort(data);
		int position = (int)(((double)quart / 4d) * Globals.numOrgs);
		if (count != Globals.numOrgs) { System.err.println("StatCalc Warning: computing quartiles without full data"); } 
		return (data[position] + data[position + 1])/2;
	}
}  // end class StatCalc


