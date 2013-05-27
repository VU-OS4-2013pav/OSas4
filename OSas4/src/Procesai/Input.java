package Procesai;

import os.Primityvai;
import os.Statiniai.VRstring;
import resourcesINFO.INFOv;
import rm.RM;

public class Input extends ProcessBase {
	 public Input() {
		 super();
	 }

	@Override
	public void execute() {
		//System.out.println("Specialaus proceso 'Input' pradzia. ");
		if (RM.regOS > 0) {
			System.out.println("Aptiktas klaviaturos pertraukimas.");
			for (int i = RM.regOS-1; i >= 0; i--) {
				INFOv inf = new INFOv();
				RM.regOS--;
				Primityvai.sukurtiResursa(VRstring.Klaviaturos_pertraukimas, true, this.nameI, inf);
			}
		}
		//System.out.println("Specialaus proceso 'Input' pabaiga. ");
		return;

		
	}

}
