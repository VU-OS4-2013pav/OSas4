package resources;

import java.util.ArrayList;
import java.util.List;

public class VienkartiniuResursuSarasuSarasas {
	public static List<VienkartiniuResursuSarasas> list = new ArrayList<VienkartiniuResursuSarasas>();
	
	public final int MOS_darbo_pabaiga = 0;
	public final int Klaviaturos_pertraukimas = 1;
	public final int Sintakses_tikrinimas = 2;
	public final int Sintakse_patikrinta = 3;
	public final int VM_nori_ivedimo = 4;
	public final int Pranesimas_apie_pertraukima = 5;
	public final int InputStream_pabaiga = 6;
	public final int Loader_pabaiga = 7;
	public final int Destroyer_XDD_pabaiga = 8;
	public final int Destroyer_XDD_pradzia = 9;
	public final int Loader_pradzia = 10;
	public final int Info_apie_nauja_VM = 11;
	public final int MainGovernor_pazadinimas = 12;
	public final int Pertraukimo_ivykis = 13;
	public final int Writer_pradzia = 14;
	public final int Swapper_pradzia = 15;
	
	
	public static void initialiseVRSS() {
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(0).vardas = "MOS darbo pabaiga";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(1).vardas = "Klaviaturos pertraukimas";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(2).vardas = "Sintakses tikrinimas";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(3).vardas = "Sintakse patikrinta";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(4).vardas = "VM nori ivedimo";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(5).vardas = "Pranesimas apie pertraukima";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(6).vardas = "InputStream pabaiga";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(7).vardas = "Loader pabaiga";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(8).vardas = "Destroyer XDD pabaiga";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(9).vardas = "Destroyer XDD pradzia";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(10).vardas = "Loader pradzia";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(11).vardas = "Info apie nauja VM";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(12).vardas = "MainGovernor pazadinimas";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(13).vardas = "Pertraukimo ivykis";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(14).vardas = "Writer pradzia";
		
		VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
		VienkartiniuResursuSarasuSarasas.list.get(15).vardas = "Swapper  pradzia";
		
	}
	
	public static void print(int i) {
		for (int j=0; j<list.get(i).resourceList.size(); j++) {
			System.out.println(list.get(i).resourceList.get(j).nameO+"||| id: "+list.get(i).resourceList.get(j).nameI);
		}
	}
}
