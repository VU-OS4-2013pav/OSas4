package Procesai;

import javax.crypto.spec.PSource;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.VRstring;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import rm.RM;


public class Interrupt extends ProcessBase {
	@Override
	public void execute() {
		switch(vieta) {
		case 0: 
			vieta++;
			Primityvai.prasytiResurso(VRstring.Pertraukimo_ivykis, nameI, 1);
			break;
		case 1:
			//�dentifikuoja virtual machine t�v� JG
			ResourceDescriptor resursas = null;
			//randam konkre�iai �itam procesui skirt� resurs�
			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.size(); i++)
				if (VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i).nameI == resursai.get(0).nameI) {
					resursas = VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i);
					break; //radom ko reikia
				}
			
			if (resursas == null) {
				System.out.println("Nerastas resursas Interrupt!");
			}
			
			int jgVardas = (Integer)((Object[])resursas.info.o)[0]; //I�siple�iam JG 
			
			//Apdorojami pertraukimo �vykiai
			
			Object[] cpu = PL.getProcess(jgVardas).cpu;
			
			//Jei ka�kas i� PI
			String regString = String.format("%H", ((char[])cpu[RM.PI])[0]);
			if (!regString.equals("0")) {
				if (regString.equals("1")) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Neiteisingas adresas");
				}
				else if (regString.equals("2")) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Neiteisinga operacija");
				}
				else if (regString.equals("3")) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Perpildymas");
				}
				else if (regString.equals("4")) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Dalyba i� nulio");
				}
				INFO inf = new INFOv();
				((Object[])inf.o)[0] = false;
				((Object[])inf.o)[1] = jgVardas;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.MainGovernor_pazadinimas, true, nameI, inf);
				return;
			}
			
			//Jei ka�kas i� SI
			regString = String.format("%H", ((char[])cpu[RM.SI])[0]);
			if (!regString.equals("0")) {
				if (regString.equals("1")) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Supervizorinis veiksmas");
				}
				INFO inf = new INFOv();
				((Object[])inf.o)[0] = false;
				((Object[])inf.o)[1] = jgVardas;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.MainGovernor_pazadinimas, true, nameI, inf);
				return;
				
			}
			
			//Jei TI
			regString = String.format("%H", ((char[])cpu[RM.TI])[0]);
			if (regString.equals("0")) {
				
			}
			
			//Jei ka�kas i� DI
			regString = String.format("%H", ((char[])cpu[RM.DI])[0]);
			if (!regString.equals("0")) {
				//Jei i�vedimas � ekran�
				if (regString.equals("3")) {
					vieta++;
					INFO inf = new INFOv();
					((Object[])inf.o)[0] = PPS.getProcess(jgVardas).sunus.get(0);
					((Object[])inf.o)[1] = Integer.parseInt(String.format("%H%H%H%H", 
							((char[])cpu[RM.CC])[0], 
							((char[])cpu[RM.CC])[1], 
							((char[])cpu[RM.CC])[2], 
							((char[])cpu[RM.CC])[3]), 16);
					Primityvai.sukurtiResursa(Statiniai.VRstring.Writer_pradzia, true, nameI, inf);
					return;
				}
				//Jei nuskaitymas i� klaviat�ros
				if (regString.equals("6")) {
					vieta++;
					INFO inf = new INFOv();
					((Object[])inf.o)[0] = Integer.parseInt(String.format("%H%H%H%H", 
							((char[])cpu[RM.CC])[0], 
							((char[])cpu[RM.CC])[1], 
							((char[])cpu[RM.CC])[2], 
							((char[])cpu[RM.CC])[3]), 16);
					Primityvai.sukurtiResursa(Statiniai.VRstring.VM_nori_ivedimo, true, nameI, inf);
					return;
				}
			}
			break;
		}
		
	}
}
