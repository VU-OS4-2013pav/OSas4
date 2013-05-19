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
			//Blokuojasi ir laukia writer prad�ia
			vieta++;
			Primityvai.prasytiResurso(VRstring.Writer_pradzia, nameI, 1);
			break;
		case 1:
			//Blokuojasi ir laukia kanal� �renginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 2:
			System.out.println("Kanal� �rengin� turiu!!!");
			break;
		}
	}
}
