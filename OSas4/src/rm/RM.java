package rm;

public class RM {
	public static final int AA = 2-1, BB = 3-1, CC = 4-1,
			PC = 1-1, SP = 6-1, PTR = 5-1, SR = 7-1, 
			DI = 10-1, SI = 8-1, PI = 9-1, TI = 11-1, 
			OS = 12-1;	
	static byte MODE;	
	static Object[] registers;
	public static int regOS = 0;
	static boolean supervisor;
		
	public static void createRM() {
		registers = new Object[11];
		
		
		for (int i = 0; i < 6; i++) {
			registers[i] = new char[4];
			((char[])registers[i])[0] = 0x0;
			((char[])registers[i])[1] = 0x0;
			((char[])registers[i])[2] = 0x0;
			((char[])registers[i])[3] = 0x0;
		}
		
		for (int i = 6; i < 11; i++) {
			registers[i] = new char[1];
			((char[])registers[i])[0] = 0x0;
		}

		stringToRegister(SP, "00FF"); 
		stringToRegister(PC, "0100");
		
		createPagingTable();		

		MODE = 1;
		((char[])registers[TI])[0] = 0xF;
		//createInterruptTable();
		
	}
	
	/*public static void createInterruptTable() {
		Memory.addWord(0x0, "0100"); //PI = 1 pertraukimo adresas
		Memory.addWord(0x1, "0110"); //PI = 2 pertraukimo adresas
		Memory.addWord(0x2, "0120"); //PI = 3 pertraukimo adresas
		Memory.addWord(0x3, "0130"); //PI = 4 pertraukimo adresas
		Memory.addWord(0x4, "0140"); //SI = 1 pertraukimo adresas
		Memory.addWord(0x5, "0150"); //TI = 0 pertraukimo adresas
		Memory.addWord(0x6, "0160"); //DI = 1 pertraukimo adresas
		Memory.addWord(0x7, "0170"); //DI = 2 pertraukimo adresas
		Memory.addWord(0x8, "0180"); //DI = 3 pertraukimo adresas
		Memory.addWord(0x9, "0190"); //DI = 4 pertraukimo adresas
		Memory.addWord(0xA, "01A0"); //DI = 5 pertraukimo adresas
		Memory.addWord(0xB, "01B0"); //DI = 6 pertraukimo adresas
		
		interruptCommandsCreator(); 
	}
	
	public static void interruptCommandsCreator() {		
		// PI
		Memory.addWord(0x0100, "O201");
		Memory.addWord(0x0101, "Y200");
		Memory.addWord(0x0102, "M900");
		Memory.addWord(0x0103, "RTRN");
		
		Memory.addWord(0x0110, "O201");
		Memory.addWord(0x0111, "Y210");
		Memory.addWord(0x0112, "M900");
		Memory.addWord(0x0113, "RTRN");
				
		Memory.addWord(0x0120, "O201");
		Memory.addWord(0x0121, "Y220");
		Memory.addWord(0x0122, "M900");
		Memory.addWord(0x0123, "RTRN");
				
		Memory.addWord(0x0130, "O201");
		Memory.addWord(0x0131, "Y230");
		Memory.addWord(0x0132, "M900");
		Memory.addWord(0x0133, "RTRN");
			
		// SI	
		Memory.addWord(0x0140, "O201");
		Memory.addWord(0x0141, "Y240");
		Memory.addWord(0x0142, "M800");
		Memory.addWord(0x0143, "RTRN");
				
		// TI
		Memory.addWord(0x0150, "MB0F");
		Memory.addWord(0x0151, "RTRN");
				
		// DI
		Memory.addWord(0x0160, "XCHG");
		Memory.addWord(0x0161, "MA00");
		Memory.addWord(0x0162, "RTRN");
				
		Memory.addWord(0x0170, "XCHG");
		Memory.addWord(0x0171, "MA00");
		Memory.addWord(0x0172, "RTRN");
				
		Memory.addWord(0x0180, "XCHG");
		Memory.addWord(0x0181, "MA00");
		Memory.addWord(0x0182, "RTRN");
				
		Memory.addWord(0x0190, "XCHG");
		Memory.addWord(0x0191, "MA00");
		Memory.addWord(0x0192, "RTRN");
				
		Memory.addWord(0x01A0, "XCHG");
		Memory.addWord(0x01A1, "MA00");
		Memory.addWord(0x01A2, "RTRN");
				
		Memory.addWord(0x01B0, "XCHG");
		Memory.addWord(0x01B1, "MA00");
		Memory.addWord(0x01B2, "RTRN");
	}*/
	

	
	public static void CHMD() { 
		if (MODE == 0) {
			// pop is supervizoriaus steko
//			PPxy(PC+1);
//			PPxy(SP+1);
			
			MODE = 1; 
			// pop is vartotojo steko
//			PPxy(AA+1);
//			PPxy(BB+1);
//			PPxy(CC+1);
				
		}
		else {
			// push i vartotojo steka 
//			PSxy(CC+1);
//			PSxy(BB+1);
//			PSxy(AA+1);
			MODE = 0;
			
//			char[] sp = new char[4];
//			sp = registerToString(SP).toCharArray();
//			
//			stringToRegister(SP, "7FFF"); 
//			
//			// push i supervizoriaus steka
//			Memory.addWord(Integer.parseInt(registerToString(SP), 16), String.valueOf(sp));
//			int spValue = Integer.parseInt(registerToString(SP), 16) - 1;
//			stringToRegister(SP, Integer.toHexString(spValue)); 
//
//			PSxy(PC+1);
				
		}
	}
	
