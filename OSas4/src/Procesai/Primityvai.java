package Procesai;

import Procesai.Statiniai.ProcessState;
import Procesai.Statiniai.Pstring;
import resources.ProcessNeedsResource;
import resources.RS;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFO;

public class Primityvai {
	public static int processId = 0;
	
	public static int sukurtiProcesa(String name, int father, int priority) {
		ProcessBase proc;
		processId++;
		
		switch (name) {
		case Pstring.IInterrupt:
			proc = new IInterrupt();
			break;
		case Pstring.InputStream:
			proc = new InputStream();
			break;
		case Pstring.SyntaxCheck:
			proc = new SyntaxCheck();
			break;
		case Pstring.Loader:
			proc = new Loader();
			break;
		case Pstring.Destroyer:
			proc = new Destroyer();
			break;
		case Pstring.Interrupt:
			proc = new Interrupt();
			break;
		case Pstring.Swapper:
			proc = new Swapper();
			break;
		case Pstring.Writer:
			proc = new Writer();
			break;
		case Pstring.MainGovernor:
			proc = new MainGovernor();
			break;
		case Pstring.JobGovernor:
			proc = new JobGovernor();
			break;
		default:// ProcessBase.VirtualMachine:
			proc = new VirtualMachine();
			break;
			
		}
	
		proc.father = father;
		proc.nameI = processId;
		proc.nameO = name;
		proc.prioritetas = priority;

//		System.out.println("Proceso isorinis vardas: "+proc.nameO);
		
		PPS.list.add(proc);
		PL.addProcess(proc);
		
		return proc.nameI; // grazina vidini varda
		
	}

	public static void prasytiResurso(String isorinis, int kas, int kiek) {
		for (int i = 0; i < PPS.list.size(); i++) {
			if (PPS.list.get(i).nameI == kas) {
				PPS.list.get(i).busena = ProcessState.BLOCKED;
				
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

	public static void sukurtiResursa(String name, boolean usable, int father, INFO inf) {
		int i;
		ResourceDescriptor.resourceID++;
		
		if (usable == false) {	
			boolean freeSpot = false;
			for(i=0; i<RSS.list.size(); i++) {
				if (RSS.list.get(i).resourceDescriptor == null) {
					RSS.list.get(i).resourceDescriptor = new ResourceDescriptor(name, usable, father, inf, ResourceDescriptor.resourceID);
					freeSpot = true;
					break;
				}
			}
			if (RSS.list.isEmpty() || !freeSpot) {
				RSS.list.add(new RS());
				RSS.list.get(RSS.list.size()-1)
						.resourceDescriptor = new ResourceDescriptor(name, usable, father, inf, ResourceDescriptor.resourceID);
			}
		}
		else {
			for(i=0; i<VRSS.list.size(); i++) {
				if (VRSS.list.get(i).vardas == name) {
					VRSS.list.get(i).resourceList.add(new ResourceDescriptor(name, usable, father, inf, ResourceDescriptor.resourceID));
					break;
				}
			}
	
			
		}
		
	}

}
