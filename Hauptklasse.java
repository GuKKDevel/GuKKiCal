
public class Hauptklasse {
	Unterklasse uk;
	String eig1;
	String eig2;

	public Hauptklasse() {
		System.out.println("Hauptklasse Konstruktor");
		// TODO Automatisch generierter Konstruktorstub
	}


	public static void main(String[] args) {
		Hauptklasse hk = new Hauptklasse();
		hk.neuezeile("au1");
		hk.neuezeile("eig1");
		hk.neuezeile("au2");
		hk.neuezeile("eig1");
		hk.neuezeile("eu2");
		hk.neuezeile("eu1");
	}

	boolean v_uk = false;

	public void neuezeile(String string) {
		System.out.println("Hauptklasse neuezeile");
		if (string.equals("au1")) {
			uk = new Unterklasse();
			v_uk = true;
		} else if (string.equals("eu1")) {
			v_uk = false;
		}else if (v_uk) {
			uk.neuezeile (string);
		} else if (string.equals("eig1")) {
			eig1 = "gesetzt";
		}
	}
}