	public static boolean runPC() { //grazina ar ivyko pertraukimas
		char[] word;
		/*if (MODE == 0) {
			word = Memory.get()[Integer.parseInt(registerToString(PC), 16)].getWord();
		}*/
		//else {
		
		// persijungia i vartotojo rezima
		if (MODE == 0)
			CHMD();
		
		//vykdo emuliatoriu
		
		word = Memory.get()[Integer.parseInt(String.valueOf(virtualToReal(registerToString(PC).toCharArray())), 16)].getWord();
		//System.out.println("VM WORDAS:: "+String.valueOf(word)+" PC::: "+registerToString(PC));
		guessTheCommand(word);	
		
		/*if (MODE == 1) {
			supervisor = false;
		}
		else {
			supervisor = true;
		}*/
			
		/*if (supervisor) { 
			if ( String.valueOf(word).equals("IRET") ) { //jeigu apdoroto pertraukimo pabaiga
				ChannelDevice.setValueOfChannel(0, 0); 
		    	ChannelDevice.setValueOfChannel(1, 0);
		    	ChannelDevice.setValueOfChannel(2, 0);
		    	ChannelDevice.setValueOfChannel(3, 0);
				CHMD();
			}
		} 
		else {*/
		// tikrina ar ivyko pertraukimas ir sumazina timeri
			CHMD();
			PRDx(TI+1); 
			return TEST();	
		//}

	}
		
