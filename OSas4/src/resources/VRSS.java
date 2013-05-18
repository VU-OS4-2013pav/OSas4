package resources;

import java.util.ArrayList;
import java.util.List;

public class VRSS {
	public static List<VRS> list = new ArrayList<VRS>();
	
	public static void initialise() {
		VRSS.list.add(new VRS());
		VRSS.list.get(0).vardas = "MOS darbo pabaiga";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(1).vardas = "Klaviaturos pertraukimas";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(2).vardas = "Sintakses tikrinimas";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(3).vardas = "Sintakse patikrinta";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(4).vardas = "VM nori ivedimo";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(5).vardas = "Pranesimas apie pertraukima";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(6).vardas = "InputStream pabaiga";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(7).vardas = "Loader pabaiga";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(8).vardas = "Destroyer XDD pabaiga";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(9).vardas = "Destroyer XDD pradzia";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(10).vardas = "Loader pradzia";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(11).vardas = "Info apie nauja VM";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(12).vardas = "MainGovernor pazadinimas";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(13).vardas = "Pertraukimo ivykis";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(14).vardas = "Writer pradzia";
		
		VRSS.list.add(new VRS());
		VRSS.list.get(15).vardas = "Swapper  pradzia";
		
	}
	
	public static void print(int i) {
		for (int j=0; j<list.get(i).resourceList.size(); j++) {
			System.out.println(list.get(i).resourceList.get(j).nameO+"||| id: "+list.get(i).resourceList.get(j).nameI);
		}
	}
}
