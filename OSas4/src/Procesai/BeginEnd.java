package Procesai;

import resources.ProcessNeedsResource;
import resources.ResourceDescriptor;
import resources.VienkartiniuResursuSarasuSarasas;
import resourcesINFO.INFO;
import resourcesINFO.INFOint;


public class BeginEnd extends ProcessBase {
	@Override
	public void execute() {
		VienkartiniuResursuSarasuSarasas.initialiseVRSS();
		
		INFO in = new INFOint(0);
		
	//Sukuriami daugkartinio panaudojimo resursai
		ResourceDescriptor.sukurtiResursa("HDD", false, 1, in);
		ResourceDescriptor.sukurtiResursa("Kanalu irenginys", false, 1, in);
		ResourceDescriptor.sukurtiResursa("Vartotojo atmintis", false, 1, in);
		
	//Sukuriami sisteminiai procesai
		IInterrupt iinterrupt = new IInterrupt();
		VienkartiniuResursuSarasuSarasas.list.get(1).processList.add(new ProcessNeedsResource(iinterrupt, 1));
		iinterrupt.busena = BLOCKED;
		iinterrupt.father = 1;
		iinterrupt.nameI = 2;
		iinterrupt.nameO = "IInterrupt";
		iinterrupt.prioritetas = 8;
		
		SyntaxCheck syntaxCheck = new SyntaxCheck();
		VienkartiniuResursuSarasuSarasas.list.get(2).processList.add(new ProcessNeedsResource(syntaxCheck, 1));
		syntaxCheck.busena = BLOCKED;
		syntaxCheck.father = 1;
		syntaxCheck.nameI = 3;
		syntaxCheck.nameO = "Syntax check";
		syntaxCheck.prioritetas = 8;
		
		Loader loader = new Loader();
		VienkartiniuResursuSarasuSarasas.list.get(10).processList.add(new ProcessNeedsResource(loader, 1));
		loader.busena = BLOCKED;
		loader.father = 1;
		loader.nameI = 4;
		loader.nameO = "Loader";
		loader.prioritetas = 8;
		
		Destroyer destroyer = new Destroyer();
		VienkartiniuResursuSarasuSarasas.list.get(9).processList.add(new ProcessNeedsResource(destroyer, 1));
		destroyer.busena = BLOCKED;
		destroyer.father = 1;
		destroyer.nameI = 5;
		destroyer.nameO = "Destroyer";
		destroyer.prioritetas = 8;
		
		Interrupt interrupt = new Interrupt();
		VienkartiniuResursuSarasuSarasas.list.get(13).processList.add(new ProcessNeedsResource(interrupt, 1));
		interrupt.busena = BLOCKED;
		interrupt.father = 1;
		interrupt.nameI = 6;
		interrupt.nameO = "Interrupt";
		interrupt.prioritetas = 8;
		
		Swapper swapper = new Swapper();
		VienkartiniuResursuSarasuSarasas.list.get(15).processList.add(new ProcessNeedsResource(swapper, 1));
		swapper.busena = BLOCKED;
		swapper.father = 1;
		swapper.nameI = 7;
		swapper.nameO = "Swapper";
		swapper.prioritetas = 8;
		
		Writer writer = new Writer();
		VienkartiniuResursuSarasuSarasas.list.get(14).processList.add(new ProcessNeedsResource(writer, 1));
		writer.busena = BLOCKED;
		writer.father = 1;
		writer.nameI = 8;
		writer.nameO = "Writer";
		writer.prioritetas = 8;	
		
		MainGovernor mainGovernor = new MainGovernor();
		VienkartiniuResursuSarasuSarasas.list.get(11).processList.add(new ProcessNeedsResource(mainGovernor, 1));
		mainGovernor.busena = BLOCKED;
		mainGovernor.father = 1;
		mainGovernor.nameI = 9;
		mainGovernor.nameO = "Main governor";
		mainGovernor.prioritetas = 9;
		
		for (int i = 0; i < VienkartiniuResursuSarasuSarasas.list.size(); i++)
			if (!VienkartiniuResursuSarasuSarasas.list.get(i).processList.isEmpty())
				System.out.println(VienkartiniuResursuSarasuSarasas.list.get(i).processList.get(0).process);
		
	//Blokuojasi ir laukia resurso <MOS darbo pabaiga>	
		this.busena = BLOCKED;
		VienkartiniuResursuSarasuSarasas.list.get(0).processList.add(new ProcessNeedsResource(this, 0));
		
	//Èia kaip ir viskas baigiasi sulaukus resurso... Naikinama viskas
	}
}
