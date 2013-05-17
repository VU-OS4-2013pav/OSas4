package resources;

import java.util.ArrayList;
import java.util.List;

public class RSS {
	public static List<RS> list = new ArrayList<RS>();
	
	public static void print(int i) {
		System.out.println(list.get(i).resourceDescriptor.nameO+"||| id: "+list.get(i).resourceDescriptor.nameI);
	}
}
