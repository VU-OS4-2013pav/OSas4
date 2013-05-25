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
	
	public String toString() {
		return String.format("nameI=%s nameO=%s usedOnce=%s father=%s free=%s", nameI, nameO, usedOnce, nameFather, laisvas);
		//("nameI=%s nameO=%s busena=%s father=%s prioritetas=%s", nameI, nameO, busena, father, prioritetas);
	}
	

}
