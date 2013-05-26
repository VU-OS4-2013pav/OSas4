package os;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import os.Statiniai.ProcessState;
import Procesai.PPS;
import resources.RSS;
import resources.VRSS;
import resourcesINFO.HDDObject;
import resourcesINFO.INFOhdd;
import resourcesINFO.INFOuserMemory;
import resourcesINFO.ProgramosInfoHDD;


public class Paskirstytojas {
	public static void skirstyk() {
		//System.out.println("Skirstau...");
		for (int i = 0; i < VRSS.list.size(); i++) {
			int maxPrioritetas = 0, kelintas = -1; //rastas did�iausias prioritetas ir kelintas procesas s�ra�e
			
			//Jei tai yra prane�imas apie pertraukim�
			if (VRSS.list.get(i).vardas == Statiniai.VRstring.Pranesimas_apie_pertraukima) {
				//Jei yra resurs�
				if (!VRSS.list.get(i).resourceList.isEmpty()) {
					//Jei yra laukian�i� proces�
					if (!VRSS.list.get(i).processList.isEmpty()) {
						for (int k = 0; k < VRSS.list.get(i).resourceList.size(); k++) {
							//Jei resursas ant kurio esam n�ra laisvas
							if (!VRSS.list.get(i).resourceList.get(k).laisvas)
								continue;
							for (int j = 0; j < VRSS.list.get(i).processList.size(); j++) {
								int jg = (Integer)((Object[])VRSS.list.get(i).resourceList.get(k).info.o)[1];
								//Radom, kam ir k� reikia paskirti
								if (VRSS.list.get(i).processList.get(j).process.nameI == jg) {
									System.out.println("Skirstau " + VRSS.list.get(i).processList.get(j).process.nameO + 
											" Resursas: " + VRSS.list.get(i).vardas + " nameI = " + VRSS.list.get(i).processList.get(j).process.nameI
											+ " o paskirstyt reik�jo su nameI: " + jg);
									
									//Pridedam resurs� � proceso turim� resurs� s�ra��
									VRSS.list.get(i).processList.get(j).process.addRes(VRSS.list.get(i).resourceList.get(k).nameO, VRSS.list.get(i).resourceList.get(k).nameI);
									
									//Pridedam proces� � pasiruo�usiu proces� s�ra��
									PPS.list.add(VRSS.list.get(i).processList.get(j).process);
									
									//Pakei�iam proceso b�sen� � READY
									VRSS.list.get(i).processList.get(j).process.busena = Statiniai.ProcessState.READY;
									
									//Pa�ymim resurs� u�imtu
									VRSS.list.get(i).resourceList.get(k).laisvas = false;
									
									//I�trinam proces� i� laukian�i� s�ra�o
									VRSS.list.get(i).processList.remove(j);
									break;
								}
							}
						}
					}
				}
			}
			
			//Jei yra resursas, einam per visus jo laukian�ius procesus, procesui su did�iausiu prioritetu j� paskiriame
			else if (!VRSS.list.get(i).resourceList.isEmpty()) {
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
						
						//Pa�ymim resurs� u�imtu
						VRSS.list.get(i).resourceList.get(kelintasLaisvas).laisvas = false;
						
						//I�trinam proces� i� laukian�i� s�ra�o
						VRSS.list.get(i).processList.remove(kelintas);
						
					}
				}
					
			}
		}
		
		for (int i = 0; i < RSS.list.size(); i++) {
			
			//Jei HDD resursas
			if (RSS.list.get(i).resourceDescriptor.nameO == Statiniai.DRstring.HDD) {
				//System.out.println("Skirstau HDD");
				boolean aptarnavau = true;
				while(aptarnavau) {
					aptarnavau = false;
					//Jei laukian�i� proces� s�ra�as netu��ias
					if (!RSS.list.get(i).list.isEmpty()) {	
						int maxPrioritetas = 0, kelintas = -1; //rastas did�iausias prioritetas ir kelintas procesas s�ra�e
						for (int j = 0; j < RSS.list.get(i).list.size(); j++) {
							//System.out.println(RSS.list.get(i).list.get(j).howMuchResourceItNeeds + " <= " + ((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).laisvuBlokuSk);
							if (RSS.list.get(i).list.get(j).howMuchResourceItNeeds <= ((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).laisvuBlokuSk) {
								if (RSS.list.get(i).list.get(j).process.prioritetas > maxPrioritetas) {
									maxPrioritetas = RSS.list.get(i).list.get(j).process.prioritetas;
									kelintas = j;
								}
							}
						}
						if (kelintas > -1) {
							//� HDD �dedama nauja programa
							int kiek, nr;
							kiek = RSS.list.get(i).list.get(kelintas).howMuchResourceItNeeds;
							nr = ((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).programs.size() + 1;
							RSS.list.get(i).list.get(kelintas).process.programaHDD = nr;
							
							((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).programs.add(new ProgramosInfoHDD(nr, kiek));
							for (int j = 0; j < ((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).hdd.size(); j++) {
								if (((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).hdd.get(j) == 0) {
									((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).hdd.set(j, nr);
									((HDDObject)((INFOhdd)RSS.list.get(i).resourceDescriptor.info).o).laisvuBlokuSk--;
									kiek--;
								}
								if (0 == kiek)
									break;
							}
							System.out.println("Skirstau " + RSS.list.get(i).list.get(kelintas).process.nameO + 
									" Resursas: " + RSS.list.get(i).resourceDescriptor.nameO);
							
							RSS.list.get(i).list.get(kelintas).process.addRes(RSS.list.get(i).resourceDescriptor.nameO, RSS.list.get(i).resourceDescriptor.nameI);
							RSS.list.get(i).list.get(kelintas).process.busena = Statiniai.ProcessState.READY;
							PPS.list.add(RSS.list.get(i).list.get(kelintas).process);
							RSS.list.get(i).list.remove(kelintas);
							aptarnavau = true;
							
							
						}
					}
				}
			}
			
			//Jei Vartotojo atminties resursas
			else if (RSS.list.get(i).resourceDescriptor.nameO == Statiniai.DRstring.Vartotojo_atmintis) {
				boolean aptarnavau = true;
				while(aptarnavau) {
					aptarnavau = false;
					//Jei laukian�i� proces� s�ra�as netu��ias
					if (!RSS.list.get(i).list.isEmpty()) {	
						
						int maxPrioritetas = 0, kelintas = -1; //rastas did�iausias prioritetas ir kelintas procesas s�ra�e
						for (int j = 0; j < RSS.list.get(i).list.size(); j++) {
							if (RSS.list.get(i).list.get(j).howMuchResourceItNeeds <= ((INFOuserMemory)RSS.list.get(i).resourceDescriptor.info).laisviBlokai) {
								if (RSS.list.get(i).list.get(j).process.prioritetas > maxPrioritetas) {
									maxPrioritetas = RSS.list.get(i).list.get(j).process.prioritetas;
									kelintas = j;
								}
							}
						}
						
						if (kelintas > -1) {
							//� proceso OA lauk� sugrudami numeriai blok�, kuriuos gauna procesas
							int kiek = RSS.list.get(i).list.get(kelintas).howMuchResourceItNeeds;
							//System.out.println("Kiek       !!!!!!!" + kiek);
							int[] blokai = new int[kiek];
							int k = 0;
							for (int j = 0; j < ((ArrayList<Boolean>)((INFOuserMemory)RSS.list.get(i).resourceDescriptor.info).o).size(); j++) {
								if (!((ArrayList<Boolean>)((INFOuserMemory)RSS.list.get(i).resourceDescriptor.info).o).get(j)) {
									blokai[k] = j;
									k++;
									((ArrayList<Boolean>)((INFOuserMemory)RSS.list.get(i).resourceDescriptor.info).o).set(j, true);
									((INFOuserMemory)RSS.list.get(i).resourceDescriptor.info).laisviBlokai--;
								}
								if (k == kiek)
									break;
							}
							
							System.out.println("Skirstau " + RSS.list.get(i).list.get(kelintas).process.nameO + 
									" Resursas: " + RSS.list.get(i).resourceDescriptor.nameO);
							
							//RSS.list.get(i).list.get(kelintas).process.addRes(RSS.list.get(i).resourceDescriptor.nameO, RSS.list.get(i).resourceDescriptor.nameI);
							RSS.list.get(i).list.get(kelintas).process.oa = blokai.clone();
							RSS.list.get(i).list.get(kelintas).process.busena = Statiniai.ProcessState.READY;
							PPS.list.add(RSS.list.get(i).list.get(kelintas).process);
							RSS.list.get(i).list.remove(kelintas);
							aptarnavau = true;
							
							
						}	
					}	
				}
			}
			
			//Jei kanal� �renginys
			else if (RSS.list.get(i).resourceDescriptor.nameO == Statiniai.DRstring.Kanalu_irenginys) {
				if (RSS.list.get(i).resourceDescriptor.laisvas) {
					if (!RSS.list.get(i).list.isEmpty()) {
						int maxPrioritetas = 0, kelintas = -1; //rastas did�iausias prioritetas ir kelintas procesas s�ra�e
						for (int j = 0; j < RSS.list.get(i).list.size(); j++) {
							if (RSS.list.get(i).list.get(j).process.prioritetas > maxPrioritetas) {
								maxPrioritetas = RSS.list.get(i).list.get(j).process.prioritetas;
								kelintas = j;
							}
						}
						if (kelintas > -1) {
							System.out.println("Skirstau " + RSS.list.get(i).list.get(kelintas).process.nameO + 
									" Resursas: " + RSS.list.get(i).resourceDescriptor.nameO);
							
							RSS.list.get(i).list.get(kelintas).process.addRes(RSS.list.get(i).resourceDescriptor.nameO, RSS.list.get(i).resourceDescriptor.nameI);
							RSS.list.get(i).list.get(kelintas).process.busena = Statiniai.ProcessState.READY;
							PPS.list.add(RSS.list.get(i).list.get(kelintas).process);
							RSS.list.get(i).list.remove(kelintas);
							RSS.list.get(i).resourceDescriptor.laisvas = false;
						}
					}
				}
			}	
		}
		Planuotojas.planuok();
		
	}
}
