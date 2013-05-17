package resources;

import Procesai.ProcessBase;

public class ProcessNeedsResource {
	public ProcessBase process;
	public int howMuchResourceItNeeds;
	
	public ProcessNeedsResource(ProcessBase process, int howMuch) {
		this.process = process;
		this.howMuchResourceItNeeds = howMuch;
	}
}
