package Procesai;

import os.Primityvai;
import os.Statiniai.VRstring;
import resourcesINFO.INFOv;
import rm.RM;


public class VirtualMachine extends ProcessBase {
	int j;
	char[] c;
	String str;
	@Override
	public void execute() {
		switch (vieta) {
		case 0:
			for (int k = 0; k < 6; k++) {
				c = new char[4];
				j = 3;
				str = Integer.toHexString((Integer)PL.getProcess(this.father).cpu[k]);
				//if (str.length() > 0)
				for (int i = str.length() -1; i >= 0; i--) {
					c[j] = str.charAt(i);
					j--;
				}
				if (j >= 0) {
					for (int i = j; i >= 0; i--) {
						c[i] = '0';
					}
				}
				RM.stringToRegister(k, String.valueOf(c));
			}
			RM.stringToRegister1B(RM.SR, Integer.toHexString((Integer)PL.getProcess(this.father).cpu[RM.SR]));
			for (int k = 7; k < 11; k++) {
				this.cpu[k] = 0;
				RM.stringToRegister1B(k, "0");
			}
				
			boolean run = true;
			while(run) {
				if (RM.runPC()) {
					for (int i = 0; i < 6; i++) {
						PL.getProcess(this.father).cpu[i] = Integer.parseInt(RM.registerToString(i), 16);
					}
					for (int i = 6; i < 11; i++) {
						PL.getProcess(this.father).cpu[i] = Integer.parseInt(RM.registerToString1B(i), 16);
					}
					
					INFOv inf = new INFOv();
					((Object[])inf.o)[0] = this.father;
					run = false;
					Primityvai.sukurtiResursa(VRstring.Pertraukimo_ivykis, true, this.nameI, inf);
					return;
					
				}
			}
			return;
		}
		
	}
}
