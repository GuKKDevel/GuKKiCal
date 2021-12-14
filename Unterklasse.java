
public class Unterklasse {
SubUnterklasse sk;
String eig1;
	public Unterklasse() {
		System.out.println("Unterklasse Konstruktor");
		// TODO Automatisch generierter Konstruktorstub
	}
	boolean v_sk = false;
	public void neuezeile(String string) {
		System.out.println("Unterklasse neuezeile");
		if (string.equals("au2")) {
			sk = new SubUnterklasse();
			v_sk = true;
		} else if (string.equals("eu2")) {
			v_sk = false;
		} else if (v_sk) {
			sk.neuezeile (string);
		} else if (string.equals("eig1")) {
			eig1 = "gesetzt";
		}
	}
}
