package rm;
public class Word {
		private final int WORD_SIZE = 4; // þodþio dydis baitais
		private char[] word; 
		
		public Word() {
			word = new char[WORD_SIZE];
		}
		
		public char[] getWord() {
			return word;
		}
		
		public void setWord(char[] word) {
			this.word = word;
		}
		
		public String toString() {
			String str = null;
			if ((word[0] < 0x10) && (word[0] >= 0x0))
				str = String.format("%H", word[0]);
			else str = String.valueOf(word[0]);
			
			if ((word[1] < 0x10) && (word[1] >= 0x0))
				str += String.format("%H", word[1]);
			else str += String.valueOf(word[1]);
			
			if ((word[2] < 0x10) && (word[2] >= 0x0))
				str += String.format("%H", word[2]);
			else str += String.valueOf(word[2]);
			
			if ((word[3] < 0x10) && (word[3] >= 0x0))
				str += String.format("%H", word[3]);
			else str += String.valueOf(word[3]);
			return str;
		}
	}