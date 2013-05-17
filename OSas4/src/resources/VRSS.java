package resources;

import java.util.ArrayList;
import java.util.List;

public class VRSS {
	public static List<VRS> list = new ArrayList<VRS>();
	
	public static final int MOS_darbo_pabaiga = 0;
	public static final int Klaviaturos_pertraukimas = 1;
	public static final int Sintakses_tikrinimas = 2;
	public static final int Sintakse_patikrinta = 3;
	public static final int VM_nori_ivedimo = 4;
	public static final int Pranesimas_apie_pertraukima = 5;
	public static final int InputStream_pabaiga = 6;
	public static final int Loader_pabaiga = 7;
	public static final int Destroyer_XDD_pabaiga = 8;
	public static final int Destroyer_XDD_pradzia = 9;
	public static final int Loader_pradzia = 10;
	public static final int Info_apie_nauja_VM = 11;
	public static final int MainGovernor_pazadinimas = 12;
	public static final int Pertraukimo_ivykis = 13;
	public static final int Writer_pradzia = 14;
	public static final int Swapper_pradzia = 15;
	
	
	public static void initialiseVRSS() {
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
