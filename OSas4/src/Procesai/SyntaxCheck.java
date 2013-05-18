package Procesai;

import Procesai.Statiniai.VRstring;


public class SyntaxCheck extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, nameI, 1);
			vieta++;
			break;
		}
		
	}
}
