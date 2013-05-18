package Procesai;

public class Statiniai {
	public static class VRstring {

		// Vienkartiniu resursu isoriniai vardai
		public static final String MOS_darbo_pabaiga = "MOS darbo pabaiga";
		public static final String Klaviaturos_pertraukimas = "Klaviaturos pertraukimas";
		public static final String Sintakses_tikrinimas = "Sintakses tikrinimas";
		public static final String Sintakse_patikrinta = "Sintakse patikrinta";
		public static final String VM_nori_ivedimo = "VM nori ivedimo";
		public static final String Pranesimas_apie_pertraukima = "Pranesimas apie pertraukima";
		public static final String InputStream_pabaiga = "InputStream pabaiga";
		public static final String Loader_pabaiga = "Loader pabaiga";
		public static final String Destroyer_XDD_pabaiga = "Destroyer XDD pabaiga";
		public static final String Destroyer_XDD_pradzia = "Destroyer XDD pradzia";
		public static final String Loader_pradzia = "Loader pradzia";
		public static final String Info_apie_nauja_VM = "Info apie nauja VM";
		public static final String MainGovernor_pazadinimas = "MainGovernor pazadinimas";
		public static final String Pertraukimo_ivykis = "Pertraukimo ivykis";
		public static final String Writer_pradzia = "Writer pradzia";
		public static final String Swapper_pradzia = "Swapper  pradzia";
		
	}
	
	public static class DRstring {
		
		//Daugkartiniu resursu isoriniai vardai
		public static final String HDD = "HDD";
		public static final String Kanalu_irenginys = "Kanalu irenginys";
		public static final String Vartotojo_atmintis = "Vartotojo atmintis";
	}
	
	public static class VRint {
		
		// Vienkartiniu resursu tvarka VRSS sarase
		public static final int MOS_darbo_pabaiga = 0;
		public static final int Klaviaturos_pertraukimas = 1;
		public static final int Sintakses_tikrinimas = 2;
		public static final int Sintakse_patikrinta = 3;
		public static final int VM_nori_ivedimo = 4;
		public static final int Pranesimas_apie_pertraukima = 5;
		public static final int InputStream_pabaiga = 6;
		public static final int Loader_pabaiga = 7;
		public static final int Destroyer_XDD_pabaiga = 8;
		public static final int Destroyer_XDD_pradzia = 9;
		public static final int Loader_pradzia = 10;
		public static final int Info_apie_nauja_VM = 11;
		public static final int MainGovernor_pazadinimas = 12;
		public static final int Pertraukimo_ivykis = 13;
		public static final int Writer_pradzia = 14;
		public static final int Swapper_pradzia = 15;
		
	}
	
	public static class DRint {
		
		// Daugkartiniu resursu tvarka RSS sarase
		public static final int HDD = 0;
		public static final int Kanalu_irenginys = 1;
		public static final int Vartotojo_atmintis = 2;
		
	}
	
	public static class Pstring {

		// Procesu isoriniai vardai
		public static final String BeginEnd = "BeginEnd";
		public static final String IInterrupt = "IInterrupt";
		public static final String InputStream = "Input stream";
		public static final String SyntaxCheck = "Syntax check";
		public static final String Loader = "Loader";
		public static final String Destroyer = "Destroyer";
		public static final String Interrupt = "Interrupt";
		public static final String Swapper = "Swapper";
		public static final String Writer = "Writer";
		public static final String MainGovernor = "Main governor";
		public static final String JobGovernor = "Job governor";
		public static final String VirtualMachine =  "Virtual machine";
		
	}
	
	public static class Pint {
		// Procesu tvarka PL sarase
		public static final int BEGIN_END = 0;
		public static final int IINTERRUPT = 1;
		public static final int INPUT_STREAM = 2;
		public static final int SYNTAX_CHECK = 3;
		public static final int LOADER = 4;
		public static final int DESTROYER = 5;
		public static final int INTERRUPT = 6;
		public static final int SWAPPER = 7;
		public static final int WRITER = 8;
		public static final int MAIN_GOVERNOR = 9;
		public static final int JOB_GOVERNOR = 10;
		public static final int VIRTUAL_MACHINE = 11;
		
	}
	
	public static class ProcessState {

		//Procesø bûsenos
		public static final String READYS = "READYS";
		public static final String READY = "READY";
		public static final String RUN = "RUN";
		public static final String BLOCKS = "BLOCKS";
		public static final String BLOCKED = "BLOCKED";
		
	}

}
