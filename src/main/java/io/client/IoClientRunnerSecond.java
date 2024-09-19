package io.client;

import java.io.IOException;
import java.util.Scanner;

public class IoClientRunnerSecond {

    public static void main(String[] args) throws IOException {

        IoClient secondClient = new IoClient("Second Client");
        secondClient.connect(
                8090, "localhost", new Scanner(System.in)
        );
    }
}
