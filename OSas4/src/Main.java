import gui.InputForm;
import os.Primityvai;
import os.Statiniai;
import rm.ChannelDevice;
import rm.HDD;
import rm.Memory;
import Procesai.BeginEnd;


public class Main {

	public static void main(String[] args) {
		ChannelDevice.createDevice();
		Memory.create();
		HDD.create();
		
		new InputForm();
		(new BeginEnd()).execute();
		
		

		//Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, 0, null);
		
	}

}
