import javax.swing.*;

public class PasswordLoginException extends GeneralException {

	private static final long serialVersionUID = 1L;
	public PasswordLoginException(String e) {
        super(e);
    }
	public void displayMessage(JFrame frame, String str) {
		System.out.println("RAHHHHH");
		JOptionPane.showMessageDialog(frame, str);
	}
	
}
