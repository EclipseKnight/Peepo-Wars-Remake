package game.handlers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrashHandler extends JFrame {
	private static final long serialVersionUID = 3378569113993753219L;
	static CrashHandler ch = new CrashHandler();
	
	public static void throwError(String error) {
		JOptionPane.showMessageDialog(ch, "This shit broke... " + error);
	}
}
