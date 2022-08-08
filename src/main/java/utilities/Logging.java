package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Logging {
	
	public static Logger logger;
	
	    public static Logger getLogger(String classname) {
	    	if(logger.equals(null)) {
	    		logger = Logger.getLogger(classname);
	    		try {
					LogManager.getLogManager().readConfiguration(new FileInputStream(System.getProperty("user.dir") +"/logging.properties"));
					Handler fileHandler = new FileHandler(System.getProperty("user.dir") + "/logger.log", 2000, 5);
					fileHandler.setFormatter(new CustomFormatter());
			        logger.addHandler(fileHandler);
			        return logger;
				} catch (SecurityException | IOException e) {
					e.printStackTrace();
					return null;
				}
	    	}else {
	    		return logger;
	    	}
	            
	    }
	    
	    
}

class CustomFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
    	
        return record.getThreadID()+"::" + record.getInstant() + "::" + record.getLevel() + "::" + record.getSourceClassName()+"::"
                +record.getSourceMethodName()+"::" +record.getMessage()+"\n";
    }

}


