package Procesai;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessBase {
	//Procesø bûsenos
	protected final String READYS = "READYS";
	protected final String READY = "READY";
	protected final String RUN = "RUN";
	protected final String BLOCKS = "BLOCKS";
	protected final String BLOCKED = "BLOCKED";
	
	public int nameI; //proceso vidinis vardas
	public String nameO; //iðorinis vardas
	public Object[] cpu = new Object[12]; //visi procesoriaus registrai
	public char[] oa = new char[4]; 
	public List<Res> resursai = new ArrayList<Res>();
	public List<Res> sukurtiResursai = new ArrayList<Res>();
	public String busena; //bûsena
	public int father; //proceso tëvas
	public List<Integer> sunus = new ArrayList<Integer>();
	public int prioritetas; 
	
	public abstract void execute();
	
	public class Res {
		String nameO; //iðorinis vardas
		int nameI; //vidinis vardas
	}
	
	public String toString() {
		return String.format("nameI=%s \n nameO=%s \n busena=%s \n father=%s \n prioritetas=%s", nameI, nameO, busena, father, prioritetas);
	}
}
