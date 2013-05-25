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
	boolean nuskaitymasBaigtas = false;
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			//Blokuojasi ir laukia kanalø árenginys
			vieta = 2;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		/*case 1:
			vieta++;
			if (Statiniai.readMem == Statiniai.vietaMem) {
				//Blokuojasi ir laukia klaviatûros pertraukimas
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			}
			break;*/
		case 2:
			System.out.println("IS case2------------------------");
			if (Statiniai.readMem < Statiniai.vietaMem && !nuskaitymasBaigtas) {
				for (int i = Statiniai.readMem; i < Statiniai.vietaMem; i++) {
					System.out.println("for'as case2==========");
					//Padidina nuskaitytø þodþiø skaièiø
					nuskaitytiZodziai++;
					//Tikrinam ar nuskaityta komanda nëra #END
					if (String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals("#END")) {
						Statiniai.readMem++;
						//Atlaisvinamas kanalø árenginys
						vieta++;
						nuskaitymasBaigtas = true;
						Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
					} else {	
						Statiniai.readMem++;
					}
				}
			}
			
			if (!nuskaitymasBaigtas) {
				vieta = 2;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			}
			else {
				vieta = 4;
				System.out.println("IS kuria sintakses tikrinima!!!");
				Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, null);
				break;
			}
			
			break;
		case 3:
			System.out.println("nuskaityti zodziai-----------------: "+nuskaitytiZodziai);
			if (!nuskaitymasBaigtas) {
				vieta = 2;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			}
			else {
				vieta = 4;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, null);
				Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
				break;
			}
			
			break;
	/*	case 3:
			vieta++;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, null);
			break;*/
		case 4: 
			//Blokuojasi ir laukia sintaksë patikrinta resurso
			vieta = 5;
			Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
			break;
		case 5:
			//Blokuojasi ir laukia kanalø árenginys
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
				System.out.println("Input stream neturi sintaksës resurso.. Baisi klaida");
			if ((boolean)sintaksesResursas.info.o) {
				//Jei visa sintaksë teisinga
				System.out.println("Sintaksë teisinga!");
				/*Primityvai.prasytiResurso(Statiniai.DRstring.HDD, nameI, 1);
				vieta++;*/
			} else {
				//Jei sintaksë neteisinga
				System.out.println("Uþduotyje buvo klaidø!");
				vieta = 8;
				Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
				
			}
			break;
		case 7:
			//Kopijuoja uþduotá á HDD
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
		case 8:
			vieta = 9;
			Primityvai.sukurtiResursa(Statiniai.VRstring.InputStream_pabaiga, true, father, null);
			break;
		}
		
	}
}
