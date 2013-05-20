import gui.InputForm;
import os.Primityvai;
import os.Statiniai;
import rm.Memory;
import Procesai.BeginEnd;


public class Main {

	public static void main(String[] args) {
		
		Memory.create();

		new InputForm();
		(new BeginEnd()).execute();
		
		

		//Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, 0, null);
		
	}

}
