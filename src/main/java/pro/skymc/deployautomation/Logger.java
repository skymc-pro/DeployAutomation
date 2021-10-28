package pro.skymc.deployautomation;

public class Logger {

    public enum Severity {
        INFO, DEBUG, WARN, ERROR, FATAL
    }

    private final String prefix;
    private boolean debug = false;

    public Logger(String prefix) {
        this.prefix = prefix;
    }


    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void log(String message, Severity severity) {
        if(severity == Severity.DEBUG && !debug) return;
        System.out.println("[" + prefix + "] " + message);
    }
}
