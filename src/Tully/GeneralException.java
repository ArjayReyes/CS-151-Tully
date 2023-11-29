import javax.swing.*;

public abstract class GeneralException extends Exception {

	private static final long serialVersionUID = 1L;
	public GeneralException(String e) {
        super(e);
    }
	public abstract void displayMessage(JFrame frame, String str);
}
