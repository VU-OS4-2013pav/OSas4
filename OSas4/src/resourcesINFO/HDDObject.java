package resourcesINFO;

import java.util.ArrayList;
import java.util.List;

public class HDDObject {
	public final static int HDD_SIZE = 10;
	public List<Integer> hdd;
	public List<ProgramosInfoHDD> programs;
	
	public HDDObject() {
		hdd = new ArrayList<Integer>();
		
		for (int i=0; i<HDD_SIZE; i++) {
			hdd.add(0);
		}
		programs = new ArrayList<ProgramosInfoHDD>();
		
	}
}
