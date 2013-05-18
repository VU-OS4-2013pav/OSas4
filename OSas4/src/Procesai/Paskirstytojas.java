package Procesai;
import resources.RSS;
import resources.VRSS;


public class Paskirstytojas {
	public static void skirstyk() {
		for (int i = 0; i < VRSS.list.size(); i++) {
			int maxPrioritetas = 0, kelintas = -1; //rastas did�iausias prioritetas ir kelintas procesas s�ra�e
			
			//Jei yra resursas, einam per visus jo laukian�ius procesus, procesui su did�iausiu prioritetu j� paskiriame
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
						
						//Pridedam resurs� � proceso turim� resurs� s�ra��
						VRSS.list.get(i).processList.get(kelintas).process.addRes(VRSS.list.get(i).resourceList.get(kelintasLaisvas).nameO, VRSS.list.get(i).resourceList.get(kelintasLaisvas).nameI);
						
						//Pridedam proces� � pasiruo�usiu proces� s�ra��
						PPS.list.add(VRSS.list.get(i).processList.get(kelintas).process);
						
						//Pakei�iam proceso b�sen� � READY
						VRSS.list.get(i).processList.get(kelintas).process.busena = Statiniai.ProcessState.READY;
						
						//I�trinam proces� i� laukian�i� s�ra�o
						VRSS.list.get(i).processList.remove(kelintas);
						
						//Pa�ymim resurs� u�imtu
						VRSS.list.get(i).resourceList.get(kelintasLaisvas).laisvas = false;
					}
				}
					
			}
		}
		
		for (int i = 0; i < RSS.list.size(); i++) {
			int maxPrioritetas = 0, kelintas = -1; //rastas did�iausias prioritetas ir kelintas procesas s�ra�e
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
					
					//Pridedam resurs� � proceso turim� s�ra��
					RSS.list.get(i).list.get(kelintas).process.addRes(RSS.list.get(i).resourceDescriptor.nameO, RSS.list.get(i).resourceDescriptor.nameI);
					
					//Pridedam proces� � pasiruo�usi� proces� s�ra��
					PPS.list.add(RSS.list.get(i).list.get(kelintas).process);
					
					//Pakei�iam proceso b�sen� � READY
					RSS.list.get(i).list.get(kelintas).process.busena = Statiniai.ProcessState.READY;
					
					//I�metam proces� i� laukian�i� s�ra�o
					RSS.list.get(i).list.remove(kelintas);
					
					//TODO Pa�ymim resurs� u�imtu.. HDD ATVEJU REIKIA PA�YM�T TIK BLOKUS
					RSS.list.get(i).resourceDescriptor.laisvas = false;
				}
			}
		}
		
	}
}
