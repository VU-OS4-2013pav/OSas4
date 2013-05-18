package Procesai;

import java.util.ArrayList;
import java.util.List;

public class ProcessListElement {
	public String name;
	public List<ProcessBase> processList = new ArrayList<ProcessBase>();
	
	public ProcessListElement(String name) {
		this.name = name;
	}
}
