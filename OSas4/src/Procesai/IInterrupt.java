package Procesai;

import resources.VRSS;
import resourcesINFO.INFO;
import Procesai.Statiniai.DRstring;
import Procesai.Statiniai.VRstring;


public class IInterrupt extends ProcessBase {
	boolean VMNoriIvedimo, naujaU�duotis, MOSpabaiga, uzduotiesPaleidimas, uzduotiesIstrinimas;
	
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
				//TODO ar naujos u�duoties prad�ia
			}
			break;
		case 2:
			//Kopijuoja kas �vesta
			//Atlaisvina kanal� �rengin�
			INFO inf = new INFO();
			inf.o = false;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, inf);
			break;
		}
//		cpu[i] = "BLOCKED";
//		
//		if(VMNoriIvedimo) {
//			
//		} else if(naujaU�duotis) {
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
