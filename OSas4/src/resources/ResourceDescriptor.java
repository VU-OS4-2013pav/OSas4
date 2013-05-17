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
			for(i=0; i<RSS.list.size(); i++) {
				if (RSS.list.get(i).resourceDescriptor == null) {
					RSS.list.get(i).resourceDescriptor = new ResourceDescriptor(name, usable, father, inf, resourceID);
					freeSpot = true;
					break;
				}
			}
			if (RSS.list.isEmpty() || !freeSpot) {
				RSS.list.add(new RS());
				RSS.list.get(RSS.list.size()-1)
						.resourceDescriptor = new ResourceDescriptor(name, usable, father, inf, resourceID);
			}
		}
		else {
			for(i=0; i<VRSS.list.size(); i++) {
				if (VRSS.list.get(i).vardas == name) {
					VRSS.list.get(i).resourceList.add(new ResourceDescriptor(name, usable, father, inf, resourceID));
					break;
				}
			}

			
		}
		
	}
	

}
