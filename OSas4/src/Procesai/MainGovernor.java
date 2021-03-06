package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRint;
import os.Statiniai.VRstring;

import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFOv;



public class MainGovernor extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			vieta++;
			//System.out.println("AS ESU CASE 0!!!!!!!!!!!!!!!!!!!!!");
			Primityvai.prasytiResurso(VRstring.MainGovernor_pazadinimas, nameI, 1);
			return;
		case 1:
			int res = -1;

			// surandame main governor pazadinimas resursa proceso sarase
			for (int i = 0; i < this.resursai.size(); i++) {
				if (this.resursai.get(i).nameO.equals(VRstring.MainGovernor_pazadinimas)) {
					res = i;
					break;
				}
			}
			//System.out.println("AS ESU CASE 1 po MG pazadinimo radimo!!!!!!!!!!!!!!!!!!!!!! i:"+res);
			if (res >= 0) { // jeigu toks resursas egzistuoja...
				// issitraukiam ta resursa
				ResourceDescriptor rd = null;
				for (int i = 0; i < VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.size(); i++) {
					if (VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.get(i).nameI == this.resursai.get(res).nameI) {
						rd = VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.get(i);
						Primityvai.naikintiResursa(VRSS.list.get(VRint.MainGovernor_pazadinimas).resourceList.get(i).nameI);
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
						vieta = 0;
						//Primityvai.naikintiResursa(rd.nameI);
						Primityvai.sukurtiResursa(VRstring.Info_apie_nauja_VM, true, this.nameI, inf);
						return;
					}
					else {
						vieta = 0;
						//Primityvai.naikintiResursa(rd.nameI);

						//naikinimas
						Primityvai.naikintiProcesa(((int)((Object[])rd.info.o)[1]), this);

					}
				}
				else
					System.out.println("Main governor. kas per velnias cia atsitiko?");
			}
			else
				System.out.println("Main Governor klaida. MainGovernor_pazadinimas resursas neegzistuoja.");

			for (int i = 0; i < this.resursai.size(); i++) {
				if (this.resursai.get(i).nameO.equals(VRstring.MainGovernor_pazadinimas))
					Primityvai.naikintiResursa(this.resursai.get(i).nameI);
			}
			
			//System.out.println("AS ESU CASE 1 galeeeeeeeeeeeeeeeee!!!!!!!!!!!!!!!!!!!!!");
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.MainGovernor_pazadinimas, this.nameI, 1);
			return;
		}
	}
}
