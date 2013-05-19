package resourcesINFO;

public class ProgramosInfoHDD {
	public String name; // programos vardas
	public int nr;	// programos identifikacinis numeris in HDD
	public int memHDD; // kiek bloku programa uzima in HDD
	public int oa; //kiek blokø uþima programa
	
	public ProgramosInfoHDD(String name, int nr, int memory, int oa) {
		this.name = name;
		this.nr = nr;
		this.memHDD = memory;
		this.oa = oa;
	}

	public ProgramosInfoHDD(int nr, int memHDD) {
		this.nr = nr;
		this.memHDD = memHDD;
	}
	
}
