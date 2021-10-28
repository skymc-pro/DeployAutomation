package pro.skymc.deployautomation;

import pro.skymc.deployautomation.config.ClientConfiguration;
import pro.skymc.deployautomation.connection.Connection;
import pro.skymc.deployautomation.connection.Protocol;
import pro.skymc.deployautomation.tasks.FolderMonitorTask;

import java.io.File;
import java.util.*;

public class Main {

    private static ClientConfiguration configuration;
    private static Connection connection;
    private static Map<String, String[]> whatToUpload = new HashMap<String, String[]>();
    public static Logger LOG;

    public static void main(String[] args) {
        configuration = new ClientConfiguration.Builder()
                .address("localhost")
                .port(22)
                .username("username").password("password")
                .protocol(Protocol.SFTP)
                .build();
        LOG = new Logger("AutoDeploy " + configuration.protocol.name());
        LOG.log("Configuration has been created", Logger.Severity.INFO);

        whatToUpload.put("file", new String[]{"path"});
        LOG.log("Collecting data about files to monitor them", Logger.Severity.INFO);

        connection = new Connection();
        LOG.log("Configuring protocol...", Logger.Severity.INFO);
        connection.setup(configuration);
        LOG.log("Trying to connect to " + configuration.address + ":" + configuration.port, Logger.Severity.INFO);
        connection.connect();
        LOG.log("Successfully connected", Logger.Severity.INFO);

        LOG.log("Creating folder monitor task", Logger.Severity.INFO);
        TimerTask task = new FolderMonitorTask(new File("/path/to/monitor/")) {
            @Override
            public void onChange(File file) {
                if(whatToUpload.get(file.getName()) != null) {
                    String[] paths = whatToUpload.get(file.getName());
                    for (String path : paths) connection.uploadFile(file, path);
                }
            }
        };

        new Timer().schedule(task, new Date(), 1000);
        LOG.log("Started folder monitor task", Logger.Severity.INFO);
    }
}
