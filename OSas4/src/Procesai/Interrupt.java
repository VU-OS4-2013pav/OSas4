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
			//�dentifikuoja virtual machine t�v� JG
			ResourceDescriptor resursas = null;
			//randam konkre�iai �itam procesui skirt� resurs�
			for (int i = 0; i < VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.size(); i++)
				if (VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i).nameI == resursai.get(0).nameI) {
					resursas = VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i);
					Primityvai.naikintiResursa(VRSS.list.get(Statiniai.VRint.Pertraukimo_ivykis).resourceList.get(i).nameI);
					break; //radom ko reikia
				}
			
			if (resursas == null) {
				System.out.println("Nerastas resursas Interrupt!");
			}
			
			jgVardas = (Integer)((Object[])resursas.info.o)[0]; //I�siple�iam JG 
			
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
			
			//Jei ka�kas i� DI
			regInt = (Integer)cpu[RM.DI];
			if (regInt > 0) {
				//Jei i�vedimas � ekran�
				if (regInt == 3) {
					vieta = 0;
					INFO inf = new INFOv();
					
					//o[0] IA
					//o[1] CC
					//o[2] IO
					//o[3] OO
					
					// randam JG pagal vidin� vard�
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
					((Object[])inf.o)[1] = jg.cpu[RM.CC]; //kiek reikia i�vest
					((Object[])inf.o)[2] = 1; //IO = 1 vedam i� vidin�s
					((Object[])inf.o)[3] = 3; //OO = 2 vedam � ekran�
					((Object[])inf.o)[4] = jgVardas; //jg vidinis vardas
					
					/*((Object[])inf.o)[0] = PPS.getProcess(jgVardas).sunus.get(0);
					((Object[])inf.o)[1] = (Integer)cpu[RM.CC];*/
					Primityvai.stabdytiProcesa(PL.getProcess(jgVardas).sunus.get(0));
					Primityvai.sukurtiResursa(Statiniai.VRstring.Writer_pradzia, true, nameI, inf);
					return;
				}
				//Jei nuskaitymas i� klaviat�ros
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
