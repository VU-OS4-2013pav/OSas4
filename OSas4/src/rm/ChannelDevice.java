package rm;
import java.io.IOException;

public class ChannelDevice {	
	public static final int IA = 0, OA = 1, IO = 2, OO = 3;
	public static int c;
	static Object[] channels;	
	
	public static void createDevice() {
		channels = new Object[4];
		
		channels[IA] = new char[4];
		channels[OA] = new char[4];
		channels[IO] = new char[1];
		channels[OO] = new char[1];
		
		for (int i=0; i<2; i++) {
			((char[])channels[i])[0] = 0x0;
			((char[])channels[i])[1] = 0x0;
			((char[])channels[i])[2] = 0x0;
			((char[])channels[i])[3] = 0x0;
		}
		
		((char[])channels[2])[0] = 0x0;
		((char[])channels[3])[0] = 0x0;
	}
	
	public static void setValueOfChannel(int channel, int value) {
		if (channel == IA || channel == OA)
			stringToRegister(channel, Integer.toHexString(value));
		else
			stringToRegister1B(channel, Integer.toHexString(value));
	}
	
	public static void runDevice() {
		switch (Integer.parseInt((registerToString1B(IO)), 16)) {
		case 1 : { // vidine atmintis ->
			switch (Integer.parseInt((registerToString1B(OO)), 16)) {
			case 1 : { // ->vidine atmintis
				int ia = Integer.parseInt(registerToString(IA), 16);
				int oa = Integer.parseInt(registerToString(OA), 16);				
				int cc = Integer.parseInt(RM.registerToString(RM.CC), 16); 
				if (cc > 0)
					for (int i = 0; i < cc; i++) {
						Memory.addWord(oa, (Memory.get()[ia]).toString());
						ia++;
						oa++;
					}
			} // end case 11
			break;
			
			case 2 : { // ->isorine atmintis
				int ia = Integer.parseInt(registerToString(IA), 16);
				int oa = Integer.parseInt(registerToString(OA), 16);
				//int cc = Integer.parseInt(RM.registerToString(RM.CC), 16);
				int cc = c;
				if (cc > 0)
					for (int i = 0; i < cc; i++) {
						HDD.addWord(oa, (Memory.get()[ia]).toString());
						ia++;
						oa++;
					}
			} // end case 12
			break;
			
			case 3 : { // ->isvedimo srautas
				
				for (int j = Integer.parseInt(registerToString(IA), 16); j < Integer.parseInt(registerToString(IA), 16)+c; j++)
					for (int i=0; i < 4; i++) {
						char d = Memory.get()[j].toString().charAt(i);
						GUI.printChar(d);
					}
				
				/*char c = ' ';
				int i;
				boolean stop = false;
				int j = Integer.parseInt(registerToString(IA), 16);
				
				while (!stop) {
					for (i=0; i < 4; i++) {
						
						c = Memory.get()[j].toString().charAt(i);
						if (c != 0x7F) {
							GUI.printChar(c);
						}
						else {
							stop = true;
							break;						
						}
					}
					if (!stop)
						j++;
				}
				GUI.printChar((char)0xA);*/			
			} // end case 13
			break;			
			} // end switch
		} // end case 01
		break;
		
		case 2 : { // isorine atmintis ->
			switch (Integer.parseInt((registerToString1B(OO)), 16)) {
			case 1 : { // ->vidine atmintis
				int ia = Integer.parseInt(registerToString(IA), 16);
				int oa = Integer.parseInt(registerToString(OA), 16);
				int cc = Integer.parseInt(RM.registerToString(3), 16);
				if (cc > 0)
					for (int i = 0; i < cc; i++) {
						Memory.addWord(oa, (HDD.get()[ia]).toString());
						ia++;
						oa++;
					}
			} // end case 21
			break;
			
			case 2 : { // ->isorine atmintis
				int ia = Integer.parseInt(registerToString(IA), 16);
				int oa = Integer.parseInt(registerToString(OA), 16);
				int cc = Integer.parseInt(RM.registerToString(3), 16);
				if (cc > 0)
					for (int i = 0; i < cc; i++) {
						HDD.addWord(oa, (HDD.get()[ia]).toString());
						ia++;
						oa++;
					}
			} // end case 22
			break;
			} // end switch
		} // end case 02
		break;
		
		case 3 : { // ivedimo srautas->
			if (Integer.parseInt((registerToString1B(OO)), 16) == 1){ // ->vidine atmintis
				readConsole();
			}
		} // end case 03
		break;
		} // end switch		
	} // end main method()
	
	public static void readConsole() {
		char[] word = new char[4];
		int i;
		boolean stop = false;
		System.out.println("Your input: ");
		
		int k = Integer.parseInt(registerToString(OA), 16);
		while (!stop) {
			word[0] = 0x0;
			word[1] = 0x0;
			word[2] = 0x0;
			word[3] = 0x0;
			
			for (i=0; i < 4; i++) {
				try {
					word[i] = (char) System.in.read();		
				} catch (IOException e) {}						
				if (word[i] == 0xD) {
					word[i] = 0x7F;
					stop = true;
					break;			
				}
			} //end for
			Memory.addWord(k, String.valueOf(word));
			if (!stop)
				k++;
		} // end while
		System.out.println("");
	}
	
	//=================================	
	
	static String registerToString(int registerNumber) {
		return String.format("%H%H%H%H", 
				((char[])channels[registerNumber])[0], 
				((char[])channels[registerNumber])[1], 
				((char[])channels[registerNumber])[2], 
				((char[])channels[registerNumber])[3]);
	}
	
	static void stringToRegister(int registerNumber, String string) {
		for (int i = 0; i < ((char[])channels[registerNumber]).length; i++)
			if (i + string.length() >= ((char[])channels[registerNumber]).length)
				((char[])channels[registerNumber])[i] = (char) Character.digit(string.charAt(i + string.length() - 4), 16);
			else ((char[])channels[registerNumber])[i] = 0x0;
	}
	
	static String registerToString1B(int registerNumber) {
		return String.format("%H", 
				((char[])channels[registerNumber])[0]);
	}	
	
	static void stringToRegister1B(int registerNumber, String string) {
		((char[])channels[registerNumber])[0] = (char) Character.digit(string.charAt(0), 16);
	}

}
