package Procesai;

import os.Primityvai;
import os.Statiniai.VRstring;


public class Interrupt extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0: 
			Primityvai.prasytiResurso(VRstring.Pertraukimo_ivykis, nameI, 1);
			vieta++;
			break;
		}
		
	}
}
