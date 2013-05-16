package Procesai;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessBase {
	public int nameI; //proceso vidinis vardas
	public String nameO; //i�orinis vardas
	public Object[] cpu = new Object[12]; //visi procesoriaus registrai
	public char[] oa = new char[4]; 
	public List<Res> resursai = new ArrayList<Res>();
	public List<Res> sukurtiResursai = new ArrayList<Res>();
	public String busena; //b�sena
	public int father; //proceso t�vas
	public List<Integer> sunus = new ArrayList<Integer>();
	public int prioritetas; 
	
	public abstract void execute();
	
	public class Res {
		String nameO; //i�orinis vardas
		int nameI; //vidinis vardas
	}
}
