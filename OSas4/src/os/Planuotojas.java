package os;

import Procesai.PPS;


public class Planuotojas {
	public static void planuok() {

		System.out.println("Planuoju...");
		int max = -1, kelintas = -1, runPrioritetas = -1, runKelintas = -1;
		
		//B�gam per vis� PPS s�ra�� ir ie�kom ar k� nors galim vykdyt
		for(int i = 0; i < PPS.list.size(); i++) {
			
			//Tikrinam ar procesas neturi b�senos RUN
			if (PPS.list.get(i).busena == Statiniai.ProcessState.RUN) {
				runPrioritetas = PPS.list.get(i).prioritetas;
				runKelintas = i;
			}
			
			//Pasi�i�rim ar proceso prioritetas didesnis u� iki �iol rasto ir ar jo b�sena READY
			if ((PPS.list.get(i).prioritetas > max) && (PPS.list.get(i).busena == Statiniai.ProcessState.READY)) {
				max = PPS.list.get(i).prioritetas;
				kelintas = i;
			}
		}
		
		if (kelintas > -1) { //ar buvo rastas procesas, kur� galima vykdyti
			
			//Pasi�i�rim ar RUN proceso duomenys sutampa su rasto proceso, kur� dabar reikia vykdyti duomenimis
			if ((max == runPrioritetas) && (kelintas == runKelintas) && (max != -1))
				PPS.list.get(kelintas).execute();
			else {
				if (runKelintas > -1) //Jei buvo rastas RUN procesas
					//RUN proces� �ymim pasiruo�usiu
					PPS.list.get(runKelintas).busena = Statiniai.ProcessState.READY;
				
				//Kei�iam proceso b�sen� � RUN
				PPS.list.get(kelintas).busena = Statiniai.ProcessState.RUN;
				
				//Vykdom proces�
				PPS.list.get(kelintas).execute();
			}
		}
	}
}
