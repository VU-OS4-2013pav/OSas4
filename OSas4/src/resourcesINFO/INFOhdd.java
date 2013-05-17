package resourcesINFO;

import java.util.ArrayList;
import java.util.List;

public class INFOhdd extends INFO {
	final static int HDD_SIZE = 10;
	List<Integer> hdd;
	List<ProgramosInfoHDD> programs;
	
	public INFOhdd() {
		hdd = new ArrayList<Integer>();
		
		for (int i=0; i<HDD_SIZE; i++) {
			hdd.add(0);
		}
		programs = new ArrayList<ProgramosInfoHDD>();
		
	}
	

}
