package Procesai;

import Procesai.Statiniai.ProcessState;
import Procesai.Statiniai.VRint;
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
		break;
		case 1:
			// ar egzistuoja nurodyta
			if (!VRSS.list.get(VRint.Loader_pradzia).resourceList.isEmpty()) {
				// egzistuoja
				
				boolean yra = false;
				for (int i = 0; i < ((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o)).programs.size(); i++) {
					if (((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o)).programs.get(i).name //LAbai ma�as ifas
							== VRSS.list.get(VRint.Loader_pradzia).resourceList.get(0).nameO) {
						
						// taip saka - main governor pazadinimas
						INFO inf = new INFOv();
						((Object[])inf.o)[0] = true;
						((Object[])inf.o)[1] = VRSS.list.get(VRint.Loader_pradzia).resourceList.get(0).nameO;
						
						//Kuriam main governor pa�adinimas!
						Primityvai.sukurtiResursa("Main governor pazadinimas", true, this.nameI, inf);					
						yra = true;
						break;
					}
				}
				
				if (!yra) {
					//ne �aka blokuojasi, klaida, atlaisvina
					this.busena = ProcessState.BLOCKED;
					RSS.list.get(1).list.add(new ProcessNeedsResource(this, 1));
					vieta = 2;
					return;
				}
			} else { 
				System.err.println("Kazkas tai atsitiko. Loader. Neegzistuoja!");
			}
			
			//sukuria loader pabaiga ir pereina � prad�i� - laukia <loader pradzia>
			Primityvai.sukurtiResursa("Loader pabaiga", true, this.nameI, new INFO());
			vieta = 1;
			VRSS.list.get(VRint.Loader_pradzia).processList.add(new ProcessNeedsResource(this, 1));
			this.busena = ProcessState.BLOCKED;
			
		break;
		case 2:
		
			
		break;
		
		}
		
	}
}
