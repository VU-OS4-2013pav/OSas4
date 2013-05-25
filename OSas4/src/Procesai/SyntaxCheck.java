package Procesai;

import com.sun.org.apache.bcel.internal.generic.INEG;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.VRstring;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;


public class SyntaxCheck extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, nameI, 1);
			break;
		case 1:
			Primityvai.naikintiResursa(resursai.get(0).nameI);
			System.out.println("O að tikrinu sintaksæ!");
			INFO inf = new INFO();
			inf.o= false;
			vieta++;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakse_patikrinta, true, nameI, inf);
			break;
		case 2:
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, nameI, 1);
			break;
		}
		
	}
}
