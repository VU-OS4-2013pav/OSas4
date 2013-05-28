package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFOv;
import rm.ChannelDevice;


public class Swapper extends ProcessBase {
	ResourceDescriptor tempResource;
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Swapper_pradzia, nameI, 1);
			return;
		case 1:
			ResourceDescriptor resource;
			int id = -1;
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO.equals(Statiniai.VRstring.Swapper_pradzia))
					id = resursai.get(i).nameI;

			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.Swapper_pradzia).resourceList.size(); i++) {
				tempResource = VRSS.list.get(Statiniai.VRint.Swapper_pradzia).resourceList.get(i);
				if (!tempResource.laisvas && (tempResource.nameI == id)) {
					resource = tempResource;
					break;
				}
			}
			
			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, this.nameI, 1);
			return;
		case 2:
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, (Integer)((Object[])tempResource.info.o)[0]);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, (Integer)((Object[])tempResource.info.o)[1]);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, (Integer)((Object[])tempResource.info.o)[2]);
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, (Integer)((Object[])tempResource.info.o)[3]);
			ChannelDevice.c = (Integer)((Object[])tempResource.info.o)[4];
			ChannelDevice.runDevice();
			
			vieta++;
			Primityvai.atlaisvintiResursa(DRstring.Kanalu_irenginys, this.nameI);
			return;
		case 3:
			int jg = (Integer)((Object[])tempResource.info.o)[5];
			
			Primityvai.naikintiResursa(tempResource.nameI);
			
			INFOv in = new INFOv();
			((Object[])in.o)[0] = false; //VM darbo baigti nereikia
			((Object[])in.o)[1] = jg;
			
			vieta = 0;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, in);
			return;
		}
		
	}
}
