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
			//Blokuojasi ir laukia kanalø árenginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 1:
			//Blokuojasi ir laukia klaviatûros pertraukimas
			vieta++;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			break;
		case 2:
			//Padidina nuskaitytø þodþiø skaièiø
			nuskaitytiZodziai++;
			//Tikrinam ar nuskaityta komanda nëra #END
			//jei ne vieta = 1;
			//jei taip vieta++;
			break;
		case 3:
			//Atlaisvinamas kanalø árenginys
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			//Sukuriamas sintaksës tikrinimas resursas
			Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, null);
			vieta++;
			//Blokuojasi ir laukia sintaksë patikrinta resurso
			Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
			break;
		case 4:
			//Blokuojasi ir laukia kanalø árenginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 5:
			//Tikrinama ar buvo klaidu ar nebuvo
			//jei buvo iðvedamas praneðimas su klaidom vieta = 7
			//jei nebuvo Blokuojasi ir laukia HDD, vieta++
			break;
		case 6:
			//Kopijuoja uþduotá á HDD
			//vieta++
			break;
		case 7:
			vieta++;
			//Atlaisvinamas kanalø árenginys
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			break;
		case 8:
			Primityvai.sukurtiResursa(Statiniai.VRstring.InputStream_pabaiga, true, nameI, null);
			//Blokuojasi, laukia neegzistuoja
			break;
		}
		
	}
}
