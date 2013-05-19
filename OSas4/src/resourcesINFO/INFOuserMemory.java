package resourcesINFO;

import java.util.ArrayList;
import java.util.List;

public class INFOuserMemory extends INFO {
	final static int UOA_SIZE = 128;
	public int laisviBlokai = 0;
	
	public INFOuserMemory() {
		o = new ArrayList<Boolean>();
		
		for (int i=0; i<UOA_SIZE; i++) {
			((ArrayList<Boolean>)o).add(false);
			laisviBlokai++;
		}
	}
	
		

}
