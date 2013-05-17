package resourcesINFO;

import java.util.ArrayList;
import java.util.List;

public class INFOuserMemory extends INFO {
	final static int UOA_SIZE = 128;
	List<Boolean> memory;
	
	public INFOuserMemory() {
		memory = new ArrayList<Boolean>();
		
		for (int i=0; i<UOA_SIZE; i++) {
			memory.add(false);
		}
	}

}
