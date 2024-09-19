package io.client;

import java.io.IOException;
import java.util.Scanner;

public class IoClientRunnerFirst {

    public static void main(String[] args) throws IOException {

        IoClient firstClient = new IoClient("First Client");
        firstClient.connect(
                8090, "localhost", new Scanner(System.in)
        );
    }
}
