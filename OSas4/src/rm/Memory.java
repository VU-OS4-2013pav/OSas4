package rm;

public class Memory {	
	public final static int MEMORY_SIZE = 0x10000;
	private static Word[] memory;
	
	public static void create() {
		memory = new Word[MEMORY_SIZE];
				
		for (int i = 0; i < MEMORY_SIZE; i++) {
			memory[i] = new Word();
			memory[i].getWord()[0] = '0';
			memory[i].getWord()[1] = '0';
			memory[i].getWord()[2] = '0';
			memory[i].getWord()[3] = '0';
		}
	}
	
	public static Word[] get() {
		return memory;
	}
	
	public static void addWord(int wordNumber, String word) {
		Memory.get()[wordNumber].setWord(word.toCharArray());
	}

}
