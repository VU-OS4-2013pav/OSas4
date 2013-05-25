package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRint;
import os.Statiniai.DRstring;
import os.Statiniai.ProcessState;
import os.Statiniai.VRint;
import os.Statiniai.VRstring;
import resources.ProcessNeedsResource;
import resources.RSS;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;


public class Loader extends ProcessBase {
	@Override
	public void execute() {
		switch(this.vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Loader_pradzia, nameI, 1);
			break;
		case 1:
			// ar egzistuoja toks resursas
			if (!VRSS.list.get(VRint.Loader_pradzia).resourceList.isEmpty()) {
				// egzistuoja
				
				HDDObject hdd = ((HDDObject)(RSS.list.get(DRint.HDD).resourceDescriptor.info.o));
				
				String pavadinimas = null;
				int kuris = -1;
				
				for (int i = 0; i < VRSS.list.get(VRint.Loader_pradzia).resourceList.size(); i++) 
					if (VRSS.list.get(VRint.Loader_pradzia).resourceList.get(i).nameI == resursai.get(0).nameI) {
						pavadinimas = (String)((Object[])VRSS.list.get(VRint.Loader_pradzia).resourceList.get(i).info.o)[0];
						kuris = i;
						break; //Radom ko reikia, galim nebetæst paieðkos
					}
				
				if (pavadinimas == null)
					System.out.println("Loader pradþia neatsineðë pavadinimo!");
				
				boolean yra = false;
				for (int i = 0; i < hdd.programs.size(); i++) {
					// ar egzistuoja nurodyta programa atmintyje?
					if (hdd.programs.get(i).name.equals(pavadinimas)) {
						
					// taip saka - main governor pazadinimas
						INFO inf = new INFOv();
						((Object[])inf.o)[0] = true;
						((Object[])inf.o)[1] = pavadinimas;
						
						vieta = 3;
						Primityvai.sukurtiResursa(Statiniai.VRstring.MainGovernor_pazadinimas, true, this.nameI, inf);					
					}
				}
				
				//ne ðaka - blokuojasi, eina i case 2
				if (!yra) {	
					vieta = 2;
					Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, this.nameI, 1);
					return;
				}
			} else { 
				System.err.println("Kazkas tai atsitiko. Loader. Neegzistuoja!");
			}
			
			break;
		case 2:
			// isveda klaidos pranesima, atlaisvina kanalus
			System.out.println("Nurodyta programa HDD neegzistuoja. Loader.");
			vieta = 3;
			Primityvai.atlaisvintiResursa(DRstring.Kanalu_irenginys, this.nameI);
			break;
		case 3:
			vieta = 4;
			Primityvai.atlaisvintiResursa(Statiniai.VRstring.Loader_pradzia, nameI);
			break;
		case 4:
			vieta = 5;
			Primityvai.sukurtiResursa(VRstring.Loader_pabaiga, true, this.nameI, new INFO());
			break;
		case 5:
			//sukuria loader pabaiga ir pereina á pradþià - laukia <loader pradzia>
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Loader_pradzia, this.nameI, 1);
			break;
		
		}
		
	}
}
