package resources;

import java.util.ArrayList;
import java.util.List;

import Procesai.ProcessBase;


import resourcesINFO.INFO;

public class ResourceDescriptor {
	public static int resourceID = 0;
	public int nameI;
	public String nameO;
	public boolean usedOnce; //false - daugkartiniai
	public int nameFather;
	public List<ProcessNeedsResource> lps = new ArrayList<ProcessNeedsResource>();
	// TODO (?) paskirstytojo laukas
	public boolean laisvas = true;
	public INFO info;
	
	public ResourceDescriptor(String name, boolean usable, int father, INFO inf, int id) {
		nameI = id;
		nameO = name;
		usedOnce = usable;
		nameFather = father;
		info = inf;		
	}
	

}
