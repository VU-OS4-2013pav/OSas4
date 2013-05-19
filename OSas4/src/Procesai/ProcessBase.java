package Procesai;

import java.util.ArrayList;
import java.util.List;

import os.Statiniai.ProcessState;


public abstract class ProcessBase {
	// Proceso deskriptorius
	public int vieta = 0;
	
	public int nameI; //proceso vidinis vardas
	public String nameO; //i�orinis vardas
	public Object[] cpu = new Object[12]; //visi procesoriaus registrai //TODO cpu processbase VM
	public int[] oa;  //TODO oa processbase VM
	public List<Res> resursai = new ArrayList<Res>(); //turimu resursu sarasas
	public List<Res> sukurtiResursai = new ArrayList<Res>();
	public String busena = ProcessState.RUN; //b�sena
	public int father; //proceso t�vas
	public List<Integer> sunus = new ArrayList<Integer>();
	public int prioritetas; 
	public int programaHDD;
	
	public abstract void execute();
	
	public class Res {
		public String nameO; // i�orinis vardas
		public int nameI; // vidinis vardas

		public Res(String name, int namei) {
			this.nameI = namei;
			this.nameO = name;
		}
		
	}
	
	public void addRes(String name, int nameI) {
		resursai.add(new Res(name, nameI));
	}
	
	public String toString() {
		return String.format("nameI=%s \n nameO=%s \n busena=%s \n father=%s \n prioritetas=%s", nameI, nameO, busena, father, prioritetas);
	}
	
	public void addResToPL(String name, int nameI) {
		sukurtiResursai.add(new Res(name, nameI));
	}
	
	
	
}
