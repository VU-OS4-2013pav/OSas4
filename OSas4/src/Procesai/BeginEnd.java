package Procesai;

import resources.ProcessNeedsResource;
import resources.ResourceDescriptor;
import resources.VRSS;
import resourcesINFO.INFO;
import resourcesINFO.INFOhdd;
import resourcesINFO.INFOuserMemory;
import resourcesINFO.INFOv;


public class BeginEnd extends ProcessBase {
	@Override
	public void execute() {	
		switch (vieta) {
		case 0:
			this.busena = RUN;
			this.nameI = 1;
			this.nameO = "BeginEnd";
			this.prioritetas = 10;
			
			VRSS.initialiseVRSS();
			
		//Sukuriami daugkartinio panaudojimo resursai
			ResourceDescriptor.sukurtiResursa("HDD", false, this.nameI, new INFOhdd());
			ResourceDescriptor.sukurtiResursa("Kanalu irenginys", false, this.nameI, new INFOv());
			ResourceDescriptor.sukurtiResursa("Vartotojo atmintis", false, this.nameI, new INFOuserMemory());
			
		//Sukuriami sisteminiai procesai
			IInterrupt iinterrupt = new IInterrupt();
			VRSS.list.get(VRSS.Klaviaturos_pertraukimas).processList.add(new ProcessNeedsResource(iinterrupt, 1));
			iinterrupt.busena = BLOCKED;
			iinterrupt.father = 1;
			iinterrupt.nameI = 2;
			iinterrupt.nameO = "IInterrupt";
			iinterrupt.prioritetas = 8;
			
			SyntaxCheck syntaxCheck = new SyntaxCheck();
			VRSS.list.get(VRSS.Sintakses_tikrinimas).processList.add(new ProcessNeedsResource(syntaxCheck, 1));
			syntaxCheck.busena = BLOCKED;
			syntaxCheck.father = 1;
			syntaxCheck.nameI = 3;
			syntaxCheck.nameO = "Syntax check";
			syntaxCheck.prioritetas = 8;
			
			Loader loader = new Loader();
			VRSS.list.get(VRSS.Loader_pradzia).processList.add(new ProcessNeedsResource(loader, 1));
			loader.busena = BLOCKED;
			loader.father = 1;
			loader.nameI = 4;
			loader.nameO = "Loader";
			loader.prioritetas = 8;
			
			Destroyer destroyer = new Destroyer();
			VRSS.list.get(VRSS.Destroyer_XDD_pradzia).processList.add(new ProcessNeedsResource(destroyer, 1));
			destroyer.busena = BLOCKED;
			destroyer.father = 1;
			destroyer.nameI = 5;
			destroyer.nameO = "Destroyer";
			destroyer.prioritetas = 8;
			
			Interrupt interrupt = new Interrupt();
			VRSS.list.get(VRSS.Pertraukimo_ivykis).processList.add(new ProcessNeedsResource(interrupt, 1));
			interrupt.busena = BLOCKED;
			interrupt.father = 1;
			interrupt.nameI = 6;
			interrupt.nameO = "Interrupt";
			interrupt.prioritetas = 8;
			
			Swapper swapper = new Swapper();
			VRSS.list.get(VRSS.Swapper_pradzia).processList.add(new ProcessNeedsResource(swapper, 1));
			swapper.busena = BLOCKED;
			swapper.father = 1;
			swapper.nameI = 7;
			swapper.nameO = "Swapper";
			swapper.prioritetas = 8;
			
			Writer writer = new Writer();
			VRSS.list.get(VRSS.Writer_pradzia).processList.add(new ProcessNeedsResource(writer, 1));
			writer.busena = BLOCKED;
			writer.father = 1;
			writer.nameI = 8;
			writer.nameO = "Writer";
			writer.prioritetas = 8;	
			
			MainGovernor mainGovernor = new MainGovernor();
			VRSS.list.get(VRSS.Info_apie_nauja_VM).processList.add(new ProcessNeedsResource(mainGovernor, 1));
			mainGovernor.busena = BLOCKED;
			mainGovernor.father = 1;
			mainGovernor.nameI = 9;
			mainGovernor.nameO = "Main governor";
			mainGovernor.prioritetas = 9;
			
			/*for (int i = 0; i < VRSS.list.size(); i++)
				if (!VRSS.list.get(i).processList.isEmpty())
					System.out.println(VRSS.list.get(i).processList.get(0).process);*/
			
		//Blokuojasi ir laukia resurso <MOS darbo pabaiga>	
			this.busena = BLOCKED;
			VRSS.list.get(VRSS.MOS_darbo_pabaiga).processList.add(new ProcessNeedsResource(this, 0));
			vieta++;
			
			break;
		case 1:
			//Èia kaip ir viskas baigiasi sulaukus resurso... Naikinama viskas
			break;
		}
		
	}
}
