
import gui.InputForm;
import os.Primityvai;
import os.Statiniai;
import rm.ChannelDevice;
import rm.GUI;
import rm.HDD;
import rm.Memory;
import rm.RM;
import Procesai.BeginEnd;


public class Main {

	public static void main(String[] args) {
		ChannelDevice.createDevice();
		Memory.create();
		HDD.create();
		RM.createRM();
		
		new InputForm();
		
		(new BeginEnd()).execute();
	}

}
