package Procesai;

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
			int regInt = (Integer)cpu[RM.PI];
			if (regInt > 0) {
				if (regInt == 1) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Neiteisingas adresas");
				}
				else if (regInt == 2) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Neiteisinga operacija");
				}
				else if (regInt == 3) {
					System.out.println("VM baigia darb� aptikus pertraukim�: Perpildymas");
				}
				else if (regInt == 4) {
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
			regInt = (Integer)cpu[RM.SI];
			if (regInt > 0) {
				if (regInt == 1) {
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
			regInt = (Integer)cpu[RM.TI];
			if (regInt == 0) {
				// sustabdom jo VM
				Primityvai.stabdytiProcesa(PL.getProcess(jgVardas).sunus.get(0));
				// pakeiciam JG prioriteta i 1
				Primityvai.keistiPrioriteta(jgVardas, 1);
				// atstatom timeri i F
				PL.getProcess(jgVardas).cpu[RM.TI] = 0xF;
				// sukuriam pranesima apie pertraukima to JG
				INFO inf = new INFOv();
				((Object[])inf.o)[0] = false;
				((Object[])inf.o)[1] = jgVardas;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.Pranesimas_apie_pertraukima, true, nameI, inf);
				return;
			}
			
			//Jei ka�kas i� DI
			regInt = (Integer)cpu[RM.DI];
			if (regInt > 0) {
				//Jei i�vedimas � ekran�
				if (regInt == 3) {
					vieta = 0;
					INFO inf = new INFOv();
					((Object[])inf.o)[0] = PPS.getProcess(jgVardas).sunus.get(0);
					((Object[])inf.o)[1] = (Integer)cpu[RM.CC];
					Primityvai.sukurtiResursa(Statiniai.VRstring.Writer_pradzia, true, nameI, inf);
					return;
				}
				//Jei nuskaitymas i� klaviat�ros
				if (regInt == 6) {
					vieta = 0;
					INFO inf = new INFOv();
					((Object[])inf.o)[0] = (Integer)cpu[RM.CC];
					Primityvai.sukurtiResursa(Statiniai.VRstring.VM_nori_ivedimo, true, nameI, inf);
					return;
				}
			}
			break;
		}
		
	}
}
