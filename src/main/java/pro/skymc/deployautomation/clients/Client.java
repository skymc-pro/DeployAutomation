package pro.skymc.deployautomation.clients;

import pro.skymc.deployautomation.config.ClientConfiguration;

import java.io.File;

public interface Client {

    void setup(ClientConfiguration configuration);
    void connect();
    void disconnect();
    void uploadFile(File file, String directory);
}
