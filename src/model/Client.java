package model;

import org.json.JSONObject;

public class Client {
    private String name;
    private int privatePort;
    private int publicPort;
    private String host;
    private String serverHost;
    private int serverPort;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

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

    public Client(String name, int privatePort, int publicPort, String host, String serverHost, int serverPort) {
        this.name = name;
        this.privatePort = privatePort;
        this.publicPort = publicPort;
        this.host = host;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("host", this.getHost());
        json.put("port", this.getPublicPort());
        return json;
    }
}
