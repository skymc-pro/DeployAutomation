package pro.skymc.deployautomation.clients;

import com.jcraft.jsch.*;
import pro.skymc.deployautomation.Logger;
import pro.skymc.deployautomation.Main;
import pro.skymc.deployautomation.config.ClientConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SftpClient implements Client {

    private Session session;

    @Override
    public void setup(ClientConfiguration configuration) {
        try {
            this.session = new JSch().getSession(configuration.username, configuration.address, configuration.port);
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            this.session.setPassword(configuration.password);
            this.session.setTimeout(5000);
        }catch (JSchException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void connect() {
        try {
            this.session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        this.session.disconnect();
    }

    @Override
    public void uploadFile(File file, String directory) {
        Main.LOG.log("Uploading \"" + file.getName() + "\" to \"" + directory + "\"", Logger.Severity.INFO);
        try {
            ChannelSftp channel = (ChannelSftp) this.session.openChannel("sftp");
            channel.connect();

            ChannelExec exec = (ChannelExec) this.session.openChannel("exec");
            exec.setCommand("mkdir -p " + directory);
            exec.connect();
            exec.disconnect();

            channel.cd(directory);
            channel.put(new FileInputStream(file), file.getName(), ChannelSftp.OVERWRITE);
            channel.disconnect();
        } catch (JSchException | SftpException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Main.LOG.log("Successfully completed upload \"" + file.getName() + "\" to \"" + directory + "\"", Logger.Severity.INFO);
    }
}
