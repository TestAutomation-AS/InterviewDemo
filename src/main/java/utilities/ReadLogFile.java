package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReadLogFile {
	
	private static BufferedReader reader;
	static Logger logger = Logger.getLogger(ReadLogFile.class.getName());
	
	public static void openFile(File file) {
		try {
			reader = new BufferedReader(new FileReader(file));
			logger.log(Level.INFO, "File opened");
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Unable to open file: " + e.getMessage());
		}
	}
	
	public static String getLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
