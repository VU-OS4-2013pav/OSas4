package Procesai;
import resources.RSS;
import resources.VRSS;


public class Paskirstytojas {
	public static void skirstyk() {
		for (int i = 0; i < VRSS.list.size(); i++) {
			int maxPrioritetas = 0, kelintas = -1; //rastas didþiausias prioritetas ir kelintas procesas sàraðe
			
			//Jei yra resursas, einam per visus jo laukianèius procesus, procesui su didþiausiu prioritetu já paskiriame
			if (!VRSS.list.get(i).resourceList.isEmpty()) {
				boolean yraLaisvu = false;
				int kelintasLaisvas = 0;
				for (int j = 0; j < VRSS.list.get(i).resourceList.size(); j++)
					if (VRSS.list.get(i).resourceList.get(j).laisvas) {
						yraLaisvu = true;
						kelintasLaisvas = j;
						break;
					}
				if (yraLaisvu) {
					for (int j = 0; j < VRSS.list.get(i).processList.size(); j++) {
						if (VRSS.list.get(i).processList.get(j).process.prioritetas > maxPrioritetas) {
							maxPrioritetas = VRSS.list.get(i).processList.get(j).process.prioritetas;
							kelintas = j;
						}
					}
					if (kelintas > -1) {
						System.out.println("Skirstau " + VRSS.list.get(i).processList.get(kelintas).process.nameO + 
								" Resursas: " + VRSS.list.get(i).vardas);
						
						//Pridedam resursà á proceso turimø resursø sàraðà
						VRSS.list.get(i).processList.get(kelintas).process.addRes(VRSS.list.get(i).resourceList.get(kelintasLaisvas).nameO, VRSS.list.get(i).resourceList.get(kelintasLaisvas).nameI);
						
						//Pridedam procesà á pasiruoðusiu procesø sàraðà
						PPS.list.add(VRSS.list.get(i).processList.get(kelintas).process);
						
						//Pakeièiam proceso bûsenà á READY
						VRSS.list.get(i).processList.get(kelintas).process.busena = Statiniai.ProcessState.READY;
						
						//Iðtrinam procesà ið laukianèiø sàraðo
						VRSS.list.get(i).processList.remove(kelintas);
						
						//Paþymim resursà uþimtu
						VRSS.list.get(i).resourceList.get(kelintasLaisvas).laisvas = false;
					}
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
					
					//Pridedam resursà á proceso turimø sàraðà
					RSS.list.get(i).list.get(kelintas).process.addRes(RSS.list.get(i).resourceDescriptor.nameO, RSS.list.get(i).resourceDescriptor.nameI);
					
					//Pridedam procesà á pasiruoðusiø procesø sàraðà
					PPS.list.add(RSS.list.get(i).list.get(kelintas).process);
					
					//Pakeièiam proceso bûsenà á READY
					RSS.list.get(i).list.get(kelintas).process.busena = Statiniai.ProcessState.READY;
					
					//Iðmetam procesà ið laukianèiø sàraðo
					RSS.list.get(i).list.remove(kelintas);
					
					//TODO Paþymim resursà uþimtu.. HDD ATVEJU REIKIA PAÞYMËT TIK BLOKUS
					RSS.list.get(i).resourceDescriptor.laisvas = false;
				}
			}
		}
		
	}
}
