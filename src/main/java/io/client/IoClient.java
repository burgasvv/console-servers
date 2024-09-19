package io.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class IoClient {

    private String name;

    public IoClient(String name) {
        this.name = name;
    }

    public void connect(
            int port, String address, Scanner scanner
    ) throws IOException {

        try (
                Socket socket = new Socket(address, port)
        ){

            while (true) {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                );

                String message = name + ": " + scanner.nextLine();
                if (
                        (name + ": quit").equalsIgnoreCase(message)
                )
                    break;

                message += System.lineSeparator();

                writer.write(message);
                writer.flush();

                System.out.println(message);
            }
        }
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }
}
