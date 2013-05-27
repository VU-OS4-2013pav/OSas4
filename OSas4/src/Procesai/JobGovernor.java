package Procesai;

import java.util.ArrayList;

import os.Primityvai;
import os.Statiniai;
import os.Statiniai.DRint;
import os.Statiniai.DRstring;
import os.Statiniai.VRint;
import os.Statiniai.VRstring;
import resources.RSS;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFO;
import resourcesINFO.INFOv;
import rm.HDD;
import rm.Memory;
import rm.RM;


public class JobGovernor extends ProcessBase {
	HDDObject hdd;
	int kelintas;
	int[] oa2;
	@Override
	public void execute() {
		switch(vieta) {
		case 0:
			vieta++;
			Primityvai.prasytiResurso(VRstring.Info_apie_nauja_VM, nameI, 1);
			return;
		case 1:
			// issitraukiam hdd deskriptoriaus info lauka
			hdd = ((HDDObject)(RSS.list.get(DRint.HDD).resourceDescriptor.info.o));
			
			// issitraukiam info apie nauja vm info lauka ir programos varda
			INFO inf = VRSS.list.get(VRint.Info_apie_nauja_VM).resourceList.get(0).info;
			
			Primityvai.naikintiResursa(VRSS.list.get(VRint.Info_apie_nauja_VM).resourceList.get(0).nameI);
			
			String programName = (String) (((Object[])inf.o)[0]);

			
			kelintas = -1;
			
			for (int i = 0; i < hdd.programs.size(); i++) {
				//System.out.println(programName + " == " + hdd.programs.get(i).name);
				if (programName.equals(hdd.programs.get(i).name)) {
					//System.out.println("AS JAAA RAAAADAAAAUUUUUUU!!!!");
					kelintas = i;
					break;
				}
			}
			if (kelintas >= 0) {
				vieta++;
				Primityvai.prasytiResurso(DRstring.Vartotojo_atmintis, nameI, hdd.programs.get(kelintas).oa+2);
				return;
			}
			else {
				vieta = 0;
				System.out.println("JG. Programa nerasta.");
				return;
			}

		case 2:
			// TVARKOMA PTR LENTELE
			/*
			 * oa[0]  stekas
			 * oa[1..size-2] programa
			 * oa[size-1] puslapiavimo lentele = ptr
			 */
			char[] c = new char[4];
			int last = -1;
			oa2 = new int[oa.length];
			// adreso virtualizacija - skirta PTR lentelei
			oa2[oa2.length-1] = oa[oa.length-1]*0x100+0x8000;
			
			// imam zodi ptr lenteleje
			int ptr = this.oa2[oa2.length-1];
			
			for (int i = 0; i < oa2.length-1; i++) {
				// adreso virtualizacija - isskirti blokai
				
				oa2[i] = oa[i]*0x100+0x8000;
				//System.out.println("JG:::: oa2[i]="+oa2[i]);
				// i char'a
				for (int j = Integer.toHexString(this.oa2[i]).length()-1; j >= 0; j--) {
					last = j;
					c[j] = (Integer.toHexString(this.oa2[i])).charAt(j);
				}
				if (last > 0) {
					for (int j = last-1; j >= 0; j--) {
						c[j] = '0';
					}
				}
				Memory.get()[ptr].setWord(c);
				c = new char[4];
				ptr++;
			}
			
			oa2[oa2.length-1] = ptr;
			
//			// ptr sutvarkymas || oa[length-1]
//			for (int j = Integer.toHexString(ptr).length()-1; j >= 0; j--) {
//				last = j;
//				c[j] = (Integer.toHexString(ptr).charAt(j));
//			}
//			if (last > 0) {
//				for (int j = last-1; j >= 0; j--) {
//					c[j] = '0';
//				}
//			}
			
			//Memory.get()[oa2[oa2.length-1]].setWord(c);

			vieta++;
			Primityvai.prasytiResurso(DRstring.Kanalu_irenginys, nameI, 1);
			return;
		case 3:
			// turim ptr su vartotojo atmintim - i kur
			// turim hdd deskriptoriu su paskirtais blokais ir hdd atminti - is kur
			
			// surenkam hdd blokus is kuriu kopijuosim
			ArrayList<Integer> hdm = new ArrayList<Integer>();
			for (int i = 0; i < hdd.hdd.size(); i++) {
				if (hdd.hdd.get(i) == hdd.programs.get(kelintas).nr) {
					hdm.add(i);
					if (hdm.size() == hdd.programs.get(kelintas).memHDD) {
						break;
					}
				}
			}
			/*
			 #!xy	Bloko numeris xy
			 #*xy	Þodþio numeris xy
			 */
			int kelintasDEM = 0;
			int pc = -1;
			int iKur = oa2[1];
			// nustatom is nuo kur pradedam mesti koda - +3 praleidzia #STR, bloku skaiciu ir programos varda
			int isKur = hdm.get(0)*0x0100+3;
			
			while (!String.valueOf(HDD.get()[isKur].getWord()).equals("#END")) {
				if (HDD.get()[isKur].getWord()[0] == '#') {
					char[] bl = new char[2];
					bl[0] = HDD.get()[isKur].getWord()[2];
					bl[1] = HDD.get()[isKur].getWord()[3];
					kelintasDEM++;
					
					
					if (HDD.get()[isKur].getWord()[1] == '!') { // bloko keitimas
						if (kelintasDEM == 1)
							pc = Integer.valueOf(String.valueOf(bl), 16)*0x100;
						iKur = hdm.get(Integer.valueOf(String.valueOf(bl), 16));
						isKur++;
					}
					else if (HDD.get()[isKur].getWord()[1] == '*') { // zodzio keitimas
						if (kelintasDEM == 1)
							pc = 0x100+Integer.valueOf(String.valueOf(bl), 16);
						else if (kelintasDEM == 2)
							pc = pc+Integer.valueOf(String.valueOf(bl), 16);
						iKur = (iKur / 0x100)*0x100 + Integer.valueOf(String.valueOf(bl), 16);
						isKur++;
					}
					
					//System.out.println("iKUR JG!!!!!!!!!!!!!!!!!!!!!!!!!"+iKur);
				}
				Memory.get()[iKur].setWord(HDD.get()[isKur].getWord());
				iKur++;
				isKur++;
			}
			for (int i = 0; i < cpu.length; i++)
				cpu[i] = 0;
			this.cpu[RM.SP] = 0x00FF;
			this.cpu[RM.PTR] = oa2[oa2.length-1];
			this.cpu[RM.PC] = pc;
			//System.out.println("=============================PC: "+this.cpu[RM.PC]+"   PTR: "+this.cpu[RM.PTR]);
			
			vieta = 6;
			Primityvai.atlaisvintiResursa(DRstring.Kanalu_irenginys, this.nameI);
			return;
		case 4:
			int resNameI = -1;
			INFO inf2 = null;
			
			for (int i = 0; i < this.resursai.size(); i++) {
				if (this.resursai.get(i).nameO.equals(VRstring.Pranesimas_apie_pertraukima)) {
					resNameI = this.resursai.get(i).nameI;
					break;
				}
			}
			
			for (int i = 0; i < VRSS.list.get(VRint.Pranesimas_apie_pertraukima).resourceList.size(); i++) {
				if (VRSS.list.get(VRint.Pranesimas_apie_pertraukima).resourceList.get(i).nameI == resNameI) {
					inf2 = VRSS.list.get(VRint.Pranesimas_apie_pertraukima).resourceList.get(i).info;
				}
			}
			
			if (inf2 != null) {
				if ((boolean)(((Object[])inf2.o)[0]) == true) { // baigti VM
					Primityvai.naikintiProcesa(this.sunus.get(0), this);
					
					Primityvai.atlaisvintiResursa(DRstring.Vartotojo_atmintis, this.nameI, true);
					
					INFOv inf3 = new INFOv();
					((Object[])inf3.o)[0] = false;
					((Object[])inf3.o)[1] = this.nameI;
					
					vieta++;
					Primityvai.sukurtiResursa(VRstring.MainGovernor_pazadinimas, true, this.nameI, inf3);
					return;
				}
				else { // pratesti VM
					vieta = 4;
					Primityvai.aktyvuotiProcesa(this.sunus.get(0));
					Primityvai.prasytiResurso(VRstring.Pranesimas_apie_pertraukima, nameI, 1);
					return;
				}
			}
			else 
				System.out.println("JobGovernor klaida. Resursas Pranesimas apie pertraukima nerastas.");
			return;
		case 5:
			Primityvai.prasytiResurso(VRstring.Neegzistuojantis, nameI, 1);
			return;
		case 6:
			//vieta++;
			vieta = 4;
			Primityvai.sukurtiProcesa(Statiniai.Pstring.VirtualMachine, this.nameI, 3);			
			Primityvai.prasytiResurso(VRstring.Pranesimas_apie_pertraukima, nameI, 1);
			return;
		}
		
	}
}
