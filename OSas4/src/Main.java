import os.Paskirstytojas;
import os.Planuotojas;
import os.Primityvai;
import os.Statiniai;
import rm.ChannelDevice;
import Procesai.BeginEnd;
import Procesai.PPS;


public class Main {

	public static void main(String[] args) {
		/*VienkartiniuResursuSarasuSarasas.initialiseVRSS();
		
		INFO in = new INFOboolean(new Boolean(true));
		ResourceDescriptor.sukurtiResursa("VM nori ivedimoafwjlbawjlf", false, 1, in);
		ResourceDescriptor.sukurtiResursa("InputStream pabaiga", true, 1, in);
		ResourceDescriptor.sukurtiResursa("InputStream pabaigaasljfbasdlja", false, 2, in);
		ResourceDescriptor.sukurtiResursa("Loader pradzia", true, 1, in);
		ResourceDescriptor.sukurtiResursa("klavaturaasfnljnfa", false, 5, in);
		
		for (int i=0; i<3; i++) {
			ResursuSarasuSarasas.print(i);
		}
		System.out.println("");
		for (int i=0; i<16; i++) {
			VienkartiniuResursuSarasuSarasas.print(i);
		}
		
		System.out.println("");*/


		(new BeginEnd()).execute();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();
		Planuotojas.planuok();

		Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, 0, null);
		Planuotojas.planuok();
		
		//Primityvai.sukurtiResursa(Statiniai.VRstring.MOS_darbo_pabaiga, true, 0, null);
		
	}

}
