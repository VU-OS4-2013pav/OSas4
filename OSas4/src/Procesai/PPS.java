package Procesai;

import java.util.ArrayList;
import java.util.List;

public class PPS {
	public static List<ProcessBase> list = new ArrayList<ProcessBase>();
	
	public static ProcessBase getProcess(int innerName) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).nameI == innerName) {
				return list.get(i);
			}
		}
		
		return null;
		
	}
	
	
}
