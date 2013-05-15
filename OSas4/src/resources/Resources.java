package resources;
import java.util.ArrayList;
import java.util.List;

import resourcesINFO.INFO;
import resourcesINFO.LPS;
import resourcesINFO.PASK;


public class Resources {
	// RESURSAI
	List<String> rid; 		// isoriniu vardu sarasas
	List<Boolean> pnr; 		// "ar daugkartinio panaudojimo" sarasas
	List<Integer> k; 		// tevo vidinis vardas
	List<LPS> lps; 			// resurso laukianciu procesu sarasas ir kiek jo reikia
	List<PASK> pask; 		// resurso paskirstytojo programos nuorodu sarasas
	List<INFO> info; 		// resursu informacijos lauku sarasas
	
	public Resources() {
		rid = new ArrayList<String>();
		pnr = new ArrayList<Boolean>();
		k = new ArrayList<Integer>();
		lps = new ArrayList<LPS>();
		pask = new ArrayList<PASK>();
		info = new ArrayList<INFO>();
		
	}

}
