package Procesai;

import java.util.ArrayList;
import java.util.List;

public class PL {
	public static final int BeginEnd = 1;
	public static final int IInterrupt = 2;
	public static final int InputStream = 3;
	public static final int SyntaxCheck = 4;
	public static final int Loader = 5;
	public static final int Destroyer = 6;
	public static final int Interrupt = 7;
	public static final int Swapper = 8;
	public static final int Writer = 9;
	public static final int MainGovernor = 10;
	
	public static final int JobGovernor = 11;
	public static final int VirtualMachine = 12;
	
	public static List<ProcessBase> list = new ArrayList<ProcessBase>();
}
