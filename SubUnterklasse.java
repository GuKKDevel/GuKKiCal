
public class SubUnterklasse {
String eig1;
	public SubUnterklasse() {
		System.out.println("SubUnterklasse Konstruktor");
		// TODO Automatisch generierter Konstruktorstub
	}
	public void neuezeile(String string) {
		System.out.println("SubUnterklasse neuezeile");
		if (string.equals("eig1")) {
			eig1 = "gesetzt";
		}
	}
}
