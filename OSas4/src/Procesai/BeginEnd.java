
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
		switch (vieta) {
		case 0:
			VRSS.initialise();
			PL.initialise();
			System.out.println("Sistemos pradzia");
			this.busena = ProcessState.RUN;
			this.nameI = 0;
			this.nameO = "BeginEnd";
			this.prioritetas = 10;
			PPS.list.add(this);
			PL.addProcess(this);
			
		//Sukuriami daugkartinio panaudojimo resursai
			Primityvai.sukurtiResursa(Statiniai.DRstring.HDD, false, this.nameI, new INFOhdd());
			INFOv in = new INFOv();
			((Object[])in.o)[0] = true;
			Primityvai.sukurtiResursa(Statiniai.DRstring.Kanalu_irenginys, false, this.nameI, in);
			Primityvai.sukurtiResursa(Statiniai.DRstring.Vartotojo_atmintis, false, this.nameI, new INFOuserMemory());
			
		//Sukuriami sisteminiai procesai
			
			Primityvai.sukurtiProcesa(Pstring.IInterrupt, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.SyntaxCheck, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.Loader, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.Destroyer, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.Interrupt, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.Swapper, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.Writer, this.nameI, 8);
			Primityvai.sukurtiProcesa(Pstring.MainGovernor, this.nameI, 9);

		//Blokuojasi ir laukia resurso <MOS darbo pabaiga>					
			vieta++;		
			Primityvai.prasytiResurso(VRstring.MOS_darbo_pabaiga, 0, 1);
			
			break;
		case 1:
			//Èia kaip ir viskas baigiasi sulaukus resurso... Naikinama viskas
			System.out.println("OS'as baige darba.");
			break;
		}
		
	}
}
