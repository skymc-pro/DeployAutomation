package pro.skymc.deployautomation.connection;

import pro.skymc.deployautomation.clients.Client;
import pro.skymc.deployautomation.clients.FtpClient;
import pro.skymc.deployautomation.clients.SftpClient;
import pro.skymc.deployautomation.config.ClientConfiguration;

import java.io.File;

public class Connection implements Client {

    private Client client;

    public Connection(){}

    @Override
    public void setup(ClientConfiguration configuration) {
        if(configuration.protocol == Protocol.SFTP) {
            this.client = new SftpClient();
        }else{
            this.client = new FtpClient(); // todo
        }
        this.client.setup(configuration);
    }

    @Override
    public void connect() {
        this.client.connect();
    }

    @Override
    public void disconnect() {
        this.client.disconnect();
    }

    @Override
    public void uploadFile(File file, String directory) {
        this.client.uploadFile(file, directory);
    }
}
