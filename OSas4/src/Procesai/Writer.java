package Procesai;

import Procesai.Statiniai.VRstring;


public class Writer extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0: 
			Primityvai.prasytiResurso(VRstring.Writer_pradzia, nameI, 1);
			vieta++;
			break;
		}
		
	}
}
