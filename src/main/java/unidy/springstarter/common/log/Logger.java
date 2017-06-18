package unidy.springstarter.common.log;

import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

public class Logger {
	
	public static void log(Class<?> clazz, LogLevel logLevel, String message) {
		org.slf4j.Logger log = LoggerFactory.getLogger(clazz);
		
        switch (logLevel) {
            case TRACE:
            	log.trace(message);
                break;
            case DEBUG:
            	log.debug(message);
                break;
            case INFO:
            	log.info(message);
                break;
            case WARN:
            	log.warn(message);
                break;
            case ERROR:
            case FATAL:
            	log.error(message);
                break;
            default:
            	log.warn("No suitable log level found");
                break;
        }
    }
}
