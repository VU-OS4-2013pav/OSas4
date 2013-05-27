package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRint;
import os.Statiniai.VRstring;
import resources.VRSS;
import resourcesINFO.INFO;
import rm.Memory;


public class SyntaxCheck extends ProcessBase {
	@Override
	public void execute() {
		int nuoKur = -1, kiek = -1;
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, nameI, 1);
			return;
		case 1:
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			return;
		case 2:
			//Bëgam per visus resursus, ieðkom turimo
			for (int i = 0; i < VRSS.list.get(VRint.Sintakses_tikrinimas).resourceList.size(); i++) {
				if (VRSS.list.get(VRint.Sintakses_tikrinimas).resourceList.get(i).nameI == resursai.get(0).nameI) {
					nuoKur = (Integer)((Object[])VRSS.list.get(VRint.Sintakses_tikrinimas).resourceList.get(i).info.o)[0];
					kiek = (Integer)((Object[])VRSS.list.get(VRint.Sintakses_tikrinimas).resourceList.get(i).info.o)[1];
					break; //radom resursà, galim nebeieðkot
				}
			}
			
			if ((nuoKur == -1) && (kiek == -1)) {
				System.out.println("Sintaksës tikrinimo resursas neturi reikiamos info!!");
			}
			
			INFO inf = new INFO();
			//System.out.println("nuo kur = " + nuoKur);
			//System.out.println(Memory.get()[nuoKur].getWord().equals("#STR"));
			if (String.valueOf(Memory.get()[nuoKur].getWord()).equals("#STR")) {
				//System.out.println("if " + Memory.get()[nuoKur + 1]+ " == #" );
				//System.out.println("if " +Memory.get()[nuoKur + 1].getWord()[1] + " == B" );
				if ((Memory.get()[nuoKur + 1].getWord()[0] == '#') && (Memory.get()[nuoKur + 1].getWord()[1] == 'B')) {
					if (Character.isDigit((Memory.get()[nuoKur + 1].getWord()[2]))) {
						if (Character.isDigit((Memory.get()[nuoKur + 1].getWord()[3]))) {
							inf.o= true;
						} else {
							inf.o= false;
						}
						} else {
							inf.o= false;
						}
					} else {
						inf.o= false;
					}
				}else {
				inf.o= false;
				}
			//System.out.println("SYNTAX CHEKAS TURI::: "+resursai.size());
			Primityvai.naikintiResursa(resursai.get(0).nameI);
			vieta++;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakse_patikrinta, true, nameI, inf);
			return;
		case 3:
			vieta++;
			//System.out.println("SYNTAX CHEKAS TURI::: "+resursai.size());
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			return;
		case 4:
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, nameI, 1);
			return;
		}
		
	}
}
