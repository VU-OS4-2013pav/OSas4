package Procesai;

import Procesai.Statiniai.DRint;
import Procesai.Statiniai.DRstring;
import Procesai.Statiniai.ProcessState;
import Procesai.Statiniai.VRint;
import Procesai.Statiniai.VRstring;
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
			Primityvai.prasytiResurso(VRstring.Loader_pradzia, nameI, 1);
			vieta++;
			break;
		case 1:
			// ar egzistuoja toks resursas
			if (!VRSS.list.get(VRint.Loader_pradzia).resourceList.isEmpty()) {
				// egzistuoja
				
				HDDObject hdd = ((HDDObject)(RSS.list.get(DRint.HDD).resourceDescriptor.info.o));
				boolean yra = false;
				for (int i = 0; i < hdd.programs.size(); i++) {
					// ar egzistuoja nurodyta programa atmintyje?
					if (hdd.programs.get(i).name == VRSS.list.get(VRint.Loader_pradzia).resourceList.get(0).nameO) {
						
					// taip saka - main governor pazadinimas
						INFO inf = new INFOv();
						((Object[])inf.o)[0] = true;
						((Object[])inf.o)[1] = VRSS.list.get(VRint.Loader_pradzia).resourceList.get(0).nameO;
						
						Primityvai.sukurtiResursa("Main governor pazadinimas", true, this.nameI, inf);					
						yra = true;
						break;
					}
				}
				
				//ne ðaka - blokuojasi, eina i case 2
				if (!yra) {	
					Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, this.nameI, 1);
					vieta = 2;
					return;
				}
			} else { 
				System.err.println("Kazkas tai atsitiko. Loader. Neegzistuoja!");
			}
			
			//sukuria loader pabaiga ir pereina á pradþià - laukia <loader pradzia>
			vieta = 1;
			
			Primityvai.sukurtiResursa(VRstring.Loader_pabaiga, true, this.nameI, new INFO());
			Primityvai.prasytiResurso(VRstring.Loader_pradzia, this.nameI, 1);
			
			break;
		case 2:
			// isveda klaidos pranesima, atlaisvina kanalus
			System.out.println("Nurodyta programa HDD neegzistuoja. Destroyer.");
			Primityvai.atlaisvintiResursa(DRstring.Kanalu_irenginys, this.nameI);
			
			//sukuria loader pabaiga ir pereina á pradþià - laukia <loader pradzia>
			vieta = 1;
			
			Primityvai.sukurtiResursa(VRstring.Loader_pabaiga, true, this.nameI, new INFO());
			Primityvai.prasytiResurso(VRstring.Loader_pradzia, this.nameI, 1);
		
			
			break;
		
		}
		
	}
}
