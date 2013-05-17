package resources;

import java.util.ArrayList;
import java.util.List;

import resourcesINFO.INFO;

public class ResourceDescriptor {
	static int resourceID = 0;
	int nameI;
	String nameO;
	boolean usedOnce = false; //false - daugkartiniai
	int nameFather;
	List<ProcessNeedsResource> lps = new ArrayList<ProcessNeedsResource>();
	// TODO (?) paskirstytojo laukas
	INFO info;
	
	public ResourceDescriptor(String name, boolean usable, int father, INFO inf, int id) {
		nameI = id;
		nameO = name;
		usedOnce = usable;
		nameFather = father;
		info = inf;		
	}
	
	public static void sukurtiResursa(String name, boolean usable, int father, INFO inf) {
		int i;
		resourceID++;
		
		if (usable == false) {	
			boolean freeSpot = false;
			for(i=0; i<ResursuSarasuSarasas.list.size(); i++) {
				if (ResursuSarasuSarasas.list.get(i).resourceDescriptor == null) {
					ResursuSarasuSarasas.list.get(i).resourceDescriptor = new ResourceDescriptor(name, usable, father, inf, resourceID);
					freeSpot = true;
					break;
				}
			}
			if (ResursuSarasuSarasas.list.isEmpty() || !freeSpot) {
				ResursuSarasuSarasas.list.add(new ResursuSarasas());
				ResursuSarasuSarasas.list.get(ResursuSarasuSarasas.list.size()-1)
						.resourceDescriptor = new ResourceDescriptor(name, usable, father, inf, resourceID);
			}
		}
		else {
			for(i=0; i<VienkartiniuResursuSarasuSarasas.list.size(); i++) {
				if (VienkartiniuResursuSarasuSarasas.list.get(i).vardas == name) {
					VienkartiniuResursuSarasuSarasas.list.get(i).resourceList.add(new ResourceDescriptor(name, usable, father, inf, resourceID));
					break;
				}
			}

			
		}
		
	}
	

}
