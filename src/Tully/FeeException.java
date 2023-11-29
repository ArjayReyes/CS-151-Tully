import javax.swing.*;

public class FeeException extends GeneralException {

	private static final long serialVersionUID = 1L;
	public FeeException(String e) {
        super(e);
    }
	public void displayMessage(JFrame frame, String str) {
		System.out.println("RAHHHHH");
		JOptionPane.showMessageDialog(frame, str);
	}
	
}
