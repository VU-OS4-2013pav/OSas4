package Procesai;

import os.Primityvai;
import os.Statiniai.VRstring;


public class SyntaxCheck extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, nameI, 1);
			break;
		}
		
	}
}
