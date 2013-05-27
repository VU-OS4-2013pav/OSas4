package os;

import os.Statiniai.Pint;
import os.Statiniai.Pstring;
import os.Statiniai.VRint;
import os.Statiniai.VRstring;
import gui.InputForm;
import Procesai.PL;
import Procesai.PPS;
import Procesai.ProcessBase;


public class Planuotojas {
	public static void planuok() {


		//System.out.println("Planuoju...");
		int max = -1, kelintas = -1, runPrioritetas = -1, runKelintas = -1;
		
		//B�gam per vis� PPS s�ra�� ir ie�kom ar k� nors galim vykdyt
		for(int i = 0; i < PPS.list.size(); i++) {
//			if (PPS.list.get(i).busena == Statiniai.ProcessState.READY || PPS.list.get(i).busena == Statiniai.ProcessState.RUN)
//				System.out.println("PROCESAS SU '"+PPS.list.get(i).busena+"' | nameO: "+PPS.list.get(i).nameO+". priority: "+PPS.list.get(i).prioritetas);
//			
			//Tikrinam ar procesas neturi b�senos RUN
			if (PPS.list.get(i).busena == Statiniai.ProcessState.RUN) {
				runPrioritetas = PPS.list.get(i).prioritetas;
				runKelintas = i;
				/*if (runPrioritetas > max) {
					max = runPrioritetas;
					kelintas = runKelintas;
				}*/
			}
			
			//Pasi�i�rim ar proceso prioritetas didesnis u� iki �iol rasto ir ar jo b�sena READY
			if ((PPS.list.get(i).prioritetas > max) && (PPS.list.get(i).busena == Statiniai.ProcessState.READY)) {
				max = PPS.list.get(i).prioritetas;
				kelintas = i;
			}
		}
		// INPUT PALEIDIMAS! NEISTRINTI!!!! INPUT PRIORITETO NIEKUR NEKEISTI!!!!
		if (Primityvai.processId >= 10 && !PL.processList.get(Pint.INPUT).processList.isEmpty()) {
			InputForm.refreshAll();
			if (runKelintas > -1)
				PPS.list.get(runKelintas).busena = Statiniai.ProcessState.READY;
			PL.getProcess(9).busena = Statiniai.ProcessState.RUN;
			PL.getProcess(9).execute();
			PL.getProcess(9).busena = Statiniai.ProcessState.READY;
			if (runKelintas > -1)
				PPS.list.get(runKelintas).busena = Statiniai.ProcessState.RUN;
		}
		
		if (kelintas > -1) { //ar buvo rastas procesas, kur� galima vykdyti
			
			
			if (max > runPrioritetas) { // leidziam nauja procesa, sustabdzius RUN'a
				// keiciam seno proceso busena i ready
				if (runKelintas > -1)
					PPS.list.get(runKelintas).busena = Statiniai.ProcessState.READY;
				
				// keiciam naujo proceso busena i run
				PPS.list.get(kelintas).busena = Statiniai.ProcessState.RUN;
				
				//Vykdom proces�
				if (PPS.list.get(kelintas).nameO.equals(Pstring.VirtualMachine) 
						|| PPS.list.get(kelintas).nameO.equals(Pstring.JobGovernor)) {
					// permetam VM i saraso gala
					ProcessBase proc = PPS.list.get(kelintas);
					PPS.list.remove(kelintas);
					PPS.list.add(proc);
					// vykdom
					InputForm.refreshAll();
					System.out.println("Vykdau " + PPS.list.get(PPS.list.size()-1).nameO);
					PPS.list.get(PPS.list.size()-1).execute();
				}
				else {
					InputForm.refreshAll();
					System.out.println("Vykdau " + PPS.list.get(kelintas).nameO);
					PPS.list.get(kelintas).execute();
				}
				

			}
			else { // vel leidziam run procesa
				InputForm.refreshAll();
				System.out.println("Vykdau " + PPS.list.get(runKelintas).nameO);
				PPS.list.get(runKelintas).execute();
			}
		}
		else {
			InputForm.refreshAll();
			System.out.println("Vykdau " + PPS.list.get(runKelintas).nameO);
			//PPS.list.get(runKelintas).execute();
		}
			
			
//			//Pasi�i�rim ar RUN proceso duomenys sutampa su rasto proceso, kur� dabar reikia vykdyti duomenimis
//			if ((max == runPrioritetas) && (kelintas == runKelintas) && (max != -1)) {
//				// i SITA SAKA NIEKUOMET NEINA
//				InputForm.refreshAll();
//				PPS.list.get(kelintas).execute();
//			}
//				
//			else {
//				if (runKelintas > -1){ //Jei buvo rastas RUN procesas 
//					//RUN proces� �ymim pasiruo�usiu
//					PPS.list.get(runKelintas).busena = Statiniai.ProcessState.READY;
//					
//				}
//					//Kei�iam proceso b�sen� � RUN
//				PPS.list.get(kelintas).busena = Statiniai.ProcessState.RUN;
//				
//				//Vykdom proces�
//				InputForm.refreshAll();
//				System.out.println("Vykdau " + PPS.list.get(kelintas).nameO);
//				PPS.list.get(kelintas).execute();
//			}
		//}
		//System.out.println("Baigiau planuoti.");
	}
}