	public static void guessTheCommand(char[] command) { 
		char[] c = command;
		switch (c[0]) {
		case 'A' : {
			if (c[1] == 'D' && isThisAValidRegisterNumber(c[2]) && isThisAValidRegisterNumber(c[3]))
				ADxy(Character.digit(c[2], 10), Character.digit(c[3], 10));
			else stringToRegister1B(PI, "2");
		}
		break; 
		case 'C' : {
			if ((c[1] == 'A') && (c[2] == 'L') && (c[3] == 'L')) {
				CALL();
			}
			else if ((c[1] == 'M') && (c[2] == 'A') && (c[3] == 'B')) {
				CMAB();
			}
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'D' : {
			if (c[1] == 'V' && isThisAValidRegisterNumber(c[2]) && isThisAValidRegisterNumber(c[3])) 
				DVxy(Character.digit(c[2], 10), Character.digit(c[3], 10));
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'H' : {
			if ((c[1] == 'A') && (c[2] == 'L') && (c[3] == 'T')) {
				HALT();
			}
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'I' : {
			if ((c[1] == 'R') && (c[2] == 'E') && (c[3] == 'T')) {
				IRET();
			}
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'Y' : {
			if (isThisAValidRegisterNumber(c[1]) && isThisThingAHexNumber(c[2]) && isThisThingAHexNumber(c[3]))
				Yxyz(Character.digit(c[1], 10), c[2], c[3]);
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'J' : {
			if ((c[1] == 'M') && (c[2] == 'P') && (c[3] == 'A')) {
				JMPA();
			}
			else if ((c[1] == 'M') && (c[2] == 'P') && (c[3] == 'E')) {
				JMPE();
			}
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'M' : {
			if (c[1] == 'L'){
				if (isThisThingAHexNumber(c[2]) && isThisThingAHexNumber(c[3]))
					MLxy(Character.digit(c[2], 10), Character.digit(c[3], 10));
			}
			else if (c[1] == 'V') {
				// M ir registras
				if (c[2] == 'M' && isThisAValidRegisterNumber(c[3]))  {
					MVxy(Character.digit(c[3], 10), "Mx");				
				}
				else if (c[3] == 'M' && isThisAValidRegisterNumber(c[2])) {
					MVxy(Character.digit(c[2], 10), "xM");
				}
				// du registrai
				else if (isThisAValidRegisterNumber(c[2]) && isThisAValidRegisterNumber(c[3])) {
					MVxy(Character.digit(c[2], 10), Character.digit(c[3], 10));
				}
				// RR RH HH HR
				else if ((c[2] == 'R' || c[2] == 'H') && (c[3] == 'R' || c[3] == 'H')) {
					MVxy(c);
				}
			}
			else if (isThisThingAHexNumber(c[1]) && isThisThingAHexNumber(c[3]))
				Mxyz(Character.digit(c[1], 16), c[3]);
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'O' : {
			if (isThisAValidRegisterNumber(c[1]) && isThisThingAHexNumber(c[2]) && isThisThingAHexNumber(c[3]))
				Oxyz(Character.digit(c[1], 10), c[2], c[3]);
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'P' : {
			if (c[1] == 'P' && isThisThingAHexNumber(c[3])) 
				PPxy(Character.digit(c[3], 10));
			else if ((c[1] == 'R') && (c[2] == 'D') && isThisThingAHexNumber(c[3])) 
				PRDx(Character.digit(c[3], 16));
			else if (c[1] == 'S' && isThisThingAHexNumber(c[3])) 
				PSxy(Character.digit(c[3], 10));
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'R' : {
			if ((c[1] == 'E') && (c[2] == 'A') && (c[3] == 'D')) {
				READ();
			}
			else if ((c[1] == 'T') && (c[2] == 'R') && (c[3] == 'N')) {
				RTRN();
			}
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'S' : {
			if (c[1] == 'B' && isThisThingAHexNumber(c[2]) && isThisThingAHexNumber(c[3]))
				SBxy(Character.digit(c[2], 10), Character.digit(c[3], 10));
			else if ((c[1] == 'C') && (c[2] == 'C' && isThisThingAHexNumber(c[3])))
				SCCx(Character.digit(c[3], 10));
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'W' : {
			if ((c[1] == 'R') && (c[2] == 'T') && (c[3] == 'D')) {
				WRTD();
			}
			else stringToRegister1B(PI, "2");
		}
		break;
		case 'X' : {
			if ((c[1] == 'C') && (c[2] == 'H') && (c[3] == 'G')) {
				XCHG();
			}
			else stringToRegister1B(PI, "2");
		}
		break;	
		default :
			stringToRegister1B(PI, "2");
		}
		
		if (!(String.valueOf(c).equals("JMPA") || String.valueOf(c).equals("JMPE") 
				|| String.valueOf(c).equals("CALL") || String.valueOf(c).equals("RTRN"))) {
			stringToRegister(PC, Integer.toHexString(Integer.parseInt(registerToString(PC), 16)+1));
		}

	}
	
	

	private static boolean canIDoThisCommand(int x) {
		if (MODE == 1) { 
			if (!(((x == AA) || (x == BB) || (x == CC)))) {
				stringToRegister1B(PI, "1");
				return false;
			}
		}
		return true;
	}
	
	private static boolean canIDoThisArithmeticCommand(int x, int y) {
		return canIDoThisCommand(x) && canIDoThisCommand(y);
	}
	
	public static void ADxy(int x, int y) {
		x--;
		y--;
		
		if (!canIDoThisArithmeticCommand(x, y))
			return;
		
		int sum = Integer.parseInt(registerToString(x), 16) + Integer.parseInt(registerToString(y), 16);
		if (sum > 65535) { //jei OF
			stringToRegister1B(PI, "3");
			return;
		}
		stringToRegister(x, Integer.toHexString(sum));
	}
	
	public static void SBxy(int x, int y) {
		x--;
		y--;
		
		if (!canIDoThisArithmeticCommand(x, y))
			return;
		
		int sub = Integer.parseInt(registerToString(x), 16) - Integer.parseInt(registerToString(y), 16);
		if (sub < 0) {
			stringToRegister1B(PI, "3");
			return;
		}
		stringToRegister(x, Integer.toHexString(sub));
	}
	
	public static void MLxy(int x, int y) {
		x--;
		y--;
		
		if (!canIDoThisArithmeticCommand(x, y))
			return;
		
		int mul = Integer.parseInt(registerToString(x), 16) * Integer.parseInt(registerToString(y), 16);
		if (mul > 65535) {
			stringToRegister1B(PI, "3");
			return;
		}
		stringToRegister(x, Integer.toHexString(mul));		
	}
	
	public static void DVxy(int x, int y) {
		x--;
		y--;

		if (!canIDoThisArithmeticCommand(x, y))
			return;
		
		if ((Integer.parseInt(registerToString(y), 16)) == 0) {
			stringToRegister1B(PI, "4");
			return;
		}
		
		int dv1 = Integer.parseInt(registerToString(x), 16) / Integer.parseInt(registerToString(y), 16);
		int dv2 = Integer.parseInt(registerToString(x), 16) % Integer.parseInt(registerToString(y), 16);
		stringToRegister(x, Integer.toHexString(dv1));		
		stringToRegister(y, Integer.toHexString(dv2));
	}
	

	
	public static void Oxyz(int x, char y, char z) {
		x--;
		if (!canIDoThisCommand(x))
			return;
		((char[])registers[x])[0] = (char) Character.digit(y, 16);
		((char[])registers[x])[1] = (char) Character.digit(z, 16);
	}
	
	public static void Yxyz(int x, char y, char z) {
		x--;
		if (!canIDoThisCommand(x))
			return;
		((char[])registers[x])[2] = (char) Character.digit(y, 16);
		((char[])registers[x])[3] = (char) Character.digit(z, 16);
	}
	
	public static void CMAB() {
		if (Integer.parseInt(registerToString(AA), 16) == Integer.parseInt(registerToString(BB), 16)) {
			((char[])registers[SR])[0] = 0x0;			
		}
		else if (Integer.parseInt(registerToString(AA), 16) < Integer.parseInt(registerToString(BB), 16)) {
			((char[])registers[SR])[0] = 0x1;			
		}
		else if (Integer.parseInt(registerToString(AA), 16) > Integer.parseInt(registerToString(BB), 16)) {
			((char[])registers[SR])[0] = 0x2;			
		}
	}
	
	public static void SCCx(int x) {
		x--;	
		if (!canIDoThisCommand(x)) {
			return;
		}
		int i;
		if (x < SR) {
			i = Integer.parseInt(registerToString(x), 16);
			i++;
			stringToRegister(x, Integer.toHexString(i));
		} else {
			i = Integer.parseInt(registerToString1B(x), 16);
			i++;
			stringToRegister1B(x, Integer.toHexString(i));
		}
	}
	
	public static void PRDx(int x) {
		x--;
		if (!canIDoThisCommand(x)) {
			return;
		}
		//jei supervizoriui, gali maþint visus registrus
		int i;
		if (x < SR) {
			i = Integer.parseInt(registerToString(x), 16);
			i--;
			stringToRegister(x, Integer.toHexString(i));
		} else {
			i = Integer.parseInt(registerToString1B(x), 16);
			i--;
			stringToRegister1B(x, Integer.toHexString(i));
		}

	}
	
	public static void PSxy(int y) {
		  y--;
		 
		  if (MODE == 0) {
			    Memory.addWord(Integer.parseInt(registerToString(SP), 16), registerToString(y));
		  }
		  else {
				Memory.addWord(Integer.parseInt(String.valueOf(virtualToReal(registerToString(SP).toCharArray())), 16), registerToString(y));	
		  }
		  int spValue = Integer.parseInt(registerToString(SP), 16) - 1;
		  stringToRegister(SP, Integer.toHexString(spValue)); 

	}
	
	public static void PPxy(int y) {
		  y--;

		  int spValue = Integer.parseInt(registerToString(SP), 16) + 1;
		  stringToRegister(SP, Integer.toHexString(spValue));
		  
		  if (MODE == 0) {
			    stringToRegister(y, String.valueOf(Memory.get()[Integer.parseInt(registerToString(SP), 16)].getWord()));
		  }
		  else {
			  	stringToRegister(y, String.valueOf(Memory.get()[Integer.parseInt(String.valueOf(virtualToReal(registerToString(SP).toCharArray())), 16)].getWord()));
		  }

	}
	
	private static void HALT() {
		stringToRegister1B(SI, "1");
	}

	public static void JMPA() {
		if ((MODE == 1) && (Integer.parseInt(registerToString(CC), 16) > 0x7FFF)) {
			stringToRegister1B(PI, "1");
			return;
		}
		
		((char[])registers[PC])[0] = ((char[])registers[CC])[0];
		((char[])registers[PC])[1] = ((char[])registers[CC])[1];
		((char[])registers[PC])[2] = ((char[])registers[CC])[2];
		((char[])registers[PC])[3] = ((char[])registers[CC])[3];
	}

	public static void JMPE() {
		if (((char[])registers[SR])[0] == 0x0)
			JMPA();
	}
	
	public static void CALL() {
		PSxy(PC+1);
		JMPA();
	}
		 
	public static void RTRN() {
		PPxy(PC+1); 
		int pcValue = Integer.parseInt(registerToString(PC), 16) + 1;
		stringToRegister(PC, Integer.toHexString(pcValue));
	}
	
	public static void IRET() {
		PPxy(PC+1); 
		int pcValue = Integer.parseInt(registerToString(PC), 16) + 1;
		stringToRegister(PC, Integer.toHexString(pcValue));
	}
	
	public static void Mxyz(int x, char z) {
		x--;
		((char[])registers[x])[0] = (char) Character.digit(z, 16);
	}
	
	public static void MVxy(int reg, String direction) { // registras x, kryptis Mx || xM
		reg--;

		if (direction.equals("Mx")) { // is memory i reg
			stringToRegister(reg, Memory.get()[ Integer.parseInt(registerToString(CC), 16) ].getWord().toString() );
		}
		else { // is reg i memory
			Memory.get()[ Integer.parseInt(registerToString(CC), 16) ].setWord(registerToString(reg).toCharArray());	
		}		
	}

	public static void MVxy(int reg1, int reg2) { // is reg1 i reg2
		reg1--;
		reg2--;
		
		if (reg1 < SR && reg2 < SR && reg1 != reg2) {
			stringToRegister(reg2, registerToString(reg1));			
		}	
	}

	public static void MVxy(char[] c) {
		if (c[2] == 'R' && c[3] == 'R') {
			Mxyz(DI+1, '1');
		}
		else if (c[2] == 'R' && c[3] == 'H') {
			Mxyz(DI+1, '2');
		} 
		else if (c[2] == 'H' && c[3] == 'H') {
			Mxyz(DI+1, '5');
		}
		else if (c[2] == 'H' && c[3] == 'R') {
			Mxyz(DI+1, '4');
		}		
	}

	public static void READ() {
		Mxyz(DI+1, '6');	
	}

	public static void WRTD() {
		Mxyz(DI+1, '3'); 
	}
	
	public static boolean TEST() {		// true jeigu ivyko pertraukimas
		if ( Integer.parseInt(registerToString1B(PI), 16) == 1) {
			/*stringToRegister(CC, (Memory.get()[0x0]).toString());
		    CALL();*/
			return true;
		} 
		else if ( Integer.parseInt(registerToString1B(PI), 16) == 2) {
			/*stringToRegister(CC, (Memory.get()[0x1]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(PI), 16) == 3) {
			/*stringToRegister(CC, (Memory.get()[0x2]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(PI), 16) == 4) {
			/*stringToRegister(CC, (Memory.get()[0x3]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(SI), 16) == 1) {
			/*stringToRegister(CC, (Memory.get()[0x4]).toString());
		    CALL();*/
			return true;
		}
		
		else if ( Integer.parseInt(registerToString1B(DI), 16) == 1) {
			/*PSxy(CC+1);
			stringToRegister(CC, (Memory.get()[0x6]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(DI), 16) == 2) {
			/*PSxy(CC+1);
			stringToRegister(CC, (Memory.get()[0x7]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(DI), 16) == 3) {
			/*PSxy(CC+1);
			stringToRegister(CC, (Memory.get()[0x8]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(DI), 16) == 4) {
			/*PSxy(CC+1);
			stringToRegister(CC, (Memory.get()[0x9]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(DI), 16) == 5) {
			/*PSxy(CC+1);
			stringToRegister(CC, (Memory.get()[0xA]).toString());
		    CALL();*/
			return true;
		}
		else if ( Integer.parseInt(registerToString1B(DI), 16) == 6) {
			/*PSxy(CC+1);
			stringToRegister(CC, (Memory.get()[0xB]).toString());
		    CALL();*/
			return true;
		}
		
		else if ( Integer.parseInt(registerToString1B(TI), 16) == 0) {
			//stringToRegister1B(TI, "F");
			/*stringToRegister(CC, (Memory.get()[0x5]).toString());
		    CALL();*/
			return true;
		}
		else {
			CHMD(); 
			return false;
		}
		
	}
	
	public static void XCHG() {
		switch (Integer.parseInt(registerToString1B(DI), 16)) {
		case 1 : {
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 0x1);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 0x1);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Integer.parseInt(String.valueOf(virtualToReal(registerToString(AA).toCharArray())), 16)); 
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, Integer.parseInt(String.valueOf(virtualToReal(registerToString(BB).toCharArray())), 16));
			PPxy(AA+1); //PC
			PPxy(CC+1); //CC
			PSxy(AA+1); //PC
		}
		break;
		case 2 : {
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 0x1);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 0x2);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Integer.parseInt(String.valueOf(virtualToReal(registerToString(AA).toCharArray())), 16));
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, Integer.parseInt(registerToString(BB), 16));
			PPxy(AA+1); //PC
			PPxy(CC+1); //CC
			PSxy(AA+1); //PC
		}
		break;
		case 3: {
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 0x1);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 0x3);
			PPxy(AA+1); //PC
			PPxy(CC+1); //CC
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Integer.parseInt(String.valueOf(virtualToReal(registerToString(CC).toCharArray())), 16));
			PSxy(AA+1); //PC
		}
		break;
		case 4 : {
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 0x2);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 0x1);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Integer.parseInt(registerToString(AA), 16));
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, Integer.parseInt(String.valueOf(virtualToReal(registerToString(BB).toCharArray())), 16));
			PPxy(AA+1); //PC
			PPxy(CC+1); //CC
			PSxy(AA+1); //PC
		}
		break;
		case 5 : {
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 0x2);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 0x2);
			ChannelDevice.setValueOfChannel(ChannelDevice.IA, Integer.parseInt(registerToString(AA), 16));
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, Integer.parseInt(registerToString(BB), 16));	
			PPxy(AA+1); //PC
			PPxy(CC+1); //CC
			PSxy(AA+1); //PC
		}
		break;
		case 6: {
			ChannelDevice.setValueOfChannel(ChannelDevice.IO, 0x3);
			ChannelDevice.setValueOfChannel(ChannelDevice.OO, 0x1);
			PPxy(AA+1); //PC
			PPxy(CC+1); //CC
			ChannelDevice.setValueOfChannel(ChannelDevice.OA, Integer.parseInt(String.valueOf(virtualToReal(registerToString(CC).toCharArray())), 16));
			PSxy(AA+1); //PC
		}
		break;
		}
		ChannelDevice.runDevice();	
	}
	
	private static void createPagingTable() {
		Memory.addWord(0x9000, "C000");
		Memory.addWord(0x9001, "F200");
		Memory.addWord(0x9002, "AF00");
		
		stringToRegister(PTR, "9003");
	}
	
	public static char[] virtualToReal(char[] addressV) {
		char[] a = { 
				registerToString(PTR).toCharArray()[0],
				registerToString(PTR).toCharArray()[1], 
				addressV[0], 
				addressV[1]
						};
		
		char[] addressR = {
				Memory.get()[Integer.parseInt(String.valueOf(a), 16)].getWord()[0],
				Memory.get()[Integer.parseInt(String.valueOf(a), 16)].getWord()[1],
				addressV[2], 
				addressV[3]
				}; 
		
		return addressR;
	}
	

	//============================================================================================	
	public static String registerToString(int registerNumber) {
		return String.format("%H%H%H%H", 
				((char[])registers[registerNumber])[0], 
				((char[])registers[registerNumber])[1], 
				((char[])registers[registerNumber])[2], 
				((char[])registers[registerNumber])[3]);
	}
	
	public static void stringToRegister(int registerNumber, String string) {
		for (int i = 0; i < ((char[])registers[registerNumber]).length; i++)
			if (i + string.length() >= ((char[])registers[registerNumber]).length)
				((char[])registers[registerNumber])[i] = (char) Character.digit(string.charAt(i + string.length() - 4), 16);
			else ((char[])registers[registerNumber])[i] = 0x0;
	}
	
	public static String registerToString1B(int registerNumber) {
		return String.format("%H", 
				((char[])registers[registerNumber])[0]);
	}
	
	public static void stringToRegister1B(int registerNumber, String string) {
		((char[])registers[registerNumber])[0] = (char) Character.digit(string.charAt(0), 16);
	}
	
	private static boolean isThisThingAHexNumber(char number) {
		return Character.digit(number, 16) != -1;
	}
	
	private static boolean isThisAValidRegisterNumber(char number) {
		return isThisThingAHexNumber(number) && 
				(Character.digit(number, 16) >= 0x0) && 
				((Character.digit(number, 16) <= 0xB));
	}
	
/*	public static void setValueOfChannel(int channel, int value) {
		if (channel < SR)
			stringToRegister(channel, Integer.toHexString(value));
		else
			stringToRegister1B(channel, Integer.toHexString(value));
	}*/

	

}
