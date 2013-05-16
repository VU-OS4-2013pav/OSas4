package resources;

import java.util.ArrayList;
import java.util.List;

public class VienkartiniuResursuSarasuSarasas {
	public static List<VienkartiniuResursuSarasas> list = new ArrayList<VienkartiniuResursuSarasas>();
	
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
			System.out.println(list.get(i).resourceList.get(j).nameO);
		}
	}
}
