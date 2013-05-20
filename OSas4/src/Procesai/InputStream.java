package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;


public class InputStream extends ProcessBase {
	int nuskaitytiZodziai = 0;
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			//Blokuojasi ir laukia kanal� �renginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 1:
			//Blokuojasi ir laukia klaviat�ros pertraukimas
			vieta++;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			break;
		case 2:
			//Padidina nuskaityt� �od�i� skai�i�
			nuskaitytiZodziai++;
			//Tikrinam ar nuskaityta komanda n�ra #END
			//jei ne vieta = 1;
			//jei taip vieta++;
			break;
		case 3:
			//Atlaisvinamas kanal� �renginys
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			//Sukuriamas sintaks�s tikrinimas resursas
			Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, null);
			vieta++;
			//Blokuojasi ir laukia sintaks� patikrinta resurso
			Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
			break;
		case 4:
			//Blokuojasi ir laukia kanal� �renginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 5:
			//Tikrinama ar buvo klaidu ar nebuvo
			//jei buvo i�vedamas prane�imas su klaidom vieta = 7
			//jei nebuvo Blokuojasi ir laukia HDD, vieta++
			break;
		case 6:
			//Kopijuoja u�duot� � HDD
			//vieta++
			break;
		case 7:
			vieta++;
			//Atlaisvinamas kanal� �renginys
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			break;
		case 8:
			Primityvai.sukurtiResursa(Statiniai.VRstring.InputStream_pabaiga, true, nameI, null);
			//Blokuojasi, laukia neegzistuoja
			break;
		}
		
	}
}
