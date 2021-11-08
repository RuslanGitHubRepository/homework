package logger;

import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.Objects;

public class Logger implements ILogger {
    private com.sun.org.slf4j.internal.Logger log = LoggerFactory.getLogger(Logger.class);
    private static Logger logger;
    private Logger() {}

    public static Logger getInstance() {
        synchronized(Logger.class) {
            if (Objects.isNull(logger)) {
                logger = new Logger();
            }
            return logger;
        }
    }

    @Override
    public void log(String message) {
        log.warn(message);
    }
}
