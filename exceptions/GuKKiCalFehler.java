package exceptions;
/**
*
* @author gukkdevel <br>
*         <br>
*
* Diese Exceptions verhindern einen korrekten Ablauf und führen zu einem Abbruch
*/
public class GuKKiCalFehler extends Exception {
	public GuKKiCalFehler () {}
	public GuKKiCalFehler (String fehlerGrund) {
		super (fehlerGrund);
	}

}
