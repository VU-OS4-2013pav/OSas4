package os;

import java.util.ArrayList;
import java.util.List;

import os.Statiniai.DRint;
import os.Statiniai.DRstring;
import os.Statiniai.ProcessState;
import os.Statiniai.Pstring;

import Procesai.Destroyer;
import Procesai.IInterrupt;
import Procesai.InputStream;
import Procesai.Interrupt;
import Procesai.JobGovernor;
import Procesai.Loader;
import Procesai.MainGovernor;
import Procesai.PL;
import Procesai.PPS;
import Procesai.ProcessBase;
import Procesai.Swapper;
import Procesai.SyntaxCheck;
import Procesai.VirtualMachine;
import Procesai.Writer;
import Procesai.ProcessBase.Res;
import resources.ProcessNeedsResource;
import resources.RS;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;

public class Primityvai {
	public static int processId = 0;
	
	public static void sukurtiProcesa(String name, int father, int priority) {
		ProcessBase proc = null;
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
		case Pstring.VirtualMachine:
			proc = new VirtualMachine();
			break;
		default: {
			System.out.println("Primityvas sukurti procesa. Something went horribly wrong.");
			return;
		}
		}
		
		proc.busena = Statiniai.ProcessState.READY;
		proc.father = father;
		proc.nameI = processId;
		proc.nameO = name;
		proc.prioritetas = priority;
		
