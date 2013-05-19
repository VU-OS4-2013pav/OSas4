package resources;

import java.util.ArrayList;
import java.util.List;

public class RSS {
	public static List<RS> list = new ArrayList<RS>();
	
	public static void print(int i) {
		if (list.get(i).resourceDescriptor != null)
			System.out.println(list.get(i).resourceDescriptor.nameO+"||| id: "+list.get(i).resourceDescriptor.nameI);
		else
			System.out.println("Daugkartinis resursas su vidiniu vardu "+ i +" neegzistuoja.");
	}
}
