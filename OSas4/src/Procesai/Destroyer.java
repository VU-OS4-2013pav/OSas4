package Procesai;

import resources.RSS;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;
import Procesai.Statiniai.DRstring;
import Procesai.Statiniai.VRint;


public class Destroyer extends ProcessBase {
	
	@Override
	public void execute() {
		switch (vieta) {
		case 0:
			// blokuojasi ir laukia destroyer pradzia
			vieta++;
			Primityvai.prasytiResurso(Statiniai.VRstring.Destroyer_XDD_pradzia, this.nameI, 1);
			break;
		case 1:
			// gavo destroyer pradzia
			// blokuojasi ir laukia kanalu irenginys
			vieta++;
			Primityvai.prasytiResurso(Statiniai.DRstring.Kanalu_irenginys, this.nameI, 1);
			break;
		case 2:
			// gavo kanalu irengini. ar egzistuoja nurodyta programa atmintyje?
			HDDObject hdd = ((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o));
			boolean yra = false;
			for (int i = 0; i < hdd.programs.size(); i++) {
				if (hdd.programs.get(i).name == VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(0).nameO) {
					
					// taip saka - sutvarkomas deskriptorius, systemoutas
					Primityvai.atlaisvintiResursa(DRstring.HDD, VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(0).nameO);
					System.out.println("Programa is HDD istrinta.");
										
					yra = true;
					break;
				}
			}
			
			if (!yra) {
				//ne ðaka klaidos pranesimas
				System.out.println("Progrma is HDD neistrinta. Pavadinimas: "+VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(0).nameO);
				
				
			}
			
			//atlaisvina kanalu irengini
			Primityvai.atlaisvintiResursa(DRstring.Kanalu_irenginys, this.nameI);
					
			// sukuria destroyer pabaiga
			vieta = 1;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Destroyer_XDD_pabaiga, true, this.nameI, new INFO());
			
			// grizta i blokuota busena ir laukia paleidimo
			Primityvai.prasytiResurso(Statiniai.VRstring.Destroyer_XDD_pradzia, this.nameI, 1);
			break;
		}
		
	}
}