		PPS.list.add(proc);
		PL.addProcess(proc);
		PL.getProcess(proc.father).sunus.add(proc.nameI);
		
	}

	public static void prasytiResurso(String isorinis, int kas, int kiek) {
		System.out.println(PL.getProcess(kas).nameO + " paprase " + isorinis);
		
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
					for (int j = 0; j < VRSS.list.size(); j++) 
						if (VRSS.list.get(j).vardas == isorinis) {
							VRSS.list.get(j).processList.add(new ProcessNeedsResource(PPS.list.get(i), kiek));
							return;
						}
				}
			}
		}
		
		// proceso busenos keitimas
		// BLOCKS -> READYS
		if (PL.getProcess(kas).busena == ProcessState.RUN) {
			PL.getProcess(kas).busena = ProcessState.BLOCKED;
		}
	}

	public static void sukurtiResursa(String name, boolean usable, int father, INFO inf) {
		System.out.println(PL.processList.get(father).name + " kuria " + name);
		int i;
		ResourceDescriptor.resourceID++;
		ResourceDescriptor res = new ResourceDescriptor(name, usable, father, inf, ResourceDescriptor.resourceID);
		
		if (usable == false) {	
			boolean freeSpot = false;
			for(i=0; i<RSS.list.size(); i++) {
				if (RSS.list.get(i).resourceDescriptor == null) {
					RSS.list.get(i).resourceDescriptor = res;
					freeSpot = true;
					break;
				}
			}
			if (RSS.list.isEmpty() || !freeSpot) {
				RSS.list.add(new RS());
				RSS.list.get(RSS.list.size()-1).resourceDescriptor = res;
			}
		}
		else {
			for(i=0; i<VRSS.list.size(); i++) {
				if (VRSS.list.get(i).vardas == name) {
					VRSS.list.get(i).resourceList.add(res);
					break;
				}
			}
		}	
		// ideda i tevo-proceso sukurtu resursu sarasa
		PL.getProcess(father).addResToPL(res.nameO, res.nameI);
		
	}
	
	public static void atlaisvintiResursa(String name, Object procORname) { //proceso id (int), arba programos vardas (String)
		
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
			ProcessBase proc = PPS.getProcess((int)procORname);
			((Object[])RSS.list.get(DRint.Kanalu_irenginys).resourceDescriptor.info.o)[0] = true;	
			
			for (int i = 0; i < proc.resursai.size(); i++) {
				if (proc.resursai.get(i).nameO == ((String) DRstring.Kanalu_irenginys)) {
					proc.resursai.remove(i);
				}
			}
			break;
		case DRstring.Vartotojo_atmintis:
			ProcessBase process = PPS.getProcess((int)procORname);
			int j;
			for (int i = 0; i < process.oa.length; i++) {
				j = process.oa[i];
				((ArrayList<Boolean>)RSS.list.get(DRint.Vartotojo_atmintis).resourceDescriptor.info.o).set(j, false);
			}
			process.oa = null;
			
			break;
		}
		
		
	}
	
	public static void naikintiProcesa(int name, ProcessBase father) {
		int i, j;
		ProcessBase proc = null;
		boolean[] yra = new boolean[4];
		
		for (i = 0; i < 4; i++)
			yra[i] = false;
		
		// surandam procesa
		for (i = 0; i < PPS.list.size(); i++) //metam is PPS
			if (PPS.list.get(i).nameI == name) {
				proc = PPS.list.get(i);
				yra[0] = true;
			}
	
		for (i = 0; i < PL.processList.size(); i++) //metam is PL
			for (j = 0; j < PL.processList.get(i).processList.size(); j++)
				if (PL.processList.get(i).processList.get(j).nameI == name) {
					proc = PL.processList.get(i).processList.get(j);
					yra[1] = true;
				}

		for (i = 0; i < RSS.list.size(); i++)  //metam is RSS
			for (j = 0; j < RSS.list.get(i).list.size(); j++)
				if (RSS.list.get(i).list.get(j).process.nameI == name) {
					proc = RSS.list.get(i).list.get(j).process;
					yra[2] = true;
				}
		
		for (i = 0; i < VRSS.list.size(); i++) //metam is VRSS
			for (j = 0; j < VRSS.list.get(i).processList.size(); j++)
				if (VRSS.list.get(i).processList.get(j).process.nameI == name) {
					proc = VRSS.list.get(i).processList.get(j).process;
					yra[3] = true;
				}

		// jeigu toki procesa rado||nerado
		if (proc != null) {
			// sunaikinami proceso sukurti procesai (vaikai)
			for (i = 0; i < proc.sunus.size(); i++) {
				naikintiProcesa(proc.sunus.get(i), proc);
			}
			
			// sunaikinami proceso sukurti resursai (tik vienkartiniai)
			for (i = 0; i < proc.sukurtiResursai.size(); i++) {
				Primityvai.naikintiResursa(proc.sukurtiResursai.get(i).nameI);
			}
			
			// ismetamas is tevo vaiku saraso
			for (i = 0; i < father.sunus.size(); i++) {
				if (father.sunus.get(i) == proc.nameI) {
					father.sunus.remove(i);
				}
			}			
			
			// naikinami/atlaisvinami visi proceso turimi resursai (VRSS + RSS)
			for (i = 0; i < proc.resursai.size(); i++) {
				if (proc.resursai.get(i).nameO == DRstring.HDD 
						|| proc.resursai.get(i).nameO == DRstring.Kanalu_irenginys 
						|| proc.resursai.get(i).nameO == DRstring.Vartotojo_atmintis) {
					atlaisvintiResursa(proc.resursai.get(i).nameO, proc);
				}
				else {
					Primityvai.naikintiResursa(proc.sukurtiResursai.get(i).nameI);
				}		
			}
			
			// procesas ismetamas is visu procesu sarasu
			if (yra[0])
				for (i = 0; i < PPS.list.size(); i++)  //metam is PPS
					if (PPS.list.get(i).nameI == name) 
						PPS.list.remove(i);
			if (yra[1])
				for (i = 0; i < PL.processList.size(); i++) //metam is PL
					for (j = 0; j < PL.processList.get(i).processList.size(); j++)
						if (PL.processList.get(i).processList.get(j).nameI == name)
							PL.processList.get(i).processList.remove(j);
			
			if (yra[2])
				for (i = 0; i < RSS.list.size(); i++) //metam is RSS
					for (j = 0; j < RSS.list.get(i).list.size(); j++)
						if (RSS.list.get(i).list.get(j).process.nameI == name)
							RSS.list.get(i).list.remove(j);
		
			if (yra[3])
				for (i = 0; i < VRSS.list.size(); i++) //metam is VRSS
					for (j = 0; j < VRSS.list.get(i).processList.size(); j++)
						if (VRSS.list.get(i).processList.get(j).process.nameI == name)
							VRSS.list.get(i).processList.remove(j);
			
			// naikinamas deskriptorius, bet cia java... visi pointeriai jau nugnaibyti
		}
		else {
			System.out.println("Sunaikinti proceso nepavyko. Procesas su vidiniu vardu ''"+name+"'' neegzistuoja.");
		}
		
		
	}
	
	public static void naikintiResursa(int name) {
		ResourceDescriptor res = null;
		int i, j;
		boolean rss = false;
		
		// randam ta resursa RSS || VRSS sarasuose
		if (name < 4) { // RSS
			res = RSS.list.get(name-1).resourceDescriptor;
			rss = true;			
		}
		else { // VRSS
			for (i = 0; i < VRSS.list.size(); i++) {
				for (j = 0; j < VRSS.list.get(i).resourceList.size(); j++) {
					if (VRSS.list.get(i).resourceList.get(j).nameI == name) {
						res = VRSS.list.get(i).resourceList.get(j);
					}		
				}
			}
		}
		
		if (res != null) {
			// deskriptorius ismetamas is tevo sukurtu resursu saraso
			for (i = 0; i < PL.getProcess(res.nameFather).sukurtiResursai.size(); i++) {
				if (PL.getProcess(res.nameFather).sukurtiResursai.get(i).nameI == name) {
					PL.getProcess(res.nameFather).sukurtiResursai.remove(i);
				}
			}	
			//naikinamas resurso elementu sarasas. bet cia java.
			
			// ismetamas is resursu sarasu VRSS ir RSS
			if (rss) {
				RSS.list.get(name-1).resourceDescriptor = null;
			}
			else {
				for (i = 0; i < VRSS.list.size(); i++) {
					for (j = 0; j < VRSS.list.get(i).resourceList.size(); j++) {
						if (VRSS.list.get(i).resourceList.get(j).nameI == name) {
							VRSS.list.get(i).resourceList.remove(j);
						}		
					}
				}
			}
			
			// naikinamas deskriptorius. bet cia java.	
		}
		else {
			System.out.println("NaikintiResursa. Something went horribly wrong here...");
		}
		
	}
	
	public static void stabdytiProcesa(int name) {
		ProcessBase proc = PL.getProcess(name);
		
		// BLOCKED -> BLOCKS
		if (proc.busena == Statiniai.ProcessState.BLOCKED) { 
			proc.busena = ProcessState.BLOCKS;
		}
		// READY -> READYS
		else if (proc.busena == Statiniai.ProcessState.READY) {
			proc.busena = ProcessState.READYS;
		}
		// RUN -> READYS
		else if (proc.busena == Statiniai.ProcessState.RUN) {
			proc.busena = ProcessState.READYS;
		}	
	}
	
	public static void aktyvuotiProcesa(int name) {
		ProcessBase proc = PL.getProcess(name);
		
		// READYS -> READY
		if (proc.busena == Statiniai.ProcessState.READYS) { 
			proc.busena = ProcessState.READY;
		}
		// BLOCKS -> BLOCKED
		else if (proc.busena == Statiniai.ProcessState.BLOCKS) { 
			proc.busena = ProcessState.BLOCKED;
		}
		
	}
	
	public static void keistiPrioriteta(int name, int priority) {
		PL.getProcess(name).prioritetas = priority;
	}

}