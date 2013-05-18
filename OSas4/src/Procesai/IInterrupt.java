package Procesai;

import resources.VRSS;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import Procesai.Statiniai.DRstring;
import Procesai.Statiniai.VRstring;


public class IInterrupt extends ProcessBase {
	boolean VMNoriIvedimo, naujaUþduotis, MOSpabaiga, uzduotiesPaleidimas, uzduotiesIstrinimas;
	
	public void execute() {
		switch(vieta) {
		case 0:
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, Statiniai.Pint.IINTERRUPT, 1);
			vieta = 1;
			break;
		case 1:
			if (!VRSS.list.get(Statiniai.VRint.VM_nori_ivedimo).resourceList.isEmpty()) {
				Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, Statiniai.Pint.IINTERRUPT, 1);
				vieta = 2;
			}
			else {
				System.out.println("VM jokio ivedimo nenori");
				//TODO ar naujos uþduoties pradþia
			}
			break;
		case 2:
			//Kopijuoja kas ávesta
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			INFOv inf = new INFOv();
			((Object[])inf.o)[0] = false;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, inf);
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, Statiniai.Pint.IINTERRUPT, 1);
			vieta = 1;
			break;
		}
//		cpu[i] = "BLOCKED";
//		
//		if(VMNoriIvedimo) {
//			
//		} else if(naujaUþduotis) {
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
