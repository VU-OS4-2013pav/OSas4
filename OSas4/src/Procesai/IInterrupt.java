package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.VRSS;
import resourcesINFO.INFOv;
import rm.Memory;


public class IInterrupt extends ProcessBase {
	//boolean naujaUþduotis, MOSpabaiga, uzduotiesPaleidimas, uzduotiesIstrinimas;
	
	public void execute() {
		//System.out.println("IInterrupt pradzia.");
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			return;
		case 1:
			//naikinam klaviatûros pertraukimo resursà
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Klaviaturos_pertraukimas) {
					Primityvai.naikintiResursa(resursai.get(i).nameI);
					break;
				}
			
			// jeigu vm nori ivedimo
			if (!VRSS.list.get(Statiniai.VRint.VM_nori_ivedimo).resourceList.isEmpty()) {
				vieta = 2;
				Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, this.nameI, 1);
				return;
			} 
			// jeigu nauja uzduotis
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".NEW")) {
				Statiniai.readMem++;

				Primityvai.sukurtiProcesa(Statiniai.Pstring.InputStream, this.nameI, 8); 
				vieta = 3;
				Primityvai.prasytiResurso(VRstring.InputStream_pabaiga, this.nameI, 1);
				return;
			} 
			// jeigu mos pabaiga
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".END")) {
				Statiniai.readMem++;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, this.nameI, null);
				return;
			} 
			// jeigu uzduoties paleidimas
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".OPN")) {
				Statiniai.readMem++;
				INFOv inf = new INFOv();
				((Object[])inf.o)[0] = String.valueOf(Memory.get()[Statiniai.readMem].getWord());
				Statiniai.readMem++;
				vieta = 5;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Loader_pradzia, true, this.nameI, inf);
				return;
			} 
			// jeigu uzduoties istrinimas
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".DEL")) {
				Statiniai.readMem++;		
				INFOv inf = new INFOv();
				((Object[])inf.o)[0] = String.valueOf(Memory.get()[Statiniai.readMem].getWord());
				Statiniai.readMem++;
				vieta = 4;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Destroyer_XDD_pradzia, true, this.nameI, inf);
				return;
			} 
			// jeigu nesamone
			else {
				System.out.println("Neatpazinta sistemos komanda.");
				vieta = 1;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
				return;
			}
			
		case 2:
			//Kopijuoja kas ávesta
			vieta = 7;
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			return;
			
		case 3:
			Primityvai.naikintiProcesa(this.sunus.get(0), this);
			vieta = 1;
			//naikinam input stream pabaigos resursà
			for (int i = 0; i < resursai.size(); i++) 
				if (resursai.get(i).nameO == Statiniai.VRstring.InputStream_pabaiga) {
					Primityvai.naikintiResursa(resursai.get(i).nameI);
					break;
				}
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, this.nameI, 1);
			return;
		case 4:
			vieta = 9;
			Primityvai.prasytiResurso(VRstring.Destroyer_XDD_pabaiga, this.nameI, 1);
			return;
		case 5:
			vieta = 8;
			Primityvai.prasytiResurso(VRstring.Loader_pabaiga, this.nameI, 1);
			return;
		case 6:
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, this.nameI, 1);
			return;
		case 7:
			INFOv inf = new INFOv();
			((Object[])inf.o)[0] = false;
			vieta = 6;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, inf);
			return;
		case 8:
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Loader_pabaiga)
					Primityvai.naikintiResursa(resursai.get(i).nameI);
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			return;
		case 9:
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Destroyer_XDD_pabaiga)
					Primityvai.naikintiResursa(resursai.get(i).nameI);
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			return;
		}

	}
}
