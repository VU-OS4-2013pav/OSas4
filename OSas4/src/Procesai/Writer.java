package Procesai;

import os.Primityvai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.RSS;


public class Writer extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0: 
			vieta++;
			Primityvai.prasytiResurso(VRstring.Writer_pradzia, nameI, 1);
			break;
		case 1:
			break;
		}
	}
}
