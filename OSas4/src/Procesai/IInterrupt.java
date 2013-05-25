package Procesai;

import os.Planuotojas;
import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.VRSS;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import rm.Memory;


public class IInterrupt extends ProcessBase {
	//boolean naujaU�duotis, MOSpabaiga, uzduotiesPaleidimas, uzduotiesIstrinimas;
	
	public void execute() {
		System.out.println("IInterrupt pradzia.");
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			break;
		case 1:
			// jeigu vm nori ivedimo
			if (!VRSS.list.get(Statiniai.VRint.VM_nori_ivedimo).resourceList.isEmpty()) {
				vieta = 2;
				Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, this.nameI, 1);
			} 
			// TODO jeigu nauja uzduotis
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".NEW")) {
				Statiniai.readMem++;
				Primityvai.sukurtiProcesa(Statiniai.Pstring.InputStream, nameI, 8);
				Primityvai.atlaisvintiResursa(Statiniai.VRstring.Klaviaturos_pertraukimas, this);
				//Planuotojas.planuok();
				
				//Blokuojasi ir laukia klaviat�ros pertraukimo
				vieta = 1;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			} 
			// jeigu mos pabaiga
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".END")) {
				Statiniai.readMem++;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, this.nameI, null);
			} 
			// TODO jeigu uzduoties paleidimas
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".OPN")) {
				Statiniai.readMem++;

			} 
			// TODO jeigu uzduoties istrinimas
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".DEL")) {
				Statiniai.readMem++;
				
			} 
			// jeigu nesamone
			else {
				System.out.println("Neatpazinta sistemos komanda.");
			}
			break;
		case 2:
			//Kopijuoja kas �vesta
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			INFOv inf = new INFOv();
			((Object[])inf.o)[0] = false;
			vieta = 1;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, inf);
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, this.nameI, 1);
			break;
		}
//		cpu[i] = "BLOCKED";
//		
//		if(VMNoriIvedimo) {
//			
//		} else if(naujaU�duotis) {
//			
//		} else if(MOSpabaiga) {
//			
//		} else if(uzduotiesPaleidimas) {
//			
//		} else if(uzduotiesIstrinimas) {
//			
//		}
	}
}
