package Procesai;

import os.Primityvai;
import os.Statiniai.VRstring;


public class Swapper extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			Primityvai.prasytiResurso(VRstring.Swapper_pradzia, nameI, 1);
			vieta++;
			break;
		}
		
	}
}
