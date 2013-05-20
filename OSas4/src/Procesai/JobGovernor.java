package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.VRstring;
import resources.ResourceDescriptor;
import resources.VRSS;


public class JobGovernor extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			//Blokuojasi ir laukia main governor paþadinimas
			vieta++;
			Primityvai.prasytiResurso(VRstring.MainGovernor_pazadinimas, nameI, 1);
			break;
		case 1:
			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.MainGovernor_pazadinimas).resourceList.size(); i++) {
				ResourceDescriptor resource = VRSS.list.get(Statiniai.VRint.MainGovernor_pazadinimas).resourceList.get(i);
				if (!resource.laisvas && resource.process == this) {
					if (!(Boolean)((Object[])resource.info.o)[0]) {//jei reikia sunaikinti
						int kuriNaikinam = (int)((Object[])resource.info.o)[1];
						Primityvai.naikintiProcesa(kuriNaikinam, this);
					}
					else { //jei reikia sukurti 
						Primityvai.sukurtiProcesa(Statiniai.Pstring.JobGovernor, nameI, 7);
						//TODO perduoti per info kiek reikës blokø ir programos pavadinimà
						Primityvai.sukurtiResursa(Statiniai.VRstring.Info_apie_nauja_VM, true, nameI, null);
					}
					break;
				}
			}
			//Blokuojasi ir laukia main governor paþadinimas
			Primityvai.prasytiResurso(VRstring.MainGovernor_pazadinimas, nameI, 1);
			
			break;
		}
		
	}
}
