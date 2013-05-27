package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import rm.ChannelDevice;
import rm.Memory;


public class InputStream extends ProcessBase {
	int nuskaitytiZodziai, nuoKur;
	boolean nuskaitymasBaigtas = false;
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			nuskaitytiZodziai = 0;
			nuoKur = Statiniai.readMem;
			//Blokuojasi ir laukia kanalø árenginys
			vieta = 2;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			return;
		/*case 1:
			vieta++;
			if (Statiniai.readMem == Statiniai.vietaMem) {
				//Blokuojasi ir laukia klaviatûros pertraukimas
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			}
			break;*/
		case 2:
			//System.out.println("IS case2------------------------");
			if (Statiniai.readMem < Statiniai.vietaMem && !nuskaitymasBaigtas) {
				for (int i = Statiniai.readMem; i < Statiniai.vietaMem; i++) {
					//System.out.println("for'as case2==========");
					//Padidina nuskaitytø þodþiø skaièiø
					nuskaitytiZodziai++;
					//Tikrinam ar nuskaityta komanda nëra #END
					if (String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals("#END")) {
						Statiniai.readMem++;
						//Atlaisvinamas kanalø árenginysmes
						vieta++;
						nuskaitymasBaigtas = true;
						Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
						return;
					} else {	
						Statiniai.readMem++;
					}
				}
			}
			
		/*	if (!nuskaitymasBaigtas) {
				vieta = 2;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			}
			else {
				vieta = 4;
				System.out.println("IS kuria sintakses tikrinima!!!");
				INFO inf = new INFOv();
				((Object[])inf.o)[0] = nuoKur;
				((Object[])inf.o)[1] = nuskaitytiZodziai;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, inf);
				break;
			}
			*/
			return;
		case 3:
			//System.out.println("nuskaityti zodziai-----------------: "+nuskaitytiZodziai);
			if (!nuskaitymasBaigtas) {
				vieta = 2;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
				return;
			}
			else {
				vieta = 5;
				INFO inf = new INFOv();
				((Object[])inf.o)[0] = nuoKur;
				((Object[])inf.o)[1] = nuskaitytiZodziai;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Sintakses_tikrinimas, true, nameI, inf);
				return;
			}
			
			
		/*case 4:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
			break;*/
		case 5: 
			//Blokuojasi ir laukia sintaksë patikrinta resurso
			vieta = 6;
			Primityvai.prasytiResurso(VRstring.Sintakse_patikrinta, nameI, 1);
			return;
		case 6:
			//Blokuojasi ir laukia kanalø árenginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			return;
		case 7:
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
				vieta++;
				Primityvai.prasytiResurso(Statiniai.DRstring.HDD, nameI, 1);
				return;
			} else {
				//Jei sintaksë neteisinga
				System.out.println("Uþduotyje buvo klaidø!");
				vieta = 9;
				Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
				return;
			}
			
		case 8:
			//Kopijuoja uþduotá á HDD
			HDDObject hdd = null;
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.DRstring.HDD) {
					hdd = ((HDDObject)(RSS.list.get(Statiniai.DRint.HDD).resourceDescriptor.info).o);
					break;
				}
			
			for (int i = 0; i < hdd.programs.size(); i++) 
				if (hdd.programs.get(i).nr == programaHDD) {
					hdd.programs.get(i).name = String.valueOf(Memory.get()[nuoKur + 2].getWord());
					
					char[] bl = new char[2];
					bl[0] = Memory.get()[nuoKur + 1].getWord()[2];
					bl[1] = Memory.get()[nuoKur + 1].getWord()[3];
					hdd.programs.get(i).oa = Integer.parseInt(String.valueOf(bl), 16);
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
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, nuoKur);
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, kurisBlokas*256);
			ChannelDevice.c = nuskaitytiZodziai; 
			ChannelDevice.runDevice();
			
			vieta = 9;
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			
			return;
		case 9:
			vieta = 10;
			Primityvai.sukurtiResursa(Statiniai.VRstring.InputStream_pabaiga, true, father, null);
			return;
		case 10:
			Primityvai.prasytiResurso(Statiniai.VRstring.Neegzistuojantis, nameI, 1);
			return;
		}
		
	}
}
