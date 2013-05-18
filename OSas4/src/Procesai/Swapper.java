package Procesai;

import Procesai.Statiniai.VRstring;


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
