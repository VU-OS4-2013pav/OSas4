import Procesai.PPS;
import Procesai.Statiniai;
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
				}
					
			}
		}
	}
}
