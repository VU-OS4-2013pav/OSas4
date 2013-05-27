package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRint;
import os.Statiniai.VRstring;
import resources.RSS;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;


public class Destroyer extends ProcessBase {
	
	@Override
	public void execute() {
		switch (vieta) {
		case 0:
			// blokuojasi ir laukia destroyer pradzia
			vieta++;
			Primityvai.prasytiResurso(Statiniai.VRstring.Destroyer_XDD_pradzia, this.nameI, 1);
			return;
		case 1:
			// gavo destroyer pradzia
			// blokuojasi ir laukia kanalu irenginys
			vieta++;
			Primityvai.prasytiResurso(Statiniai.DRstring.Kanalu_irenginys, this.nameI, 1);
			return;
		case 2:
			// gavo kanalu irengini. ar egzistuoja nurodyta programa atmintyje?
			
			//gaunam pavadinimà, kà trinti
			String pavadinimas = null;
			int kuris = -1;
			
			for (int i = 0; i < VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.size(); i++) 
				if (VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(i).nameI == resursai.get(0).nameI) {
					pavadinimas = (String)((Object[])VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(i).info.o)[0];
					kuris = i;
					break; //Radom ko reikia, galim nebetæst paieðkos
				}
			
			if (pavadinimas == null)
				System.out.println("Destroyer pradþia neatsineðë pavadinimo!");
			System.out.println("pavadinimas: " + pavadinimas);
			HDDObject hdd = ((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o));
			
			boolean yra = false;
			for (int i = 0; i < hdd.programs.size(); i++) {
				if (hdd.programs.get(i).name.equals(pavadinimas)) {
					
					// taip saka - sutvarkomas deskriptorius, systemoutas
					vieta++;
					Primityvai.atlaisvintiResursa(DRstring.HDD, hdd.programs.get(i).nr);
					return;
					/*System.out.println("Programa is HDD istrinta.");
										
					yra = true;
					break;*/
				}
			}
			
			if (!yra) {
				//ne ðaka klaidos pranesimas
				System.out.println("Progrma is HDD neistrinta. Pavadinimas: "+VRSS.list.get(VRint.Destroyer_XDD_pradzia).resourceList.get(0).nameO);
			}
			
			return;
		case 3:
			vieta = 5;
			
			for (int i = 0; i < this.resursai.size(); i++) {
				if (this.resursai.get(i).nameO.equals(VRstring.Destroyer_XDD_pradzia)) {
					Primityvai.naikintiResursa(this.resursai.get(i).nameI);
				}
			}

			//atlaisvina kanalu irengini

			Primityvai.atlaisvintiResursa(DRstring.Kanalu_irenginys, this.nameI);
			return;
		case 5:
			// sukuria destroyer pabaiga ir grizta i pradzia
			vieta = 0;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Destroyer_XDD_pabaiga, true, this.nameI, new INFO());
			return;
		}
		
	}
}
