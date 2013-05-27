package Procesai;

import os.Planuotojas;
import rm.RM;

public class Idle extends ProcessBase {
	boolean idle;

	@Override
	public void execute() {
		
		idle = true;
		System.out.println("Idle pradzia.");
		while (RM.regOS == 0) {
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			//System.out.println(RM.regOS);
		}
		System.out.println("Idle pabaiga.");
		Planuotojas.planuok();
		return;
		
	}

}
