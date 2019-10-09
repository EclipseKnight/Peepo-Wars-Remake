package resources;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import game.handlers.CrashHandler;

public class ResourceLoader {
	public static ResourceLoader rl = new ResourceLoader();
	
	public static Image loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(rl.getClass().getResource(imageName));
		return ii.getImage();
	}
	
	public static File loadFile(String fileName) {
		File file = null;
		try {
			file = new File(rl.getClass().getResource(fileName).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			CrashHandler.throwError(e.toString());
		}
		return file;
	}
	
	public static InputStream loadStream(String fileName) {
		InputStream in = rl.getClass().getResourceAsStream(fileName);
		if(in != null) {
			return in;
		}
		CrashHandler.throwError(fileName + " not found");
		return null;
	}
}
