package resources;

import java.util.ArrayList;
import java.util.List;

import Procesai.PPS;
import Procesai.ProcessBase;

import resourcesINFO.INFO;

public class ResourceDescriptor {
	public static int resourceID = 0;
	public int nameI;
	public String nameO;
	public boolean usedOnce = false; //false - daugkartiniai
	public int nameFather;
	public List<ProcessNeedsResource> lps = new ArrayList<ProcessNeedsResource>();
	// TODO (?) paskirstytojo laukas
	public INFO info;
	
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
	
	public static void prasytiResurso(String isorinis, int kas, int kiek) {
		for (int i = 0; i < PPS.list.size(); i++) {
			if (PPS.list.get(i).nameI == kas) {
				PPS.list.get(i).busena = ProcessBase.BLOCKED;
				
				if ((isorinis == "HDD") || (isorinis == "Kanalu irenginys") || (isorinis == "Vartotojo atmintis")) {
					switch(isorinis) {
					case "HDD" :
						RSS.list.get(0).list.add(new ProcessNeedsResource(PPS.list.get(i), kiek));
						return;
					case "Kanalu irenginys" :
						RSS.list.get(1).list.add(new ProcessNeedsResource(PPS.list.get(i), kiek));
						return;
					case "Vartotojo atmintis" :
						RSS.list.get(2).list.add(new ProcessNeedsResource(PPS.list.get(i), kiek));
						return;
					}
				}
				else {
					for (int j = 0; j < VRSS.list.size(); j++) {
						if (VRSS.list.get(j).vardas == isorinis) {
							VRSS.list.get(j).processList.add(new ProcessNeedsResource(PPS.list.get(i), kiek));
							return;
						}
					}
				}
			}
		}
		
	}
	

}
