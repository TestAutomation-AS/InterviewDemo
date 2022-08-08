package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pojo.CommonEventDetails;

public class Utility {
	
	static Logger logger = Logger.getLogger(Utility.class.getName());
	public static HashMap<String, CommonEventDetails> map = null;
	private static SessionFactory sessionFactory = null;
	
	
	public static void SetMap(HashMap<String, CommonEventDetails> hashmap) {
		map = hashmap;
	}
	
	public static HashMap<String, CommonEventDetails> getMap() {
		return map;
	}
	
	public static void setSessionFactory() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static boolean checkArgument(String[] args) {
		if(args == null || args.length == 0) {
			logger.log(Level.SEVERE, "No arguments passed for file path");
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean checkFilePresent(File file) {
		if(!file.exists()) {
			logger.log(Level.SEVERE,"No file is present on the location");
			return false;
		}else {
			return true;
		}
	     
	}
	
	public static double getFileSizeInMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024) ;
	}
	
	
	
	
}
