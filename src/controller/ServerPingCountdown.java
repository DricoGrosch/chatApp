package controller;

import model.Server;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.ScheduledExecutorService;

public class ServerPingCountdown extends Thread {
    private static int countdown = 10;
    private static Timer timer;
    private ServerSocket listener;
    private JSONObject client;
    int countdownStarter = 5;

    public ServerPingCountdown(ServerSocket listener, JSONObject client) {
        this.listener = listener;
        this.client = client;
    }

    public void removeClient() {

    }

    @Override
    public void run() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Runnable runnable = new Runnable() {
            public void run() {
                new Thread(() -> {
                    try {
                        listener.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
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
}
