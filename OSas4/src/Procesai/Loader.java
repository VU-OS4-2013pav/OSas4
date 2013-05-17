package Procesai;

import java.util.Arrays;

import resources.ProcessNeedsResource;
import resources.RSS;
import resources.ResourceDescriptor;
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
			if (!VRSS.list.get(VRSS.Loader_pradzia).resourceList.isEmpty()) {
				// egzistuoja
				
				boolean yra = false;
				for (int i = 0; i < ((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o)).programs.size(); i++) {
					if (((HDDObject)(RSS.list.get(0).resourceDescriptor.info.o)).programs.get(i).name //LAbai maþas ifas
							== VRSS.list.get(VRSS.Loader_pradzia).resourceList.get(0).nameO) {
						
						// taip saka - main governor pazadinimas
						INFO inf = new INFOv();
						((Object[])inf.o)[0] = true;
						((Object[])inf.o)[1] = VRSS.list.get(VRSS.Loader_pradzia).resourceList.get(0).nameO;
						
						//Kuriam main governor paþadinimas!
						ResourceDescriptor.sukurtiResursa("Main governor pazadinimas", true, this.nameI, inf);					
						yra = true;
						break;
					}
				}
				
				if (!yra) {
					//ne ðaka blokuojasi, klaida, atlaisvina
					this.busena = BLOCKED;
					RSS.list.get(1).list.add(new ProcessNeedsResource(this, 1));
					vieta = 2;
					return;
				}
			} else { 
				System.err.println("Kazkas tai atsitiko. Loader. Neegzistuoja!");
			}
			
			//sukuria loader pabaiga ir pereina á pradþià - laukia <loader pradzia>
			ResourceDescriptor.sukurtiResursa("Loader pabaiga", true, this.nameI, new INFO());
			vieta = 1;
			VRSS.list.get(VRSS.Loader_pradzia).processList.add(new ProcessNeedsResource(this, 1));
			this.busena = BLOCKED;
			
		break;
		case 2:
		
			
		break;
		
		}
		
	}
}
