package resources;

import java.util.ArrayList;
import java.util.List;

import resourcesINFO.INFO;

public class ResourceDescriptor {
	// Daugkartinio panaudojimo resursai
	int nameI;
	String nameO;
	boolean usedOnce = false; //false - daugkartiniai
	int nameFather;
	List<ProcessNeedsResource> lps = new ArrayList<ProcessNeedsResource>();
	// TODO (?) paskirstytojo laukas
	INFO info;
	
	public ResourceDescriptor(String name, boolean usable, int father, INFO inf) {
//		System.out.println(name);
		//TODO nameI
		nameO = name;
		usedOnce = usable;
		nameFather = father;
		info = inf;		
	}
	
	public static void sukurtiResursa(String name, boolean usable, int father, INFO inf) {
		int i;
//		System.out.println("KUKUUUUU usable= "+usable);
		if (usable == false) {	
//			System.out.println("usable false");
			boolean freeSpot = false;
			for(i=0; i<ResursuSarasuSarasas.list.size(); i++) {
				if (ResursuSarasuSarasas.list.get(i).resourceDescriptor == null) {
//					System.out.println("if i= "+i);
					ResursuSarasuSarasas.list.get(i).resourceDescriptor = new ResourceDescriptor(name, usable, father, inf);
					freeSpot = true;
					break;
				}
			}
			if (ResursuSarasuSarasas.list.isEmpty() || !freeSpot) {
//				System.out.println("if ashdgajshdbasd");
				ResursuSarasuSarasas.list.add(new ResursuSarasas());
				ResursuSarasuSarasas.list.get(ResursuSarasuSarasas.list.size()-1)
						.resourceDescriptor = new ResourceDescriptor(name, usable, father, inf);
			}
		}
		else {
//			System.out.println("usable true");
//			System.out.println("size: " +VienkartiniuResursuSarasuSarasas.list.size());
			for(i=0; i<VienkartiniuResursuSarasuSarasas.list.size(); i++) {
				if (VienkartiniuResursuSarasuSarasas.list.get(i).vardas == name) {
//					System.out.println("else i= "+i);
					VienkartiniuResursuSarasuSarasas.list.get(i).resourceList.add(new ResourceDescriptor(name, usable, father, inf));
					break;
				}
			}
/*			if (VienkartiniuResursuSarasuSarasas.list.isEmpty()) {
				System.out.println("else ashdgajshdbasd       "+name);
				VienkartiniuResursuSarasuSarasas.list.add(new VienkartiniuResursuSarasas());
				VienkartiniuResursuSarasuSarasas.list.get(VienkartiniuResursuSarasuSarasas.list.size()-1)
						.resourceList.add(new ResourceDescriptor(name, usable, father, inf));
			}*/
			
		}
		
	}
	

}
