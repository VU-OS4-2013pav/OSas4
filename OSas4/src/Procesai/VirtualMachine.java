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
			char[] c = new char[4];
			int j = 3;
			String str = Integer.toHexString((Integer)PL.getProcess(this.father).cpu[RM.PC]);
			System.out.println("VM PC////////: "+str);
			for (int i = str.length()-1; i >= 0; i--) {
				c[j] = str.charAt(i);
				j--;
			}
			if (j >= 0) {
				for (int i = j; i >= 0; i--) {
					c[i] = '0';
				}
			}
			System.out.println("ASLDJNLAJFNSLKF: "+String.valueOf(c));
			RM.stringToRegister(RM.PC, String.valueOf(c));
			RM.stringToRegister(RM.PTR, Integer.toHexString((Integer)PL.getProcess(this.father).cpu[RM.PTR]));
			System.out.println("VM PC: "+RM.registerToString(RM.PC));
			vieta++;
			// cia breako neturi buti!!!!
		case 1:
			char[] c1 = new char[4];
			int j1 = 3;
			String str1 = Integer.toHexString((Integer)PL.getProcess(this.father).cpu[RM.PC]);
			for (int i = str1.length() -1; i >= 0; i--) {
				c1[j1] = str1.charAt(i);
				j1--;
			}
			if (j1 >= 0) {
				for (int i = j1; i >= 0; i--) {
					c1[i] = '0';
				}
			}

			
			RM.stringToRegister(RM.PC, String.valueOf(c1));
			boolean run = true;
			while(run) {
				if (RM.runPC()) {
					System.out.println("VM PC: "+RM.registerToString(RM.PC));
					INFOv inf = new INFOv();
					((Object[])inf.o)[0] = this.father;
					PL.getProcess(this.father).cpu[RM.PC] = Integer.parseInt(RM.registerToString(RM.PC), 16);
					Primityvai.sukurtiResursa(VRstring.Pertraukimo_ivykis, true, this.nameI, inf);
					run = false;
				}
			}
			break;
		}
		
	}
}
