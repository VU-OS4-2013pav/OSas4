package resources;

import java.util.ArrayList;
import java.util.List;

public class ResursuSarasuSarasas {
	public static List<ResursuSarasas> list = new ArrayList<ResursuSarasas>();
	
	public static void print(int i) {
		System.out.println(list.get(i).resourceDescriptor.nameO);
	}
}
