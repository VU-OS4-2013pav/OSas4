package rm;

public class HDD {	
	public final static int HDD_SIZE = 0xA00;
	private static Word[] hdd;
	
	public static void create() {
		hdd = new Word[HDD_SIZE];
				
		for (int i = 0; i < HDD_SIZE; i++) {
			hdd[i] = new Word();
			hdd[i].getWord()[0] = '0';
			hdd[i].getWord()[1] = '0';
			hdd[i].getWord()[2] = '0';
			hdd[i].getWord()[3] = '0';
		}
	}
	
	public static Word[] get() {
		return hdd;
	}
	
	public static void addWord(int wordNumber, String word) {
		HDD.get()[wordNumber].setWord(word.toCharArray());
	}

}
