package model;

public class Client {
    private String name;
  private int port;
    private String host;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public Client(String name, int port, String host) {
        this.name = name;
        this.port = port;
        this.host = host;
    }
}
