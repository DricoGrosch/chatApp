package model;

public class Client {
    private String name;
    private int privatePort;
    private int publicPort;
    private String host;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrivatePort() {
        return privatePort;
    }

    public void setPrivatePort(int privatePort) {
        this.privatePort = privatePort;
    }

    public int getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public Client(String name, int privatePort, int publicPort, String host) {
        this.name = name;
        this.privatePort = privatePort;
        this.publicPort = publicPort;
        this.host = host;
    }
}
