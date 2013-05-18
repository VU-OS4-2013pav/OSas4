package Procesai;

import Procesai.Statiniai.ProcessState;
import Procesai.Statiniai.Pstring;
import Procesai.Statiniai.VRstring;
import resources.VRSS;
import resourcesINFO.INFOhdd;
import resourcesINFO.INFOuserMemory;
import resourcesINFO.INFOv;


public class BeginEnd extends ProcessBase {
	@Override
	public void execute() {	
		VRSS.initialise();
		PL.initialise();
		
		switch (vieta) {
		case 0:
			System.out.println("Sistemos pradzia");
			this.busena = ProcessState.RUN;
			this.nameI = 0;
			this.nameO = "BeginEnd";
			this.prioritetas = 10;
			PPS.list.add(this);
			PL.addProcess(this);
			
		//Sukuriami daugkartinio panaudojimo resursai
			Primityvai.sukurtiResursa(Statiniai.DRstring.HDD, false, this.nameI, new INFOhdd());
			Primityvai.sukurtiResursa(Statiniai.DRstring.Kanalu_irenginys, false, this.nameI, new INFOv());
			Primityvai.sukurtiResursa(Statiniai.DRstring.Vartotojo_atmintis, false, this.nameI, new INFOuserMemory());
			
		//Sukuriami sisteminiai procesai
			//TODO iskelti resursu prasyma i execute metodus case 0
			int proc;
			
			proc = Primityvai.sukurtiProcesa(Pstring.IInterrupt, this.nameI, 8);
			
			proc = Primityvai.sukurtiProcesa(Pstring.SyntaxCheck, this.nameI, 8);
			Primityvai.prasytiResurso(VRstring.Sintakses_tikrinimas, proc, 1);
			
			proc = Primityvai.sukurtiProcesa(Pstring.Loader, this.nameI, 8);
			Primityvai.prasytiResurso(VRstring.Loader_pradzia, proc, 1);
			
			proc = Primityvai.sukurtiProcesa(Pstring.Destroyer, this.nameI, 8);
			Primityvai.prasytiResurso(VRstring.Destroyer_XDD_pradzia, proc, 1);
			
			proc = Primityvai.sukurtiProcesa(Pstring.Interrupt, this.nameI, 8);
			Primityvai.prasytiResurso(VRstring.Pertraukimo_ivykis, proc, 1);
			
			proc = Primityvai.sukurtiProcesa(Pstring.Swapper, this.nameI, 8);
			Primityvai.prasytiResurso(VRstring.Swapper_pradzia, proc, 1);
			
			proc = Primityvai.sukurtiProcesa(Pstring.Writer, this.nameI, 8);
			Primityvai.prasytiResurso(VRstring.Writer_pradzia, proc, 1);
			
			proc = Primityvai.sukurtiProcesa(Pstring.MainGovernor, this.nameI, 9);
			Primityvai.prasytiResurso(VRstring.Info_apie_nauja_VM, proc, 1);

		//Blokuojasi ir laukia resurso <MOS darbo pabaiga>					
			vieta++;		
			Primityvai.prasytiResurso(VRstring.MOS_darbo_pabaiga, 0, 1);
/*			System.out.println("\n\n");
			for (int i = 0; i < VRSS.list.size(); i++) {
				System.out.println("      "+VRSS.list.get(i).vardas);
				if (!VRSS.list.get(i).processList.isEmpty())
					System.out.println(VRSS.list.get(i).processList.get(0).process);
			}
			System.out.println("\n\n");*/
			
			break;
		case 1:
			//Èia kaip ir viskas baigiasi sulaukus resurso... Naikinama viskas
			System.out.println("OS'as baige darba.");
			break;
		}
		
	}
}
