package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import rm.ChannelDevice;


public class Writer extends ProcessBase {
	ResourceDescriptor tempResource;
	
	int jg;
	public void execute() {
		switch(vieta) {
		case 0: 
			//Blokuojasi ir laukia writer pradþia
			vieta++;
			Primityvai.prasytiResurso(VRstring.Writer_pradzia, nameI, 1);
			return;
		case 1:
			//Blokuojasi ir laukia kanalø árenginys
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			break;
		case 2:
			//Iðveda ið atminties á ekranà
			//INFO laukas turëtø bûti: (beabejonës galima ir pakeisti)
			//o[0] IA
			//o[1] CC
			//o[2] IO
			//o[3] OO
			//Pasigriebiam resursà
			ResourceDescriptor resource;
			int id = -1;
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Writer_pradzia)
					id = resursai.get(i).nameI;

			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.Writer_pradzia).resourceList.size(); i++) {
				tempResource = VRSS.list.get(Statiniai.VRint.Writer_pradzia).resourceList.get(i);
				if (!tempResource.laisvas && (tempResource.nameI == id)) {
					resource = tempResource;
					break;
				}
			}
			ChannelDevice.setValueOfChannel(0, (int)((Object[])tempResource.info.o)[0]);
			ChannelDevice.c = (int)((Object[])tempResource.info.o)[1];
			ChannelDevice.setValueOfChannel(2, (int)((Object[])tempResource.info.o)[2]);
			ChannelDevice.setValueOfChannel(3, (int)((Object[])tempResource.info.o)[3]);
			jg = (Integer)((Object[])tempResource.info.o)[4];
			//Po ðito stebuklo turëtø bûti iðvedimas
			ChannelDevice.runDevice();
			Primityvai.naikintiResursa(tempResource.nameI);
			vieta++;
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			return;
		case 3:
			INFO in = new INFO();
			((Object[])in.o)[0] = false; //VM darbo baigti nereikia
			((Object[])in.o)[1] = jg;
			vieta++;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, in);
			return;
		case 4:
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Writer_pradzia, nameI, 1);
			return;
		}
	}
}
