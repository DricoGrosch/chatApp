package controller;

import model.Server;
import org.json.JSONObject;

import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ServerPingCountdown extends Thread {
    private JSONObject client;
    int countdownStarter = 5;

    public JSONObject getClient() {
        return client;
    }

    public ServerPingCountdown(JSONObject client) {
        this.client = client;
    }

    public void removeClient() {
        for (int i = 0; i < Server.clients.length(); i++) {
            if (Server.clients.getJSONObject(i) == this.client) {
                Server.clients.remove(i);
            }

        }
    }

    @Override
    public void run() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Runnable runnable = new Runnable() {
            public void run() {
                System.out.println(countdownStarter);
                countdownStarter--;
                if (countdownStarter < 0) {
                    System.out.println("Timer Over!");
                    scheduler.shutdown();
                    removeClient();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
    }

    public void resetTimer() {
        countdownStarter = 5;

    }
}
