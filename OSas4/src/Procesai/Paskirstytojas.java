package Procesai;
import resources.RSS;
import resources.VRSS;


public class Paskirstytojas {
	public static void skirstyk() {
		for (int i = 0; i < VRSS.list.size(); i++) {
			int maxPrioritetas = 0, kelintas = -1; //rastas didþiausias prioritetas ir kelintas procesas sàraðe
			
			//Jei yra resursas, einam per visus jo laukianèius procesus, procesui su didþiausiu prioritetu já paskiriame
			if (!VRSS.list.get(i).resourceList.isEmpty()) {
				for (int j = 0; j < VRSS.list.get(i).processList.size(); j++) {
					if (VRSS.list.get(i).processList.get(j).process.prioritetas > maxPrioritetas) {
						maxPrioritetas = VRSS.list.get(i).processList.get(j).process.prioritetas;
						kelintas = j;
					}
				}
				if (kelintas > -1) {
					System.out.println("Skirstau " + VRSS.list.get(i).processList.get(kelintas).process.nameO + 
							" Resursas: " + VRSS.list.get(i).vardas);
					
					PPS.list.add(VRSS.list.get(i).processList.get(kelintas).process);
					VRSS.list.get(i).processList.get(kelintas).process.busena = Statiniai.ProcessState.READY;
					VRSS.list.get(i).processList.remove(kelintas);
					VRSS.list.get(i).resourceList.remove(0);
				}
					
			}
		}
		
		for (int i = 0; i < RSS.list.size(); i++) {
			int maxPrioritetas = 0, kelintas = -1; //rastas didþiausias prioritetas ir kelintas procesas sàraðe
			if (RSS.list.get(i).resourceDescriptor.laisvas) {
				for (int j = 0; j < RSS.list.get(i).list.size(); j++) {
					if (RSS.list.get(i).list.get(j).process.prioritetas > maxPrioritetas) {
						maxPrioritetas = RSS.list.get(i).list.get(j).process.prioritetas;
						kelintas = j;
					}
				}
				if (kelintas > -1) {
					System.out.println("Skirstau " + RSS.list.get(i).list.get(kelintas).process.nameO + 
							" Resursas: " + RSS.list.get(i).resourceDescriptor.nameO);
					
					PPS.list.add(RSS.list.get(i).list.get(kelintas).process);
					RSS.list.get(i).list.get(kelintas).process.busena = Statiniai.ProcessState.READY;
					RSS.list.get(i).list.remove(kelintas);
				}
			}
		}
		
	}
}
