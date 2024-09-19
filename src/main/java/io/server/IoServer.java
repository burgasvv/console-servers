package io.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IoServer {

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public void start(int port) throws IOException {

        try (
                ServerSocket serverSocket = new ServerSocket(port)
        ){
            while (serverSocket.isBound()) {

                Socket client = serverSocket.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(client.getInputStream())
                );

                System.out.println(
                        "Client: " + client.getInetAddress() + " :: "
                        + client.getPort() + " is CONNECTED"
                );

                executorService.submit(
                        () -> reader.lines().forEach(System.out::println)
                );
            }
        }
    }
}
