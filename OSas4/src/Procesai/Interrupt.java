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
			//Ádentifikuoja virtual machine tëvà JG
			ResourceDescriptor resursas = null;
			//randam konkreèiai ðitam procesui skirtà resursà
			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.size(); i++)
				if (VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i).nameI == resursai.get(0).nameI) {
					resursas = VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i);
					break; //radom ko reikia
				}
			
			if (resursas == null) {
				System.out.println("Nerastas resursas Interrupt!");
			}
			
			int jgVardas = (Integer)((Object[])resursas.info.o)[0]; //Iðsipleðiam JG 
			
			//Apdorojami pertraukimo ávykiai
			
			Object[] cpu = PL.getProcess(jgVardas).cpu;
			
			//Jei kaþkas ið PI
			int regInt = (Integer)cpu[RM.PI];
			if (regInt > 0) {
				if (regInt == 1) {
					System.out.println("VM baigia darbà aptikus pertraukimà: Neiteisingas adresas");
				}
				else if (regInt == 2) {
					System.out.println("VM baigia darbà aptikus pertraukimà: Neiteisinga operacija");
				}
				else if (regInt == 3) {
					System.out.println("VM baigia darbà aptikus pertraukimà: Perpildymas");
				}
				else if (regInt == 4) {
					System.out.println("VM baigia darbà aptikus pertraukimà: Dalyba ið nulio");
				}
				INFO inf = new INFOv();
				((Object[])inf.o)[0] = false;
				((Object[])inf.o)[1] = jgVardas;
				vieta = 0;
				Primityvai.sukurtiResursa(Statiniai.VRstring.MainGovernor_pazadinimas, true, nameI, inf);
				return;
			}
			
			//Jei kaþkas ið SI
			regInt = (Integer)cpu[RM.SI];
			if (regInt > 0) {
				if (regInt == 1) {
					System.out.println("VM baigia darbà aptikus pertraukimà: Supervizorinis veiksmas");
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
			
			//Jei kaþkas ið DI
			regInt = (Integer)cpu[RM.DI];
			if (regInt > 0) {
				//Jei iðvedimas á ekranà
				if (regInt == 3) {
					vieta = 0;
					INFO inf = new INFOv();
					((Object[])inf.o)[0] = PPS.getProcess(jgVardas).sunus.get(0);
					((Object[])inf.o)[1] = (Integer)cpu[RM.CC];
					Primityvai.sukurtiResursa(Statiniai.VRstring.Writer_pradzia, true, nameI, inf);
					return;
				}
				//Jei nuskaitymas ið klaviatûros
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
