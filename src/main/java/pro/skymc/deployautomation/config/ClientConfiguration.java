package pro.skymc.deployautomation.config;

import pro.skymc.deployautomation.connection.Protocol;

public class ClientConfiguration {

    public String address;
    public int port;
    public String username;
    public String password;
    public Protocol protocol;

    public ClientConfiguration(String address, int port, String username, String password, Protocol protocol) {
        this.address = address;
        this.port = port;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
    }

    private ClientConfiguration(Builder builder) {
        this.address = builder.address;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.protocol = builder.protocol;
    }

    public static class Builder {
        private String address;
        private int port;
        private String username;
        private String password;
        private Protocol protocol;

        public Builder(){}

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public ClientConfiguration build() {
            return new ClientConfiguration(this);
        }
    }
}
