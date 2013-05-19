package os;

import os.Statiniai.ProcessState;
import Procesai.PPS;


public class Planuotojas {
	public static void planuok() {
		int max = -1, kelintas = -1;
		for(int i = 0; i < PPS.list.size(); i++) {
			if ((PPS.list.get(i).prioritetas > max) && (PPS.list.get(i).busena == Statiniai.ProcessState.READY)) {
				max = PPS.list.get(i).prioritetas;
				kelintas = i;
			}
		}
		
		if (kelintas > -1) {
			PPS.list.get(kelintas).execute();
		}
	}
}
