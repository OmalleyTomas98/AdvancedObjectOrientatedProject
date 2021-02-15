package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.*;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
/**
 * 
 * 
 * @author Tomas O'malley
 * @version 2.0
 * @since 1.8
 * 
 *        Public class <b>Runner</b> Begins the entire Program by beginning the first
 *        method call in AppWindow 
 */
public class Runner {
	
	
	private EmbeddedStorageManager db=null;
	private  List<Class> root = new ArrayList();

	public static void main(String[] args) throws FileNotFoundException , IOException , NoClassDefFoundError {
		
		/**
		 *        Launches javafx user interface
		 */
		try {
		System.out.println("[INFO] Launching GUI...");
		Application.launch(AppWindow.class, args);
	}catch(NoClassDefFoundError e) {
		
		e.printStackTrace();
	}
	}
}
