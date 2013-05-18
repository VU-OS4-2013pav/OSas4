package Procesai;

import java.util.ArrayList;
import java.util.List;

import Procesai.Statiniai.DRint;
import Procesai.Statiniai.DRstring;
import Procesai.Statiniai.ProcessState;
import Procesai.Statiniai.Pstring;
import resources.ProcessNeedsResource;
import resources.RS;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;
import resourcesINFO.INFOuserMemory;
import resourcesINFO.ProgramosInfoHDD;

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
	
	public static void atlaisvintiResursa(String name, Object procORname) { //proceso id (int), arba programos vardas (String)
		ProcessBase proc = PPS.getProcess((int)procORname);
		switch (name) {
		case DRstring.HDD:
			HDDObject o = ((HDDObject)RSS.list.get(DRint.HDD).resourceDescriptor.info.o);
			
			int program = -1;

			for (int i = 0; i < o.programs.size(); i++) { //began per visas programas ir ieskom nurodytos parametruose
				if (o.programs.get(i).name == (String)procORname) { // jeigu ta programa
					program = i;
				}
			}
			
			if (program > -1) { // jei programa buvo rasta
				// pazymim visus programos turimus blokus hdd kaip laisvus
				for (int k = 0; k < o.programs.get(program).memHDD; k++) { //iteruojam per programos turimus blokus
					for (int j = 0; j < HDDObject.HDD_SIZE; j++) { //iteruojam per hdd
						
						if (o.hdd.get(j).intValue() == o.programs.get(program).nr) {
							o.hdd.set(j, 0);
							break;
						}
					}
				}
				
				//ismetam informacija apie programa is programu saraso
				o.programs.remove(program);
			}
			else 
				System.out.println("Programa diske nerasta: "+(String)procORname);
					
			break;
		case DRstring.Kanalu_irenginys:
			((Boolean[])RSS.list.get(DRint.Kanalu_irenginys).resourceDescriptor.info.o)[0] = true;	
			
			for (int i = 0; i < proc.resursai.size(); i++) {
				if (proc.resursai.get(i).nameO == ((String) DRstring.Kanalu_irenginys)) {
					proc.resursai.remove(i);
				}
			}
			break;
		case DRstring.Vartotojo_atmintis:
			int j;
			for (int i = 0; i < proc.oa.length; i++) {
				j = proc.oa[i];
				((ArrayList<Boolean>)RSS.list.get(DRint.Vartotojo_atmintis).resourceDescriptor.info.o).set(j, false);
			}
			proc.oa = null;
			
			break;
		}
		
		
	}

}
