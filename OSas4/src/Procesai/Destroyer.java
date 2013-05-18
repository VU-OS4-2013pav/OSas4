package Procesai;

import resources.ProcessNeedsResource;
import resources.RSS;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import Procesai.Statiniai.ProcessState;
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
	
			boolean yra = false;
			for (int i = 0; i < ((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o)).programs.size(); i++) {
				if (((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o)).programs.get(i).name //LAbai maþas ifas
						== VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(0).nameO) {
					
					// taip saka - sutvarkomas deskriptorius, systemoutas
										
					yra = true;
					break;
				}
			}
			
			if (!yra) {
				//ne ðaka klaidos pranesimas
				
				
			}
			
			//atlaisvina kanalu irengini
			
			
			// sukuria destroyer pabaiga
			vieta = 1;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Destroyer_XDD_pabaiga, true, this.nameI, new INFO());
			
			// grizta i blokuota busena ir laukia paleidimo
			Primityvai.prasytiResurso(Statiniai.VRstring.Destroyer_XDD_pradzia, this.nameI, 1);
			break;
		}
		
	}
}
