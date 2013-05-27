package Procesai;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.VRstring;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import rm.Memory;
import rm.RM;


public class Interrupt extends ProcessBase {
	int jgVardas;
	@Override
	public void execute() {
		switch(vieta) {
		case 0: 
			vieta++;
			Primityvai.prasytiResurso(VRstring.Pertraukimo_ivykis, nameI, 1);
			return;
		case 1:
			//Ádentifikuoja virtual machine tëvà JG
			ResourceDescriptor resursas = null;
			//randam konkreèiai ðitam procesui skirtà resursà
			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.size(); i++)
				if (VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i).nameI == resursai.get(0).nameI) {
					resursas = VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i);
					Primityvai.naikintiResursa(VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i).nameI);
					break; //radom ko reikia
				}
			
			if (resursas == null) {
				System.out.println("Nerastas resursas Interrupt!");
			}
			
			jgVardas = (Integer)((Object[])resursas.info.o)[0]; //Iðsipleðiam JG 
			
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
				//Primityvai.keistiPrioriteta(jgVardas, 4);
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
					
					//o[0] IA
					//o[1] CC
					//o[2] IO
					//o[3] OO
					
					// randam JG pagal vidiná vardà
					ProcessBase jg = PL.getProcess(jgVardas);
					 int iKur = (int) jg.cpu[RM.AA]; // virtualus adresas
					   
					   char[] c = new char[4];
					   int j = 3;
					   String str = Integer.toHexString(iKur);
					   
					   for (int i = str.length() -1; i >= 0; i--) {
					    c[j] = str.charAt(i);
					    j--;
					   }
					   if (j >= 0) {
					    for (int i = j; i >= 0; i--) {
					     c[i] = '0';
					    }
					   }
					   
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
					
					   
					   
					((Object[])inf.o)[0] = Integer.parseInt(String.valueOf(addressR), 16); //Adresas nuo kurio pradedam vest
					((Object[])inf.o)[1] = jg.cpu[RM.CC]; //kiek reikia iðvest
					((Object[])inf.o)[2] = 1; //IO = 1 vedam ið vidinës
					((Object[])inf.o)[3] = 3; //OO = 2 vedam á ekranà
					((Object[])inf.o)[4] = jgVardas; //jg vidinis vardas
					
					/*((Object[])inf.o)[0] = PPS.getProcess(jgVardas).sunus.get(0);
					((Object[])inf.o)[1] = (Integer)cpu[RM.CC];*/
					Primityvai.stabdytiProcesa(PL.getProcess(jgVardas).sunus.get(0));
					Primityvai.sukurtiResursa(Statiniai.VRstring.Writer_pradzia, true, nameI, inf);
					return;
				}
				//Jei nuskaitymas ið klaviatûros
				if (regInt == 6) {
					vieta = 0;
					INFO inf = new INFOv();
					((Object[])inf.o)[0] = (Integer)cpu[RM.CC];
					Primityvai.stabdytiProcesa(PL.getProcess(jgVardas).sunus.get(0));
					Primityvai.sukurtiResursa(Statiniai.VRstring.VM_nori_ivedimo, true, nameI, inf);
					return;
				}
			}
			
			vieta = 1;
			Primityvai.prasytiResurso(VRstring.Pertraukimo_ivykis, nameI, 1);
			return;
		} 
		
	}
}
