package Procesai;

import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFOv;
import Procesai.Statiniai.DRstring;
import Procesai.Statiniai.VRint;
import Procesai.Statiniai.VRstring;


public class MainGovernor extends ProcessBase {
	@Override
	public void execute() {
		int res = -1;
		
		// surandame main governor pazadinimas resursa proceso sarase
		for (int i = 0; i < this.resursai.size(); i++) {
			if (this.resursai.get(i).nameO == VRstring.MainGovernor_pazadinimas) {
				res = i;
				break;
			}
		}
		
		if (res >= 0) { // jeigu toks resursas egzistuoja...
			// issitraukiam ta resursa
			ResourceDescriptor rd = null;
			for (int i = 0; i < VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.size(); i++) {
				if (VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.get(i).nameI == this.resursai.get(res).nameI) {
					rd = VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.get(i);
				}
			}
			
			if (rd != null) {
				// tikrinam, ar JG reikia sukurti ar sunaikinti
				if (((boolean)((Object[])rd.info.o)[0]) == true) {
					// proceso kurimas
					Primityvai.sukurtiProcesa(Statiniai.Pstring.JobGovernor, this.nameI, 7);
					// info apie nauja vm kurimas
					INFOv inf = new INFOv();
					((Object[])inf.o)[0] = ((Object[])rd.info.o)[1];
					Primityvai.sukurtiResursa(VRstring.Info_apie_nauja_VM, true, this.nameI, inf);
				}
				else {
					//naikinimas
					Primityvai.naikintiProcesa(((int)((Object[])rd.info.o)[1]), this);
				}
			}
			else
				System.out.println("Main governor. kas per velnias cia atsitiko?");
		}
		else
			System.out.println("Main Governor klaida. MainGovernor_pazadinimas resursas neegzistuoja.");

		Primityvai.prasytiResurso(VRstring.MainGovernor_pazadinimas, this.nameI, 1);
	}
}
