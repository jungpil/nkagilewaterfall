package obj;

import java.util.Arrays;
import util.Globals;


public class Interdependence {
	int position;
	boolean[] dependencies;
	int k; // number of dependent policy choices
	
	public Interdependence(int idx, boolean[] deps) {
		position = idx;
		dependencies = new boolean[Globals.N];
		System.arraycopy(deps, 0, dependencies, 0, deps.length);
		int count = 0;
		for (int i = 0; i < dependencies.length; i++) {
			if (dependencies[i] == true) { count++; }
		}
		k = count - 1;
	}

	public boolean isDependent(int idx) {
		if (dependencies[idx] == true) { return true; } 
		else { return false; }
	}

	public int getPolicyPosition() {
		return position;
	}
	
	public String toString() {
		return position + ":\t" + Arrays.toString(dependencies);
	}
	
	public int getNumDependencies() {
		return k;
	}
	
	public static void main(String args[]) {
		boolean[] j = new boolean[5];
		j[0] = true;
		j[1] = false;
		j[2] = false;
		j[3] = true;
		j[4] = true;
		Interdependence i = new Interdependence(5, j);
		System.out.println(i.toString());
		for (int x = 0; x < 5; x++) {
			if (i.isDependent(x)) { System.out.println(x + ": true"); } else {System.out.println(x + ": false"); }
		}
	}
}
