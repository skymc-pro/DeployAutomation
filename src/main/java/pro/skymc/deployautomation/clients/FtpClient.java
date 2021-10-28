package pro.skymc.deployautomation.clients;

import pro.skymc.deployautomation.config.ClientConfiguration;

import java.io.File;

public class FtpClient implements Client {

    @Override
    public void setup(ClientConfiguration configuration) {}

    @Override
    public void connect() {}

    @Override
    public void disconnect() {}

    @Override
    public void uploadFile(File file, String target) {}
}
