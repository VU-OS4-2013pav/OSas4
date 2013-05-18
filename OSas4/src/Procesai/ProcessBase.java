package Procesai;

import java.util.ArrayList;
import java.util.List;

import Procesai.Statiniai.ProcessState;

public abstract class ProcessBase {
	// Proceso deskriptorius
	public int vieta = 0;
	
	public int nameI; //proceso vidinis vardas
	public String nameO; //iðorinis vardas
	public Object[] cpu = new Object[12]; //visi procesoriaus registrai //TODO cpu processbase VM
	public char[] oa = new char[4];  //TODO oa processbase VM
	public List<Res> resursai = new ArrayList<Res>();
	public List<Res> sukurtiResursai = new ArrayList<Res>();
	public String busena = ProcessState.RUN; //bûsena
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
