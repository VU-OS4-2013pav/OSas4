package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFOhdd;
import rm.ChannelDevice;
import rm.HDD;
import rm.Memory;


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
			if (String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals("#END")) {
				//Atlaisvinamas kanal� �renginys
				vieta++;
				Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			} else {
				//Blokuojasi ir laukia klaviat�ros pertraukimo
				Statiniai.readMem++;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			}
			
			break;
		case 3:
			vieta++;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, null);
			break;
		case 4: 
			//Blokuojasi ir laukia sintaks� patikrinta resurso
			vieta++;
			Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
			break;
		case 5:
			//Blokuojasi ir laukia kanal� �renginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 6:
			//Tikrinama ar buvo klaidu ar nebuvo
			ResourceDescriptor sintaksesResursas = null;
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Sintakse_patikrinta) {
					for (int j = 0; j < VRSS.list.get(Statiniai.VRint.Sintakse_patikrinta).resourceList.size(); j++)
						if (VRSS.list.get(Statiniai.VRint.Sintakse_patikrinta).resourceList.get(j).nameI == resursai.get(i).nameI) {
							sintaksesResursas = VRSS.list.get(Statiniai.VRint.Sintakse_patikrinta).resourceList.get(j);
							break;
						}
					break;
				}
			if (sintaksesResursas == null)
				System.out.println("Input stream neturi sintaks�s resurso.. Baisi klaida");
			if ((boolean)sintaksesResursas.info.o) {
				//Jei visa sintaks� teisinga
				Primityvai.prasytiResurso(Statiniai.DRstring.HDD, nameI, 1);
				vieta++;
			} else {
				//Jei sintaks� neteisinga
				System.out.println("U�duotyje buvo klaid�!");
				Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, this);
				Primityvai.sukurtiResursa(Statiniai.VRstring.InputStream_pabaiga, true, father, null);
			}
			break;
		case 7:
			//Kopijuoja u�duot� � HDD
			int blokas;
			HDDObject hdd = null;
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.DRstring.HDD) {
					hdd = ((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o);
					break;
				}
			//gaunam kuriam bloke programa
			int kurisBlokas = 0;
			for (int i = 0; i < hdd.HDD_SIZE; i++)
				if (hdd.hdd.get(i) == programaHDD) {
					kurisBlokas = i;
					break;
				}
			
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 1);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 2);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Statiniai.vietaMem);
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, kurisBlokas*255);
			ChannelDevice.c = nuskaitytiZodziai; 
			ChannelDevice.runDevice();
			
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, this);
			Primityvai.sukurtiResursa(Statiniai.VRstring.InputStream_pabaiga, true, father, null);
			break;
		}
		
	}
}
