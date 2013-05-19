package Procesai;

import os.Primityvai;
import os.Statiniai.VRstring;
import resourcesINFO.INFOv;
import rm.RM;


public class VirtualMachine extends ProcessBase {
	@Override
	public void execute() {
		switch (vieta) {
		case 0:
			boolean run = true;
			
			while(run) {
				if (RM.runPC()) {
					INFOv inf = new INFOv();
					((Object[])inf.o)[0] = this.father;
					Primityvai.sukurtiResursa(VRstring.Pertraukimo_ivykis, true, this.nameI, inf);
					run = false;
				}
			}
			break;
		}
		
	}
}
