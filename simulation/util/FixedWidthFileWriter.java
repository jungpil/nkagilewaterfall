package util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class FixedWidthFileWriter extends PrintWriter {
	private static int LENGTH = 12;
	
	public FixedWidthFileWriter(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}
	
	public void println(double x) {
		String s = (new BigDecimal(Double.toString(x))).toPlainString();
		int strlen = s.length();
		if (strlen < (LENGTH + 3)) {
//			System.out.println(s);
			for (int j = 0; j < LENGTH + 2 - strlen; j++) { s += "0"; } // append 0s to end of strings shorter than length
		}
		s = s.substring(2, LENGTH+2);
		super.println(s);
	}
	

}
