package os;

import Procesai.PPS;


public class Planuotojas {
	public static void planuok() {

		System.out.println("Planuoju...");
		int max = -1, kelintas = -1, runPrioritetas = -1, runKelintas = -1;
		
		//Bëgam per visà PPS sàraðà ir ieðkom ar kà nors galim vykdyt
		for(int i = 0; i < PPS.list.size(); i++) {
			
			//Tikrinam ar procesas neturi bûsenos RUN
			if (PPS.list.get(i).busena == Statiniai.ProcessState.RUN) {
				runPrioritetas = PPS.list.get(i).prioritetas;
				runKelintas = i;
			}
			
			//Pasiþiûrim ar proceso prioritetas didesnis uþ iki ðiol rasto ir ar jo bûsena READY
			if ((PPS.list.get(i).prioritetas > max) && (PPS.list.get(i).busena == Statiniai.ProcessState.READY)) {
				max = PPS.list.get(i).prioritetas;
				kelintas = i;
			}
		}
		
		if (kelintas > -1) { //ar buvo rastas procesas, kurá galima vykdyti
			
			//Pasiþiûrim ar RUN proceso duomenys sutampa su rasto proceso, kurá dabar reikia vykdyti duomenimis
			if ((max == runPrioritetas) && (kelintas == runKelintas) && (max != -1))
				PPS.list.get(kelintas).execute();
			else {
				if (runKelintas > -1) //Jei buvo rastas RUN procesas
					//RUN procesà þymim pasiruoðusiu
					PPS.list.get(runKelintas).busena = Statiniai.ProcessState.READY;
				
				//Keièiam proceso bûsenà á RUN
				PPS.list.get(kelintas).busena = Statiniai.ProcessState.RUN;
				
				//Vykdom procesà
				PPS.list.get(kelintas).execute();
			}
		}
	}
}
