package executor;


import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import utilities.ReadLogFile;
import utilities.Utility;
import pojo.CommonEventDetails;
import pojo.Event;

public class LogfileHandler {
	
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(LogfileHandler.class.getName());
		File file = null;
		
		String[] str = {"C:\\Logfiles"};
		
		//check the argument 
		 if(!Utility.checkArgument(str)) {
		     return;
		  }
		 
	 	file = new File(str[0] + "/logfile.txt");
	 	if(!Utility.checkFilePresent(file)) {
			return;	
		}
	 	
		//get the file size in megabytes
		 double fileSize = Utility.getFileSizeInMegaBytes(file);
		 int numberOfThread = 1; //default number of threads will be 1
		 //calculate the number of threads based on file size
		 if(fileSize > 100) {
			 numberOfThread = (int) fileSize / 100 ;
			 logger.log(Level.INFO, "Total no of threads: " + numberOfThread);
		 }
		 
		 //open the log file
		 ReadLogFile.openFile(file);
		 
		 HashMap<String, CommonEventDetails> map = new HashMap<String, CommonEventDetails>();
		 Utility.SetMap(map);
		 Utility.setSessionFactory();
		 
		 //create multiple thread based on file size
		 for (int i = 0; i < numberOfThread; i++) {
	            ProcessData object = new ProcessData();
	            object.start();
	     }
		 
		 Utility.getSessionFactory().close();
	}

}

class ProcessData extends Thread {
	static Logger logger = Logger.getLogger(ProcessData.class.getName());
	 public void run(){
        try {
        	Session session = Utility.getSessionFactory().openSession();
        	session.beginTransaction();
        	 String line = "";
    		 CommonEventDetails event;
    		 
	    	 while(line != null) {
    			 line = ReadLogFile.getLine();
    			 if(line == null) {
    				break;
    			 }
    			 event = new ObjectMapper().readValue(line, CommonEventDetails.class);
    			 checkAlreadyMapped(event, session);	 
    		}
	    	 session.getTransaction().commit();
			 session.close(); 
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured in thread operation: " + e.getMessage());
        }
	}
	 
	public static void checkAlreadyMapped(CommonEventDetails event, Session session) {
		CommonEventDetails dataPresent = Utility.getMap().putIfAbsent(event.getId(), event);
		if(dataPresent != null) {
			Event e = getEventData(dataPresent, event);
			session.persist(e);
			Utility.getMap().remove(event.getId());
		}
	}
	
	public static Event getEventData(CommonEventDetails p, CommonEventDetails c) {
		Event event = new Event();
		event.setId(p.getId());
		if(p.getHost() != null) {
			event.setHost(p.getHost());
		}else if(c.getHost() != null) {
			event.setHost(c.getHost());
		}
		if(p.getType() != null) {
			event.setType(p.getType());
		}else if(c.getType() != null) {
			event.setType(p.getType());
		}
		
		event.setDuration(Math.abs(p.getTimestamp()-c.getTimestamp()));
		if(event.getDuration() > 4) {
			event.setAlert(true);
		}else {
			event.setAlert(false);
		}
		return event;
		
			
		
	}
	 
	 
}
