package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRstring;
import os.Statiniai.VRstring;
import resources.VRSS;
import resourcesINFO.INFOv;
import rm.ChannelDevice;
import rm.Memory;
import rm.RM;


public class IInterrupt extends ProcessBase {
	//boolean naujaUþduotis, MOSpabaiga, uzduotiesPaleidimas, uzduotiesIstrinimas;
	INFOv inf =  null;

	public void execute() {
		//System.out.println("IInterrupt pradzia.");
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			return;
		case 1:
			//naikinam klaviatûros pertraukimo resursà
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Klaviaturos_pertraukimas) {
					Primityvai.naikintiResursa(resursai.get(i).nameI);
					break;
				}

			// jeigu vm nori ivedimo
			if (!VRSS.list.get(Statiniai.VRint.VM_nori_ivedimo).resourceList.isEmpty() 
					&& Character.valueOf(Memory.get()[Statiniai.readMem].getWord()[0]).equals('.') 
					&& Character.valueOf(Memory.get()[Statiniai.readMem].getWord()[1]).equals('.')) {
				Primityvai.naikintiResursa(VRSS.list.get(Statiniai.VRint.VM_nori_ivedimo).resourceList.get(0).nameI);
				vieta = 2;
				Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, this.nameI, 1);
				return;
			} 
			// jeigu nauja uzduotis
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".NEW")) {
				Statiniai.readMem++;

				Primityvai.sukurtiProcesa(Statiniai.Pstring.InputStream, this.nameI, 8); 
				vieta = 3;
				Primityvai.prasytiResurso(VRstring.InputStream_pabaiga, this.nameI, 1);
				return;
			} 
			// jeigu mos pabaiga
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".END")) {
				Statiniai.readMem++;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, this.nameI, null);
				return;
			} 
			// jeigu uzduoties paleidimas
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".OPN")) {
				Statiniai.readMem++;
				INFOv inf = new INFOv();
				((Object[])inf.o)[0] = String.valueOf(Memory.get()[Statiniai.readMem].getWord());
				Statiniai.readMem++;
				vieta = 5;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Loader_pradzia, true, this.nameI, inf);
				return;
			} 
			// jeigu uzduoties istrinimas
			else if(String.valueOf(Memory.get()[Statiniai.readMem].getWord()).equals(".DEL")) {
				Statiniai.readMem++;		
				INFOv inf = new INFOv();
				((Object[])inf.o)[0] = String.valueOf(Memory.get()[Statiniai.readMem].getWord());
				Statiniai.readMem++;
				vieta = 4;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Destroyer_XDD_pradzia, true, this.nameI, inf);
				return;
			} 
			// jeigu nesamone
			else {
				System.out.println("Neatpazinta sistemos komanda.");
				vieta = 1;
				Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
				return;
			}

		case 2:
			// issitraukiam VM inner name
			char[] vm = new char[2];
			vm[0] = Memory.get()[Statiniai.readMem].getWord()[2];
			vm[1] =	Memory.get()[Statiniai.readMem].getWord()[3];
			int vmName = Integer.parseInt(String.valueOf(vm));
			Statiniai.readMem++;
			

			// randam JG pagal VM father name
			ProcessBase jg = PL.getProcess(PL.getProcess(vmName).father);
			int iKur = (int) jg.cpu[RM.CC]; // virtualus adresas
			
			char[] c = new char[4];
			int j = 3;
			String str = Integer.toHexString(iKur);
			//if (str.length() > 0)
			for (int i = str.length() -1; i >= 0; i--) {
				c[j] = str.charAt(i);
				j--;
			}
			if (j >= 0) {
				for (int i = j; i >= 0; i--) {
					c[i] = '0';
				}
			}
			
			// kiek kopijuosim
			int kiek = Statiniai.vietaMem - Statiniai.readMem;
			
			//virtualizacija
			char[] a = { 
					Integer.toHexString((int) jg.cpu[RM.PTR]).charAt(0),
					Integer.toHexString((int) jg.cpu[RM.PTR]).charAt(1),
					c[0],
					c[1]
			};

			char[] addressR = {
					Memory.get()[Integer.parseInt(String.valueOf(a), 16)].getWord()[0],
					Memory.get()[Integer.parseInt(String.valueOf(a), 16)].getWord()[1],
					c[2],
					c[3]
			}; 

			// nustatom chaneliu reiksmes (reikia adresu virtualizacijos is VA -> RA!!!)
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 1);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 1);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Statiniai.readMem); // is kur
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, Integer.parseInt(String.valueOf(addressR), 16)); // i kur
			ChannelDevice.c = kiek; 

			// paleidziam runDevice
			ChannelDevice.runDevice();
			
			// sunaikinam VM nori ivedimo
			for (int i = 0; i < this.resursai.size(); i++) {
				if (this.resursai.get(i).nameO.equals(VRstring.VM_nori_ivedimo))
					Primityvai.naikintiResursa(this.resursai.get(i).nameI);
					
			}
			
			Statiniai.readMem =  Statiniai.readMem + kiek;

			inf = new INFOv();
			((Object[])inf.o)[0] = false;
			((Object[])inf.o)[1] = jg.nameI;
			// baigiam ivedima
			vieta = 7;
			Primityvai.atlaisvintiResursa(Statiniai.DRstring.Kanalu_irenginys, nameI);
			return;

		case 3:
			Primityvai.naikintiProcesa(this.sunus.get(0), this);
			vieta = 1;
			//naikinam input stream pabaigos resursà
			for (int i = 0; i < resursai.size(); i++) 
				if (resursai.get(i).nameO == Statiniai.VRstring.InputStream_pabaiga) {
					Primityvai.naikintiResursa(resursai.get(i).nameI);
					break;
				}
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, this.nameI, 1);
			return;
		case 4:
			vieta = 9;
			Primityvai.prasytiResurso(VRstring.Destroyer_XDD_pabaiga, this.nameI, 1);
			return;
		case 5:
			vieta = 8;
			Primityvai.prasytiResurso(VRstring.Loader_pabaiga, this.nameI, 1);
			return;
		case 6:
			vieta = 1;
			inf = null;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, this.nameI, 1);
			return;
		case 7:
			vieta = 6;
			Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, inf);
			return;
		case 8:
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Loader_pabaiga)
					Primityvai.naikintiResursa(resursai.get(i).nameI);
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			return;
		case 9:
			for (int i = 0; i < resursai.size(); i++)
				if (resursai.get(i).nameO == Statiniai.VRstring.Destroyer_XDD_pabaiga)
					Primityvai.naikintiResursa(resursai.get(i).nameI);
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Klaviaturos_pertraukimas, nameI, 1);
			return;
		}

	}
}
