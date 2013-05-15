package Procesai;


public class BeginEnd extends ProcessBase {
	public void run() {
		Sarasai.st.add("bla");
		System.out.println(Sarasai.st.get(0));
		System.out.println("blablabla");
	}
}
